package ca.ralphsplace.react;

import ca.ralphsplace.react.model.ClientStockData;
import ca.ralphsplace.react.model.StockDataRecord;
import ca.ralphsplace.react.util.StockDataUtil;
import java.io.StringReader;
import java.util.List;
import java.util.stream.Collectors;

public class UnitTestConstants {

  public static final String CLIENT_ID = "abc";

  public static final String CSV =
      "quarter,stock,date,open,high,low,close,volume,percent_change_price,percent_change_volume_over_last_wk,previous_weeks_volume,next_weeks_open,next_weeks_close,percent_change_next_weeks_price,days_to_next_dividend,percent_return_next_dividend\n"
          + "1,AA,1/7/2011,$15.82,$16.72,$15.78,$16.42,239655616,3.79267,,,$16.71,$15.97,-4.42849,26,0.182704\n"
          + "1,AA,1/21/2011,$16.19,$16.38,$15.60,$15.79,138428495,-2.47066,-43.02495926,242963398,$15.87,$16.13,1.63831,12,0.189994\n"
          + "1,AA,1/28/2011,$15.87,$16.63,$15.82,$16.13,151379173,1.63831,9.355500109,138428495,$16.18,$17.14,5.93325,5,0.185989\n"
          + "1,AA,2/4/2011,$16.18,$17.39,$16.18,$17.14,154387761,5.93325,1.987451735,151379173,$17.33,$17.37,0.230814,97,0.175029\n"
          + "1,AA,2/11/2011,$17.33,$17.48,$16.97,$17.37,114691279,0.230814,-25.71219489,154387761,$17.39,$17.28,-0.632547,90,0.172712\n"
          + "1,AA,2/18/2011,$17.39,$17.68,$17.28,$17.28,80023895,-0.632547,-30.22669579,114691279,$16.98,$16.68,-1.76678,83,0.173611\n"
          + "1,AA,2/25/2011,$16.98,$17.15,$15.96,$16.68,132981863,-1.76678,66.17769355,80023895,$16.81,$16.58,-1.36823,76,0.179856\n"
          + "1,AA,3/4/2011,$16.81,$16.94,$16.13,$16.58,109493077,-1.36823,-17.66315005,132981863,$16.58,$16.03,-3.31725,69,0.180941\n"
          + "1,AA,3/11/2011,$16.58,$16.75,$15.42,$16.03,114332562,-3.31725,4.419900447,109493077,$15.95,$16.11,1.00313,62,0.187149\n"
          + "1,AA,3/18/2011,$15.95,$16.33,$15.43,$16.11,130374108,1.00313,14.03060136,114332562,$16.38,$17.09,4.33455,55,0.18622\n"
          + "1,AA,3/25/2011,$16.38,$17.24,$16.26,$17.09,95550392,4.33455,-26.71060729,130374108,$17.13,$17.47,1.98482,48,0.175541\n"
          + "1,AXP,1/7/2011,$43.30,$45.60,$43.11,$44.36,45102042,2.44804,,,$44.20,$46.25,4.63801,89,0.405771\n"
          + "1,AXP,1/14/2011,$44.20,$46.25,$44.01,$46.25,25913713,4.63801,-42.54425775,45102042,$46.03,$46.00,-0.0651749,82,0.389189\n"
          + "1,AXP,1/21/2011,$46.03,$46.71,$44.71,$46.00,38824728,-0.0651749,49.82309945,25913713,$46.05,$43.86,-4.7557,75,0.391304\n"
          + "1,AXP,1/28/2011,$46.05,$46.27,$43.42,$43.86,51427274,-4.7557,32.4601012,38824728,$44.13,$43.82,-0.70247,68,0.410397\n"
          + "1,AXP,2/4/2011,$44.13,$44.23,$43.15,$43.82,39501680,-0.70247,-23.18924001,51427274,$43.96,$46.75,6.34668,61,0.410771\n"
          + "1,AXP,2/11/2011,$43.96,$46.79,$43.88,$46.75,43746998,6.34668,10.74718341,39501680,$46.42,$45.53,-1.91728,54,0.385027\n"
          + "1,AXP,2/18/2011,$46.42,$46.93,$45.53,$45.53,28564910,-1.91728,-34.70429674,43746998,$44.94,$43.53,-3.13752,47,0.395344\n"
          + "1,AXP,2/25/2011,$44.94,$45.12,$43.01,$43.53,39654146,-3.13752,38.82118305,28564910,$43.73,$43.72,-0.0228676,40,0.413508\n"
          + "1,AXP,3/4/2011,$43.73,$44.68,$42.75,$43.72,38985037,-0.0228676,-1.687362023,39654146,$43.86,$44.28,0.957592,33,0.411711\n"
          + "1,AXP,3/11/2011,$43.86,$45.54,$43.53,$44.28,37613429,0.957592,-3.518293442,38985037,$43.86,$44.17,0.706794,26,0.406504\n"
          + "1,AXP,3/18/2011,$43.86,$44.47,$42.19,$44.17,41757526,0.706794,11.01759959,37613429,$44.75,$45.59,1.87709,19,0.407516\n"
          + "1,AXP,3/25/2011,$44.75,$45.61,$44.10,$45.59,30798332,1.87709,-26.24483548,41757526,$45.54,$45.36,-0.395257,12,0.394823";

  public static final StockDataRecord EXPECTED_SDR =
      new StockDataRecord(
          "1",
          "AA",
          "1/14/2011",
          "$16.71",
          "$16.71",
          "$15.64",
          "$15.97",
          "242963398",
          "-4.42849",
          "1.380223028",
          "239655616",
          "$16.19",
          "$15.79",
          "-2.47066",
          "19",
          "0.187852");
  public static final ClientStockData EXPECTED_CSD = EXPECTED_SDR.toClientStockData(CLIENT_ID);

  public static final List<ClientStockData> EXPECTED_CSD_LIST =
      StockDataUtil.csvToClientStockData(CLIENT_ID, new StringReader(CSV));
  public static final List<StockDataRecord> EXPECTED_SDR_LIST =
      EXPECTED_CSD_LIST.stream()
          .map(ClientStockData::toStockDataRecord)
          .collect(Collectors.toList());
  public static final String JSON_STOCK_DATA =
      "{\"quarter\":\"1\",\"stock\":\"AA\",\"date\":\"1/14/2011\",\"open\":\"$16.71\",\"high\":\"$16.71\",\"low\":\"$15.64\",\"close\":\"$15.97\",\"volume\":\"242963398\",\"percentChangePrice\":\"-4.42849\",\"percentChangeVolumeOverLastWk\":\"1.380223028\",\"previousWeeksVolume\":\"239655616\",\"nextWeeksOpen\":\"$16.19\",\"nextWeeksClose\":\"$15.79\",\"percentChangeNextWeeksPrice\":\"-2.47066\",\"daysToNextDividend\":\"19\",\"percentReturnNextDividend\":\"0.187852\"}";
}
