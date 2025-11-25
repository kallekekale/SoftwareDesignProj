import { create } from "zustand";
import type { Coordinates } from "../types/brewery";

interface LocationState {
  selectedLocation: Coordinates | null;
  isGettingCurrentLocation: boolean;
  locationError: string | null;

  setLocation: (coordinates: Coordinates) => void;
  getCurrentLocation: () => void;
  clearLocationError: () => void;
}

export const useLocationStore = create<LocationState>((set) => ({
  selectedLocation: null,
  isGettingCurrentLocation: false,
  locationError: null,

  setLocation: (coordinates) => {
    set({
      selectedLocation: coordinates,
      locationError: null,
    });
  },

  getCurrentLocation: () => {
    if (!navigator.geolocation) {
      set({
        locationError: "Geolocation is not supported by your browser",
        isGettingCurrentLocation: false,
      });
      return;
    }

    set({ isGettingCurrentLocation: true, locationError: null });

    navigator.geolocation.getCurrentPosition(
      (position) => {
        set({
          selectedLocation: {
            latitude: position.coords.latitude,
            longitude: position.coords.longitude,
          },
          isGettingCurrentLocation: false,
        });
      },
      (error) => {
        set({
          locationError: `Unable to get location: ${error.message}`,
          isGettingCurrentLocation: false,
        });
      }
    );
  },

  clearLocationError: () => {
    set({ locationError: null });
  },
}));
