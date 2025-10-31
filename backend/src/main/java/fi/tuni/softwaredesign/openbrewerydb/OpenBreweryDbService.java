package fi.tuni.softwaredesign.openbrewerydb;

import fi.tuni.softwaredesign.distance.DistanceService;
import fi.tuni.softwaredesign.shared.domain.dto.request.CoordinateDto;
import fi.tuni.softwaredesign.shared.domain.dto.response.OpenBreweryDbDistanceResponseDto;
import fi.tuni.softwaredesign.shared.domain.dto.response.OpenBreweryDbResponseDto;
import fi.tuni.softwaredesign.shared.http.HttpRequester;
import fi.tuni.softwaredesign.shared.http.exceptions.BreweryNotFoundException;
import fi.tuni.softwaredesign.shared.http.exceptions.BreweryNotFoundWithDistException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/** Service for interacting with the Open Brewery DB API. */
@Service
public class OpenBreweryDbService {
  private static final Logger logger = LoggerFactory.getLogger(OpenBreweryDbService.class);

  private static final String BASE_URL = "https://api.openbrewerydb.org/v1/breweries";
  private final HttpRequester httpRequester;
  private final DistanceService distanceService;

  public OpenBreweryDbService(HttpRequester httpRequester, DistanceService distanceService) {
    this.httpRequester = httpRequester;
    this.distanceService = distanceService;
  }

  /**
   * Get a single brewery by its ID.
   *
   * @param id the brewery ID
   * @return OpenBreweryDbDto containing the brewery information
   * @throws BreweryNotFoundException if the brewery is not found
   */
  public OpenBreweryDbResponseDto getBreweryById(String id) {
    String url = BASE_URL + "/" + id;
    try {
      logger.debug("Fetching brewery with ID: {}", id);
      return httpRequester.get(url, OpenBreweryDbResponseDto.class);
    } catch (Exception e) {
      logger.error("Error fetching brewery with ID: {}", id);
      throw new BreweryNotFoundException(id);
    }
  }

  /**
   * Get breweries sorted by distance from an origin point.
   *
   * @param coordinates the origin coordinates
   * @param perPage the number of results per page (optional)
   * @return list of OpenBreweryDbDistanceResponseDto containing brewery information with distances
   * @throws BreweryNotFoundWithDistException if breweries are not found with the given coordinates
   */
  public List<OpenBreweryDbDistanceResponseDto> getBreweriesByDistance(
      CoordinateDto coordinates, Integer perPage) {
    try {
      String url =
          String.format(
              "%s?by_dist=%s,%s&per_page=%d",
              BASE_URL,
              coordinates.latitude(),
              coordinates.longitude(),
              perPage != null ? perPage : 10);

      logger.debug("Fetching breweries by distance: {}", coordinates);
      OpenBreweryDbResponseDto[] breweries =
          httpRequester.get(url, OpenBreweryDbResponseDto[].class);

      return Arrays.stream(breweries)
          .map(
              brewery -> {
                CoordinateDto breweryCoordinate =
                    new CoordinateDto(brewery.latitude(), brewery.longitude());
                double distance = distanceService.calculateDistance(coordinates, breweryCoordinate);
                return new OpenBreweryDbDistanceResponseDto(brewery, distance);
              })
          .collect(Collectors.toList());
    } catch (Exception e) {
      logger.error("Error fetching breweries by distance: {}", coordinates);
      throw new BreweryNotFoundWithDistException(coordinates);
    }
  }
}
