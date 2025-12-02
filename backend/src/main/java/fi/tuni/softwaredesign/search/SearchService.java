package fi.tuni.softwaredesign.search;

import fi.tuni.softwaredesign.openbrewerydb.OpenBreweryDbService;
import fi.tuni.softwaredesign.shared.domain.dto.request.CoordinateDto;
import fi.tuni.softwaredesign.shared.domain.dto.response.OpenBreweryDbDistanceResponseDto;
import fi.tuni.softwaredesign.shared.domain.dto.response.OpenBreweryDbResponseDto;
import fi.tuni.softwaredesign.shared.domain.dto.response.YelpBusinessDistanceResponseDto;
import fi.tuni.softwaredesign.yelp.YelpPlacesService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/** Service for search operations. Combines brewery and restaurant data for convenient querying. */
@Service
public class SearchService {
  private static final Logger logger = LoggerFactory.getLogger(SearchService.class);

  private final OpenBreweryDbService breweryService;
  private final YelpPlacesService yelpService;

  public SearchService(OpenBreweryDbService breweryService, YelpPlacesService yelpService) {
    this.breweryService = breweryService;
    this.yelpService = yelpService;
  }

  /**
   * Get breweries sorted by distance from the given coordinates.
   *
   * @param coordinates the search origin coordinates
   * @param limit the number of results to return (optional)
   * @return list of breweries with distances
   */
  @Cacheable(
      value = "breweries",
      key = "#coordinates.latitude + ',' + #coordinates.longitude + ',' + #limit")
  public List<OpenBreweryDbDistanceResponseDto> getBreweries(
      CoordinateDto coordinates, Integer limit) {
    logger.debug("Fetching breweries for coordinates: {}", coordinates);
    return breweryService.getBreweriesByDistance(coordinates, limit);
  }

  /**
   * Get restaurants sorted by distance from the given coordinates.
   *
   * @param coordinates the search origin coordinates
   * @param limit the number of results to return (optional)
   * @return list of restaurants with distances
   */
  @Cacheable(
      value = "restaurants",
      key = "#coordinates.latitude + ',' + #coordinates.longitude + ',' + #limit")
  public List<YelpBusinessDistanceResponseDto> getRestaurants(
      CoordinateDto coordinates, Integer limit) {
    logger.debug("Fetching restaurants for coordinates: {}", coordinates);
    return yelpService.getNearbyRestaurants(coordinates, limit);
  }

  /**
   * Get a brewery by ID and fetch nearby restaurants.
   *
   * @param breweryId the Open Brewery DB brewery ID
   * @param restaurantLimit the number of restaurants to return (optional)
   * @return brewery with nearby restaurants
   */
  @Cacheable(value = "breweryWithRestaurants", key = "#breweryId + ',' + #restaurantLimit")
  public BreweryWithRestaurantsDto getBreweryWithRestaurants(
      String breweryId, Integer restaurantLimit) {
    logger.debug("Fetching brewery with ID: {} and nearby restaurants", breweryId);

    // Get the brewery by ID
    OpenBreweryDbResponseDto brewery = breweryService.getBreweryById(breweryId);

    // Create a distance DTO (distance is 0 since we're directly fetching by ID)
    OpenBreweryDbDistanceResponseDto breweryWithDist =
        new OpenBreweryDbDistanceResponseDto(brewery, 0.0);

    // Get restaurants near the brewery
    CoordinateDto breweryCoordinates = new CoordinateDto(brewery.latitude(), brewery.longitude());
    List<YelpBusinessDistanceResponseDto> restaurants =
        getRestaurants(breweryCoordinates, restaurantLimit);

    logger.debug("Found {} restaurants near brewery: {}", restaurants.size(), breweryId);
    return new BreweryWithRestaurantsDto(breweryWithDist, restaurants);
  }
}
