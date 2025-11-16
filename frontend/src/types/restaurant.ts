export interface Coordinates {
  latitude: number;
  longitude: number;
}

export interface Location {
  address1: string | null;
  address2: string | null;
  address3: string | null;
  city: string;
  zip_code: string;
  country: string;
  state: string;
  display_address?: string[];
}

export interface Restaurant {
  id: string;
  name: string;
  rating: number;
  review_count: number;
  distance: number;
  location: Location;
  image_url: string;
  price?: string; // $, $$, $$$, $$$$
  url?: string; // Link to Yelp page
}
