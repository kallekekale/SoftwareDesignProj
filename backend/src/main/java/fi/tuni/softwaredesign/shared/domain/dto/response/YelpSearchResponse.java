package fi.tuni.softwaredesign.yelp;

import com.fasterxml.jackson.annotation.JsonProperty;
import fi.tuni.softwaredesign.shared.domain.dto.response.YelpBusinessResponseDto;
import java.util.List;

/** DTO wrapper for Yelp search API responses. */
public record YelpSearchResponse(
    @JsonProperty("businesses") List<YelpBusinessResponseDto> businesses) {}
