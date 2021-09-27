import React, { useContext, createContext } from 'react';
import { useStore, useDispatch } from 'react-redux';
import PropTypes from 'prop-types';

import { loginRequest } from '../api';
import { setToken, clearToken } from '../actions/Actions';

const authContext = createContext();

// Provider component that wraps your app and makes auth object ...
// ... available to any child component that calls useAuth().
export function ProvideAuth({ children }) {
  const auth = useProvideAuth();

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
function useProvideAuth() {
  const store = useStore();
  const dispatch = useDispatch();

  const login = (username, token) => {
    return loginRequest(username, token)
      .then((token) => {
        dispatch(setToken(token));
      })
      .catch((error) => {
        console.error('login error: ', error);
      });
  };

  const logout = () => {
    dispatch(clearToken());

    return Promise.resolve();
  };

  const getToken = () => store.getState().appHandler.token;

  return {
    userToken: getToken,
    login,
    logout,
  };
}
