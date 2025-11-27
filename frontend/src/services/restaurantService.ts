import type { YelpRestaurant } from "../types/restaurant";
import type { Coordinates } from "../types/brewery";

const API_BASE_URL = "/api";

export const restaurantService = {
  async getNearbyRestaurants(
    coordinates: Coordinates,
    limit: number = 10
  ): Promise<YelpRestaurant[]> {
    const response = await fetch(
      `${API_BASE_URL}/yelp/restaurants/nearby?limit=${limit}`,
      {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          latitude: coordinates.latitude,
          longitude: coordinates.longitude,
        }),
      }
    );

    if (!response.ok) {
      throw new Error("Failed to fetch restaurants");
    }

    return response.json();
  },
};
