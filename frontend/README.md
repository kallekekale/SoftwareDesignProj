# Brewery Finder - Frontend

A React + TypeScript + Vite application that finds the 10 closest breweries from a selected location.

## Features

- **Location Selection**: Choose from predefined mocked locations
- **Current Location**: Use browser geolocation to find nearby breweries
- **Brewery Search**: Fetches the 10 closest breweries from the selected location
- **Distance Display**: Shows the distance in kilometers for each brewery
- **Brewery Details**: Displays brewery name, type, address, and location information

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

1. **Select a Location**: Click on one of the location buttons (Brooklyn, Manhattan, or Downtown LA)
2. **Use Current Location**: Click "📍 My Current Location" to use your browser's geolocation
3. **View Results**: The app will fetch and display the 10 closest breweries
4. **Check Details**: Each brewery shows:
   - Name and type
   - City and state/country
   - Street address (if available)
   - Distance in kilometers

## Mocked Locations

The application comes with three predefined locations:
- **Brooklyn, NY** (40.6782°N, 73.9442°W)
- **Manhattan, NY** (40.7831°N, 73.9712°W)
- **Downtown LA** (34.0407°N, 118.2468°W)

## API Integration

The frontend communicates with the backend API via Vite proxy:
- **Endpoint**: `POST /api/breweries/distance`
- **Request**: Coordinates (latitude, longitude)
- **Response**: List of breweries with calculated distances

The Vite proxy forwards requests from `/api` to `http://localhost:8080`, avoiding CORS issues during development.

## Tech Stack

- **React 19**: UI library with hooks
- **TypeScript**: Type-safe development
- **Vite**: Fast build tool and dev server with proxy configuration
- **TanStack Query**: Data fetching, caching, and state management
- **Custom Hooks**: `useBreweries` for encapsulated data fetching logic
- **Singleton Service Pattern**: `breweryService` for API calls
- **CSS**: Custom styling with consistent design system

## Project Structure

```
frontend/
├── src/
│   ├── hooks/
│   │   └── useBreweries.ts         # Custom hook for brewery data fetching
│   ├── services/
│   │   └── breweryService.ts       # Singleton service for API calls
│   ├── types/
│   │   └── brewery.ts              # TypeScript type definitions
│   ├── lib/
│   │   └── queryClient.ts          # TanStack Query configuration
│   ├── App.tsx                     # Main application component
│   ├── App.css                     # Application styles
│   ├── index.css                   # Global styles
│   └── main.tsx                    # Application entry point
├── public/                         # Static assets
└── vite.config.ts                  # Vite configuration with proxy
```

## Available Scripts

- `pnpm dev` - Start the development server
- `pnpm build` - Build for production
- `pnpm preview` - Preview the production build
- `pnpm lint` - Run ESLint
- `pnpm prettier:check` - Check code formatting
- `pnpm prettier:write` - Format code with Prettier

## Development

To modify the mocked locations, edit the `MOCKED_LOCATIONS` array in `src/App.tsx`:

```typescript
const MOCKED_LOCATIONS: { name: string; coordinates: Coordinates }[] = [
  { name: "Your City", coordinates: { latitude: 0, longitude: 0 } },
  // Add more locations...
];
```
