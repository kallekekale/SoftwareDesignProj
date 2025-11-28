package fi.tuni.softwaredesign.shared.service;

import fi.tuni.softwaredesign.shared.domain.dto.request.CoordinateDto;
import fi.tuni.softwaredesign.shared.domain.dto.response.OpenBreweryDbDistanceResponseDto;
import fi.tuni.softwaredesign.shared.domain.dto.response.YelpBusinessDistanceResponseDto;
import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class BreweryRestaurantController {

  private final BreweryRestaurantService service;

  public BreweryRestaurantController(BreweryRestaurantService service) {
    this.service = service;
  }

  @PostMapping("/breweries")
  public List<OpenBreweryDbDistanceResponseDto> getBreweries(
      @RequestBody CoordinateDto coordinates, @RequestParam(required = false) Integer limit) {
    return service.getBreweries(coordinates, limit);
  }

  @PostMapping("/restaurants")
  public List<YelpBusinessDistanceResponseDto> getRestaurants(
      @RequestBody CoordinateDto coordinates, @RequestParam(required = false) Integer limit) {
    return service.getRestaurants(coordinates, limit);
  }
}
