package fi.tuni.softwaredesign.yelp;

import fi.tuni.softwaredesign.shared.domain.dto.response.YelpBusinessDistanceResponseDto;
import fi.tuni.softwaredesign.shared.domain.dto.response.YelpLocationDto;
import java.util.Arrays;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/** Mock controller for testing Yelp API integration with sample data. */
@RestController
@RequestMapping("/api/yelp/mock")
public class YelpMockController {

  /**
   * Get mock restaurants with price and url fields.
   *
   * @return list of mock YelpBusinessDistanceResponseDto
   */
  @GetMapping("/restaurants")
  public ResponseEntity<List<YelpBusinessDistanceResponseDto>> getMockRestaurants() {
    List<YelpBusinessDistanceResponseDto> mockRestaurants =
        Arrays.asList(
            new YelpBusinessDistanceResponseDto(
                "mock-restaurant-1",
                "The Fancy Bistro",
                4.5,
                250,
                150.5,
                new YelpLocationDto(
                    "123 Main Street",
                    null,
                    null,
                    "Tampere",
                    "Pirkanmaa",
                    "33100",
                    "FI",
                    Arrays.asList("123 Main Street", "Tampere, Pirkanmaa 33100", "FI")),
                "https://example.com/images/bistro.jpg",
                "$$$",
                "https://www.yelp.com/biz/the-fancy-bistro-tampere"),
            new YelpBusinessDistanceResponseDto(
                "mock-restaurant-2",
                "Quick Pizza Place",
                4.0,
                180,
                300.0,
                new YelpLocationDto(
                    "456 Pizza Avenue",
                    "Suite 2",
                    null,
                    "Tampere",
                    "Pirkanmaa",
                    "33200",
                    "FI",
                    Arrays.asList("456 Pizza Avenue", "Suite 2", "Tampere, Pirkanmaa 33200")),
                "https://example.com/images/pizza.jpg",
                "$",
                "https://www.yelp.com/biz/quick-pizza-place-tampere"),
            new YelpBusinessDistanceResponseDto(
                "mock-restaurant-3",
                "Luxury Dining Experience",
                4.8,
                420,
                450.0,
                new YelpLocationDto(
                    "789 Gourmet Boulevard",
                    null,
                    null,
                    "Tampere",
                    "Pirkanmaa",
                    "33300",
                    "FI",
                    Arrays.asList("789 Gourmet Boulevard", "Tampere, Pirkanmaa 33300")),
                "https://example.com/images/luxury.jpg",
                "$$$$",
                "https://www.yelp.com/biz/luxury-dining-tampere"),
            new YelpBusinessDistanceResponseDto(
                "mock-restaurant-4",
                "Closed Cafe",
                3.5,
                95,
                200.0,
                new YelpLocationDto(
                    "321 Quiet Street",
                    null,
                    null,
                    "Tampere",
                    "Pirkanmaa",
                    "33400",
                    "FI",
                    Arrays.asList("321 Quiet Street", "Tampere, Pirkanmaa 33400")),
                "https://example.com/images/cafe.jpg",
                "$$",
                "https://www.yelp.com/biz/closed-cafe-tampere"),
            new YelpBusinessDistanceResponseDto(
                "mock-restaurant-5",
                "Burger Joint",
                4.2,
                310,
                100.0,
                new YelpLocationDto(
                    "555 Burger Lane",
                    null,
                    null,
                    "Tampere",
                    "Pirkanmaa",
                    "33500",
                    "FI",
                    Arrays.asList("555 Burger Lane", "Tampere, Pirkanmaa 33500")),
                "https://example.com/images/burger.jpg",
                "$$",
                "https://www.yelp.com/biz/burger-joint-tampere"));

    return ResponseEntity.ok(mockRestaurants);
  }
}
