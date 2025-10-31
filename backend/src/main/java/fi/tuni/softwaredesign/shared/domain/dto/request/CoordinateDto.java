package fi.tuni.softwaredesign.shared.domain.dto.request;

/**
 * Request DTO for geographic coordinates.
 *
 * @param latitude the latitude in degrees (-90 to 90)
 * @param longitude the longitude in degrees (-180 to 180)
 */
public record CoordinateDto(double latitude, double longitude) {}
