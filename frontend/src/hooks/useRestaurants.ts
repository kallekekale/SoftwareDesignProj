import { useQuery } from "@tanstack/react-query";
import type { Coordinates } from "../types/brewery";
import { restaurantService } from "../services/restaurantService";

export function useRestaurants(
  coordinates: Coordinates | null,
  limit: number = 10
) {
  return useQuery({
    queryKey: ["restaurants", coordinates, limit],
    queryFn: () => {
      if (!coordinates) {
        throw new Error("No coordinates provided");
      }
      return restaurantService.getNearbyRestaurants(coordinates, limit);
    },
    enabled: !!coordinates,
  });
}
