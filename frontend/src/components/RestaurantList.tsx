import { useRestaurants } from "../hooks/useRestaurants";
import type { Coordinates } from "../types/restaurant";
import "./RestaurantList.css";

interface RestaurantListProps {
  coordinates: Coordinates;
  limit?: number;
}

export const RestaurantList = ({
  coordinates,
  limit = 10,
}: RestaurantListProps) => {
  const {
    data: restaurants,
    isLoading,
    error,
  } = useRestaurants(coordinates, limit);

  if (isLoading) {
    return <div className="loading">Loading restaurants...</div>;
  }

  if (error) {
    return (
      <div className="error">Error loading restaurants: {error.message}</div>
    );
  }

  if (!restaurants || restaurants.length === 0) {
    return <div className="no-results">No restaurants found nearby.</div>;
  }

  return (
    <div className="restaurant-list">
      <h2>Nearby Restaurants</h2>
      <div className="restaurants">
        {restaurants.map((restaurant) => (
          <div key={restaurant.id} className="restaurant-card">
            <div className="restaurant-header">
              <h3>{restaurant.name}</h3>
              {restaurant.is_closed && (
                <span className="status-badge closed">Closed</span>
              )}
              {restaurant.is_closed === false && (
                <span className="status-badge open">Open</span>
              )}
            </div>

            {restaurant.image_url && (
              <img
                src={restaurant.image_url}
                alt={restaurant.name}
                className="restaurant-image"
              />
            )}

            <div className="restaurant-details">
              <div className="rating-section">
                <span className="rating">⭐ {restaurant.rating}</span>
                <span className="review-count">
                  ({restaurant.review_count} reviews)
                </span>
                {restaurant.price && (
                  <span className="price-category">{restaurant.price}</span>
                )}
              </div>

              <div className="location">
                <p>{restaurant.location.display_address.join(", ")}</p>
              </div>

              <div className="distance">
                <span>
                  📍 {(restaurant.distance / 1000).toFixed(2)} km away
                </span>
              </div>

              {restaurant.url && (
                <a
                  href={restaurant.url}
                  target="_blank"
                  rel="noopener noreferrer"
                  className="yelp-link"
                >
                  View on Yelp →
                </a>
              )}
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};
