package fi.tuni.softwaredesign.shared.http.exceptions;

/** Exception thrown when a brewery is not found with given coordinates. */
public class BreweryNotFoundWithDistException extends RuntimeException {
  private final String byDist;

  public BreweryNotFoundWithDistException(String byDist) {
    super("Brewery not found with distance: " + byDist);
    this.byDist = byDist;
  }

  public BreweryNotFoundWithDistException(String byDist, Throwable cause) {
    super("Brewery not found with distance: " + byDist, cause);
    this.byDist = byDist;
  }

  public String getByDist() {
    return byDist;
  }
}
