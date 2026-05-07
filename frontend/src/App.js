import { BrowserRouter, NavLink, Route, Routes } from "react-router-dom";
import Home from "./pages/Home";
import Login from "./pages/Login";
import Register from "./pages/Register";
import Profile from "./pages/Profile";

function navClass({ isActive }) {
  return isActive ? "active" : undefined;
}

export default function App() {
  return (
    <BrowserRouter>
      <header className="top">
        <NavLink to="/" end className="brand">
          DecideWithContext
        </NavLink>
        <nav>
          <NavLink to="/" end className={navClass}>
            Home
          </NavLink>
          <NavLink to="/login" className={navClass}>
            Login
          </NavLink>
          <NavLink to="/register" className={navClass}>
            Register
          </NavLink>
          <NavLink to="/profile" className={navClass}>
            Profile
          </NavLink>
        </nav>
      </header>
      <main>
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/login" element={<Login />} />
          <Route path="/register" element={<Register />} />
          <Route path="/profile" element={<Profile />} />
        </Routes>
      </main>
    </BrowserRouter>
  );
}
