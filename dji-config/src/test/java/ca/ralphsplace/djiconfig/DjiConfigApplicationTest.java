package ca.ralphsplace.djiconfig;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


@SpringBootTest
class DjiConfigApplicationTest {
    @Test
    void contextLoads() throws Exception {

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8191/dji-stocks-data/dev"))
            .build();
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
            .thenApply(HttpResponse::body)
            .thenAccept(a -> Assertions.assertTrue(a.indexOf("\"spring.data.mongodb.host\":\"mongoDB\"") > -1))
            .join();
    }
}
