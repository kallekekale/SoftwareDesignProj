package fi.tuni.softwaredesign.shared.http.exceptions;

/** Exception thrown when a brewery is not found. */
public class BreweryNotFoundException extends RuntimeException {

  private final String breweryId;

  public BreweryNotFoundException(String breweryId) {
    super("Brewery not found with ID: " + breweryId);
    this.breweryId = breweryId;
  }

  public BreweryNotFoundException(String breweryId, Throwable cause) {
    super("Brewery not found with ID: " + breweryId, cause);
    this.breweryId = breweryId;
  }

  public String getBreweryId() {
    return breweryId;
  }
}
