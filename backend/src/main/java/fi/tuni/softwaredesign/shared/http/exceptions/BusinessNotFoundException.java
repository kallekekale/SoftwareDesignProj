package fi.tuni.softwaredesign.shared.http.exceptions;

/** Exception thrown when a business is not found. */
public class BusinessNotFoundException extends RuntimeException {

  private final String businessId;

  public BusinessNotFoundException(String businessId) {
    super("Business not found with ID: " + businessId);
    this.businessId = businessId;
  }

  public BusinessNotFoundException(String businessId, Throwable cause) {
    super("Business not found with ID: " + businessId, cause);
    this.businessId = businessId;
  }

  public String getBusinessId() {
    return businessId;
  }
}
