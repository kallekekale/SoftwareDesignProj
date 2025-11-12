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

TODO!

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

### 4.4 Design Patterns

TODO

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

| Member | Responsibility | Deliverables | Timeline |
|--------|-----------------|--------------|----------|
|        |                 |              |          |

### 6.2 Self-Assessment

What went well, what didn't, key learnings, technical trade-offs, team collaboration.

### 6.3 Changes to the Original Plan

TODO

### 6.4 Extra Work

TODO

### 6.5 AI Usage

Where AI was used (e.g., drafting Sections 4–5, code generation, refactoring ideas). Include:
- Models/tools used
- Human review process