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

### 4.3 Data Flow (will change)

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

## 6. Process & Evaluation

### 6.1 Division of Work

Who did what, responsibilities and roles.

#### Wilhelm

- Restructured design document.
- Planned what needs to be done for the midterm submission.
- Wrote about AI usage.
- Made the main use case diagram.
- Documented extra work.

#### Nandan

- Implemented and merged small frontend reconsturction
- Documented self-assesment, changes to original plan and extra work.

### 6.2 Self-Assessment

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

### 6.3 Changes to the Original Plan

During development we made a deliberate shift from one of our original feature goals. The midterm plan included adding an interactive map view that would display breweries and nearby restaurants visually. After early investigation and prioritization discussions, the team decided to postpone the map and instead invest the same effort in enriching restaurant details (website link, price category and opening hours). The change was driven by a desire to deliver higher immediate user value within the same time budget.

What changed
- Deferred: Interactive map view (showing brewery and restaurant markers on a map).
- Added (in place of the map): Detailed restaurant information surfaced from Yelp — specifically website links, price tiers, and opening hours — implemented as described in Section 6.4.

Reasons for the change
- Implementation complexity: A robust map integration requires additional dependencies (map library, map tiles or provider such as Mapbox/OpenStreetMap), correct handling of coordinate projections and markers, mobile/responsive UI work, and additional API/configuration work for map keys and usage quotas.
- Higher perceived user value: The team judged that surfacing concrete decision-making information (website, price, hours) would be immediately more useful for users choosing a restaurant than a map visualization at the midterm stage.
- Lower risk to core flow: Adding restaurant details reused existing Yelp endpoints and fit naturally into our current backend/service structure, whereas a map would have required new cross-cutting UI and UX work and more integration testing.

### 6.4 Extra Work

Beyond the original plan, we implemented enhanced restaurant information retrieval from the Yelp API. Rather than displaying only basic proximity data, we extended the restaurant list feature to fetch and present detailed information about each establishment when selected by the user.

**Feature: Detailed Restaurant Information**

When a user selects a restaurant from the nearby restaurants list, the application now retrieves comprehensive details from Yelp, including:
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
- Website link: Renders as "Visit website" with target="_blank" and rel="noopener noreferrer".
- Price: Displayed next to the restaurant name as the Yelp price string (or a dash/fallback when unavailable).
- Hours: Shows "Open now" / "Closed" status when hours and current time are available and provides today's hours inline. Users can expand to show a full weekly schedule if needed.

**Documentation**

We made the documention much more comprehensive than required in the submission guidelines.

### 6.5 AI Usage

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
