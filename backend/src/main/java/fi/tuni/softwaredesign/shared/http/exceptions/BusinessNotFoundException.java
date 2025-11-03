package fi.tuni.softwaredesign.shared.http.exceptions;

import fi.tuni.softwaredesign.shared.domain.dto.request.CoordinateDto;

/** Exception thrown when a business is not found. */
public class BusinessNotFoundException extends RuntimeException {

  private final String businessId;
  private final CoordinateDto coordinates;

  public BusinessNotFoundException(String businessId) {
    super("Business not found with ID: " + businessId);
    this.businessId = businessId;
    this.coordinates = null;
  }

  public BusinessNotFoundException(CoordinateDto coordinates) {
    super(
        String.format(
            "No businesses found near coordinates: (%.5f, %.5f)",
            coordinates.latitude(), coordinates.longitude()));
    this.businessId = null;
    this.coordinates = coordinates;
  }

  public String getBusinessId() {
    return businessId;
  }

  public CoordinateDto getCoordinates() {
    return coordinates;
  }
}
