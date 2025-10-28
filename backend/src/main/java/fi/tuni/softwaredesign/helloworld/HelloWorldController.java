package fi.tuni.softwaredesign.helloworld;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for Hello World endpoint.
 * Provides a simple greeting response for testing.
 *
 * @since 1.0
 */
@RestController
public final class HelloWorldController {

    /**
     * Root endpoint that returns a greeting message.
     *
     * @return ResponseEntity containing "Hello, World!" message
     */
    @GetMapping("/")
    public ResponseEntity<String> root() {
        return ResponseEntity.ok("Hello, World!");
    }
}
