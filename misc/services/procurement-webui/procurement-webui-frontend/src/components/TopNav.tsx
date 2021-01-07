import React, { FunctionComponent, ReactElement } from 'react';
import { Link, useLocation } from 'react-router-dom';

const TopNav: FunctionComponent = (): ReactElement => {
  const location = useLocation();

  if (['login', 'password'].indexOf(location.pathname) >= 0) {
    return null;
  }

  return (
    <nav className="nav">
      <Link
        to={{
          pathname: '/',
          state: { prev: false },
        }}
        className="nav-link"
      >
        Home
      </Link>
      <Link
        to={{
          pathname: '/daily',
          state: { prev: true },
        }}
        className="nav-link"
      >
        Daily
      </Link>
    </nav>
  );
};

export default TopNav;
