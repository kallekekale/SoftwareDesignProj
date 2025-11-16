import { useQuery } from "@tanstack/react-query";
import { breweryService } from "../services/breweryService";
import type { Coordinates } from "../types/brewery";

/**
 * Custom hook to fetch breweries sorted by distance from given coordinates
 * @param coordinates - The origin coordinates (latitude, longitude)
 * @param perPage - Number of results to return (default: 10)
 * @returns TanStack Query result with breweries data, loading state, and error
 */
export const useBreweries = (
  coordinates: Coordinates | null,
  perPage: number = 10,
) => {
  return useQuery({
    queryKey: ["breweries", coordinates, perPage],
    queryFn: () => breweryService.getBreweriesByDistance(coordinates!, perPage),
    enabled: coordinates !== null,
  });
};
