import { useQuery } from "@tanstack/react-query";
import type { UseQueryResult } from "@tanstack/react-query";
import { searchService } from "../services/searchService";
import type { Coordinates } from "../types/brewery";
import type { BreweryWithDistance } from "../types/brewery";
import type { YelpRestaurant } from "../types/restaurant";

type SearchType = "breweries" | "restaurants";

export function useNearbyData(
  type: "breweries",
  coordinates: Coordinates | null,
  limit?: number
): UseQueryResult<BreweryWithDistance[], Error>;

export function useNearbyData(
  type: "restaurants",
  coordinates: Coordinates | null,
  limit?: number
): UseQueryResult<YelpRestaurant[], Error>;

export function useNearbyData(
  type: SearchType,
  coordinates: Coordinates | null,
  limit: number = 10
) {
  const coordKey = coordinates
    ? [coordinates.latitude, coordinates.longitude]
    : null;

  return useQuery({
    queryKey: ["nearby", type, coordKey, limit],
    enabled: !!coordinates,
    queryFn: async () => {
      if (!coordinates) throw new Error("No coordinates provided");

      if (type === "breweries") {
        return await searchService.getNearbyBreweries(coordinates, limit);
      } else {
        return await searchService.getNearbyRestaurants(coordinates, limit);
      }
    },
  });
}
