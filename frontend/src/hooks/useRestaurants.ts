import { useQuery } from "@tanstack/react-query";
import { restaurantService } from "../services/restaurantService";
import type { Coordinates } from "../types/restaurant";

export const useRestaurants = (
  coordinates: Coordinates,
  limit: number = 10
) => {
  return useQuery({
    queryKey: ["restaurants", coordinates, limit],
    queryFn: () =>
      restaurantService.getRestaurantsByDistance(coordinates, limit),
    enabled: !!coordinates.latitude && !!coordinates.longitude,
  });
};

export const useRestaurant = (id: string) => {
  return useQuery({
    queryKey: ["restaurant", id],
    queryFn: () => restaurantService.getRestaurantById(id),
    enabled: !!id,
  });
};
