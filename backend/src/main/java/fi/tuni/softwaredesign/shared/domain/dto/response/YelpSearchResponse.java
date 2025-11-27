package fi.tuni.softwaredesign.shared.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/** DTO wrapper for Yelp search API responses. */
public record YelpSearchResponse(
    @JsonProperty("businesses") List<YelpBusinessResponseDto> businesses) {}
