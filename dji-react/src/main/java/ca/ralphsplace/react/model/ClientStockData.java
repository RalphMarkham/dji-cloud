package ca.ralphsplace.react.model;

import com.opencsv.bean.CsvToBeanBuilder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@Document
public class ClientStockData implements Comparable<ClientStockData>, Comparator<ClientStockData>, Serializable {

    public static final String WEEKLY_STOCK_DATA = "weeklyStockData";

    @Id
    private String id;
    private String clientId;
    private LocalDate localDate;
    private String quarter;
    private String stock;
    private String date;
    private String open;
    private String high;
    private String low;
    private String close;
    private String volume;
    private String percentChangePrice;
    private String percentChangeVolumeOverLastWk;
    private String previousWeeksVolume;
    private String nextWeeksOpen;
    private String nextWeeksClose;
    private String percentChangeNextWeeksPrice;
    private String daysToNextDividend;
    private String percentReturnNextDividend;

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

    public StockDataRecord toStockDataRecord() {
        var trd = new StockDataRecord();

        trd.setQuarter(quarter);
        trd.setStock(stock);
        trd.setDate(date);
        trd.setOpen(open);
        trd.setHigh(high);
        trd.setLow(low);
        trd.setClose(close);
        trd.setVolume(volume);
        trd.setPercentChangePrice(percentChangePrice);
        trd.setPercentChangeVolumeOverLastWk(percentChangeVolumeOverLastWk);
        trd.setPreviousWeeksVolume(previousWeeksVolume);
        trd.setNextWeeksOpen(nextWeeksOpen);
        trd.setNextWeeksClose(nextWeeksClose);
        trd.setPercentChangeNextWeeksPrice(percentChangeNextWeeksPrice);
        trd.setDaysToNextDividend(daysToNextDividend);
        trd.setPercentReturnNextDividend(percentReturnNextDividend);

        return trd;
    }

    @Override
    public int compareTo(ClientStockData o) {
        return compare(this, o);
    }

    @Override
    public int compare(ClientStockData o1, ClientStockData o2) {
        int clientIdCompare = CharSequence.compare(o1.clientId, o2.clientId);
        int stockCompare = CharSequence.compare(o1.stock, o2.stock);
        int dateCompare = CharSequence.compare(o1.date, o2.date);

        int stage1 = clientIdCompare != 0 ? clientIdCompare : stockCompare;

        return stage1 != 0 ? stage1 : dateCompare; // stage 2
    }
}
