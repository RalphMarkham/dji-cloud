package ca.ralphsplace.react.util;

import ca.ralphsplace.react.model.ClientStockData;
import ca.ralphsplace.react.model.StockDataRecord;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.stream.Collectors;

public final class StockDataUtil {

    private StockDataUtil(){}

    public static List<ClientStockData> csvToClientStockData(String cid, InputStream is) {
        return csvToClientStockData(cid, new InputStreamReader(is));
    }

    public static List<ClientStockData> csvToClientStockData(String cid, Reader reader) {

        return new CsvToBeanBuilder<StockDataRecord>(new BufferedReader(reader))
                .withType(StockDataRecord.class)
                .build()
                .stream()
                .map(a -> a.toClientStockData(cid))
                .collect(Collectors.toList());
    }
}
