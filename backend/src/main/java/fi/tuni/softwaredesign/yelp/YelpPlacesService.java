package fi.tuni.softwaredesign.yelp;

import fi.tuni.softwaredesign.distance.DistanceService;
import fi.tuni.softwaredesign.shared.domain.dto.request.CoordinateDto;
import fi.tuni.softwaredesign.shared.domain.dto.response.YelpBusinessDistanceResponseDto;
import fi.tuni.softwaredesign.shared.domain.dto.response.YelpBusinessResponseDto;
import fi.tuni.softwaredesign.shared.http.HttpRequester;
import fi.tuni.softwaredesign.shared.http.exceptions.BusinessNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/** Service for interacting with the Yelp Fusion API. */
@Service
public class YelpPlacesService {
  private static final Logger logger = LoggerFactory.getLogger(YelpPlacesService.class);

  private static final String BASE_URL = "https://api.yelp.com/v3/businesses";
  private final HttpRequester httpRequester;
  private final DistanceService distanceService;

  @Value("${yelp.api.key}")
  private String yelpApiKey;

  public YelpPlacesService(HttpRequester httpRequester, DistanceService distanceService) {
    this.httpRequester = httpRequester;
    this.distanceService = distanceService;
  }

  /** Get a single business by its Yelp ID. */
  public YelpBusinessResponseDto getBusinessById(String id) {
    String url = BASE_URL + "/" + id;
    try {
      logger.debug("Fetching Yelp business with ID: {}", id);
      Map<String, String> headers = Map.of("Authorization", "Bearer " + yelpApiKey);
      return httpRequester.get(url, YelpBusinessResponseDto.class, headers);
    } catch (Exception e) {
      logger.error("Error fetching Yelp business with ID: {}", id, e);
      throw new BusinessNotFoundException(id);
    }
  }

  /** Get nearby restaurants sorted by distance. */
  public List<YelpBusinessDistanceResponseDto> getNearbyRestaurants(
      CoordinateDto coordinates, Integer limit) {
    try {
      String url =
          String.format(
              "%s/search?term=restaurants&latitude=%s&longitude=%s&sort_by=distance&limit=%d",
              BASE_URL,
              coordinates.latitude(),
              coordinates.longitude(),
              limit != null ? limit : 10);

      Map<String, String> headers = Map.of("Authorization", "Bearer " + yelpApiKey);
      YelpSearchResponse response = httpRequester.get(url, YelpSearchResponse.class, headers);

      if (response == null || response.businesses() == null) {
        throw new BusinessNotFoundException(coordinates);
      }

      return response.businesses().stream()
          .map(
              b ->
                  new YelpBusinessDistanceResponseDto(
                      b.id(),
                      b.name(),
                      b.rating(),
                      b.reviewCount(),
                      b.distance(),
                      b.location(),
                      b.imageUrl()))
          .collect(Collectors.toList());
    } catch (Exception e) {
      logger.error("Error fetching nearby Yelp restaurants for: {}", coordinates, e);
      throw new BusinessNotFoundException(coordinates);
    }
  }
}
