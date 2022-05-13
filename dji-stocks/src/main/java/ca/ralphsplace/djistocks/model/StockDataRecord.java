package ca.ralphsplace.djistocks.model;

import com.opencsv.bean.CsvBindByName;
import org.springframework.data.mongodb.core.index.Indexed;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Objects;

public class StockDataRecord {

    @CsvBindByName(column = "quarter", required = true)
    private String quarter;
    @NotNull
    @Indexed
    @CsvBindByName(column = "stock", required = true)
    private String stock;
    @NotNull
    @Indexed
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


    public String getQuarter() {
        return quarter;
    }

    public void setQuarter(String quarter) {
        this.quarter = quarter;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getLow() {
        return low;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public String getClose() {
        return close;
    }

    public void setClose(String close) {
        this.close = close;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getPercentChangePrice() {
        return percentChangePrice;
    }

    public void setPercentChangePrice(String percentChangePrice) {
        this.percentChangePrice = percentChangePrice;
    }

    public String getPercentChangeVolumeOverLastWk() {
        return percentChangeVolumeOverLastWk;
    }

    public void setPercentChangeVolumeOverLastWk(String percentChangeVolumeOverLastWk) {
        this.percentChangeVolumeOverLastWk = percentChangeVolumeOverLastWk;
    }

    public String getPreviousWeeksVolume() {
        return previousWeeksVolume;
    }

    public void setPreviousWeeksVolume(String previousWeeksVolume) {
        this.previousWeeksVolume = previousWeeksVolume;
    }

    public String getNextWeeksOpen() {
        return nextWeeksOpen;
    }

    public void setNextWeeksOpen(String nextWeeksOpen) {
        this.nextWeeksOpen = nextWeeksOpen;
    }

    public String getNextWeeksClose() {
        return nextWeeksClose;
    }

    public void setNextWeeksClose(String nextWeeksClose) {
        this.nextWeeksClose = nextWeeksClose;
    }

    public String getPercentChangeNextWeeksPrice() {
        return percentChangeNextWeeksPrice;
    }

    public void setPercentChangeNextWeeksPrice(String percentChangeNextWeeksPrice) {
        this.percentChangeNextWeeksPrice = percentChangeNextWeeksPrice;
    }

    public String getDaysToNextDividend() {
        return daysToNextDividend;
    }

    public void setDaysToNextDividend(String daysToNextDividend) {
        this.daysToNextDividend = daysToNextDividend;
    }

    public String getPercentReturnNextDividend() {
        return percentReturnNextDividend;
    }

    public void setPercentReturnNextDividend(String percentReturnNextDividend) {
        this.percentReturnNextDividend = percentReturnNextDividend;
    }

    public ClientStockData toClientStockData(String clientId) {
        ClientStockData ctd = new ClientStockData();
        ctd.setId(clientId+this.getStock()+this.getDate());
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
        int day = Integer.parseInt(str.substring(indx1+1, indx2));
        int month = Integer.parseInt(str.substring(0, indx1));
        int year = Integer.parseInt(str.substring(indx2+1));

        return LocalDate.of(year, month, day);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StockDataRecord that = (StockDataRecord) o;
        return stock.equals(that.stock) && date.equals(that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stock, date);
    }

    @Override
    public String toString() {
        return "TradeDataRecord{" +
                ", quarter='" + quarter + '\'' +
                ", stock='" + stock + '\'' +
                ", date='" + date + '\'' +
                ", open='" + open + '\'' +
                ", high='" + high + '\'' +
                ", low='" + low + '\'' +
                ", close='" + close + '\'' +
                ", volume='" + volume + '\'' +
                ", percentChangePrice='" + percentChangePrice + '\'' +
                ", percentChangeVolumeOverLastWk='" + percentChangeVolumeOverLastWk + '\'' +
                ", previousWeeksVolume='" + previousWeeksVolume + '\'' +
                ", nextWeeksOpen='" + nextWeeksOpen + '\'' +
                ", nextWeeksClose='" + nextWeeksClose + '\'' +
                ", percentChangeNextWeeksPrice='" + percentChangeNextWeeksPrice + '\'' +
                ", daysToNextDividend='" + daysToNextDividend + '\'' +
                ", percentReturnNextDividend='" + percentReturnNextDividend + '\'' +
                '}';
    }
}
