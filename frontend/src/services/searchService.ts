import type { Coordinates } from "../types/brewery";
import type { BreweryWithDistance } from "../types/brewery";
import type { YelpRestaurant } from "../types/restaurant";

const API_BASE = "/api/search";

export const searchService = {
  async getNearbyBreweries(
    coordinates: Coordinates,
    limit: number = 10
  ): Promise<BreweryWithDistance[]> {
    try {
      const response = await fetch(`${API_BASE}/breweries?limit=${limit}`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(coordinates),
      });

      if (!response.ok) {
        const errorData = await response.text();
        console.error("Breweries API error:", response.status, errorData);
        throw new Error(`Failed to fetch breweries: ${response.status}`);
      }
      return response.json();
    } catch (error) {
      console.error("Breweries fetch error:", error);
      throw error;
    }
  },

  async getNearbyRestaurants(
    coordinates: Coordinates,
    limit: number = 10
  ): Promise<YelpRestaurant[]> {
    try {
      const response = await fetch(`${API_BASE}/restaurants?limit=${limit}`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(coordinates),
      });

      if (!response.ok) {
        const errorData = await response.text();
        console.error("Restaurants API error:", response.status, errorData);
        throw new Error(`Failed to fetch restaurants: ${response.status}`);
      }
      return response.json();
    } catch (error) {
      console.error("Restaurants fetch error:", error);
      throw error;
    }
  },
};
