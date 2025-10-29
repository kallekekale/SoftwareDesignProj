package fi.tuni.softwaredesign.http;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * RestTemplate-based HTTP client service for making external API calls.
 * Uses Spring's RestTemplate for synchronous HTTP operations.
 */
@Service
public class HttpRequesterService implements HttpRequester {
    
    private final RestTemplate restTemplate;
    
    public HttpRequesterService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    
    @Override
    public <T> T get(String url, Class<T> responseType, Map<String, String> headers) {
        HttpHeaders httpHeaders = createHeaders(headers);
        HttpEntity<?> entity = new HttpEntity<>(httpHeaders);
        ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.GET, entity, responseType);
        return response.getBody();
    }
    
    @Override
    public <T> T get(String url, Class<T> responseType) {
        return restTemplate.getForObject(url, responseType);
    }
    
    @Override
    public <T> T post(String url, Object body, Class<T> responseType, Map<String, String> headers) {
        HttpHeaders httpHeaders = createHeaders(headers);
        HttpEntity<?> entity = new HttpEntity<>(body, httpHeaders);
        ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.POST, entity, responseType);
        return response.getBody();
    }
    
    @Override
    public <T> T post(String url, Object body, Class<T> responseType) {
        return restTemplate.postForObject(url, body, responseType);
    }
    
    @Override
    public <T> T put(String url, Object body, Class<T> responseType, Map<String, String> headers) {
        HttpHeaders httpHeaders = createHeaders(headers);
        HttpEntity<?> entity = new HttpEntity<>(body, httpHeaders);
        ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.PUT, entity, responseType);
        return response.getBody();
    }
    
    @Override
    public <T> T put(String url, Object body, Class<T> responseType) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> entity = new HttpEntity<>(body, httpHeaders);
        ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.PUT, entity, responseType);
        return response.getBody();
    }
    
    @Override
    public <T> T patch(String url, Object body, Class<T> responseType, Map<String, String> headers) {
        HttpHeaders httpHeaders = createHeaders(headers);
        HttpEntity<?> entity = new HttpEntity<>(body, httpHeaders);
        ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.PATCH, entity, responseType);
        return response.getBody();
    }
    
    @Override
    public <T> T patch(String url, Object body, Class<T> responseType) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> entity = new HttpEntity<>(body, httpHeaders);
        ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.PATCH, entity, responseType);
        return response.getBody();
    }
    
    @Override
    public <T> T delete(String url, Class<T> responseType, Map<String, String> headers) {
        HttpHeaders httpHeaders = createHeaders(headers);
        HttpEntity<?> entity = new HttpEntity<>(httpHeaders);
        ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.DELETE, entity, responseType);
        return response.getBody();
    }
    
    @Override
    public <T> T delete(String url, Class<T> responseType) {
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<?> entity = new HttpEntity<>(httpHeaders);
        ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.DELETE, entity, responseType);
        return response.getBody();
    }
    
    @Override
    public void delete(String url, Map<String, String> headers) {
        HttpHeaders httpHeaders = createHeaders(headers);
        HttpEntity<?> entity = new HttpEntity<>(httpHeaders);
        restTemplate.exchange(url, HttpMethod.DELETE, entity, Void.class);
    }
    
    @Override
    public void delete(String url) {
        restTemplate.delete(url);
    }
    
    private HttpHeaders createHeaders(Map<String, String> headers) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        if (headers != null) {
            headers.forEach(httpHeaders::set);
        }
        return httpHeaders;
    }
}
