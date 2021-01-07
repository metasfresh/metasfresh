import React, { FunctionComponent, ReactElement } from 'react';
import { Link, useLocation } from 'react-router-dom';

const BottomNav: FunctionComponent = (): ReactElement => {
  const location = useLocation();

  if (['login', 'password'].indexOf(location.pathname) >= 0) {
    return null;
  }

  return (
    <nav className="navbar">
      <Link
        to={{
          pathname: '/',
          state: { prev: false },
        }}
        className="nav-link"
      >
        Daily
      </Link>
      <Link
        to={{
          pathname: '/weekly',
          state: { prev: true },
        }}
        className="nav-link"
      >
        Weekly
      </Link>
    </nav>
  );
};

export default BottomNav;
