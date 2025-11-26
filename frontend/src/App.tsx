import { Route, Routes } from "react-router-dom";
import "./App.css";
import BreweryList from "./components/BreweryList";

function App() {
  return (
    <Routes>
      <Route path="/" element={<BreweryList />} />
    </Routes>
  );
}

export default App;
