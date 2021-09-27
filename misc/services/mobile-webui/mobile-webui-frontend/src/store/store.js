import { createMemoryHistory } from 'history';
import { routerMiddleware } from 'connected-react-router';
import { applyMiddleware, compose, createStore } from 'redux';
import thunk from 'redux-thunk';
import createRootReducer from '../reducers';
// import { offline } from '@redux-offline/redux-offline';
// import offlineConfig from '@redux-offline/redux-offline/lib/defaults';

export const history = createMemoryHistory();
const composeEnhancer =
  window.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__({ serialize: true, latency: 0, features: { persist: false } }) || compose;

export const store = function configureStore(preloadedState) {
  const store = createStore(
    createRootReducer(history),
    preloadedState,
    // composeEnhancer(offline(offlineConfig), applyMiddleware(routerMiddleware(history), thunk)) // unhook offline due to caching issues
    composeEnhancer(applyMiddleware(routerMiddleware(history), thunk))
  );
  return store;
};
