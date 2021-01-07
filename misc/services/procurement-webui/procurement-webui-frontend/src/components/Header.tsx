import React, { FunctionComponent, ReactElement } from 'react';
import { Link, useLocation } from 'react-router-dom';

const Header: FunctionComponent = (): ReactElement => {
  const location = useLocation();
  let link = null;

  if (location.pathname === '/') {
    link = (
      <div className="logout-link">
        <Link
          to={{
            pathname: '/logout',
            state: { prev: true },
          }}
          className="nav-link"
        >
          Logout
        </Link>
      </div>
    );
  }

  return (
    <div className="navbar header">
      <div className="header-title">Page name</div>
      {link}
    </div>
  );
};

export default Header;
