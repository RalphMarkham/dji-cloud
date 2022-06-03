package ca.ralphsplace.react;

import ca.ralphsplace.react.controller.StockDataController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class WebFluxApplicationTests {

	@Autowired
	StockDataController stockDataController;

	@Test
	void contextLoads() {
		assertThat(stockDataController).isNotNull();
	}

}
