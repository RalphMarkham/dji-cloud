package ca.ralphsplace.djiconfig;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class DjiConfigApplicationTest {

  @Test
  void contextLoads(@Autowired TestRestTemplate restTemplate) {
    String body = restTemplate.getForObject("/dji-stocks-data/dev", String.class);
    Assertions.assertTrue(body.contains("\"spring.data.mongodb.host\":\"mongoDB\""));
  }
}
