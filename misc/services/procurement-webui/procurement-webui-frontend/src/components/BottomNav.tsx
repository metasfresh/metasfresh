import React, { FunctionComponent, ReactElement } from 'react';
import { Link, useLocation } from 'react-router-dom';

const BottomNav: FunctionComponent = (): ReactElement => {
  const location = useLocation();

  if (['login', 'password'].indexOf(location.pathname) >= 0) {
    return null;
  }

  return (
    <footer className="p-4 navbar is-fixed-bottom">
      <Link
        to={{
          pathname: '/',
          state: { prev: false },
        }}
      >
        Daily
      </Link>
      <Link
        to={{
          pathname: '/weekly',
          state: { prev: true },
        }}
      >
        Weekly
      </Link>
    </footer>
  );
};

export default BottomNav;
