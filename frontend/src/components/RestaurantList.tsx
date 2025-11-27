import { useRestaurants } from "../hooks/useRestaurants";
import type { Coordinates } from "../types/brewery";
import type { YelpRestaurant } from "../types/restaurant";

interface RestaurantListProps {
  breweryCoordinates: Coordinates;
  breweryName: string;
  onClose: () => void;
}

const DAY_NAMES = ["Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"];

function formatTime(time: string): string {
  // Convert format from "1730" to "17:30" (24-hour format)
  const hours = time.substring(0, 2);
  const minutes = time.substring(2, 4);
  return `${hours}:${minutes}`;
}

export default function RestaurantList({
  breweryCoordinates,
  breweryName,
  onClose,
}: RestaurantListProps) {
  const {
    data: restaurants,
    isLoading,
    error,
  } = useRestaurants(breweryCoordinates, 10);

  return (
    <div className="restaurant-overlay">
      <div className="restaurant-modal">
        <div className="restaurant-header">
          <h2>Restaurants near {breweryName}</h2>
          <button onClick={onClose} className="close-button">
            ✕
          </button>
        </div>

        {isLoading && <p className="status-message">Loading restaurants...</p>}

        {error && (
          <p className="error-message">
            Error loading restaurants:{" "}
            {error instanceof Error ? error.message : "Unknown error"}
          </p>
        )}

        {restaurants && restaurants.length > 0 && (
          <ul className="restaurant-list">
            {restaurants.map((restaurant: YelpRestaurant) => (
              <li key={restaurant.id} className="restaurant-item">
                {restaurant.image_url && (
                  <img
                    src={restaurant.image_url}
                    alt={restaurant.name}
                    className="restaurant-image"
                  />
                )}
                <div className="restaurant-details">
                  <h3>{restaurant.name}</h3>

                  <div className="restaurant-info">
                    <span className="rating">
                      ⭐ {restaurant.rating.toFixed(1)}
                    </span>
                    <span className="review-count">
                      ({restaurant.review_count} reviews)
                    </span>
                    {restaurant.price && (
                      <span className="price-category">{restaurant.price}</span>
                    )}
                  </div>

                  <p className="restaurant-location">
                    {restaurant.location.address1}
                    {restaurant.location.city &&
                      `, ${restaurant.location.city}`}
                  </p>

                  <p className="restaurant-distance">
                    <strong>Distance:</strong>{" "}
                    {(restaurant.distance / 1000).toFixed(2)} km
                  </p>

                  {restaurant.business_hours &&
                    restaurant.business_hours.length > 0 && (
                      <div className="restaurant-hours">
                        <strong>Hours:</strong>
                        <div className="hours-list">
                          {restaurant.business_hours[0].open.map(
                            (hours, index) => (
                              <div key={index} className="hours-item">
                                <span className="day">
                                  {DAY_NAMES[hours.day]}:
                                </span>
                                <span className="time">
                                  {formatTime(hours.start)} -{" "}
                                  {formatTime(hours.end)}
                                </span>
                              </div>
                            )
                          )}
                        </div>
                        {restaurant.business_hours[0].is_open_now !==
                          undefined && (
                          <p
                            className={`open-status ${restaurant.business_hours[0].is_open_now ? "open" : "closed"}`}
                          >
                            {restaurant.business_hours[0].is_open_now
                              ? "🟢 Open Now"
                              : "🔴 Closed"}
                          </p>
                        )}
                      </div>
                    )}

                  {restaurant.url && (
                    <a
                      href={restaurant.url}
                      target="_blank"
                      rel="noopener noreferrer"
                      className="restaurant-link"
                    >
                      View on Yelp →
                    </a>
                  )}
                </div>
              </li>
            ))}
          </ul>
        )}

        {restaurants && restaurants.length === 0 && (
          <p className="status-message">
            No restaurants found near this brewery.
          </p>
        )}
      </div>
    </div>
  );
}
