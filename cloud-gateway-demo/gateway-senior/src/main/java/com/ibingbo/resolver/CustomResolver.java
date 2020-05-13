package com.ibingbo.resolver;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class CustomResolver {
    @Bean
    KeyResolver userKeyResolver() {
//        return exchange -> Mono.just(exchange.getRequest().getQueryParams().getFirst("user")); // 根据user参数限流
        return exchange -> Mono.just("");
//        return exchange -> Mono.just(exchange.getRequest().getRemoteAddress().getHostName()); //根据IP来限流

    }
}
