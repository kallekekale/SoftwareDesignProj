package fi.tuni.softwaredesign.distance;

import fi.tuni.softwaredesign.shared.domain.dto.request.CoordinateDto;
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
  public double calculateDistance(
      final CoordinateDto coordinate1, final CoordinateDto coordinate2) {
    validateCoordinate(coordinate1);
    validateCoordinate(coordinate2);

    // Convert degrees to radians
    double lat1Rad = Math.toRadians(coordinate1.latitude());
    double lat2Rad = Math.toRadians(coordinate2.latitude());
    double lon1Rad = Math.toRadians(coordinate1.longitude());
    double lon2Rad = Math.toRadians(coordinate2.longitude());

    return EARTH_RADIUS
        * Math.acos(
            Math.sin(lat1Rad) * Math.sin(lat2Rad)
                + Math.cos(lat1Rad) * Math.cos(lat2Rad) * Math.cos(lon1Rad - lon2Rad));
  }

  private void validateCoordinate(final CoordinateDto coordinate) {
    if (coordinate.latitude() < -90 || coordinate.latitude() > 90) {
      throw new IllegalArgumentException("Latitude must be between -90 and 90 degrees");
    }
    if (coordinate.longitude() < -180 || coordinate.longitude() > 180) {
      throw new IllegalArgumentException("Longitude must be between -180 and 180 degrees");
    }
  }
}