package fi.tuni.softwaredesign.shared.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * DTO representing a business's location information from the Yelp Fusion API.
 */
public record YelpLocationDto(
    @JsonProperty("address1") String address1,
    @JsonProperty("address2") String address2,
    @JsonProperty("address3") String address3,
    String city,
    String state,
    @JsonProperty("zip_code") String zipCode,
    String country
) {}
