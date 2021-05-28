import React, { useState, useContext, createContext } from 'react';
import { useDispatch } from 'react-redux';

import { loginWithToken, localLoginRequest, logoutRequest } from '../api';
import Auth from '../services/Auth';
import { loginSuccess as loginAction } from '../actions/AppActions';
import useSynchronousState from './useSynchronousState';

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
  const dispatch = useDispatch();
  const [isLoggedIn, setLoggedIn] = useState(localStorage.isLogged);
  const [authRequestPending, setAuthRequestPending] = useSynchronousState(
    false
  );
  const [redirectRoute, set] = useState(null);

  const clearRedirectRoute = () => {
    setRedirectRoute(null);
  };

  const setRedirectRoute = (url) => {
    set(url);
  };

  const checkAuthentication = () => {
    setAuthRequestPending(true);

    return localLoginRequest().then((resp) => {
      setAuthRequestPending(false);

      if (resp.data) {
        _loginSuccess();
      } else {
        _logoutSuccess();
      }

      return Promise.resolve(resp.data);
    });
  };

  const tokenLogin = (token) => {
    if (!authRequestPending()) {
      setAuthRequestPending(true);

      return loginWithToken(token).then(async () => await login());
    }

    return Promise.resolve(false);
  };

  const _loginSuccess = () => {
    localStorage.setItem('isLogged', true);
    setLoggedIn(true);
  };

  const login = () => {
    setAuthRequestPending(true);

    return dispatch(loginAction(auth))
      .then(() => {
        setAuthRequestPending(false);
        _loginSuccess();
      })
      .catch(() => setAuthRequestPending(false));
  };

  const _logoutSuccess = () => {
    setLoggedIn(false);
    localStorage.removeItem('isLogged');
  };

  const logout = () => {
    if (isLoggedIn) {
      setAuthRequestPending(true);

      return logoutRequest().finally(() => {
        auth.close();
        _logoutSuccess();
        setAuthRequestPending(false);
      });
    }

    auth.close();
    _logoutSuccess();
  };

  return {
    isLoggedIn,
    redirectRoute,
    auth,
    login,
    logout,
    tokenLogin,
    checkAuthentication,
    authRequestPending,
    setRedirectRoute,
    clearRedirectRoute,
  };
}
