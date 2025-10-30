package fi.tuni.softwaredesign.openbrewerydb;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data Transfer Object for Open Brewery DB API responses. Maps to the brewery data returned by the
 * Open Brewery DB API.
 */
public record OpenBreweryDbDto(
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
