import React from 'react';
import { Link, useLocation } from 'react-router-dom';

export default function Nav() {
  let location = useLocation();

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
          pathname: '/view2',
          state: { prev: true },
        }}
        className="nav-link"
      >
        About
      </Link>
    </nav>
  );
}
