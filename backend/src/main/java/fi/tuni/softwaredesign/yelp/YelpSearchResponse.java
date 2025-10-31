package fi.tuni.softwaredesign.yelp;

import com.fasterxml.jackson.annotation.JsonProperty;
import fi.tuni.softwaredesign.shared.domain.dto.response.YelpBusinessResponseDto;
import java.util.List;

/** DTO wrapper for Yelp search API responses. */
public class YelpSearchResponse {

  @JsonProperty("businesses")
  private List<YelpBusinessResponseDto> businesses;

  public List<YelpBusinessResponseDto> getBusinesses() {
    return businesses;
  }

  public void setBusinesses(List<YelpBusinessResponseDto> businesses) {
    this.businesses = businesses;
  }
}
