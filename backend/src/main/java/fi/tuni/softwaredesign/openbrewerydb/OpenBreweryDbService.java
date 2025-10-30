package fi.tuni.softwaredesign.openbrewerydb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import fi.tuni.softwaredesign.shared.http.HttpRequester;
import fi.tuni.softwaredesign.shared.http.exceptions.BreweryNotFoundException;
import fi.tuni.softwaredesign.shared.http.exceptions.BreweryNotFoundWithDistException;

/** Service for interacting with the Open Brewery DB API. */
@Service
public class OpenBreweryDbService {
  Logger logger = LoggerFactory.getLogger(OpenBreweryDbService.class);

  private static final String BASE_URL = "https://api.openbrewerydb.org/v1/breweries";
  private final HttpRequester httpRequester;

  public OpenBreweryDbService(HttpRequester httpRequester) {
    this.httpRequester = httpRequester;
  }

  /**
   * Get a single brewery by its ID.
   *
   * @param id the brewery ID
   * @return OpenBreweryDbDto containing the brewery information
   * @throws BreweryNotFoundException if the brewery is not found
   */
  public OpenBreweryDbDto getBreweryById(String id) {
    String url = BASE_URL + "/" + id;
    try {
      logger.debug("Fetching brewery with ID: {}", id);
      return httpRequester.get(url, OpenBreweryDbDto.class);
    } catch (Exception e) {
      logger.error("Error fetching brewery with ID: {}", id);
      throw new BreweryNotFoundException(id);
    }
  }

  /**
   * Get breweries sorted by distance from an origin point.
   *
   * @param byDist the origin point as "latitude,longitude"
   * @param perPage the number of results per page (optional)
   * @return array of OpenBreweryDbDto containing the brewery information
   * @throws BreweryNotFoundWithDistException if breweries are not found with the given coordinates
   */
  public OpenBreweryDbDto[] getBreweriesByDistance(String byDist, Integer perPage) {
    try {
      StringBuilder url = new StringBuilder(BASE_URL + "?by_dist=" + byDist);
      url.append("&per_page=").append(perPage != null ? perPage : 10);
      logger.debug("Fetching breweries by distance: {}", byDist);
      return httpRequester.get(url.toString(), OpenBreweryDbDto[].class);
    } catch (Exception e) {
      logger.error("Error fetching breweries by distance: {}", byDist);
      throw new BreweryNotFoundWithDistException(byDist);
    }
  }
}
