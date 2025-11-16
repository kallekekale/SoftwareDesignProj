import type { Coordinates, Restaurant } from "../types/restaurant";

class RestaurantService {
  private readonly baseUrl = "/api/yelp";

  /**
   * Fetch restaurants sorted by distance from given coordinates
   * @param coordinates - The origin coordinates
   * @param limit - Number of results to return (default: 10)
   * @returns Promise with array of restaurants with distances
   */
  async getRestaurantsByDistance(
    coordinates: Coordinates,
    limit: number = 10
  ): Promise<Restaurant[]> {
    const response = await fetch(
      `${this.baseUrl}/restaurants/nearby?limit=${limit}`,
      {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(coordinates),
      }
    );

    if (!response.ok) {
      throw new Error(`Failed to fetch restaurants: ${response.statusText}`);
    }

    return response.json();
  }

  /**
   * Fetch a single restaurant by its Yelp ID
   * @param id - The Yelp business ID
   * @returns Promise with restaurant details
   */
  async getRestaurantById(id: string): Promise<Restaurant> {
    const response = await fetch(`${this.baseUrl}/${id}`);

    if (!response.ok) {
      throw new Error(`Failed to fetch restaurant: ${response.statusText}`);
    }

    return response.json();
  }
}

export const restaurantService = new RestaurantService();
