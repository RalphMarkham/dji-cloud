package ca.ralphsplace.react.controller;

import ca.ralphsplace.react.ClientId;
import ca.ralphsplace.react.model.ClientStockData;
import ca.ralphsplace.react.model.StockDataRecord;
import ca.ralphsplace.react.service.StockDataService;
import ca.ralphsplace.react.util.InputStreamCollector;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.Part;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@RestController
@RequestMapping("/api/stock-data")
@Slf4j
public class StockDataController {

    private final StockDataService stockDataService;

    public StockDataController(StockDataService stockDataService) {
        this.stockDataService = stockDataService;
    }

    @Operation(summary = "Get weekly price data for a stock")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found stock data",
                    content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema( schema = @Schema(implementation = StockDataRecord.class))) }),
            @ApiResponse(responseCode = "400", description = "Invalid stock supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "NOT FOUND", content = @Content),
            @ApiResponse(responseCode = "403", description = "FORBIDDEN")})
    @GetMapping(value = "/{stock}",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            headers = {ClientId.HEADER})
    public Mono<ResponseEntity<Flux<StockDataRecord>>> getTradeDataTicker(@RequestHeader(ClientId.HEADER) final String clientId,
                                                                          @PathVariable final String stock) {

        var fluxData = stockDataService.findByStock(clientId, stock);
        var monoHasElements = fluxData.hasElements();

        return monoHasElements.flatMap(b -> Boolean.TRUE.equals(b)
                ? Mono.just(ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(fluxData))
                    .doOnError(e -> ResponseEntity.status(HttpStatus.BAD_REQUEST).build())
                : Mono.just(ResponseEntity.notFound().build()));
    }

    @Operation(summary = "Create weekly price data for a stock")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Stock data created",
                    content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = StockDataRecord.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid stock data supplied", content = @Content),
            @ApiResponse(responseCode = "403", description = "FORBIDDEN")})
    @PostMapping(value = "/",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE},
            headers = {ClientId.HEADER})
    public Mono<ResponseEntity<Mono<StockDataRecord>>> createTradeData(@RequestHeader(ClientId.HEADER) final String clientId,
                                                                        @RequestBody final StockDataRecord stockDataRecord) {

        return Mono.just(ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(stockDataService.save(stockDataRecord.toClientStockData(clientId))))
            .doOnError(e -> ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }

    @Operation(summary = "CSV file upload, for bulk creation of weekly stock data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Stock data created",
                    content = { @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
                            schema = @Schema(implementation = String.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid stock data supplied", content = @Content),
            @ApiResponse(responseCode = "403", description = "FORBIDDEN")})
    @PostMapping(value = "/bulk-insert",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE},
            headers = {ClientId.HEADER})
    public Mono<ResponseEntity<Flux<StockDataRecord>>> bulkUpdate(@RequestHeader(ClientId.HEADER) final String clientId,
                                                                  @RequestPart("file") final Part file) {

        return file.content()
            .subscribeOn(Schedulers.boundedElastic())
            .collect(InputStreamCollector::new, (s, db) -> s.collect(db.asInputStream(true)))
            .map( is -> ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(stockDataService.bulkSave(ClientStockData.csvToClientStockData(clientId, is.getInputStream()))))
            .doOnError(e -> ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }
}
