package ca.ralphsplace.react.service;

import ca.ralphsplace.react.model.ClientStockData;
import ca.ralphsplace.react.model.StockDataRecord;
import ca.ralphsplace.react.repository.StockDataRepo;
import java.util.List;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class StockDataService {

  private final StockDataRepo stockDataRepo;

  public StockDataService(StockDataRepo stockDataRepo) {
    this.stockDataRepo = stockDataRepo;
  }

  public Mono<StockDataRecord> save(final ClientStockData ctd) {
    return stockDataRepo.save(ctd).log().map(ClientStockData::toStockDataRecord);
  }

  public Flux<StockDataRecord> findByStock(final String clientId, final String stock) {
    return stockDataRepo.findByStock(clientId, stock).log().map(ClientStockData::toStockDataRecord);
  }

  public Flux<StockDataRecord> bulkSave(final List<ClientStockData> list) {
    return stockDataRepo.bulkSave(list).log().map(ClientStockData::toStockDataRecord);
  }
}
