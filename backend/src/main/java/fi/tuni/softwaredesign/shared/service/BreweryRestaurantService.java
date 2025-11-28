package fi.tuni.softwaredesign.shared.service;

import fi.tuni.softwaredesign.openbrewerydb.OpenBreweryDbService;
import fi.tuni.softwaredesign.shared.domain.dto.request.CoordinateDto;
import fi.tuni.softwaredesign.shared.domain.dto.response.OpenBreweryDbDistanceResponseDto;
import fi.tuni.softwaredesign.shared.domain.dto.response.YelpBusinessDistanceResponseDto;
import fi.tuni.softwaredesign.yelp.YelpPlacesService;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class BreweryRestaurantService {

  private final OpenBreweryDbService breweryService;
  private final YelpPlacesService yelpService;

  public BreweryRestaurantService(
      OpenBreweryDbService breweryService, YelpPlacesService yelpService) {
    this.breweryService = breweryService;
    this.yelpService = yelpService;
  }

  public List<OpenBreweryDbDistanceResponseDto> getBreweries(
      CoordinateDto coordinates, Integer limit) {
    return breweryService.getBreweriesByDistance(coordinates, limit);
  }

  public List<YelpBusinessDistanceResponseDto> getRestaurants(
      CoordinateDto coordinates, Integer limit) {
    return yelpService.getNearbyRestaurants(coordinates, limit);
  }
}
