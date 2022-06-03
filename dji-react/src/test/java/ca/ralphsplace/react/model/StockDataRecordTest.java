package ca.ralphsplace.react.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class StockDataRecordTest {

    final StockDataRecord sdr1 = new StockDataRecord("1","AA","1/14/2011","$16.71","$16.71","$15.64","$15.97","242963398","-4.42849","1.380223028","239655616","$16.19","$15.79","-2.47066","19","0.187852");
    final StockDataRecord sdr2 = new StockDataRecord("1","AA","2/14/2011","$16.71","$16.71","$15.64","$15.97","242963398","-4.42849","1.380223028","239655616","$16.19","$15.79","-2.47066","19","0.187852");
    final StockDataRecord sdr3 = new StockDataRecord("1","BB","2/14/2011","$16.71","$16.71","$15.64","$15.97","242963398","-4.42849","1.380223028","239655616","$16.19","$15.79","-2.47066","19","0.187852");


    @Test
    void compareTo() {
        Assertions.assertEquals(0, sdr1.compareTo(sdr1));
        Assertions.assertEquals(-1, sdr1.compareTo(sdr2));
        Assertions.assertEquals(-1, sdr1.compareTo(sdr3));
    }
}