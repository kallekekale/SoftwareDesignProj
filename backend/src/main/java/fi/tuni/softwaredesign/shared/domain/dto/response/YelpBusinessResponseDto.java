package fi.tuni.softwaredesign.shared.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record YelpBusinessResponseDto(
    String id,
    String name,
    double rating,
    @JsonProperty("review_count") int reviewCount,
    double distance,
    YelpLocationDto location,
    @JsonProperty("image_url") String imageUrl) {}
