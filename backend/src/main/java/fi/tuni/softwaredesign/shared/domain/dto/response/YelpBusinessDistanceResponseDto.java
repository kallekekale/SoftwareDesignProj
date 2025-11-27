package fi.tuni.softwaredesign.shared.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/** Response DTO for restaurant information with distance from origin point. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record YelpBusinessDistanceResponseDto(
    String id,
    String name,
    Double rating,
    @JsonProperty("review_count") Integer reviewCount,
    Double distance,
    YelpLocationDto location,
    @JsonProperty("image_url") String imageUrl,
    String price,
    String url,
    @JsonProperty("business_hours") List<YelpHoursDto> businessHours) {}
