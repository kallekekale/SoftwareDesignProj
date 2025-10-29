package fi.tuni.softwaredesign.http;

import java.util.Map;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * RestTemplate-based HTTP client service for making external API calls. Uses Spring's RestTemplate
 * for synchronous HTTP operations. This class is final and cannot be extended.
 */
@Service
public final class HttpRequesterService implements HttpRequester {

  /** The RestTemplate used for HTTP operations. */
  private final RestTemplate restTemplate;

  /**
   * Constructs an HttpRequesterService with the given RestTemplate.
   *
   * @param template The RestTemplate to use for HTTP operations
   */
  public HttpRequesterService(final RestTemplate template) {
    this.restTemplate = template;
  }

  @Override
  public <T> T get(
      final String url, final Class<T> responseType, final Map<String, String> headers) {
    HttpHeaders httpHeaders = createHeaders(headers);
    HttpEntity<?> entity = new HttpEntity<>(httpHeaders);
    ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.GET, entity, responseType);
    return response.getBody();
  }

  @Override
  public <T> T get(final String url, final Class<T> responseType) {
    return restTemplate.getForObject(url, responseType);
  }

  @Override
  public <T> T post(
      final String url,
      final Object body,
      final Class<T> responseType,
      final Map<String, String> headers) {
    HttpHeaders httpHeaders = createHeaders(headers);
    HttpEntity<?> entity = new HttpEntity<>(body, httpHeaders);
    ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.POST, entity, responseType);
    return response.getBody();
  }

  @Override
  public <T> T post(final String url, final Object body, final Class<T> responseType) {
    return restTemplate.postForObject(url, body, responseType);
  }

  @Override
  public <T> T put(
      final String url,
      final Object body,
      final Class<T> responseType,
      final Map<String, String> headers) {
    HttpHeaders httpHeaders = createHeaders(headers);
    HttpEntity<?> entity = new HttpEntity<>(body, httpHeaders);
    ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.PUT, entity, responseType);
    return response.getBody();
  }

  @Override
  public <T> T put(final String url, final Object body, final Class<T> responseType) {
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<?> entity = new HttpEntity<>(body, httpHeaders);
    ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.PUT, entity, responseType);
    return response.getBody();
  }

  @Override
  public <T> T patch(
      final String url,
      final Object body,
      final Class<T> responseType,
      final Map<String, String> headers) {
    HttpHeaders httpHeaders = createHeaders(headers);
    HttpEntity<?> entity = new HttpEntity<>(body, httpHeaders);
    ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.PATCH, entity, responseType);
    return response.getBody();
  }

  @Override
  public <T> T patch(final String url, final Object body, final Class<T> responseType) {
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<?> entity = new HttpEntity<>(body, httpHeaders);
    ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.PATCH, entity, responseType);
    return response.getBody();
  }

  @Override
  public <T> T delete(
      final String url, final Class<T> responseType, final Map<String, String> headers) {
    HttpHeaders httpHeaders = createHeaders(headers);
    HttpEntity<?> entity = new HttpEntity<>(httpHeaders);
    ResponseEntity<T> response =
        restTemplate.exchange(url, HttpMethod.DELETE, entity, responseType);
    return response.getBody();
  }

  @Override
  public <T> T delete(final String url, final Class<T> responseType) {
    HttpHeaders httpHeaders = new HttpHeaders();
    HttpEntity<?> entity = new HttpEntity<>(httpHeaders);
    ResponseEntity<T> response =
        restTemplate.exchange(url, HttpMethod.DELETE, entity, responseType);
    return response.getBody();
  }

  @Override
  public void delete(final String url, final Map<String, String> headers) {
    HttpHeaders httpHeaders = createHeaders(headers);
    HttpEntity<?> entity = new HttpEntity<>(httpHeaders);
    restTemplate.exchange(url, HttpMethod.DELETE, entity, Void.class);
  }

  @Override
  public void delete(final String url) {
    restTemplate.delete(url);
  }

  /**
   * Creates HTTP headers with the given custom headers. Sets content type to JSON by default.
   *
   * @param headers Optional map of custom headers
   * @return HttpHeaders object with the configured headers
   */
  private HttpHeaders createHeaders(final Map<String, String> headers) {
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
    if (headers != null) {
      headers.forEach(httpHeaders::set);
    }
    return httpHeaders;
  }
}
