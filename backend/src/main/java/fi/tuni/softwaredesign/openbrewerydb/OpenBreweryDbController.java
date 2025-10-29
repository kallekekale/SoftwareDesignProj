package fi.tuni.softwaredesign.openbrewerydb;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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
}
