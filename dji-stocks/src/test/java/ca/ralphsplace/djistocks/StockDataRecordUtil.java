package ca.ralphsplace.djistocks;

import ca.ralphsplace.djistocks.model.StockDataRecord;

public class StockDataRecordUtil {

  public static StockDataRecord buildTradeDataRecord(
      String quarter,
      String stock,
      String date,
      String open,
      String high,
      String low,
      String close,
      String volume,
      String percentChangePrice,
      String percentChangeVolumeOverLastWk,
      String previousWeeksVolume,
      String nextWeeksOpen,
      String nextWeeksClose,
      String percentChangeNextWeeksPrice,
      String daysToNextDividend,
      String percentReturnNextDividend) {
    StockDataRecord dataRecord = new StockDataRecord();
    dataRecord.setQuarter(quarter);
    dataRecord.setStock(stock);
    dataRecord.setDate(date);
    dataRecord.setOpen(open);
    dataRecord.setHigh(high);
    dataRecord.setLow(low);
    dataRecord.setClose(close);
    dataRecord.setVolume(volume);
    dataRecord.setPercentChangePrice(percentChangePrice);
    dataRecord.setPercentChangeVolumeOverLastWk(percentChangeVolumeOverLastWk);
    dataRecord.setPreviousWeeksVolume(previousWeeksVolume);
    dataRecord.setNextWeeksOpen(nextWeeksOpen);
    dataRecord.setNextWeeksClose(nextWeeksClose);
    dataRecord.setPercentChangeNextWeeksPrice(percentChangeNextWeeksPrice);
    dataRecord.setDaysToNextDividend(daysToNextDividend);
    dataRecord.setPercentReturnNextDividend(percentReturnNextDividend);
    return dataRecord;
  }
}
