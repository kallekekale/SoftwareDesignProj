package fi.tuni.softwaredesign.http;

import java.util.Map;

/**
 * Interface for making HTTP requests to external REST APIs.
 * Provides methods for common HTTP operations (GET, POST, PUT, DELETE, PATCH).
 */
public interface HttpRequester {
    
    /**
     * Performs a GET request
     * @param url The target URL
     * @param responseType The expected response class type
     * @param headers Optional headers map
     * @return Response object of type T
     */
    <T> T get(String url, Class<T> responseType, Map<String, String> headers);
    
    /**
     * Performs a GET request without custom headers
     * @param url The target URL
     * @param responseType The expected response class type
     * @return Response object of type T
     */
    <T> T get(String url, Class<T> responseType);
    
    /**
     * Performs a POST request
     * @param url The target URL
     * @param body The request body
     * @param responseType The expected response class type
     * @param headers Optional headers map
     * @return Response object of type T
     */
    <T> T post(String url, Object body, Class<T> responseType, Map<String, String> headers);
    
    /**
     * Performs a POST request without custom headers
     * @param url The target URL
     * @param body The request body
     * @param responseType The expected response class type
     * @return Response object of type T
     */
    <T> T post(String url, Object body, Class<T> responseType);
    
    /**
     * Performs a PUT request
     * @param url The target URL
     * @param body The request body
     * @param responseType The expected response class type
     * @param headers Optional headers map
     * @return Response object of type T
     */
    <T> T put(String url, Object body, Class<T> responseType, Map<String, String> headers);
    
    /**
     * Performs a PUT request without custom headers
     * @param url The target URL
     * @param body The request body
     * @param responseType The expected response class type
     * @return Response object of type T
     */
    <T> T put(String url, Object body, Class<T> responseType);
    
    /**
     * Performs a PATCH request
     * @param url The target URL
     * @param body The request body
     * @param responseType The expected response class type
     * @param headers Optional headers map
     * @return Response object of type T
     */
    <T> T patch(String url, Object body, Class<T> responseType, Map<String, String> headers);
    
    /**
     * Performs a PATCH request without custom headers
     * @param url The target URL
     * @param body The request body
     * @param responseType The expected response class type
     * @return Response object of type T
     */
    <T> T patch(String url, Object body, Class<T> responseType);
    
    /**
     * Performs a DELETE request
     * @param url The target URL
     * @param responseType The expected response class type
     * @param headers Optional headers map
     * @return Response object of type T
     */
    <T> T delete(String url, Class<T> responseType, Map<String, String> headers);
    
    /**
     * Performs a DELETE request without custom headers
     * @param url The target URL
     * @param responseType The expected response class type
     * @return Response object of type T
     */
    <T> T delete(String url, Class<T> responseType);
    
    /**
     * Performs a DELETE request without expecting a response body
     * @param url The target URL
     * @param headers Optional headers map
     */
    void delete(String url, Map<String, String> headers);
    
    /**
     * Performs a DELETE request without expecting a response body
     * @param url The target URL
     */
    void delete(String url);
}
