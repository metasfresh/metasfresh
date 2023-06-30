import { createBrowserHistory } from 'history';
import { routerMiddleware } from 'connected-react-router';
import { applyMiddleware, compose, createStore } from 'redux';
import thunk from 'redux-thunk';

import createRootReducer from '../reducers';

// Import the necessary methods for saving and loading
import { save } from 'redux-localstorage-simple';

// import { offline } from '@redux-offline/redux-offline';
// import offlineConfig from '@redux-offline/redux-offline/lib/defaults';

export const history = createBrowserHistory({
  basename: '/mobile',
});
const composeEnhancer =
  typeof window === 'object' && window.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__
    ? window.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__({ serialize: true, latency: 0, features: { persist: false } })
    : compose;

export const store = function configureStore(preloadedState) {
  return createStore(
    createRootReducer(history),
    preloadedState,
    // composeEnhancer(offline(offlineConfig), applyMiddleware(routerMiddleware(history), thunk)) // unhook offline due to caching issues
    composeEnhancer(applyMiddleware(routerMiddleware(history), thunk, save({ ignoreStates: ['appHandler', 'router'] })))
  );
};
