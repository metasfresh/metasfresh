import React from 'react';
import axios from 'axios';
import { Provider } from 'react-redux';
import { applyMiddleware, combineReducers, createStore } from 'redux';
import thunk from 'redux-thunk';
import { act, render } from '@testing-library/react';

import appHandler from '../../reducers/appHandler';
import { ProvideAuth } from '../../hooks/useAuth';
import App from '../../containers/App';

const mockAuthHolder = {};

jest.mock('../../api/login', () => ({
  checkLoginRequest: jest.fn(),
  loginWithToken: jest.fn(),
  logoutRequest: jest.fn(() => Promise.resolve({})),
  getAvailableLang: jest.fn(() => new Promise(() => {})),
}));

jest.mock('../../actions/AppActions', () => ({
  loginSuccess: () => (dispatch) => {
    dispatch({ type: 'LOGIN_SUCCESS' });

    return Promise.resolve();
  },
  addNotification: jest.fn((title) => ({ type: 'TEST_NOTIFICATION', title })),
  connectionError: jest.fn(() => ({ type: 'TEST_CONNECTION_ERROR' })),
  setLanguages: jest.fn(() => ({ type: 'TEST_SET_LANGUAGES' })),
  setProcessSaved: jest.fn(() => ({ type: 'TEST_PROCESS_SAVED' })),
}));

jest.mock('../../services/History', () => ({ push: jest.fn() }));
jest.mock('../../hooks/useNotificationsWebsocket', () => ({
  useNotificationsWebsocket: jest.fn(),
}));
jest.mock('../../hooks/useSessionWebsocket', () => ({
  useSessionWebsocket: jest.fn(),
}));
jest.mock('../../utils/locale', () => ({
  initCurrentActiveLocale: jest.fn(),
  setCurrentActiveLocale: jest.fn(),
}));
jest.mock('../../components/Translation', () => ({ children }) => children);
jest.mock('../../components/notifications/NotificationHandler', () => ({
  children,
}) => children);
jest.mock('../../components/keyshortcuts/ShortcutProvider', () => ({
  ShortcutProvider: ({ children }) => children,
}));
// stands in for the app's routes: exposes the *current* auth object, like
// a login form component would use it
const mockRoutes = () => {
  const { useAuth: useCurrentAuth } = require('../../hooks/useAuth');

  // eslint-disable-next-line react-hooks/rules-of-hooks
  mockAuthHolder.auth = useCurrentAuth();

  return null;
};
jest.mock('../../routes', () => ({
  __esModule: true,
  default: () => mockRoutes(),
}));

const { addNotification } = require('../../actions/AppActions');
const { logoutRequest } = require('../../api/login');
const history = require('../../services/History');

const userFriendlyError = () => ({
  response: {
    status: 500,
    data: {
      path: '/rest/api/window',
      message: 'The thing you asked for is not possible',
      userFriendlyError: true,
    },
    request: { responseURL: 'http://localhost/rest/api/window' },
  },
});

describe('App: server error notifications follow the current login state', () => {
  let onRejected;
  let useSpy;

  const boot = () => {
    const store = createStore(
      combineReducers({ appHandler }),
      applyMiddleware(thunk)
    );

    render(
      <Provider store={store}>
        <ProvideAuth>
          <App />
        </ProvideAuth>
      </Provider>
    );
    // the interceptor App has just registered
    onRejected = useSpy.mock.calls[useSpy.mock.calls.length - 1][1];
  };

  const failWithUserFriendlyError = async () => {
    addNotification.mockClear();
    // the interceptor hands the error on as a rejection; only the notification matters here
    await Promise.resolve(onRejected(userFriendlyError())).catch(() => {});
  };

  beforeEach(() => {
    localStorage.clear();
    logoutRequest.mockClear();
    history.push.mockClear();
    useSpy = jest.spyOn(axios.interceptors.response, 'use');
  });

  afterEach(() => {
    useSpy.mockRestore();
  });

  it('shows the error after a login without page reload (app booted logged out)', async () => {
    boot();

    await act(async () => {
      await mockAuthHolder.auth.login();
    });
    await failWithUserFriendlyError();

    expect(addNotification).toHaveBeenCalledTimes(1);
  });

  it('shows no error after a logout without page reload, and again after the next login', async () => {
    localStorage.setItem('isLogged', 'true');
    boot();

    await failWithUserFriendlyError();
    expect(addNotification).toHaveBeenCalledTimes(1);

    await act(async () => {
      await mockAuthHolder.auth.logout();
    });
    await failWithUserFriendlyError();
    expect(addNotification).not.toHaveBeenCalled();

    await act(async () => {
      await mockAuthHolder.auth.login();
    });
    await failWithUserFriendlyError();
    expect(addNotification).toHaveBeenCalledTimes(1);
  });

  it('handles a 401 after a logout without page reload as logged out: no second logout request, redirect to login', async () => {
    localStorage.setItem('isLogged', 'true');
    boot();

    await act(async () => {
      await mockAuthHolder.auth.logout();
    });
    expect(logoutRequest).toHaveBeenCalledTimes(1);

    await act(async () => {
      await Promise.resolve(
        onRejected({
          response: {
            status: 401,
            data: {},
            request: { responseURL: 'http://localhost/rest/api/window' },
          },
        })
      ).catch(() => {});
    });

    expect(logoutRequest).toHaveBeenCalledTimes(1);
    expect(history.push).toHaveBeenCalledWith('/login');
  });

  it('logs out through the current auth on an invalid token, after a login without page reload', async () => {
    boot();

    await act(async () => {
      await mockAuthHolder.auth.login();
    });
    expect(localStorage.getItem('isLogged')).toBe('true');

    await act(async () => {
      await onRejected({
        response: {
          status: 500,
          data: { path: '/rest/api/authenticate', message: 'Invalid token' },
        },
      });
    });

    expect(logoutRequest).toHaveBeenCalledTimes(1);
    expect(localStorage.getItem('isLogged')).toBeNull();
    expect(history.push).toHaveBeenCalledWith('/login');
  });
});
