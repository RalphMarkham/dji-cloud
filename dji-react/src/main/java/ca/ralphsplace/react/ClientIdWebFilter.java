package ca.ralphsplace.react;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
public class ClientIdWebFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange serverWebExchange, WebFilterChain webFilterChain) {
        var clientId = serverWebExchange.getRequest().getHeaders().getFirst(ClientId.HEADER);
        if (clientId != null && clientId.trim().length() > 0) {
            return webFilterChain.filter(serverWebExchange);
        } else {
            serverWebExchange.getResponse().setRawStatusCode(HttpStatus.FORBIDDEN.value());
            return Mono.empty();
        }
    }
}
