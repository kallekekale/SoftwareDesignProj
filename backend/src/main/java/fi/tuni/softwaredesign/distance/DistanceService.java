package fi.tuni.softwaredesign.distance;

import org.springframework.stereotype.Service;

/**
 * Service for calculating distances between geographic coordinates. Uses the Haversine formula to
 * calculate the great-circle distance between two points.
 */
@Service
public final class DistanceService {

  /** Earth's radius in kilometers. */
  private static final double EARTH_RADIUS = 6371.0;

  public DistanceService() {}

  /**
   * Calculates the distance between two geographic coordinates using the Haversine formula.
   *
   * @param coordinate1 the first coordinate
   * @param coordinate2 the second coordinate
   * @return the distance between the two points in kilometers
   * @throws IllegalArgumentException if any coordinate is out of valid range
   */
  public double calculateDistance(final Coordinate coordinate1, final Coordinate coordinate2) {
    validateCoordinate(coordinate1);
    validateCoordinate(coordinate2);

    return EARTH_RADIUS
        * Math.acos(
            Math.sin(coordinate1.latitude()) * Math.sin(coordinate2.latitude())
                + Math.cos(coordinate1.latitude())
                    * Math.cos(coordinate2.latitude())
                    * Math.cos(coordinate1.longitude() - coordinate2.longitude()));
  }

  private void validateCoordinate(final Coordinate coordinate) {
    if (coordinate.latitude() < -90 || coordinate.latitude() > 90) {
      throw new IllegalArgumentException("Latitude must be between -90 and 90 degrees");
    }
    if (coordinate.longitude() < -180 || coordinate.longitude() > 180) {
      throw new IllegalArgumentException("Longitude must be between -180 and 180 degrees");
    }
  }

  private record Coordinate(double latitude, double longitude) {}
}
