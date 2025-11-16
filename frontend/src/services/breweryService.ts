import type { Coordinates, BreweryWithDistance } from "../types/brewery";

class BreweryService {
  private readonly baseUrl = "/api/breweries";

  /**
   * Fetch breweries sorted by distance from given coordinates
   * @param coordinates - The origin coordinates
   * @param perPage - Number of results to return (default: 10)
   * @returns Promise with array of breweries with distances
   */
  async getBreweriesByDistance(
    coordinates: Coordinates,
    perPage: number = 10,
  ): Promise<BreweryWithDistance[]> {
    const response = await fetch(
      `${this.baseUrl}/distance?per_page=${perPage}`,
      {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(coordinates),
      },
    );

    if (!response.ok) {
      throw new Error(`Failed to fetch breweries: ${response.statusText}`);
    }

    return response.json();
  }
}

export const breweryService = new BreweryService();
