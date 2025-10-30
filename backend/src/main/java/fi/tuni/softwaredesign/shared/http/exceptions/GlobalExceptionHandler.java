package fi.tuni.softwaredesign.shared.http.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

/** Global exception handler for all REST controllers. */
@RestControllerAdvice
public class GlobalExceptionHandler {

  private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  /**
   * Handle BreweryNotFoundException.
   *
   * @param ex      the exception
   * @param request the web request
   * @return ResponseEntity with error details
   */
  @ExceptionHandler(BreweryNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleBreweryNotFoundException(
      BreweryNotFoundException ex, WebRequest request) {
    logger.error("Brewery not found: {}", ex.getBreweryId(), ex);
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
        new ErrorResponse(
            LocalDateTime.now(),
            HttpStatus.NOT_FOUND.value(),
            "Not Found",
            ex.getMessage()));
  }

  /**
   * Handle BreweryNotFoundWithDistException.
   *
   * @param ex      the exception
   * @param request the web request
   * @return ResponseEntity with error details
   */
  @ExceptionHandler(BreweryNotFoundWithDistException.class)
  public ResponseEntity<ErrorResponse> handleBreweryNotFoundWithDistException(
      BreweryNotFoundWithDistException ex, WebRequest request) {
    logger.error("Brewery not found with distance: {}", ex.getByDist(), ex);
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
        new ErrorResponse(
            LocalDateTime.now(),
            HttpStatus.NOT_FOUND.value(),
            "Not Found",
            ex.getMessage()));
  }

  /**
   * Handle generic exceptions.
   *
   * @param ex      the exception
   * @param request the web request
   * @return ResponseEntity with error details
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleGlobalException(
      Exception ex, WebRequest request) {
    logger.error("Unexpected error occurred", ex);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
        new ErrorResponse(
            LocalDateTime.now(),
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "Internal Server Error",
            "An unexpected error occurred. Please try again later."));
  }

  /**
   * Handle IllegalArgumentException.
   *
   * @param ex      the exception
   * @param request the web request
   * @return ResponseEntity with error details
   */
  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ErrorResponse> handleIllegalArgumentException(
      IllegalArgumentException ex, WebRequest request) {
    logger.error("Invalid argument: {}", ex.getMessage(), ex);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
        new ErrorResponse(
            LocalDateTime.now(),
            HttpStatus.BAD_REQUEST.value(),
            "Bad Request",
            ex.getMessage()));
  }

  public record ErrorResponse(LocalDateTime timestamp, int status, String error, String message) {
  }
}
