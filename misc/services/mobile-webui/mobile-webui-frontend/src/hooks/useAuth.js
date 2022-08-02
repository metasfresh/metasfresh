import React, { useContext, createContext } from 'react';
import { useStore, useDispatch } from 'react-redux';
import PropTypes from 'prop-types';
import axios from 'axios';
import Cookies from 'js-cookie';

import { loginRequest } from '../api/login';
import { COOKIE_EXPIRATION } from '../constants/Cookie';
import { setToken, clearToken } from '../actions/TokenActions';
import { setLanguage } from '../utils/translations';
import { getIsLoggedInFromState, getTokenFromState } from '../reducers/appHandler';

const authContext = createContext();

// Provider component that wraps your app and makes auth object ...
// ... available to any child component that calls useAuth().
export function ProvideAuth({ children }) {
  const auth = createAuthObject();

  //
  // If there is no token in the store but we have one in the cookie,
  // then use the cookied and "login" the store.
  // This is useful in order to not lose the token while browser refreshing (F5).
  const token = auth.userToken();
  if (!token) {
    const tokenFromCookie = Cookies.get('Token');
    const languageFromCookie = Cookies.get('Language');
    if (tokenFromCookie) {
      auth.loginByToken({ token: tokenFromCookie, language: languageFromCookie });
      console.log('ProvideAuth: Logged in by token from cookie');
    }
  }

  return <authContext.Provider value={auth}>{children}</authContext.Provider>;
}

ProvideAuth.propTypes = {
  children: PropTypes.oneOfType([PropTypes.arrayOf(PropTypes.node), PropTypes.node]).isRequired,
};
/**
 * @file React hook, that provides components with the authentication abject and
 * re-renders them when authentication changes
 */
export const useAuth = () => {
  return useContext(authContext);
};

// Provider hook that creates auth object and handles state
function createAuthObject() {
  const store = useStore();
  const dispatch = useDispatch();

  const loginByToken = async ({ token, language }) => {
    //console.log('auth.loginByToken: token=', { token, language });

    if (language) {
      setLanguage(language);
      Cookies.set('Language', language, { expires: COOKIE_EXPIRATION });
    }

    await dispatch(setToken(token));
    Cookies.set('Token', token, { expires: COOKIE_EXPIRATION });
    axios.defaults.headers.common['Authorization'] = token;
  };

  const login = (username, password) => {
    return loginRequest(username, password)
      .then(({ data }) => {
        const { error, token, language } = data;

        if (error) {
          return Promise.reject(error);
        } else {
          loginByToken({ token, language });
          return Promise.resolve();
        }
      })
      .catch((error) => {
        console.error('login error: ', error);
        return Promise.reject(error);
      });
  };

  const logout = () => {
    dispatch(clearToken());

    Cookies.remove('Token', { expires: COOKIE_EXPIRATION });

    axios.defaults.headers.common['Authorization'] = null;

    return Promise.resolve();
  };

  const getToken = () => getTokenFromState(store.getState());

  const isLoggedIn = () => getIsLoggedInFromState(store.getState());

  return {
    userToken: getToken,
    isLoggedIn,
    isLoggedInNow: isLoggedIn,
    login,
    loginByToken,
    logout,
  };
}
