# SoftwareDesignProj - Design Document

**Team:** Aleksanteri Heinonen, Juho Kangas, Kalle Kekäle, Nandan von Veh, Wilhem Tcheng  
**Version:** Midterm Submission

## 1. Overview

The goal of this application is to let a user pick a city and then browse breweries in that city (data from Open Brewery DB). After selecting a brewery, the app shows nearby restaurants (data from Yelp API) ordered by proximity to that brewery.

### High-level User Flow

1. User selects a city
2. The app fetches breweries in that city from Open Brewery DB, sorted by distance (if coordinates are available)
3. User selects a brewery from the list
4. The app fetches nearby restaurants around that brewery from Yelp and shows them

### 1.1 Use Case Diagrams

#### Main Use Case Diagram

```
        ┌─────────────────┐
        │      User       │
        └────────┬────────┘
                 │
        ┌────────┴────────┐
        │                 │
        ▼                 ▼
   ┌─────────────┐  ┌──────────────────┐
   │ Select City │  │ Search Breweries │
   └─────────────┘  │   in City        │
        │           └──────────────────┘
        │                 │
        ▼                 ▼
   ┌─────────────────────────────┐
   │  View Brewery List          │
   │  (sorted by distance)       │
   └────────────┬────────────────┘
                │
                ▼
        ┌──────────────────┐
        │ Select Brewery   │
        └────────┬─────────┘
                 │
                 ▼
        ┌──────────────────────┐
        │ View Brewery Details │
        └────────┬─────────────┘
                 │
                 ▼
        ┌──────────────────────────┐
        │ Search Nearby            │
        │ Restaurants (by Yelp)    │
        └────────┬─────────────────┘
                 │
                 ▼
        ┌──────────────────────────┐
        │ View Restaurant List     │
        │ (sorted by proximity)    │
        └──────────────────────────┘
```

## 2. Data Sources & APIs

This section answers: Which data sources will be used and how?

### 2.1 Open Brewery DB

- **URL:** `https://api.openbrewerydb.org/v1/breweries`
- **Authentication:** None (public)
- **Usage in our app:**
  - Get all breweries in a given city
  - Get location (lat/lon) of the selected brewery

### 2.2 Yelp API (Fusion)

- **URL (base):** `https://api.yelp.com/v3/businesses/search`
- **Authentication:** Required (Bearer token / API key)
- **Usage in our app:**
  - Given the brewery's coordinates (lat, lon), search for nearby restaurants

## 3. Planned Technologies

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
                        │   Yelp API   │
                        │              │
                        └──────────────┘
```

### 4.2 Component Responsibilities

#### Frontend Components

- **App Component:** Application shell and routing
- **Brewery List:** Listing and filtering of breweries
- **Restaurant List:** Display of nearby restaurants

#### Backend Components

- **HttpRequester:** Generic HTTP client for external API calls
- **SearchController:** Unified REST endpoints for search operations
- **SearchService:** Coordinates brewery and restaurant searches with caching
- **OpenBreweryDbService:** Brewery data fetching and processing
- **YelpPlacesService:** Restaurant data fetching and processing
- **DistanceService:** Geographic distance calculations

### 4.3 Component Diagram

```
┌────────────────────────────────────────────────────────────────────┐
│                        FRONTEND (React + TypeScript)               │
├────────────────────────────────────────────────────────────────────┤
│                                                                    │
│  ┌─────────────────────────────────────────────────────────────┐   │
│  │                    UI Components                            │   │
│  ├─────────────────────────────────────────────────────────────┤   │
│  │  • App (Main Router)                                        │   │
│  │  • BreweryList (Primary Display Component)                  │   │
│  └─────────────────────────────────────────────────────────────┘   │
│                              △                                     │
│                              │ uses                                │
│  ┌─────────────────────────────────────────────────────────────┐   │
│  │                    Custom Hooks                             │   │
│  ├─────────────────────────────────────────────────────────────┤   │
│  │  • useNearbyData (fetches breweries & restaurants data)     │   │
│  └─────────────────────────────────────────────────────────────┘   │
│                              △                                     │
│                              │ uses                                │
│  ┌─────────────────────────────────────────────────────────────┐   │
│  │              Services & State Management                    │   │
│  ├─────────────────────────────────────────────────────────────┤   │
│  │  • searchService (API calls to backend)                     │   │
│  │  • queryClient (React Query caching)                        │   │
│  │  • locationStore (Zustand state - location selection)       │   │
│  └─────────────────────────────────────────────────────────────┘   │
│                                △                                   │
│                                │ HTTP calls                        │
└────────────────────────────────┼──────────────────────────────────┘
                                 │
                                 │ REST API
                                 ▼
┌────────────────────────────────────────────────────────────────────┐
│                   BACKEND (Spring Boot + Java)                     │
├────────────────────────────────────────────────────────────────────┤
│                                                                    │
│  ┌─────────────────────────────────────────────────────────────┐   │
│  │                    REST Controllers                         │   │
│  ├─────────────────────────────────────────────────────────────┤   │
│  │  • SearchController                                         │   │
│  │    - POST /api/search/breweries                             │   │
│  │    - POST /api/search/restaurants                           │   │
│  │    - GET /api/search/brewery/{id}                           │   │
│  └─────────────────────────────────────────────────────────────┘   │
│                              △                                     │
│                              │ delegates                           │
│  ┌─────────────────────────────────────────────────────────────┐   │
│  │                    Business Logic Services                  │   │
│  ├─────────────────────────────────────────────────────────────┤   │
│  │  • SearchService (coordinates searches & caching)           │   │
│  │  • OpenBreweryDbService (brewery retrieval & filtering)     │   │
│  │  • YelpPlacesService (restaurant search & details)          │   │
│  │  • DistanceService (proximity calculations)                 │   │
│  └─────────────────────────────────────────────────────────────┘   │
│                              △                                     │
│                              │ uses                                │
│  ┌─────────────────────────────────────────────────────────────┐   │
│  │              Infrastructure Services                        │   │
│  ├─────────────────────────────────────────────────────────────┤   │
│  │  • HttpRequesterService (generic HTTP client)               │   │
│  │  • GlobalExceptionHandler (cross-cutting error handling)    │   │
│  │  • HttpRequesterConfig (configuration & retry logic)        │   │
│  └─────────────────────────────────────────────────────────────┘   │
│                              △                                     │
│                              │ HTTP calls                          │
└────────────────────────────────┼──────────────────────────────────┘
                                 │
                    ┌────────────┼
                    │            │
                    ▼            ▼
         ┌──────────────────┐ ┌──────────────┐
         │ Open Brewery DB  │ │  Yelp API    │
         │     (Public)     │ │  (External)  │
         └──────────────────┘ └──────────────┘
```

**Component Responsibilities:**

This section describes the purpose and internal structure of each component in the system.

#### Frontend Components

**1. App (Router)**

- **Purpose:** Serves as the application shell and manages client-side routing
- **Responsibilities:**
  - Wraps the application with React Router for navigation
  - Defines route structure and maps URLs to components
- **Internal Structure:**
  - Uses `Routes` and `Route` components from react-router-dom
  - Route "/" maps to BreweryList component
  - Route "/restaurants/:breweryName" maps to RestaurantList component
  - Navigation between views passes brewery coordinates via route state

**2. BreweryList**

- **Purpose:** Primary UI component for displaying brewery information and handling user interactions
- **Responsibilities:**
  - Renders the brewery search interface with location selection
  - Displays list of breweries with distance information
  - Handles user interactions (location selection, current location request)
  - Shows loading and error states
- **Internal Structure:**
  - Uses `useNearbyData("breweries", ...)` hook for data fetching
  - Uses `locationStore` for managing selected location state
  - Manages local UI state (loading indicators, error messages)
  - Renders brewery cards with name, type, address, and distance
  - Navigates to restaurant view when brewery is selected

**3. useNearbyData (Custom Hook)**

- **Purpose:** Generic hook for fetching nearby breweries or restaurants with reactive state management
- **Responsibilities:**
  - Manages data fetching lifecycle for both breweries and restaurants
  - Provides loading, error, and data states to components
  - Handles query caching and invalidation
- **Internal Structure:**
  - Parameters: `type` ("breweries" | "restaurants"), `coordinates` (Coordinates | null), `limit` (number, default: 10)
  - Uses TanStack Query's `useQuery` hook
  - Query key: `["nearby", type, coordinates, limit]`
  - Query function: calls `searchService.getNearbyBreweries()` or `searchService.getNearbyRestaurants()` based on type
  - Enabled condition: only fetches when coordinates are not null
  - Returns: `{ data, isLoading, error }` from TanStack Query

**4. searchService (Service Object)**

- **Purpose:** Handles all API communication with backend search endpoints
- **Responsibilities:**
  - Makes HTTP requests to search API endpoints
  - Serializes request data and deserializes responses
  - Handles HTTP errors and throws appropriate exceptions
- **Internal Structure:**
  - Object: `searchService` with exported methods
  - Private field: `API_BASE = "/api/search"`
  - Method: `getNearbyBreweries(coordinates, limit)`
    - Constructs POST request to `/api/search/breweries?limit={limit}`
    - Sets Content-Type header to application/json
    - Sends coordinates as JSON body
    - Returns Promise<BreweryWithDistance[]>
    - Throws error if response status is not ok
  - Method: `getNearbyRestaurants(coordinates, limit)`
    - Constructs POST request to `/api/search/restaurants?limit={limit}`
    - Sets Content-Type header to application/json
    - Sends coordinates as JSON body
    - Returns Promise<YelpRestaurant[]>
    - Throws error if response status is not ok

**5. locationStore (Zustand State)**

- **Purpose:** Manages global location selection state across components
- **Responsibilities:**
  - Stores currently selected coordinates
  - Provides methods to update selected location
  - Handles geolocation API for current location requests
  - Manages loading and error states for location operations
  - Enables state sharing between components without prop drilling
- **Internal Structure:**
  - Uses Zustand for lightweight state management
  - State:
    - `selectedLocation` (Coordinates | null)
    - `isGettingCurrentLocation` (boolean)
    - `locationError` (string | null)
  - Actions:
    - `setLocation(coordinates)`: Sets selected location
    - `getCurrentLocation()`: Requests user's current location via browser geolocation API
    - `clearLocationError()`: Clears error messages
  - Subscribers automatically re-render when state changes

**6. queryClient**

- **Purpose:** Configures React Query for data fetching, caching, and synchronization
- **Responsibilities:**
  - Sets global defaults for query behavior
  - Manages cache invalidation and refetching strategies
  - Provides optimistic updates and background refetching
- **Internal Structure:**
  - Created with `new QueryClient()`
  - Default options: `staleTime: 5 minutes`, `refetchOnWindowFocus: false`
  - Wrapped in `QueryClientProvider` in main.tsx

#### Backend Components

**1. SearchController**

- **Purpose:** Unified REST controller for all search operations (breweries and restaurants)
- **Responsibilities:**
  - Receives and validates HTTP requests for search operations
  - Delegates business logic to SearchService
  - Returns properly formatted HTTP responses
- **Internal Structure:**
  - Annotation: `@RestController`, `@RequestMapping("/api/search")`
  - Dependencies (Injected in constructor): `SearchService`
  - Endpoints:
    - `POST /breweries?limit={number}`: Search breweries by coordinates
      - Request body: `CoordinateDto` (latitude, longitude)
      - Response: `List<OpenBreweryDbDistanceResponseDto>`
    - `POST /restaurants?limit={number}`: Search restaurants by coordinates
      - Request body: `CoordinateDto` (latitude, longitude)
      - Response: `List<YelpBusinessDistanceResponseDto>`
    - `GET /brewery/{breweryId}?restaurantLimit={number}`: Get brewery with nearby restaurants
      - Response: `BreweryWithRestaurantsDto`

**2. SearchService**

- **Purpose:** Coordinates brewery and restaurant searches with Redis caching
- **Responsibilities:**
  - Combines data from OpenBreweryDbService and YelpPlacesService
  - Implements caching strategy for search results
  - Provides unified interface for search operations
- **Internal Structure:**
  - Annotation: `@Service`
  - Dependencies (Injected in constructor): `OpenBreweryDbService`, `YelpPlacesService`
  - Methods:
    - `getBreweries(CoordinateDto, Integer limit)`: Cached brewery search
      - Cache name: "breweries"
      - Cache key: "{latitude},{longitude},{limit}"
    - `getRestaurants(CoordinateDto, Integer limit)`: Cached restaurant search
      - Cache name: "restaurants"
      - Cache key: "{latitude},{longitude},{limit}"
    - `getBreweryWithRestaurants(String breweryId, Integer restaurantLimit)`: Cached combined query
      - Cache name: "breweryWithRestaurants"
      - Cache key: "{breweryId},{restaurantLimit}"
      - Fetches brewery by ID and nearby restaurants

**3. OpenBreweryDbService**

- **Purpose:** Handles brewery data retrieval from Open Brewery DB API and distance calculations
- **Responsibilities:**
  - Fetches brewery data from external API
  - Calculates distances between breweries and reference points
  - Filters and sorts breweries by proximity
  - Handles errors and throws domain-specific exceptions
- **Internal Structure:**
  - Annotation: `@Service`
  - Constants: `BASE_URL = "https://api.openbrewerydb.org/v1/breweries"`
  - Dependencies (Injected in constructor): `HttpRequester`, `DistanceService`
  - Methods:
    - `getBreweryById(String id)`: Fetches single brewery, throws `BreweryNotFoundException`
    - `getBreweriesByDistance(CoordinateDto, Integer perPage)`:
      - Constructs query URL with coordinates and pagination
      - Fetches breweries using HttpRequester
      - Maps each brewery to include calculated distance using DistanceService
      - Returns `List<OpenBreweryDbDistanceResponseDto>`
      - Throws `BreweryNotFoundWithDistException` on errors

**4. YelpPlacesService**

- **Purpose:** Manages restaurant data retrieval from Yelp API
- **Responsibilities:**
  - Searches for restaurants near given coordinates
  - Retrieves detailed business information including hours, price, website
  - Handles Yelp API authentication with Bearer token
- **Internal Structure:**
  - Annotation: `@Service`
  - Constants: `BASE_URL = "https://api.yelp.com/v3/businesses"`
  - Dependencies (Injected in constructor): `HttpRequester`
  - Configuration: `@Value("${yelp.api.key}")` for API key injection
  - Methods:
    - `getNearbyRestaurants(CoordinateDto, Integer limit)`: Returns nearby restaurants sorted by distance
      - Constructs search URL with coordinates and limit
      - Adds Authorization header with Bearer token
      - Returns `List<YelpBusinessDistanceResponseDto>`
    - `getBusinessById(String id)`: Returns detailed restaurant information
      - Throws `BusinessNotFoundException` if not found

**5. DistanceService**

- **Purpose:** Calculates geographic distances between coordinate pairs
- **Responsibilities:**
  - Implements Haversine formula for great-circle distance
  - Validates coordinate ranges
  - Returns distances in kilometers
- **Internal Structure:**
  - Annotation: `@Service`
  - Constants: `EARTH_RADIUS = 6371.0` (kilometers)
  - Methods:
    - `calculateDistance(CoordinateDto coord1, CoordinateDto coord2)`:
      - Validates coordinates (lat: -90 to 90, lon: -180 to 180)
      - Converts degrees to radians
      - Applies Haversine formula: `d = R * acos(sin(lat1) * sin(lat2) + cos(lat1) * cos(lat2) * cos(lon1 - lon2))`
      - Returns distance in kilometers
      - Throws `IllegalArgumentException` for invalid coordinates

**6. HttpRequesterService**

- **Purpose:** Provides generic HTTP client functionality for external API calls
- **Responsibilities:**
  - Makes HTTP GET/POST requests with proper headers
  - Handles connection timeouts and retries
  - Deserializes JSON responses to specified types
  - Logs requests and responses for debugging
- **Internal Structure:**
  - Annotation: `@Service`
  - Dependencies: `WebClient` (configured by HttpRequesterConfig)
  - Methods:
    - `get(String url, Class<T> responseType)`: Performs GET request
    - `post(String url, Object body, Class<T> responseType)`: Performs POST request
  - Features: Retry logic, timeout handling, error mapping

**7. GlobalExceptionHandler**

- **Purpose:** Provides centralized error handling across all REST endpoints
- **Responsibilities:**
  - Catches and handles domain-specific exceptions
  - Formats error responses consistently
  - Logs errors with appropriate severity levels
  - Maps exceptions to HTTP status codes
- **Internal Structure:**
  - Annotation: `@RestControllerAdvice`
  - Exception handlers:
    - `@ExceptionHandler(BreweryNotFoundException.class)`: Returns 404
    - `@ExceptionHandler(BreweryNotFoundWithDistException.class)`: Returns 404
    - `@ExceptionHandler(Exception.class)`: Returns 500 for unexpected errors
  - Response format: `{ "status": number, "message": string, "details": string }`

**8. HttpRequesterConfig**

- **Purpose:** Configures HTTP client with timeouts, retry strategies, and connection pooling
- **Responsibilities:**
  - Creates and configures WebClient bean
  - Sets connection and read timeouts
  - Configures retry logic for transient failures
  - Sets up connection pool parameters
- **Internal Structure:**
  - Annotation: `@Configuration`
  - Bean method: `webClient()` returns configured `WebClient`
  - Configuration: Connection timeout, read timeout, retry attempts, backoff strategy

#### External APIs

**1. Open Brewery DB**

- **Purpose:** Third-party public API providing brewery data
- **Usage:** Queried by OpenBreweryDbService for brewery information
- **Authentication:** None required (public API)

**2. Yelp API (Fusion)**

- **Purpose:** Third-party external API providing restaurant and business information
- **Usage:** Queried by YelpPlacesService for restaurant search and details
- **Authentication:** Bearer token required (API key)

### 4.4 Data Flow

**Brewery Search Flow:**

1. User selects location in BreweryList component
2. `useNearbyData("breweries", ...)` hook triggers
3. `searchService.getNearbyBreweries()` makes POST request to `/api/search/breweries`
4. `SearchController` delegates to `SearchService.getBreweries()` (checks Redis cache first)
5. If cache miss, `OpenBreweryDbService` fetches from Open Brewery DB API
6. `DistanceService` calculates distances from user's coordinates
7. Results cached in Redis (10 min TTL) and returned to frontend
8. Frontend displays brewery list with distances

**Restaurant Search Flow:**

1. User clicks brewery in BreweryList
2. Navigation to RestaurantList with brewery coordinates
3. `useNearbyData("restaurants", ...)` hook triggers
4. `searchService.getNearbyRestaurants()` makes POST request to `/api/search/restaurants`
5. `SearchController` delegates to `SearchService.getRestaurants()` (checks Redis cache first)
6. If cache miss, `YelpPlacesService` fetches from Yelp API with Bearer token auth
7. Results cached in Redis (10 min TTL) and returned to frontend
8. Frontend displays restaurant list with details (hours, price, rating, distance)

## 5. API Design

### 5.1 Backend REST Endpoints

#### Search API Endpoints

**POST /api/search/breweries?limit={number}**

Request Body:

```json
{
  "latitude": number,
  "longitude": number
}
```

Response:

```json
[
  {
    "id": "string",
    "name": "string",
    "brewery_type": "string",
    "address_1": "string",
    "city": "string",
    "state_province": "string",
    "postal_code": "string",
    "country": "string",
    "longitude": "string",
    "latitude": "string",
    "phone": "string",
    "website_url": "string",
    "distance": number
  }
]
```

**POST /api/search/restaurants?limit={number}**

Request Body:

```json
{
  "latitude": number,
  "longitude": number
}
```

Response:

```json
[
  {
    "id": "string",
    "name": "string",
    "rating": number,
    "review_count": number,
    "distance": number,
    "location": {
      "address1": "string",
      "city": "string",
      "zip_code": "string",
      "country": "string",
      "state": "string"
    },
    "image_url": "string",
    "price": "string",
    "url": "string",
    "business_hours": []
  }
]
```

**GET /api/search/brewery/{breweryId}?restaurantLimit={number}**

Response: Combined brewery and restaurant data

```json
{
  "brewery": {
    "id": "string",
    "name": "string",
    "distance": 0
  },
  "restaurants": []
}
```

### 5.2 Error Handling

- Use standard HTTP status codes for different scenarios
- Consistent error response format:

```json
{
  "status": 404,
  "message": "Resource not found",
  "details": "No breweries found in the specified city"
}
```

> **Note:** Some content in Sections 4–5 originated from AI-assisted drafting; see Section 6.5.

## 6. Design Decisions

This section documents the key design decisions made throughout the project, including technology choices, architectural patterns, and responsibility division.

### 6.1 Technology Stack Selection

#### Frontend Technologies

**React 19 with TypeScript**

- Frontend framework with type safety for component-based UI development and compile-time error checking

**Vite 7.1.7**

- Build tool providing fast development server, hot module replacement, and built-in proxy configuration for CORS handling

**TanStack Query 5.90.5 (React Query)**

- Data fetching and caching library for managing server state with automatic refetching and loading/error states

**React Router DOM 7.9.6**

- Client-side routing library for declarative navigation and route management

**Zustand 5.0.8**

- Lightweight state management library for global client state (location selection)

**Prettier 3.6.2**

- Code formatter ensuring consistent style across the codebase

**ESLint**

- Static code analysis tool for catching errors and enforcing best practices

#### Backend Technologies

**Spring Boot with Java 21**

- Backend framework for building REST APIs with dependency injection and external service integration

**Maven**

- Dependency management and build automation tool for the Java project

**Spring Web**

- Core module for building RESTful endpoints with HTTP handling utilities

**Spring WebClient**

- Reactive HTTP client for external API calls with timeout and retry support

### 6.2 Design Patterns

**Service Object Pattern**

- Frontend services (searchService) implemented as exported objects with methods for API communication

**Custom Hooks Pattern**

- Data fetching logic encapsulated in custom hooks (useNearbyData) to separate concerns from UI rendering and provide reusable logic for multiple data types

**Dependency Injection**

- Backend services use constructor-based dependency injection for explicit dependencies and testability

**Generic HTTP Client**

- Centralized HttpRequester (interface and implementation) handles all external API calls with consistent timeout, retry, and error handling logic

**Facade Pattern**

- SearchService acts as a facade coordinating OpenBreweryDbService and YelpPlacesService, providing a simplified unified API to the controller layer

### 6.3 External APIs

**Open Brewery DB**

- Public API providing brewery data including names, types, addresses, and coordinates
- No authentication required
- Used for brewery search and listing functionality

**Yelp Fusion API**

- External API providing restaurant and business information
- Requires Bearer token authentication
- Used for nearby restaurant search and detailed business information (website, price, hours)

### 6.4 Responsibility Division

**Frontend Architecture**

- **UI Layer (Components):** BreweryList and RestaurantList handle rendering and user interactions
- **Data Layer (Hooks):** useNearbyData manages data fetching lifecycle and caching for both breweries and restaurants
- **Service Layer (Services):** searchService handles HTTP communication with backend
- **State Management:** TanStack Query for server state, Zustand for client state (location selection)

**Backend Architecture**

- **Controllers:** SearchController handles HTTP concerns (request/response formatting, validation)
- **Coordination Services:** SearchService orchestrates multiple data sources and implements caching
- **Domain Services:** Implement business logic (OpenBreweryDbService, YelpPlacesService, DistanceService)
- **Infrastructure:** Generic utilities (HttpRequester, error handling, Redis cache configuration)

### 6.5 Other Design Decisions

**CORS Handling**

- Vite proxy configuration used instead of backend CORS headers to avoid preflight requests during development

**Error Handling**

- GlobalExceptionHandler provides centralized error handling using Spring's @RestControllerAdvice for consistent error responses

**Distance Calculation**

- Haversine formula implemented in DistanceService for calculating great-circle distances between coordinates

**API Design**

- POST method used for coordinate-based searches to send complex query parameters in request body rather than URL

### 6.6 Justification and Quality Requirements

Our design decisions were primarily guided by maintainability, ease of development, and code quality. We chose TypeScript and Spring Boot to catch errors early and make the codebase easier to understand and modify for all team members. The use of established patterns like dependency injection, custom hooks, and service objects helps organize code in a way that's familiar to developers and makes testing simpler. External libraries like TanStack Query and React Router were selected because they solve common problems well and reduce the amount of custom code we need to write and maintain.

Performance and practical development needs also influenced our choices. Vite provides fast build times and quick feedback during development, which speeds up the development process. The Vite proxy configuration simplifies local development by avoiding CORS issues without complicated backend setup. For distance calculations, we implemented the Haversine formula because it provides sufficient accuracy for our needs while being straightforward to implement. Overall, our decisions balance getting features working quickly while keeping the code organized and maintainable for future development.

## 7. Process & Evaluation

### 7.1 Division of Work

Who did what, responsibilities and roles.

#### Wilhelm

- Restructured design document.
- Planned what needs to be done for the midterm submission.
- Wrote about AI usage.
- Made the main use case diagram.
- Documented extra work.

#### Juho

- Created structure for backend implementing global error handling, generic http requester, initial brewery CRUD DTOs.
- Rewieved the PRs of other members .

#### Nandan

- Implemented the base for the frontend frontend.
- Documented self-assesment, changes to original plan, extra work,
  component responsibilities, internal structure, functions of components and
  design decisions.

#### Kalle

- Worked on implementing "Feature: Detailed Restaurant Information" from the extra work section but was unable to finish it in a timely manner and unfortunately the functionality is still very much a work in progress. Will aim to finish this in the near future.

#### Aleksanteri

- Created Yelp for business account & implemented Yelp controller.
- Implemented Redis cache

### 7.2 Self-Assessment

Overall the project has been completed successfully. We met all primary goals for the final submission: the core brewery lookup flow is implemented end-to-end, and the extra feature to surface detailed restaurant information has been successfully integrated. Documentation is comprehensive and reflects all design decisions and functionality, which has helped keep everyone aligned throughout development.

What went well

- Feature implementation: The additional restaurant-detail feature was implemented cleanly and integrated into the existing backend and frontend flow.
- Documentation: The design and API documentation were improved alongside the implementation, making it easier to understand how the services interact and how to extend them.
- Collaboration: The team has collaborated effectively using GitHub, pull requests, and code reviews. Regular check-ins and distributed responsibilities helped maintain steady progress.

Challenges and minor issues

- Integration complexity: Adding extra API calls required careful design to avoid duplicating logic and to keep error handling consistent; this introduced more integration work than initially expected.
- API management: Handling external API keys, rate limits, and differing response shapes (Open Brewery DB vs. Yelp) required extra testing.
- Communication hiccups: Small miscommunications occurred, which occasionally led to small merge conflicts.

Team availability and coordination

- Throughout the project everyone has been balancing other studies and work commitments, but we have managed to achieve our planned goals by proactively sharing availability and progress updates.
- By informing each other about when we are available or busy, the team avoided major scheduling conflicts. This transparency meant tasks were reassigned or sequenced when necessary, and everyone contributed their part to move the project forward.

Key learnings

- Modular service design matters: Encapsulating external API logic in dedicated services (HttpRequester, YelpPlacesService, OpenBreweryDbService) makes adding functionality less risky and easier to test.
- Clear API contracts and examples prevent rework: Keeping the API design and example payloads up-to-date in the documentation helped preventing ambiguities.
- Prioritize error handling and UX for degraded external services: Designing consistent error responses and graceful UI fallbacks improves resilience when third-party services fail or are rate-limited.

Overall, the project was completed successfully. The implementation of all planned features, the comprehensive documentation, and effective team collaboration resulted in a fully functional application that meets all project requirements.

### 7.3 Changes to the Original Plan

During development we made a deliberate shift from one of our original feature goals. The original plan included adding an interactive map view that would display breweries and nearby restaurants visually. After early investigation and prioritization discussions, the team decided to postpone the map and instead invest the same effort in enriching restaurant details (website link, price category and opening hours). The change was driven by a desire to deliver higher immediate user value within the same time budget.

What changed

- Deferred: Interactive map view (showing brewery and restaurant markers on a map).
- Added (in place of the map): Detailed restaurant information surfaced from Yelp — specifically website links, price tiers, and opening hours — implemented as described in Section 6.4.

Reasons for the change

- Implementation complexity: A robust map integration requires additional dependencies (map library, map tiles or provider such as Mapbox/OpenStreetMap), correct handling of coordinate projections and markers, mobile/responsive UI work, and additional API/configuration work for map keys and usage quotas.
- Higher perceived user value: The team judged that surfacing concrete decision-making information (website, price, hours) would be immediately more useful for users choosing a restaurant than a map visualization.
- Lower risk to core flow: Adding restaurant details reused existing Yelp endpoints and fit naturally into our current backend/service structure, whereas a map would have required new cross-cutting UI and UX work and more integration testing.

### 7.4 Extra Work

Beyond the original plan, we implemented enhanced restaurant information retrieval from the Yelp API. Rather than displaying only basic proximity data, we extended the restaurant list feature to fetch and present detailed information about each establishment when selected by the user.

**Feature: Detailed Restaurant Information**

When a user selects a restaurant from the nearby restaurants list, the application will retrieve comprehensive details from Yelp, including:

- A direct link to the restaurant's website (when available).
- A price category indicator (e.g. $, $$, $$$).
- Opening hours (displayed as today's hours and an optional weekly schedule).

Feature summary

- Website link: When a restaurant has a website URL in Yelp's response we show a clearly labeled link in the restaurant detail view. The link opens in a new tab and uses safe attributes (rel="noopener noreferrer").
- Price category: We map Yelp's price string (for example "$$", "$$$") to a compact UI element next to the restaurant name so users can quickly compare cost levels.
- Opening hours: We request the restaurant's hours information and present today's opening hours prominently; users can expand the view to see the full weekly schedule where available. If hours data is absent, the UI shows a short, user-friendly fallback message.

**Implementation Details:**

Backend:

- Endpoint changes:

  - The unified SearchController at `/api/search` handles all search operations
  - Restaurant details (hours, price, url) are included in the search response via `YelpBusinessDistanceResponseDto`
  - The Yelp API search endpoint returns comprehensive business information including hours, ratings, and website URLs

- Service layer:
  - `YelpPlacesService.getNearbyRestaurants()`: Queries Yelp API with coordinates and returns full business details
  - `YelpPlacesService.getBusinessById()`: Fetches individual business details by Yelp ID
  - Both methods use Bearer token authentication via `@Value("${yelp.api.key}")`
  - HttpRequester supports custom headers for API authentication
- Data mapping and defensive handling:

  - Price and url are optional in Yelp responses; mapping code checks for presence and returns null/empty values where appropriate.
  - Hours are normalized into a consistent format (weekday -> open/close times) and the service calculates today’s schedule based on the brewery/restaurant timezone if available; otherwise it includes the raw hours structure returned by Yelp.

- Error handling:
  - Backend returns consistent error responses when Yelp business details are unavailable (e.g., 404 when Yelp does not have details, 503 on rate limit/third-party failure).

Frontend:

- `RestaurantList` component: Displays restaurant information retrieved from the search endpoint
- Restaurant details are shown directly in the list view, including:
  - Restaurant image (`image_url`)
  - Name and rating (⭐ with review count)
  - Price category (e.g., $, $$, $$$)
  - Address and distance from brewery
  - Business hours formatted by day with open/close times
  - "Open Now" / "Closed" status indicator (🟢/🔴)
  - "View on Yelp" link opening in new tab with `target="_blank"` and `rel="noopener noreferrer"`
- The `useNearbyData("restaurants", ...)` hook fetches all this data in a single API call
- No separate detail view needed - all information displayed in list format

**Documentation**

We made the documention much more comprehensive than required in the submission guidelines.

### 7.5 AI Usage

**Tools used**

- GitHub Copilot
- ChatGPT 5
- Claude 4.5

**Application Areas**

- Translating text to English.
- Design Document drafting (Sections 4-5).
- Inline comments.
- Implementing APIs.
- Frontend drafting.
- Making diagrams look formatted.

**Human Review**

- All AI-generated content underwent thorough review to ensure accuracy, relevance, and alignment with project requirements.

No sensitive data was shared with AI
