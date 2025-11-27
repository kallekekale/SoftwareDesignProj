import { Route, Routes } from "react-router-dom";
import "./App.css";
import BreweryList from "./components/BreweryList";
import RestaurantList from "./components/RestaurantList";

function App() {
  return (
    <Routes>
      <Route path="/" element={<BreweryList />} />
      <Route path="/restaurants/:breweryName" element={<RestaurantList />} />
    </Routes>
  );
}

export default App;
