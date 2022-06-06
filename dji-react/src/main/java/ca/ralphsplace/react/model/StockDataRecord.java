package ca.ralphsplace.react.model;

import com.opencsv.bean.CsvBindByName;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Comparator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockDataRecord
    implements Comparable<StockDataRecord>, Comparator<StockDataRecord>, Serializable {

  @CsvBindByName(column = "quarter", required = true)
  private String quarter;

  @CsvBindByName(column = "stock", required = true)
  private String stock;

  @CsvBindByName(column = "date", required = true)
  private String date;

  @CsvBindByName(column = "open")
  private String open;

  @CsvBindByName(column = "high")
  private String high;

  @CsvBindByName(column = "low")
  private String low;

  @CsvBindByName(column = "close")
  private String close;

  @CsvBindByName(column = "volume")
  private String volume;

  @CsvBindByName(column = "percent_change_price")
  private String percentChangePrice;

  @CsvBindByName(column = "percent_change_volume_over_last_wk")
  private String percentChangeVolumeOverLastWk;

  @CsvBindByName(column = "previous_weeks_volume")
  private String previousWeeksVolume;

  @CsvBindByName(column = "next_weeks_open")
  private String nextWeeksOpen;

  @CsvBindByName(column = "next_weeks_close")
  private String nextWeeksClose;

  @CsvBindByName(column = "percent_change_next_weeks_price")
  private String percentChangeNextWeeksPrice;

  @CsvBindByName(column = "days_to_next_dividend")
  private String daysToNextDividend;

  @CsvBindByName(column = "percent_return_next_dividend")
  private String percentReturnNextDividend;

  public ClientStockData toClientStockData(String clientId) {
    var ctd = new ClientStockData();
    ctd.setId(clientId + this.getStock() + this.getDate());
    ctd.setClientId(clientId);
    ctd.setLocalDate(str2LocalDate(this.getDate()));
    ctd.setQuarter(this.getQuarter());
    ctd.setStock(this.getStock());
    ctd.setDate(this.getDate());
    ctd.setOpen(this.getOpen());
    ctd.setHigh(this.getHigh());
    ctd.setLow(this.getLow());
    ctd.setClose(this.getClose());
    ctd.setVolume(this.getVolume());
    ctd.setPercentChangePrice(this.getPercentChangePrice());
    ctd.setPercentChangeVolumeOverLastWk(this.getPercentChangeVolumeOverLastWk());
    ctd.setPreviousWeeksVolume(this.getPreviousWeeksVolume());
    ctd.setNextWeeksOpen(this.getNextWeeksOpen());
    ctd.setNextWeeksClose(this.getNextWeeksClose());
    ctd.setPercentChangeNextWeeksPrice(this.getPercentChangeNextWeeksPrice());
    ctd.setDaysToNextDividend(this.getDaysToNextDividend());
    ctd.setPercentReturnNextDividend(this.getPercentReturnNextDividend());

    return ctd;
  }

  private LocalDate str2LocalDate(String str) {
    int indx1 = str.indexOf('/');
    int indx2 = str.lastIndexOf('/');
    int day = Integer.parseInt(str.substring(indx1 + 1, indx2));
    int month = Integer.parseInt(str.substring(0, indx1));
    int year = Integer.parseInt(str.substring(indx2 + 1));

    return LocalDate.of(year, month, day);
  }

  @Override
  public int compareTo(StockDataRecord o) {
    return compare(this, o);
  }

  @Override
  public int compare(StockDataRecord o1, StockDataRecord o2) {
    int stockCompare = CharSequence.compare(o1.stock, o2.stock);
    int dateCompare = CharSequence.compare(o1.date, o2.date);

    return stockCompare != 0 ? stockCompare : dateCompare;
  }
}
