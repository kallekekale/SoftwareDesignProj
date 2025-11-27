import { useNavigate, useParams, useLocation } from "react-router-dom";
import { useRestaurants } from "../hooks/useRestaurants";
import type { Coordinates } from "../types/brewery";
import type { YelpRestaurant } from "../types/restaurant";
import { formatTime } from "../lib/utils";

const DAY_NAMES = ["Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"];

export default function RestaurantList() {
  const navigate = useNavigate();
  const { breweryName } = useParams<{ breweryName: string }>();
  const location = useLocation();
  const breweryCoordinates = location.state?.coordinates as Coordinates | undefined;

  const {
    data: restaurants,
    isLoading,
    error,
  } = useRestaurants(breweryCoordinates || null, 10);

  const handleGoBack = () => {
    navigate("/");
  };

  if (!breweryCoordinates) {
    return (
      <div className="app-container">
        <div className="error-message">
          <p>No brewery coordinates provided.</p>
          <button onClick={handleGoBack} className="location-button">
            ← Back to Breweries
          </button>
        </div>
      </div>
    );
  }

  return (
    <div className="app-container">
      <div className="restaurant-page">
        <div className="restaurant-header">
          <button onClick={handleGoBack} className="back-button">
            ← Back to Breweries
          </button>
          <h2>Restaurants near {decodeURIComponent(breweryName || "")}</h2>
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
                    {restaurant.rating !== null && (
                      <span className="rating">
                        ⭐ {restaurant.rating.toFixed(1)}
                      </span>
                    )}
                    {restaurant.review_count !== null && (
                      <span className="review-count">
                        ({restaurant.review_count} reviews)
                      </span>
                    )}
                    {restaurant.price && (
                      <span className="price-category">{restaurant.price}</span>
                    )}
                  </div>

                  <p className="restaurant-location">
                    {restaurant.location.address1}
                    {restaurant.location.city &&
                      `, ${restaurant.location.city}`}
                  </p>

                  {restaurant.distance !== null && (
                    <p className="restaurant-distance">
                      <strong>Distance:</strong>{" "}
                      {(restaurant.distance / 1000).toFixed(2)} km
                    </p>
                  )}

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
