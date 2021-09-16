import React, { useState, useContext, createContext } from 'react';
import { useDispatch, useStore } from 'react-redux';
import PropTypes from 'prop-types';
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

ProvideAuth.propTypes = {
  children: PropTypes.oneOfType([
    PropTypes.arrayOf(PropTypes.node),
    PropTypes.node,
  ]).isRequired,
};
/**
 * @file React hook, that provides components with the authentication abject and
 * re-renders them when authentication changes
 */
export const useAuth = () => {
  return useContext(authContext);
};

// Provider hook that creates auth object and handles state
function useProvideAuth() {
  const auth = new Auth();
  const dispatch = useDispatch();
  const store = useStore();

  // controls if user is authenticated and stores in the local storage as well as local value
  const [isLoggedIn, setLoggedIn] = useState(localStorage.isLogged);
  // flag indicating if there's an ongoing authentication request
  const [authRequestPending, setAuthRequestPending] =
    useSynchronousState(false);
  const [redirectRoute, setRedirectRoute] = useState(null);

  const clearRedirectRoute = () => {
    setRedirectRoute(null);
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
    if (!store.getState().appHandler.isLogged) {
      return dispatch(loginAction(auth)).then(() => {
        _loginSuccess();
      });
    }
  };

  const _logoutSuccess = () => {
    auth.close();
    setLoggedIn(false);
    localStorage.removeItem('isLogged');
    setAuthRequestPending(false);
  };

  const logout = () => {
    if (isLoggedIn) {
      setAuthRequestPending(true);

      return logoutRequest()
        .then(() => _logoutSuccess())
        .catch(() => _logoutSuccess());
    } else {
      _logoutSuccess();
    }
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
