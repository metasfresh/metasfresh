import React from 'react';
import { Provider } from 'react-redux';
import { applyMiddleware, combineReducers, createStore } from 'redux';
import thunk from 'redux-thunk';
import { act, render } from '@testing-library/react';

import appHandler from '../../reducers/appHandler';
import { ProvideAuth, useAuth } from '../../hooks/useAuth';
import { logoutRequest } from '../../api/login';

jest.mock('../../api/login', () => ({
  checkLoginRequest: jest.fn(),
  loginWithToken: jest.fn(),
  logoutRequest: jest.fn(() => Promise.resolve({})),
}));

jest.mock('../../actions/AppActions', () => ({
  loginSuccess: () => (dispatch) => {
    dispatch({ type: 'LOGIN_SUCCESS' });

    return Promise.resolve();
  },
}));

jest.mock('../../services/History', () => ({ push: jest.fn() }));

describe('useAuth', () => {
  beforeEach(() => {
    localStorage.clear();
    logoutRequest.mockClear();
  });

  it('logout() on an auth object captured before a no-reload login uses the current login state', async () => {
    const store = createStore(
      combineReducers({ appHandler }),
      applyMiddleware(thunk)
    );
    const authRefs = [];
    const Consumer = () => {
      authRefs.push(useAuth());

      return null;
    };

    render(
      <Provider store={store}>
        <ProvideAuth>
          <Consumer />
        </ProvideAuth>
      </Provider>
    );

    // created while logged out; this is the reference a long-lived closure
    // (e.g. the App axios interceptor) keeps holding
    const staleAuth = authRefs[0];
    expect(staleAuth.isLoggedIn).toBeFalsy();

    // form login without a page reload
    await act(async () => {
      await staleAuth.login();
    });
    expect(store.getState().appHandler.isLogged).toBe(true);

    await act(async () => {
      await staleAuth.logout();
    });

    expect(logoutRequest).toHaveBeenCalledTimes(1);
    expect(store.getState().appHandler.isLogged).toBe(false);
    expect(localStorage.getItem('isLogged')).toBeNull();
  });
});
