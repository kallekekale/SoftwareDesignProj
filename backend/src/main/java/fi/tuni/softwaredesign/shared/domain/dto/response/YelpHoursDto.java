package fi.tuni.softwaredesign.shared.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/** DTO representing business hours from the Yelp Fusion API. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record YelpHoursDto(
    @JsonProperty("open") List<YelpOpenHoursDto> open,
    @JsonProperty("hours_type") String hoursType,
    @JsonProperty("is_open_now") Boolean isOpenNow) {}
