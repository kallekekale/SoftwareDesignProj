package fi.tuni.softwaredesign.shared.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/** DTO representing individual opening hours from the Yelp Fusion API. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record YelpOpenHoursDto(
    @JsonProperty("is_overnight") Boolean isOvernight,
    @JsonProperty("start") String start,
    @JsonProperty("end") String end,
    @JsonProperty("day") Integer day) {}
