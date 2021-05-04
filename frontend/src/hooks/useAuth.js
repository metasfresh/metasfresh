import React, { useState, useEffect, useContext, createContext } from 'react';

import Auth from '../services/Auth';

const authContext = createContext();

// Provider component that wraps your app and makes auth object ...
// ... available to any child component that calls useAuth().
export function ProvideAuth({ children }) {
  const auth = useProvideAuth();
  return <authContext.Provider value={auth}>{children}</authContext.Provider>;
}

// Hook for child components to get the auth object ...
// ... and re-render when it changes.
export const useAuth = () => {
  return useContext(authContext);
};

// Provider hook that creates auth object and handles state
function useProvideAuth() {
  const auth = new Auth();
  const isLoggedIn = () => localStorage.isLogged;

  // Return the user object and auth methods
  return {
    isLoggedIn,
    auth,
  };
}
