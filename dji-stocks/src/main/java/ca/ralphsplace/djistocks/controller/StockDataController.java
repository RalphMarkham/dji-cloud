package ca.ralphsplace.djistocks.controller;

import ca.ralphsplace.djistocks.model.ClientStockData;
import ca.ralphsplace.djistocks.model.StockDataRecord;
import ca.ralphsplace.djistocks.service.StockDataService;
import com.opencsv.bean.CsvToBeanBuilder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/stock-data")
@Validated
public class StockDataController {

    private static final String ERROR_MSG = "error caught exception: ";
    private static final Logger LOG = LoggerFactory.getLogger(StockDataController.class);

    private final StockDataService stockDataService;

    public StockDataController(StockDataService stockDataService) {
        this.stockDataService = stockDataService;
    }

    @Operation(summary = "Get weekly trade data for a stock")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found trade data",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema( schema = @Schema(implementation = StockDataRecord.class))) }),
            @ApiResponse(responseCode = "400", description = "Invalid stock supplied", content = @Content),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED")})
    @GetMapping(value = "/{stock}", produces = {"application/json"}, headers = {"X-Client_Id"})
    @Async("controllerAsyncExecutor")
    public CompletableFuture<ResponseEntity<Collection<StockDataRecord>>> getTradeDataTicker(@Valid @RequestHeader(name="X-Client_Id") final String clientId, @PathVariable final String stock) {
        return stockDataService.findByStock(clientId, stock)
                .thenApply(ResponseEntity::ok)
                .exceptionally(t -> {
                    LOG.error(ERROR_MSG, t);
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                });
    }

    @Operation(summary = "Create weekly trade data for a stock")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Trade data created",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StockDataRecord.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid trade data supplied", content = @Content),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED")})
    @PostMapping(value = "/", consumes = {"application/json"}, produces = {"application/json"}, headers = {"X-Client_Id"})
    @Async("controllerAsyncExecutor")
    public CompletableFuture<ResponseEntity<StockDataRecord>> createTradeData(@Valid @RequestHeader("X-Client_Id") final String clientId, @RequestBody final StockDataRecord stockDataRecord) {
        return stockDataService.save(stockDataRecord.toClientStockData(clientId))
                .thenApply(r -> ResponseEntity.status(HttpStatus.CREATED).body(r))
                .exceptionally(t -> {
                    LOG.error(ERROR_MSG, t);
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                });
    }

    @Operation(summary = "CSV file upload, for bulk creation of weekly trade data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Trade data created",
                    content = { @Content(mediaType = "text/plain",
                            schema = @Schema(implementation = String.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid trade data supplied", content = @Content),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED")})
    @PostMapping(value = "/bulk-insert", consumes = {"multipart/form-data"}, produces = {"application/json"}, headers = {"X-Client_Id"})
    @Async("controllerAsyncExecutor")
    public CompletableFuture<ResponseEntity<Collection<StockDataRecord>>> bulkUpdate(@Valid @RequestHeader("X-Client_Id") final String clientId, @RequestParam final MultipartFile file) {
        CompletableFuture<List<ClientStockData>> cfRecords = CompletableFuture.supplyAsync(() -> {
                    try {
                        return new CsvToBeanBuilder<StockDataRecord>(
                                new BufferedReader(new InputStreamReader(file.getInputStream())))
                                        .withType(StockDataRecord.class)
                                        .build()
                                        .stream()
                                        .map(tdr -> tdr.toClientStockData(clientId))
                                        .collect(Collectors.toList());
                    } catch (IOException e) {
                        throw new CompletionException(e);
                    }
                });

        try {
            return stockDataService.bulkSave(cfRecords.join())
                    .thenApply(r -> ResponseEntity.status(HttpStatus.CREATED).body(r))
                    .exceptionally(t -> {
                        LOG.error(ERROR_MSG, t);
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                    });
        } catch (CompletionException e) {
            LOG.error(ERROR_MSG, e);
            return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
        }
    }
}
