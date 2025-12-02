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
- **City Selection:** Search and selection of cities
- **Brewery List:** Listing and filtering of breweries
- **Restaurant List:** Display of nearby restaurants
- **Map View (optional):** Visual display of locations

#### Backend Components

- **HttpRequester:** Generic HTTP client for external API calls
- **OpenBreweryDbService:** Brewery data fetching and processing
- **OpenBreweryDbController:** REST endpoints for brewery operations
- **YelpService:** Restaurant data fetching and processing

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
│  │  • useBreweries (fetches & caches brewery data)             │   │
│  └─────────────────────────────────────────────────────────────┘   │
│                              △                                     │
│                              │ uses                                │
│  ┌─────────────────────────────────────────────────────────────┐   │
│  │              Services & State Management                    │   │
│  ├─────────────────────────────────────────────────────────────┤   │
│  │  • breweryService (API calls to backend)                    │   │
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
│  │  • OpenBreweryDbController                                  │   │
│  │    - GET/POST /api/breweries/distance                       │   │
│  │  • YelpPlacesController                                     │   │
│  │    - GET/POST /api/restaurants/nearby                       │   │
│  │    - GET /api/restaurants/{id} (details)                    │   │
│  └─────────────────────────────────────────────────────────────┘   │
│                              △                                     │
│                              │ delegates                           │
│  ┌─────────────────────────────────────────────────────────────┐   │
│  │                    Business Logic Services                  │   │
│  ├─────────────────────────────────────────────────────────────┤   │
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
                    ┌────────────┼────────────┐
                    │            │            │
                    ▼            ▼            ▼
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
  - Single route "/" maps to BreweryList component
  - Can be extended with additional routes for restaurant details or other views

**2. BreweryList**

- **Purpose:** Primary UI component for displaying brewery information and handling user interactions
- **Responsibilities:**
  - Renders the brewery search interface with location selection
  - Displays list of breweries with distance information
  - Handles user interactions (location selection, current location request)
  - Shows loading and error states
- **Internal Structure:**
  - Uses `useBreweries` hook for data fetching
  - Uses `locationStore` for managing selected location state
  - Manages local UI state (loading indicators, error messages)
  - Renders brewery cards with name, type, address, and distance

**3. useBreweries (Custom Hook)**

- **Purpose:** Encapsulates brewery data fetching logic and provides reactive state management
- **Responsibilities:**
  - Manages brewery data fetching lifecycle
  - Provides loading, error, and data states to components
  - Handles query caching and invalidation
- **Internal Structure:**
  - Parameters: `coordinates` (Coordinates | null), `perPage` (number, default: 10)
  - Uses TanStack Query's `useQuery` hook
  - Query key: `["breweries", coordinates, perPage]`
  - Query function: calls `breweryService.getBreweriesByDistance()`
  - Enabled condition: only fetches when coordinates are not null
  - Returns: `{ data, isLoading, error }` from TanStack Query

**4. breweryService (Singleton Service)**

- **Purpose:** Handles all API communication with backend brewery endpoints
- **Responsibilities:**
  - Makes HTTP requests to brewery API endpoints
  - Serializes request data and deserializes responses
  - Handles HTTP errors and throws appropriate exceptions
- **Internal Structure:**
  - Class: `BreweryService` with singleton export pattern
  - Private field: `baseUrl = "/api/breweries"`
  - Method: `getBreweriesByDistance(coordinates, perPage)`
    - Constructs POST request to `/api/breweries/distance?per_page={perPage}`
    - Sets Content-Type header to application/json
    - Sends coordinates as JSON body
    - Returns Promise<BreweryWithDistance[]>
    - Throws error if response status is not ok

**5. locationStore (Zustand State)**

- **Purpose:** Manages global location selection state across components
- **Responsibilities:**
  - Stores currently selected coordinates
  - Provides methods to update selected location
  - Enables state sharing between components without prop drilling
- **Internal Structure:**
  - Uses Zustand for lightweight state management
  - State: `selectedLocation` (Coordinates | null)
  - Actions: `setLocation(coordinates)`, `clearLocation()`
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

**1. OpenBreweryDbController**

- **Purpose:** Exposes REST endpoints for brewery-related operations
- **Responsibilities:**
  - Receives and validates HTTP requests
  - Delegates business logic to OpenBreweryDbService
  - Returns properly formatted HTTP responses
- **Internal Structure:**
  - Annotation: `@RestController`, `@RequestMapping("/api/breweries")`
  - Dependencies: `OpenBreweryDbService` (autowired)
  - Endpoints:
    - `GET /{id}`: Returns single brewery by ID
    - `POST /distance?per_page={number}`: Returns breweries sorted by distance
      - Request body: `CoordinateDto` (latitude, longitude)
      - Response: `List<OpenBreweryDbDistanceResponseDto>`

**2. YelpPlacesController**

- **Purpose:** Exposes REST endpoints for restaurant search and details
- **Responsibilities:**
  - Handles restaurant search requests near given coordinates
  - Retrieves detailed information for specific restaurants
  - Validates request parameters and coordinates
- **Internal Structure:**
  - Annotation: `@RestController`, `@RequestMapping("/api/restaurants")`
  - Dependencies: `YelpPlacesService` (autowired)
  - Endpoints:
    - `POST /nearby?limit={number}`: Search restaurants near coordinates
    - `GET /{id}`: Get detailed restaurant information

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
  - Dependencies: `HttpRequester`, `DistanceService` (constructor injected)
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
  - Handles Yelp API authentication and rate limiting
- **Internal Structure:**
  - Annotation: `@Service`
  - Dependencies: `HttpRequester` (constructor injected)
  - Methods:
    - `searchNearby(CoordinateDto, Integer limit)`: Returns nearby restaurants
    - `getBusinessDetails(String id)`: Returns detailed restaurant information

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

### 4.4 Data Flow (will change)

1. Frontend calls backend for city-based brewery search
2. Backend fetches brewery data from Open Brewery DB
3. User selects a brewery, triggering restaurant search
4. Backend fetches restaurant data from Yelp API
5. Frontend displays combined results to the user

## 5. API Design

### 5.1 Backend REST Endpoints

#### OpenBreweryDB Brewery API Endpoints

**POST /api/breweries/distance?per_page={number}**

Request Body:

```json
{
  "coordinates": {
    "latitude": number,
    "longitude": number
  },
  "city": "string"
}
```

Response: List of breweries in the specified city/area

**GET /api/breweries/{id}**

Response: Detailed information about a specific brewery

#### Yelp Restaurants API Endpoints

**POST /api/yelp/restaurants/nearby?limit={number}**

Request Body:

```json
{
  "coordinates": {
    "latitude": number,
    "longitude": number
  }
}
```

Response: List of restaurants nearby

**GET /api/yelp/{id}**

Response: Details for a specific Yelp business

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

**Singleton Pattern**

- Frontend services (breweryService) implemented as singleton classes for consistent instances and encapsulated configuration

**Custom Hooks Pattern**

- Data fetching logic encapsulated in custom hooks (useBreweries) to separate concerns from UI rendering

**Dependency Injection**

- Backend services use constructor-based dependency injection for explicit dependencies and testability

**Generic HTTP Client**

- Centralized HttpRequesterService handles all external API calls with consistent timeout, retry, and error handling logic

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

- **UI Layer (Components):** BreweryList handles rendering and user interactions
- **Data Layer (Hooks):** useBreweries manages data fetching lifecycle and caching
- **Service Layer (Services):** breweryService handles HTTP communication
- **State Management:** TanStack Query for server state, Zustand for client state

**Backend Architecture**

- **Controllers:** Handle HTTP concerns (request/response formatting, validation)
- **Services:** Implement business logic (distance calculations, data transformation)
- **Infrastructure:** Generic utilities (HttpRequester, error handling)

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

Our design decisions were primarily guided by maintainability, ease of development, and code quality. We chose TypeScript and Spring Boot to catch errors early and make the codebase easier to understand and modify for all team members. The use of established patterns like dependency injection, custom hooks, and singleton services helps organize code in a way that's familiar to developers and makes testing simpler. External libraries like TanStack Query and React Router were selected because they solve common problems well and reduce the amount of custom code we need to write and maintain.

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
- Rewieved the PRs of other members.
- Worked on frontend state management with Zustand and refactoring.

#### Nandan

- Implemented and merged small frontend reconsturction.
- Documented self-assesment, changes to original plan and extra work.

#### Kalle

- Setup project structure
- Setup GitHub repo with CI/CD pipeline
- Configure vite dev server proxy to forward API requests to backend
- Implemented detailed restaurant information

#### Aleksanteri

- Created Yelp for business account & implemented Yelp controller.
- Implemented Redis cache

### 7.2 Self-Assessment

Overall the project is progressing well. We met the primary goals for the midterm: the core brewery lookup flow is implemented end-to-end, and the extra feature to surface detailed restaurant information has been successfully integrated. Documentation is in good shape and has been expanded to reflect design decisions and the new functionality, which has helped keep everyone aligned.

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

- Modular service design matters: Encapsulating external API logic in dedicated services (HttpRequester, YelpService, OpenBreweryDbService) makes adding functionality less risky and easier to test.
- Clear API contracts and examples prevent rework: Keeping the API design and example payloads up-to-date in the documentation helped preventing ambiguities.
- Prioritize error handling and UX for degraded external services: Designing consistent error responses and graceful UI fallbacks improves resilience when third-party services fail or are rate-limited.

Overall, the team is on track. The implementation of the additional feature and the improved documentation show solid progress, and the small communication issues are addressable with minor process adjustments.

### 7.3 Changes to the Original Plan

During development we made a deliberate shift from one of our original feature goals. The midterm plan included adding an interactive map view that would display breweries and nearby restaurants visually. After early investigation and prioritization discussions, the team decided to postpone the map and instead invest the same effort in enriching restaurant details (website link, price category and opening hours). The change was driven by a desire to deliver higher immediate user value within the same time budget.

What changed

- Deferred: Interactive map view (showing brewery and restaurant markers on a map).
- Added (in place of the map): Detailed restaurant information surfaced from Yelp — specifically website links, price tiers, and opening hours — implemented as described in Section 6.4.

Reasons for the change

- Implementation complexity: A robust map integration requires additional dependencies (map library, map tiles or provider such as Mapbox/OpenStreetMap), correct handling of coordinate projections and markers, mobile/responsive UI work, and additional API/configuration work for map keys and usage quotas.
- Higher perceived user value: The team judged that surfacing concrete decision-making information (website, price, hours) would be immediately more useful for users choosing a restaurant than a map visualization at the midterm stage.
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

- Endpoint changes:

  - We reused the existing Yelp-related endpoints and added/ensured support for retrieving more business details from... . The backend exposes this through GET /api/yelp/{id} which returns a RestaurantDetail DTO containing: id, name, coordinates, url, price, hours (structured).

- Service layer:
  - YelpService: Added a method getBusinessDetails(businessId) that calls Yelp's business details endpoint and normalizes the response into our DTO.
  - HttpRequester: Extended to support additional headers and safe timeout/retry settings used by the new calls.
- Data mapping and defensive handling:

  - Price and url are optional in Yelp responses; mapping code checks for presence and returns null/empty values where appropriate.
  - Hours are normalized into a consistent format (weekday -> open/close times) and the service calculates today’s schedule based on the brewery/restaurant timezone if available; otherwise it includes the raw hours structure returned by Yelp.

- Error handling:
  - Backend returns consistent error responses when Yelp business details are unavailable (e.g., 404 when Yelp does not have details, 503 on rate limit/third-party failure).

Frontend

- RestaurantList / RestaurantDetail components: When a user selects a restaurant, the frontend fetches (or uses already-fetched) detailed information and displays website link, price category and hours in a clearly separated section of the details panel.
- Website link: Renders as "Visit website" with target="\_blank" and rel="noopener noreferrer".
- Price: Displayed next to the restaurant name as the Yelp price string (or a dash/fallback when unavailable).
- Hours: Shows "Open now" / "Closed" status when hours and current time are available and provides today's hours inline. Users can expand to show a full weekly schedule if needed.

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
