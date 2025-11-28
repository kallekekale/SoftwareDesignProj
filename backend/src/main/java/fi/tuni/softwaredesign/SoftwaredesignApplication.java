package fi.tuni.softwaredesign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * Main Spring Boot application class for the Software Design course backend.
 *
 * @since 1.0
 */
@SpringBootApplication
@EnableCaching
public class SoftwaredesignApplication {

  /**
   * Main method to start the Spring Boot application.
   *
   * @param args command line arguments
   */
  public static void main(final String[] args) {
    SpringApplication.run(SoftwaredesignApplication.class, args);
  }
}
