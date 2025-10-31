package fi.tuni.softwaredesign.shared.http.exceptions;

import fi.tuni.softwaredesign.shared.domain.dto.request.CoordinateDto;

/** Exception thrown when a brewery is not found with given coordinates. */
public class BreweryNotFoundWithDistException extends RuntimeException {
  private final CoordinateDto coordinates;

  public BreweryNotFoundWithDistException(CoordinateDto coordinates) {
    super("Brewery not found with coordinates: " + coordinates);
    this.coordinates = coordinates;
  }

  public BreweryNotFoundWithDistException(CoordinateDto coordinates, Throwable cause) {
    super("Brewery not found with coordinates: " + coordinates, cause);
    this.coordinates = coordinates;
  }

  public CoordinateDto getCoordinates() {
    return coordinates;
  }
}
