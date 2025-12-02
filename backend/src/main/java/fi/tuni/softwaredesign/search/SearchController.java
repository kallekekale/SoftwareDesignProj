package fi.tuni.softwaredesign.search;

import fi.tuni.softwaredesign.shared.domain.dto.request.CoordinateDto;
import fi.tuni.softwaredesign.shared.domain.dto.response.OpenBreweryDbDistanceResponseDto;
import fi.tuni.softwaredesign.shared.domain.dto.response.YelpBusinessDistanceResponseDto;
import java.util.List;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for search operations. Provides endpoints for searching breweries, restaurants, and
 * combined brewery + restaurant data.
 */
@RestController
@RequestMapping("/api/search")
public class SearchController {

  private final SearchService searchService;

  public SearchController(SearchService searchService) {
    this.searchService = searchService;
  }

  /**
   * Search for breweries by coordinates.
   *
   * @param coordinates the search origin coordinates
   * @param limit the number of results to return (optional, default 10)
   * @return list of breweries with distances from the origin
   */
  @PostMapping("/breweries")
  public List<OpenBreweryDbDistanceResponseDto> getBreweries(
      @RequestBody CoordinateDto coordinates, @RequestParam(required = false) Integer limit) {
    return searchService.getBreweries(coordinates, limit);
  }

  /**
   * Search for restaurants by coordinates.
   *
   * @param coordinates the search origin coordinates
   * @param limit the number of results to return (optional, default 10)
   * @return list of restaurants with distances from the origin
   */
  @PostMapping("/restaurants")
  public List<YelpBusinessDistanceResponseDto> getRestaurants(
      @RequestBody CoordinateDto coordinates, @RequestParam(required = false) Integer limit) {
    return searchService.getRestaurants(coordinates, limit);
  }

  /**
   * Get a specific brewery and nearby restaurants.
   *
   * @param breweryId the Open Brewery DB brewery ID
   * @param restaurantLimit the number of restaurants to return (optional, default 10)
   * @return brewery with nearby restaurants (cached in Redis)
   */
  @GetMapping("/brewery/{breweryId}")
  public BreweryWithRestaurantsDto getBreweryWithRestaurants(
      @PathVariable String breweryId, @RequestParam(required = false) Integer restaurantLimit) {
    return searchService.getBreweryWithRestaurants(breweryId, restaurantLimit);
  }
}
