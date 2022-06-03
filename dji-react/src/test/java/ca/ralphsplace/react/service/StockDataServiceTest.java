package ca.ralphsplace.react.service;

import ca.ralphsplace.react.UnitTestConstants;
import ca.ralphsplace.react.model.StockDataRecord;
import ca.ralphsplace.react.repository.StockDataRepo;
import com.mongodb.reactivestreams.client.MongoClients;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.ImmutableMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfig;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Publisher;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import reactor.test.StepVerifier;

class StockDataServiceTest implements UnitTestConstants {

    private static final String CONNECTION_STRING = "mongodb://%s:%d";

    private MongodExecutable mongodExecutable;
    private ReactiveMongoTemplate mongoTemplate;
    private StockDataRepo stockDataRepo;
    private StockDataService stockDataService;

    @AfterEach
    void clean() {
        mongodExecutable.stop();
    }

    @BeforeEach
    void setup() throws Exception {
        String ip = "localhost";
        int port = 27017;

        ImmutableMongodConfig mongodConfig = MongodConfig
                .builder()
                .version(Version.Main.PRODUCTION)
                .net(new Net(ip, port, Network.localhostIsIPv6()))
                .build();

        MongodStarter starter = MongodStarter.getDefaultInstance();
        mongodExecutable = starter.prepare(mongodConfig);
        mongodExecutable.start();
        mongoTemplate = new ReactiveMongoTemplate(MongoClients.create(String.format(CONNECTION_STRING, ip, port)), "test");
        stockDataRepo = new StockDataRepo(mongoTemplate);
        stockDataService = new StockDataService(stockDataRepo);
    }

    @Test
    void save() {
        Publisher<StockDataRecord> setup = stockDataService.save(expectedCsd);
        StepVerifier
                .create(setup)
                .expectNext(expectedSdr)
                .verifyComplete();
    }

    @Test
    void findByStockEmpty() {
        Publisher<StockDataRecord> setup = stockDataService.findByStock(clientId, "AA");
        StepVerifier
                .create(setup)
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    void findByStockWithData() {
        save();
        Publisher<StockDataRecord> setup = stockDataService.findByStock(clientId, "AA");
        StepVerifier
                .create(setup)
                .expectNext(expectedSdr)
                .verifyComplete();
    }

    @Test
    void bulkSave() {
        Publisher<StockDataRecord> setup = stockDataService.bulkSave(expectedCsdList);
        StepVerifier
                .create(setup)
                .expectNextSequence(expectedSdrList)
                .verifyComplete();
    }
}