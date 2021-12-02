import React, { useState, useContext, createContext } from 'react';
import { useDispatch, useStore } from 'react-redux';
import PropTypes from 'prop-types';

import { loginWithToken, checkLoginRequest, logoutRequest } from '../api';

import history from '../services/History';
import Auth from '../services/Auth';
import {
  loginSuccess as loginAction,
  getNotifications,
  getNotificationsEndpoint,
} from '../actions/AppActions';
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

  /**
   * checkAuthentication
   * @summary check if user is authenticated on the backend and set the `isLoggedIn`
   * flag accordingly
   */
  const checkAuthentication = () => {
    const state = store.getState();

    if (!state.appHandler.loggedIn) {
      setAuthRequestPending(true);

      return checkLoginRequest()
        .then((resp) => {
          setAuthRequestPending(false);

          if (resp.data) {
            _loginSuccess();
          } else {
            _logoutSuccess();
          }

          return Promise.resolve(resp.data);
        })
        .catch((error) => {
          if (error.response.status !== 500) {
            Promise.reject(error);
          }
        });
    }

    return Promise.reject(null);
  };

  /**
   * tokenLogin
   * @summary login user via token. In case of an error, redirect to login. (any actual
   * logout will be performed by the main error handler in the App container)
   * @param {string} token
   */
  const tokenLogin = (token) => {
    if (!authRequestPending() && !store.getState().appHandler.isLogged) {
      setAuthRequestPending(true);

      return loginWithToken(token)
        .then(async () => await login())
        .catch((error) => {
          // user already logged in error
          if (
            error &&
            error.response &&
            error.response.data.message.includes('User already logged in')
          ) {
            setAuthRequestPending(false);

            return login().then(() => {
              history.push('/');
            });
          } else {
            clearRedirectRoute();
            history.push('/login');

            return Promise.reject(error);
          }
        });
    }

    return Promise.resolve(false);
  };

  /**
   * getDerivedStateFromProps lifecycle - hold in the state of the component the last page
   * @summary set the flags indicating user is authenticated, and store it in the localStorage
   */
  const _loginSuccess = () => {
    localStorage.setItem('isLogged', true);
    setLoggedIn(true);

    return Promise.resolve();
  };

  const fetchNotifications = () => {
    dispatch(getNotificationsEndpoint(auth));
    dispatch(getNotifications());

    return Promise.resolve();
  };

  /**
   * login
   * @summary dispatch the action running helper requests, for user's session,
   * setting language etc
   */
  const login = () => {
    if (!store.getState().appHandler.isLogged) {
      return dispatch(loginAction(auth)).then(() => {
        _loginSuccess();
      });
      // user is already authenticated but we need to refresh notifications
    } else {
      return fetchNotifications();
    }
  };

  /**
   * _logoutSuccess internal function
   * @summary reset the authentication flags
   */
  const _logoutSuccess = () => {
    auth.close();
    setLoggedIn(false);
    localStorage.removeItem('isLogged');
    setAuthRequestPending(false);
  };

  /**
   * logout
   * @summary run the logout request on the backend
   */
  const logout = () => {
    if (isLoggedIn) {
      setAuthRequestPending(true);

      return logoutRequest().finally(() => _logoutSuccess());
    }

    return Promise.resolve(() => _logoutSuccess());
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
