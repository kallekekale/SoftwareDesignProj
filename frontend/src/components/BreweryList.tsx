import { useState } from "react";
import { useBreweries } from "../hooks/useBreweries";
import { useLocationStore } from "../stores/locationStore";
import type { BreweryWithDistance, Coordinates } from "../types/brewery";
import RestaurantList from "./RestaurantList";

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

export default function BreweryList() {
  const [selectedBrewery, setSelectedBrewery] = useState<{
    coordinates: Coordinates;
    name: string;
  } | null>(null);

  const selectedLocation = useLocationStore((state) => state.selectedLocation);
  const isGettingCurrentLocation = useLocationStore(
    (state) => state.isGettingCurrentLocation
  );
  const locationError = useLocationStore((state) => state.locationError);
  const setLocation = useLocationStore((state) => state.setLocation);
  const getCurrentLocation = useLocationStore(
    (state) => state.getCurrentLocation
  );

  const { data: breweries, isLoading, error } = useBreweries(selectedLocation);

  const handleLocationClick = (coordinates: Coordinates) => {
    setLocation(coordinates);
  };

  const handleBreweryClick = (brewery: BreweryWithDistance) => {
    if (brewery.latitude && brewery.longitude) {
      setSelectedBrewery({
        coordinates: {
          latitude: brewery.latitude,
          longitude: brewery.longitude,
        },
        name: brewery.name,
      });
    }
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
          onClick={getCurrentLocation}
          className="location-button current-location-button"
          disabled={isGettingCurrentLocation}
        >
          {isGettingCurrentLocation
            ? "Getting location..."
            : "📍 My Current Location"}
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
          <p className="click-hint">
            Click on a brewery to see nearby restaurants
          </p>
          <ul className="brewery-list">
            {breweries.map((brewery: BreweryWithDistance, index: number) => (
              <li
                key={brewery.id}
                className="brewery-item clickable"
                onClick={() => handleBreweryClick(brewery)}
              >
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

      {selectedBrewery && (
        <RestaurantList
          breweryCoordinates={selectedBrewery.coordinates}
          breweryName={selectedBrewery.name}
          onClose={() => setSelectedBrewery(null)}
        />
      )}
    </div>
  );
}
