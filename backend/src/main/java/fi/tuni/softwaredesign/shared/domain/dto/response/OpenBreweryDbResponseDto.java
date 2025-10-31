package fi.tuni.softwaredesign.shared.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Response DTO for brewery information from Open Brewery DB API. Maps to the brewery data structure
 * returned by the external API.
 */
public record OpenBreweryDbResponseDto(
    String id,
    String name,
    @JsonProperty("brewery_type") String breweryType,
    @JsonProperty("address_1") String address1,
    @JsonProperty("address_2") String address2,
    @JsonProperty("address_3") String address3,
    String city,
    @JsonProperty("state_province") String stateProvince,
    @JsonProperty("postal_code") String postalCode,
    String country,
    Double longitude,
    Double latitude,
    String phone,
    @JsonProperty("website_url") String websiteUrl,
    String state,
    String street) {}
