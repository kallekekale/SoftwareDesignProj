package fi.tuni.softwaredesign.shared.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

/**
 * Response DTO containing Open Brewery DB information with calculated distance. Used for endpoints
 * that return breweries sorted by distance from a coordinate.
 *
 * @param brewery the brewery information from Open Brewery DB
 * @param distance the distance from the origin point in kilometers
 */
public record OpenBreweryDbDistanceResponseDto(
    @JsonUnwrapped OpenBreweryDbResponseDto brewery, double distance) {}
