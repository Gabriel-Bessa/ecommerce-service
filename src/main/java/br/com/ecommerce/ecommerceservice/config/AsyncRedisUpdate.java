package br.com.ecommerce.ecommerceservice.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;

@Slf4j
@EnableAsync
@Configuration
public class AsyncRedisUpdate {

    @Async
    public void updateRedisCache() {
        log.info("[AsyncRedisUpdate::updateRedisCache] Async method to update redis cache");
    }
}
