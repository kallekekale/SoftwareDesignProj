# SoftwareDesignProj

## Design Document

 **Team** Aleksanteri Heinonen, Juho Kangas, Kalle Kekäle, Nandan von Veh and Wilhem Tcheng
 **Version:** Prototype

## 1. Overview
The goal of this application is to let a user pick a **city** and then browse **breweries in that city** (data from **Open Brewery DB**). After selecting a brewery, the app shows **nearby restaurants** (data from **Yelp API**) ordered by proximity to that brewery.

**High-level user flow:**

1. User selects a **city**.
2. App fetches **breweries in that city** from **Open Brewery DB**, sorted by distance (if we have user/brewery coordinates).
3. User selects a **brewery** from the list.
4. App fetches **nearby restaurants** around that brewery from **Yelp** and shows them.

## 2. Data sources & APIs
This section answers: **“which data sources will be used and how.”**

### 2.1 Open Brewery DB
- **URL (current):** `https://api.openbrewerydb.org/v1/breweries`
- **Auth:** none (public)
- **Usage in our app:**
  - Get all breweries in a given city
  - Get location (lat/lon) of the selected brewery

### 2.2 Yelp API (Fusion)
- **URL (base):** `https://api.yelp.com/v3/businesses/search`
- **Auth:** required (Bearer token / API key)
- **Usage in our app:**
- Given the brewery’s coordinates (lat, lon), search for nearby restaurants

## 3. Planned technologies

### 3.1 Backend
- **Language:** Java
- **Framework:** Spring Boot
- **Key Dependencies:**
  - Spring Web (REST APIs)
  - Spring WebClient (API integration)
  - Maven (dependency management)

### 3.2 Frontend
- **Language:** TypeScript
- **Framework:** React
- **Build Tool:** Vite
- **Key Dependencies:**
  - React Query (API data fetching)
  - ESLint (code quality)

## 4. Architecture

### 4.1 System Architecture
```
┌─────────────┐         ┌──────────────┐         ┌─────────────────┐
│             │         │              │         │                 │
│   React     │ ──────► │  Spring Boot │ ──────► │ Open Brewery DB │
│  Frontend   │         │   Backend    │         │                 │
│             │         │              │         │                 │
└─────────────┘         └──────────────┘         └─────────────────┘
                              │
                              │
                              ▼
                        ┌──────────────┐
                        │              │
                        │  Yelp API    │
                        │              │
                        └──────────────┘
```

### 4.2 Component Responsibilities

#### Frontend Components
- **App Component:** Main application container and routing
- **City Selection:** Search and selection of cities
- **Brewery List:** Display and filtering of breweries
- **Restaurant List:** Display of nearby restaurants
- **Map View:** (Optional) Visual display of locations

#### Backend Components
- **HttpRequester:** Generic HTTP client for external API calls
- **OpenBreweryDbService:** Handles brewery data fetching and processing
- **OpenBreweryDbController:** REST endpoints for brewery operations
- **YelpService:** (To be implemented) Handles restaurant data fetching

### 4.3 Data Flow
1. Frontend makes API call to backend for city search
2. Backend fetches brewery data from Open Brewery DB
3. User selects brewery, triggering restaurant search
4. Backend fetches restaurant data from Yelp API
5. Frontend displays combined data to user

## 5. API Design

### 5.1 Backend REST Endpoints

#### Brewery Endpoints
```
GET /api/breweries/search?city={city}
Response: List of breweries in the specified city

GET /api/breweries/{id}
Response: Detailed information about a specific brewery
```

#### Restaurant Endpoints (To be implemented)
```
GET /api/restaurants?lat={latitude}&lon={longitude}
Response: List of restaurants near the specified coordinates
```

### 5.2 Error Handling
- Standard HTTP status codes for different scenarios
- Consistent error response format:
  ```json
  {
    "status": 404,
    "message": "Resource not found",
    "details": "No breweries found in the specified city"
  }
  ```

(Part 4 and 5 have been made with Claude 3.5 Sonnet ai)