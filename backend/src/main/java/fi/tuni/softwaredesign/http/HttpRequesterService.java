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

  /**
   * Performs an HTTP GET request and returns the response body.
   *
   * <p>Note: If the server returns an empty response body, this method will return {@code null}.
   *
   * @param <T> The type of the response
   * @param url the URL to send the GET request to
   * @param responseType the expected response type
   * @param headers additional headers to include in the request
   * @return the response body, or {@code null} if the response is empty
   */
  @Override
  public <T> T get(
      final String url, final Class<T> responseType, final Map<String, String> headers) {
    HttpHeaders httpHeaders = createHeaders(headers, false);
    HttpEntity<?> entity = new HttpEntity<>(httpHeaders);
    ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.GET, entity, responseType);
    return response.getBody();
  }

  /**
   * Performs an HTTP GET request and returns the response body.
   *
   * <p>Note: If the server returns an empty response body, this method will return {@code null}.
   *
   * @param <T> The type of the response
   * @param url the URL to send the GET request to
   * @param responseType the expected response type
   * @return the response body, or {@code null} if the response is empty
   */
  @Override
  public <T> T get(final String url, final Class<T> responseType) {
    return restTemplate.getForObject(url, responseType);
  }

  /**
   * Performs an HTTP POST request and returns the response body.
   *
   * <p>Note: If the server returns an empty response body, this method will return {@code null}.
   *
   * @param <T> The type of the response
   * @param url the URL to send the POST request to
   * @param body the request body
   * @param responseType the expected response type
   * @param headers additional headers to include in the request
   * @return the response body, or {@code null} if the response is empty
   */
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

  /**
   * Performs an HTTP POST request and returns the response body.
   *
   * <p>Note: If the server returns an empty response body, this method will return {@code null}.
   *
   * @param <T> The type of the response
   * @param url the URL to send the POST request to
   * @param body the request body
   * @param responseType the expected response type
   * @return the response body, or {@code null} if the response is empty
   */
  @Override
  public <T> T post(final String url, final Object body, final Class<T> responseType) {
    return restTemplate.postForObject(url, body, responseType);
  }

  /**
   * Performs an HTTP PUT request and returns the response body.
   *
   * <p>Note: If the server returns an empty response body, this method will return {@code null}.
   *
   * @param <T> The type of the response
   * @param url the URL to send the PUT request to
   * @param body the request body
   * @param responseType the expected response type
   * @param headers additional headers to include in the request
   * @return the response body, or {@code null} if the response is empty
   */
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

  /**
   * Performs an HTTP PUT request and returns the response body.
   *
   * <p>Note: If the server returns an empty response body, this method will return {@code null}.
   *
   * @param <T> The type of the response
   * @param url the URL to send the PUT request to
   * @param body the request body
   * @param responseType the expected response type
   * @return the response body, or {@code null} if the response is empty
   */
  @Override
  public <T> T put(final String url, final Object body, final Class<T> responseType) {
    HttpHeaders httpHeaders = createHeaders(null);
    HttpEntity<?> entity = new HttpEntity<>(body, httpHeaders);
    ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.PUT, entity, responseType);
    return response.getBody();
  }

  /**
   * Performs an HTTP PATCH request and returns the response body.
   *
   * <p>Note: If the server returns an empty response body, this method will return {@code null}.
   *
   * @param <T> The type of the response
   * @param url the URL to send the PATCH request to
   * @param body the request body
   * @param responseType the expected response type
   * @param headers additional headers to include in the request
   * @return the response body, or {@code null} if the response is empty
   */
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

  /**
   * Performs an HTTP PATCH request and returns the response body.
   *
   * <p>Note: If the server returns an empty response body, this method will return {@code null}.
   *
   * @param <T> The type of the response
   * @param url the URL to send the PATCH request to
   * @param body the request body
   * @param responseType the expected response type
   * @return the response body, or {@code null} if the response is empty
   */
  @Override
  public <T> T patch(final String url, final Object body, final Class<T> responseType) {
    HttpHeaders httpHeaders = createHeaders(null);
    HttpEntity<?> entity = new HttpEntity<>(body, httpHeaders);
    ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.PATCH, entity, responseType);
    return response.getBody();
  }

  /**
   * Performs an HTTP DELETE request and returns the response body.
   *
   * <p>Note: If the server returns an empty response body, this method will return {@code null}.
   *
   * @param <T> The type of the response
   * @param url the URL to send the DELETE request to
   * @param responseType the expected response type
   * @param headers additional headers to include in the request
   * @return the response body, or {@code null} if the response is empty
   */
  @Override
  public <T> T delete(
      final String url, final Class<T> responseType, final Map<String, String> headers) {
    HttpHeaders httpHeaders = createHeaders(headers, false);
    HttpEntity<?> entity = new HttpEntity<>(httpHeaders);
    ResponseEntity<T> response =
        restTemplate.exchange(url, HttpMethod.DELETE, entity, responseType);
    return response.getBody();
  }

  /**
   * Performs an HTTP DELETE request and returns the response body.
   *
   * <p>Note: If the server returns an empty response body, this method will return {@code null}.
   *
   * @param <T> The type of the response
   * @param url the URL to send the DELETE request to
   * @param responseType the expected response type
   * @return the response body, or {@code null} if the response is empty
   */
  @Override
  public <T> T delete(final String url, final Class<T> responseType) {
    HttpHeaders httpHeaders = createHeaders(null, false);
    HttpEntity<?> entity = new HttpEntity<>(httpHeaders);
    ResponseEntity<T> response =
        restTemplate.exchange(url, HttpMethod.DELETE, entity, responseType);
    return response.getBody();
  }

  @Override
  public void delete(final String url, final Map<String, String> headers) {
    HttpHeaders httpHeaders = createHeaders(headers, false);
    HttpEntity<?> entity = new HttpEntity<>(httpHeaders);
    restTemplate.exchange(url, HttpMethod.DELETE, entity, Void.class);
  }

  @Override
  public void delete(final String url) {
    restTemplate.delete(url);
  }

  /**
   * Creates HTTP headers with the given custom headers. Sets content type to JSON by default for
   * requests with a body.
   *
   * @param headers Optional map of custom headers
   * @param includeContentType Whether to set Content-Type header to application/json
   * @return HttpHeaders object with the configured headers
   */
  private HttpHeaders createHeaders(final Map<String, String> headers, boolean includeContentType) {
    HttpHeaders httpHeaders = new HttpHeaders();
    if (includeContentType) {
      httpHeaders.setContentType(MediaType.APPLICATION_JSON);
    }
    if (headers != null) {
      headers.forEach(httpHeaders::set);
    }
    return httpHeaders;
  }

  /**
   * Creates HTTP headers with the given custom headers. Sets content type to JSON by default.
   *
   * @param headers Optional map of custom headers
   * @return HttpHeaders object with the configured headers
   */
  private HttpHeaders createHeaders(final Map<String, String> headers) {
    return createHeaders(headers, true);
  }
}
