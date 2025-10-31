import { useState } from "react";
import { useQuery } from "@tanstack/react-query";
import { fetchBreweriesByDistance } from "./services/breweryService";
import type { Coordinates, BreweryWithDistance } from "./types/brewery";
import "./App.css";

// Mocked locations
const MOCKED_LOCATIONS: { name: string; coordinates: Coordinates }[] = [
  { name: "Brooklyn, NY", coordinates: { latitude: 40.6782, longitude: -73.9442 } },
  { name: "Manhattan, NY", coordinates: { latitude: 40.7831, longitude: -73.9712 } },
  { name: "Downtown, LA", coordinates: { latitude: 34.0407, longitude: -118.2468 } },
];

function App() {
  const [selectedLocation, setSelectedLocation] = useState<Coordinates | null>(null);
  const [gettingLocation, setGettingLocation] = useState(false);
  const [locationError, setLocationError] = useState<string | null>(null);

  const { data: breweries, isLoading, error } = useQuery({
    queryKey: ["breweries", selectedLocation],
    queryFn: () => fetchBreweriesByDistance(selectedLocation!, 10),
    enabled: selectedLocation !== null,
  });

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
          Error loading breweries: {error instanceof Error ? error.message : "Unknown error"}
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
                  {brewery.street && <p className="brewery-address">{brewery.street}</p>}
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
    </div>
  );
}

export default App;
