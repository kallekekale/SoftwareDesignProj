import type { Coordinates, BreweryWithDistance } from "../types/brewery";

const API_BASE_URL = "/api";

export const fetchBreweriesByDistance = async (
  coordinates: Coordinates,
  perPage: number = 10,
): Promise<BreweryWithDistance[]> => {
  const response = await fetch(
    `${API_BASE_URL}/breweries/distance?per_page=${perPage}`,
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
};
