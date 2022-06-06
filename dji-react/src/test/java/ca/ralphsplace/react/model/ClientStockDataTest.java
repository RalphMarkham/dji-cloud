package ca.ralphsplace.react.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ClientStockDataTest {

  final ClientStockData csd1 =
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
              "0.187852")
          .toClientStockData("abc");
  final ClientStockData csd2 =
      new StockDataRecord(
              "1",
              "AA",
              "2/14/2011",
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
              "0.187852")
          .toClientStockData("abc");
  final ClientStockData csd3 =
      new StockDataRecord(
              "1",
              "BB",
              "2/14/2011",
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
              "0.187852")
          .toClientStockData("abc");
  final ClientStockData csd4 =
      new StockDataRecord(
              "1",
              "BB",
              "2/14/2011",
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
              "0.187852")
          .toClientStockData("bbc");

  @Test
  void compareTo() {
    Assertions.assertEquals(0, csd1.compareTo(csd1));
    Assertions.assertEquals(-1, csd1.compareTo(csd2));
    Assertions.assertEquals(-1, csd1.compareTo(csd3));
    Assertions.assertEquals(-1, csd1.compareTo(csd4));
  }
}
