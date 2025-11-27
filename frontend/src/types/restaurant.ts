export interface YelpLocation {
  address1: string;
  address2?: string;
  address3?: string;
  city: string;
  state: string;
  zip_code: string;
  country: string;
}

export interface YelpOpenHours {
  is_overnight: boolean;
  start: string;
  end: string;
  day: number;
}

export interface YelpHours {
  open: YelpOpenHours[];
  hours_type: string;
  is_open_now: boolean;
}

export interface YelpRestaurant {
  id: string;
  name: string;
  rating: number | null;
  review_count: number | null;
  distance: number | null;
  location: YelpLocation;
  image_url: string;
  price?: string;
  url?: string;
  business_hours?: YelpHours[];
}
