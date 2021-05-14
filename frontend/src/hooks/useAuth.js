import React, { useState, useEffect, useContext, createContext } from 'react';
import { useDispatch } from 'react-redux';

import { loginWithToken, localLoginRequest } from '../api';
import Auth from '../services/Auth';
import { loginSuccess as loginAction } from '../actions/AppActions';

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
  const [authRequestPending, setAuthRequestPending] = useState(false);

  // in case we still have `isLoggedIn`
  // useEffect(() => {
  //   if (isLoggedIn) {
  //     checkAuthentication();
  //   }
  // }, []);

  /*
  if (!isLoggedIn) { //&& !authRequest) {
  } else {
    // if (!authRequest) {
      // if (hasTutorial) {
      //   dispatch(enableTutorial());
      // }
      console.log('PrivateRoute logged: ', props.location, pathname, loggedIn, authRequest)

      if (props.location.pathname !== '/logout') {
        console.log('PrivateRoute clearNotifications')
        // dispatch(clearNotifications());
        // auth.loginSuccess();
      } else {
        console.log('PrivateRoute pathname equal logout: ', props.location.pathname, pathname)
      }
    // }
  }
  */
  const checkAuthentication = () => {
    // if (!authRequestPending) {
      setAuthRequestPending(true);

      return localLoginRequest().then((resp) => {
        console.log('auth.checkAuthentication: ', resp.data)
        setAuthRequestPending(false);

        if (resp.data) {
          _loginSuccess();
        } else {
          _logoutSuccess();
        }
      });
    // }

    // return Promise.resolve(false);
  };

  const tokenLogin = (token) => {
    setAuthRequestPending(true);

    return loginWithToken(token).then(() => login());
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
    auth.close();
    _logoutSuccess();
  };

  return {
    isLoggedIn,
    auth,
    login,
    logout,
    tokenLogin,
    checkAuthentication,
    authRequestPending,
  };
}
