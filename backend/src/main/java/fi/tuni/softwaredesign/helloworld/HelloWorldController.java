package fi.tuni.softwaredesign.helloworld;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    @GetMapping("/")
    public ResponseEntity<String> root() {
        return ResponseEntity.ok("Hello, World!");
    }
}
