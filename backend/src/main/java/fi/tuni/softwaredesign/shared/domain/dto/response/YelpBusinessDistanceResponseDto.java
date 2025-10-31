package fi.tuni.softwaredesign.shared.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

/** Response DTO for restaurant information with distance from origin point. */
public record YelpBusinessDistanceResponseDto(
    String id,
    String name,
    double rating,
    @JsonProperty("review_count") int reviewCount,
    double distance,
    YelpLocationDto location,
    @JsonProperty("image_url") String imageUrl) {}
