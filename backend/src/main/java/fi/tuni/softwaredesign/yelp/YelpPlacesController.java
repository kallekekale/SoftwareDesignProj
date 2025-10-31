package fi.tuni.softwaredesign.yelp;

import fi.tuni.softwaredesign.shared.domain.dto.request.CoordinateDto;
import fi.tuni.softwaredesign.shared.domain.dto.response.YelpBusinessResponseDto;
import fi.tuni.softwaredesign.shared.domain.dto.response.YelpBusinessDistanceResponseDto;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/** REST controller for Yelp Places API endpoints. */
@RestController
@RequestMapping("/api/yelp")
public class YelpPlacesController {

  private final YelpPlacesService yelpPlacesService;

  @Autowired
  public YelpPlacesController(YelpPlacesService yelpPlacesService) {
    this.yelpPlacesService = yelpPlacesService;
  }

  /**
   * Get a single business by its Yelp ID.
   *
   * @param id the Yelp business ID
   * @return YelpBusinessResponseDto containing the business information
   */
  @GetMapping("/{id}")
  public ResponseEntity<YelpBusinessResponseDto> getBusinessById(@PathVariable String id) {
    YelpBusinessResponseDto business = yelpPlacesService.getBusinessById(id);
    return ResponseEntity.ok(business);
  }

  /**
   * Get restaurants near a coordinate sorted by distance.
   *
   * @param coordinates the origin coordinates from the request body
   * @param limit the number of results to return (optional)
   * @return list of YelpBusinessDistanceResponseDto sorted by distance
   */
  @PostMapping("/restaurants/nearby")
  public ResponseEntity<List<YelpBusinessDistanceResponseDto>> getNearbyRestaurants(
      @RequestBody CoordinateDto coordinates,
      @RequestParam(value = "limit", required = false) Integer limit) {

    List<YelpBusinessDistanceResponseDto> restaurants =
        yelpPlacesService.getNearbyRestaurants(coordinates, limit);
    return ResponseEntity.ok(restaurants);
  }
}
