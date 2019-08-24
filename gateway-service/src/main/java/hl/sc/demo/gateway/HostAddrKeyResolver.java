package hl.sc.demo.gateway;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * @author: hl
 * @created: 2019-08-24 15:43
 **/
public class HostAddrKeyResolver implements KeyResolver {
    /**
     * 指定根据什么限流
     * 这里根据HostAddress限流
     *
     * @param exchange
     * @return
     */
    @Override
    public Mono<String> resolve(ServerWebExchange exchange) {
        return Mono.just(Objects.requireNonNull(exchange.getRequest().getRemoteAddress()).getAddress().getHostAddress());
    }
}
