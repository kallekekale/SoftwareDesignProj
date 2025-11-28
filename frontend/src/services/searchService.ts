import type { Coordinates } from "../types/brewery";
import type { BreweryWithDistance } from "../types/brewery";
import type { YelpRestaurant } from "../types/restaurant";

const API_BASE = "/api/search";

export const searchService = {
  async getNearbyBreweries(
    coordinates: Coordinates,
    limit: number = 10
  ): Promise<BreweryWithDistance[]> {
    const response = await fetch(`${API_BASE}/breweries?limit=${limit}`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(coordinates),
    });

    if (!response.ok) {
      throw new Error("Failed to fetch breweries");
    }
    return response.json() as Promise<BreweryWithDistance[]>;
  },

  async getNearbyRestaurants(
    coordinates: Coordinates,
    limit: number = 10
  ): Promise<YelpRestaurant[]> {
    const response = await fetch(`${API_BASE}/restaurants?limit=${limit}`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(coordinates),
    });

    if (!response.ok) {
      throw new Error("Failed to fetch restaurants");
    }
    return response.json() as Promise<YelpRestaurant[]>;
  },
};
