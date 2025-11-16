import { useState } from "react";
import { useBreweries } from "./hooks/useBreweries";
import { useRestaurants } from "./hooks/useRestaurants";
import type { Coordinates, BreweryWithDistance } from "./types/brewery";
import type { Restaurant } from "./types/restaurant";
import "./App.css";

// Mocked locations
const MOCKED_LOCATIONS: { name: string; coordinates: Coordinates }[] = [
  {
    name: "Brooklyn, NY",
    coordinates: { latitude: 40.6782, longitude: -73.9442 },
  },
  {
    name: "Manhattan, NY",
    coordinates: { latitude: 40.7831, longitude: -73.9712 },
  },
  {
    name: "Downtown, LA",
    coordinates: { latitude: 34.0407, longitude: -118.2468 },
  },
];

function App() {
  const [selectedLocation, setSelectedLocation] = useState<Coordinates | null>(
    null
  );
  const [gettingLocation, setGettingLocation] = useState(false);
  const [locationError, setLocationError] = useState<string | null>(null);

  const { data: breweries, isLoading, error } = useBreweries(selectedLocation);
  const {
    data: restaurants,
    isLoading: restaurantsLoading,
    error: restaurantsError,
  } = useRestaurants(selectedLocation || { latitude: 0, longitude: 0 }, 10);

  const handleLocationClick = (coordinates: Coordinates) => {
    setSelectedLocation(coordinates);
    setLocationError(null);
  };

  const handleCurrentLocation = () => {
    if (!navigator.geolocation) {
      setLocationError("Geolocation is not supported by your browser");
      return;
    }

    setGettingLocation(true);
    setLocationError(null);

    navigator.geolocation.getCurrentPosition(
      (position) => {
        setSelectedLocation({
          latitude: position.coords.latitude,
          longitude: position.coords.longitude,
        });
        setGettingLocation(false);
      },
      (error) => {
        setLocationError(`Unable to get location: ${error.message}`);
        setGettingLocation(false);
      }
    );
  };

  return (
    <div className="app-container">
      <h1>Brewery Finder</h1>
      <p>Click a location to find the 10 closest breweries:</p>

      <div className="location-buttons">
        {MOCKED_LOCATIONS.map((location, index) => (
          <button
            key={index}
            onClick={() => handleLocationClick(location.coordinates)}
            className="location-button"
          >
            {location.name}
          </button>
        ))}
        <button
          onClick={handleCurrentLocation}
          className="location-button current-location-button"
          disabled={gettingLocation}
        >
          {gettingLocation ? "Getting location..." : "📍 My Current Location"}
        </button>
      </div>

      {locationError && <p className="error-message">{locationError}</p>}

      {isLoading && <p className="status-message">Loading breweries...</p>}

      {error && (
        <p className="error-message">
          Error loading breweries:{" "}
          {error instanceof Error ? error.message : "Unknown error"}
        </p>
      )}

      {breweries && breweries.length > 0 && (
        <div className="breweries-container">
          <h2>Closest Breweries</h2>
          <ul className="brewery-list">
            {breweries.map((brewery: BreweryWithDistance, index: number) => (
              <li key={brewery.id} className="brewery-item">
                <div className="brewery-rank">{index + 1}</div>
                <div className="brewery-details">
                  <h3>{brewery.name}</h3>
                  <p className="brewery-type">{brewery.brewery_type}</p>
                  <p className="brewery-location">
                    {brewery.city}, {brewery.state_province || brewery.country}
                  </p>
                  {brewery.street && (
                    <p className="brewery-address">{brewery.street}</p>
                  )}
                  <p className="brewery-distance">
                    <strong>Distance:</strong> {brewery.distance.toFixed(2)} km
                  </p>
                </div>
              </li>
            ))}
          </ul>
        </div>
      )}

      {breweries && breweries.length === 0 && (
        <p className="status-message">No breweries found for this location.</p>
      )}

      {/* Restaurants Section */}
      {selectedLocation && (
        <div className="restaurants-section">
          <h2>Nearby Restaurants (Yelp)</h2>

          {restaurantsLoading && (
            <p className="status-message">Loading restaurants...</p>
          )}

          {restaurantsError && (
            <p className="error-message">
              Error loading restaurants:{" "}
              {restaurantsError instanceof Error
                ? restaurantsError.message
                : "Unknown error"}
            </p>
          )}

          {restaurants && restaurants.length > 0 && (
            <div className="restaurants-container">
              <ul className="restaurant-list">
                {restaurants.map((restaurant: Restaurant, index: number) => (
                  <li key={restaurant.id} className="restaurant-item">
                    <div className="restaurant-rank">{index + 1}</div>
                    <div className="restaurant-details">
                      <div className="restaurant-header">
                        <h3>{restaurant.name}</h3>
                      </div>

                      <div className="restaurant-rating">
                        <span className="rating">⭐ {restaurant.rating}</span>
                        <span className="review-count">
                          ({restaurant.review_count} reviews)
                        </span>
                        {restaurant.price && (
                          <span className="price-category">
                            {restaurant.price}
                          </span>
                        )}
                      </div>

                      <p className="restaurant-location">
                        {restaurant.location.display_address
                          ? restaurant.location.display_address.join(", ")
                          : `${restaurant.location.address1 || ""}, ${restaurant.location.city}, ${restaurant.location.state}`}
                      </p>

                      <p className="restaurant-distance">
                        <strong>Distance:</strong>{" "}
                        {(restaurant.distance / 1000).toFixed(2)} km
                      </p>

                      {restaurant.url && (
                        <a
                          href={restaurant.url}
                          target="_blank"
                          rel="noopener noreferrer"
                          className="yelp-link"
                        >
                          View on Yelp →
                        </a>
                      )}
                    </div>
                  </li>
                ))}
              </ul>
            </div>
          )}

          {restaurants && restaurants.length === 0 && (
            <p className="status-message">
              No restaurants found for this location.
            </p>
          )}
        </div>
      )}
    </div>
  );
}

export default App;
