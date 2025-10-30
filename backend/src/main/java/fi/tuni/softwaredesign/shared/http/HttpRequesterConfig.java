package fi.tuni.softwaredesign.shared.http;

import java.time.Duration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * Configuration class for HTTP requester beans. Provides configured RestTemplate bean for
 * dependency injection.
 */
@Configuration
public class HttpRequesterConfig {

  /** Connection timeout in seconds. */
  private static final int CONNECT_TIMEOUT_SECONDS = 10;

  /** Read timeout in seconds. */
  private static final int READ_TIMEOUT_SECONDS = 30;

  /**
   * Creates and configures a RestTemplate bean. Sets connection and read timeouts for HTTP
   * requests.
   *
   * @param builder The RestTemplateBuilder provided by Spring Boot
   * @return Configured RestTemplate instance
   */
  @Bean
  public RestTemplate restTemplate(final RestTemplateBuilder builder) {
    var factory = new SimpleClientHttpRequestFactory();
    factory.setConnectTimeout((int) Duration.ofSeconds(CONNECT_TIMEOUT_SECONDS).toMillis());
    factory.setReadTimeout((int) Duration.ofSeconds(READ_TIMEOUT_SECONDS).toMillis());
    return builder.requestFactory(() -> factory).build();
  }
}
