package ca.ralphsplace.react.config;

import com.mongodb.reactivestreams.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

@Configuration
public class MongoConfig {

  @Bean
  ReactiveMongoTemplate reactiveMongoTemplate() {
    return new ReactiveMongoTemplate(MongoClients.create(), "stockData");
  }
}
