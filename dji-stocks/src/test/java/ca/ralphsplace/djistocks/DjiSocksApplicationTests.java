package ca.ralphsplace.djistocks;

import static org.assertj.core.api.Assertions.assertThat;

import ca.ralphsplace.djistocks.controller.StockDataController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(
    properties = {
      "spring.data.mongodb.host=localhost",
      "spring.data.mongodb.port=1234",
      "spring.data.mongodb.database=db"
    })
class DjiSocksApplicationTests {

  @Autowired StockDataController controller;

  @Test
  void contextLoads() {
    assertThat(controller).isNotNull();
  }
}
