package ca.ralphsplace.react;


import ca.ralphsplace.react.controller.StockDataController;
import ca.ralphsplace.react.model.StockDataRecord;
import ca.ralphsplace.react.service.StockDataService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA;

@ExtendWith(SpringExtension.class)
@WebFluxTest(StockDataController.class)
class WebLayerTest implements UnitTestConstants {
    @Autowired
    WebTestClient webClient;

    @MockBean
    private StockDataService stockDataService;

    @Test
    void getShouldReturnQueriedEmptyTradeData() {
        when(stockDataService.findByStock(clientId, "AA")).thenReturn(Flux.empty());
        webClient.get().uri("/api/stock-data/{stock}","AA")
                .header(ClientId.HEADER, clientId)
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void postShouldReturnSavedStockData() {
        when(stockDataService.save(expectedSdr.toClientStockData(clientId))).thenReturn(Mono.just(expectedSdr));
        webClient.post().uri("/api/stock-data/")
                .header(ClientId.HEADER, clientId)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .bodyValue(jsonStockData)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody(StockDataRecord.class).isEqualTo(expectedSdr);
    }

    @Test
    void getShouldReturnQueriedSingleTradeData() {
        when(stockDataService.findByStock(clientId, "AA")).thenReturn(Flux.just(expectedSdr));
        webClient.get().uri("/api/stock-data/{stock}","AA")
                .header(ClientId.HEADER, clientId)
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBodyList(StockDataRecord.class).isEqualTo(List.of(expectedSdr));
    }

    @Test
    void postShouldReturnBulkSavedTradeData() {
        when(stockDataService.bulkSave(anyList()))
                .thenReturn(Flux.fromIterable(expectedSdrList));

        MultipartBodyBuilder bodyBuilder = new MultipartBodyBuilder();
        bodyBuilder.part("file", csv.getBytes(StandardCharsets.UTF_8),MULTIPART_FORM_DATA);

        webClient.post().uri("/api/stock-data/bulk-insert/")
                .header(ClientId.HEADER, clientId)
                .accept(APPLICATION_JSON)
                .bodyValue(bodyBuilder.build())
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBodyList(StockDataRecord.class).isEqualTo(expectedSdrList);
    }

    @Test
    void testUnauthorized() {
        webClient.get().uri("/api/stock-data/{stock}","AA")
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isForbidden();
    }
}
