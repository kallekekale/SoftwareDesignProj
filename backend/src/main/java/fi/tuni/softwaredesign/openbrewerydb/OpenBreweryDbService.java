package fi.tuni.softwaredesign.openbrewerydb;

import fi.tuni.softwaredesign.http.HttpRequester;
import org.springframework.stereotype.Service;

/** Service for interacting with the Open Brewery DB API. */
@Service
public class OpenBreweryDbService {

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
   */
  public OpenBreweryDbDto getBreweryById(String id) {
    String url = BASE_URL + "/" + id;
    return httpRequester.get(url, OpenBreweryDbDto.class);
  }

  /**
   * Get breweries sorted by distance from an origin point.
   *
   * @param byDist the origin point as "latitude,longitude"
   * @param perPage the number of results per page (optional)
   * @return array of OpenBreweryDbDto containing the brewery information
   */
  public OpenBreweryDbDto[] getBreweriesByDistance(String byDist, Integer perPage) {
    StringBuilder url = new StringBuilder(BASE_URL + "?by_dist=" + byDist);
    url.append("&per_page=").append(perPage != null ? perPage : 10);
    return httpRequester.get(url.toString(), OpenBreweryDbDto[].class);
  }
}
