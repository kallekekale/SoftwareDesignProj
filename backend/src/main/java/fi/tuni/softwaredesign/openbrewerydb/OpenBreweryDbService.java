package fi.tuni.softwaredesign.openbrewerydb;

import fi.tuni.softwaredesign.http.HttpRequester;
import org.springframework.stereotype.Service;

/**
 * Service for interacting with the Open Brewery DB API.
 */
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
}
