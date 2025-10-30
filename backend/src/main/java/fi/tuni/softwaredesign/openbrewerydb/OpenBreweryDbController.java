package fi.tuni.softwaredesign.openbrewerydb;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/** REST controller for Open Brewery DB API endpoints. */
@RestController
@RequestMapping("/api/breweries")
public class OpenBreweryDbController {

  private final OpenBreweryDbService openBreweryDbService;

  public OpenBreweryDbController(OpenBreweryDbService openBreweryDbService) {
    this.openBreweryDbService = openBreweryDbService;
  }

  /**
   * Get a single brewery by its ID.
   *
   * @param id the brewery ID
   * @return OpenBreweryDbDto containing the brewery information
   */
  @GetMapping("/{id}")
  public OpenBreweryDbDto getBreweryById(@PathVariable String id) {
    return openBreweryDbService.getBreweryById(id);
  }

  /**
   * Get breweries sorted by distance from an origin point.
   *
   * @param byDist the origin point as "latitude,longitude"
   * @param perPage the number of results per page (optional)
   * @return array of OpenBreweryDbDto containing the brewery information
   */
  @GetMapping
  public OpenBreweryDbDto[] getBreweriesByDistance(
      @RequestParam("by_dist") String byDist,
      @RequestParam(value = "per_page", required = false) Integer perPage) {
    return openBreweryDbService.getBreweriesByDistance(byDist, perPage);
  }
}
