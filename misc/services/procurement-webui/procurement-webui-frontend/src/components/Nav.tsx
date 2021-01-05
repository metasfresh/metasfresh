import React from 'react';
import { Link } from 'react-router-dom';

export default function Nav() {
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