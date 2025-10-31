package fi.tuni.softwaredesign.openbrewerydb;

import fi.tuni.softwaredesign.shared.domain.dto.request.CoordinateDto;
import fi.tuni.softwaredesign.shared.domain.dto.response.OpenBreweryDbDistanceResponseDto;
import fi.tuni.softwaredesign.shared.domain.dto.response.OpenBreweryDbResponseDto;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/** REST controller for Open Brewery DB API endpoints. */
@RestController
@RequestMapping("/api/breweries")
public class OpenBreweryDbController {

  private final OpenBreweryDbService openBreweryDbService;

  @Autowired
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
  public ResponseEntity<OpenBreweryDbResponseDto> getBreweryById(@PathVariable String id) {
    OpenBreweryDbResponseDto brewery = openBreweryDbService.getBreweryById(id);
    return ResponseEntity.ok(brewery);
  }

  /**
   * Get breweries sorted by distance from an origin point.
   *
   * @param coordinates the origin coordinates from the request body
   * @param perPage the number of results per page (optional)
   * @return list of OpenBreweryDbDistanceResponseDto sorted by distance
   */
  @PostMapping("/distance")
  public ResponseEntity<List<OpenBreweryDbDistanceResponseDto>> getBreweriesByDistance(
      @RequestBody CoordinateDto coordinates,
      @RequestParam(value = "per_page", required = false) Integer perPage) {
    List<OpenBreweryDbDistanceResponseDto> breweries =
        openBreweryDbService.getBreweriesByDistance(coordinates, perPage);
    return ResponseEntity.ok(breweries);
  }
}
