# Brewery Finder - Usage Guide

## Overview

This is a React application that finds the 10 closest breweries from a selected location.

## Prerequisites

- Backend API running on `http://localhost:8080`
- Node.js 18+ and pnpm installed

## Running the Application

1. **Install dependencies:**
   ```bash
   pnpm install
   ```

2. **Start the development server:**
   ```bash
   pnpm dev
   ```

3. **Open in browser:**
   Navigate to `http://localhost:5173`

## How to Use

1. **Select a Location**: Click on one of the location buttons (Helsinki, San Diego, or Portland)
2. **View Results**: The app will fetch and display the 10 closest breweries
3. **Check Details**: Each brewery shows:
   - Name and type
   - City and state/country
   - Street address (if available)
   - Distance in kilometers

## Mocked Locations

The application comes with three predefined locations:
- **Helsinki, Finland** (60.1699°N, 24.9384°E)
- **San Diego, USA** (32.7157°N, -117.1611°W)
- **Portland, USA** (45.5152°N, -122.6784°W)

## API Integration

The frontend communicates with the backend API:
- **Endpoint**: `POST /api/breweries/distance`
- **Request**: Coordinates (latitude, longitude)
- **Response**: List of breweries with calculated distances

## Tech Stack

- React 19 with TypeScript
- TanStack Query for data fetching
- Vite for development and bundling
- Custom CSS for styling

## Development

To modify the mocked locations, edit the `MOCKED_LOCATIONS` array in `src/App.tsx`:

```typescript
const MOCKED_LOCATIONS: { name: string; coordinates: Coordinates }[] = [
  { name: "Your City", coordinates: { latitude: 0, longitude: 0 } },
  // Add more locations...
];
```
