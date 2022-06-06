package ca.ralphsplace.react.repository;

import ca.ralphsplace.react.ClientId;
import ca.ralphsplace.react.model.ClientStockData;
import java.util.List;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class StockDataRepo {

  private final ReactiveMongoTemplate mongoTemplate;

  public StockDataRepo(ReactiveMongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  @Transactional
  public Mono<ClientStockData> save(final ClientStockData ctd) {
    return mongoTemplate.save(ctd, ClientStockData.WEEKLY_STOCK_DATA);
  }

  @Transactional
  public Flux<ClientStockData> bulkSave(final List<ClientStockData> ctdList) {
    return mongoTemplate.insert(ctdList, ClientStockData.WEEKLY_STOCK_DATA);
  }

  @Transactional
  public Flux<ClientStockData> findByStock(final String clientId, final String stock) {
    var query = new Query();
    query.addCriteria(
        Criteria.where(ClientId.ID).is(clientId).andOperator(Criteria.where("stock").is(stock)));

    return mongoTemplate.find(query, ClientStockData.class, ClientStockData.WEEKLY_STOCK_DATA);
  }
}
