package fi.tuni.softwaredesign.http;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

/**
 * Configuration class for HTTP requester beans.
 * Provides configured RestTemplate bean for dependency injection.
 */
@Configuration
public class HttpRequesterConfig {
    
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
            .requestFactory(() -> {
                var factory = new org.springframework.http.client.SimpleClientHttpRequestFactory();
                factory.setConnectTimeout((int) Duration.ofSeconds(10).toMillis());
                factory.setReadTimeout((int) Duration.ofSeconds(30).toMillis());
                return factory;
            })
            .build();
    }
}
