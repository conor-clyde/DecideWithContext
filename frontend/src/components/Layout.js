import { NavLink, Outlet } from "react-router-dom";

function Layout() {
  return (
    <>
      <header>
        <NavLink to="/" end>
          DecideWithContext
        </NavLink>
        <nav aria-label="Main">
          <NavLink to="/" end>
            Home
          </NavLink>
          <NavLink to="/recommend">Recommend</NavLink>
          <NavLink to="/about">About</NavLink>
        </nav>
      </header>
      <main>
        <Outlet />
      </main>
    </>
  );
}

export default Layout;
