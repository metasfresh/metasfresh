import { applyMiddleware, compose, createStore } from 'redux';
import thunk from 'redux-thunk';
import promiseMiddleware from 'redux-promise';

import { createRootReducer } from '../reducers';

export default function configureStore() {
  const composeEnhancer =
    (window.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__ &&
      window.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__({
        trace: true,
        traceLimit: 25,
      })) ||
    compose;

  const reducer = createRootReducer();

  const store = createStore(
    reducer,
    {},
    composeEnhancer(applyMiddleware(thunk, promiseMiddleware))
  );

  if (module.hot) {
    module.hot.accept('../reducers', () => {
      const nextReducer = createRootReducer();
      store.replaceReducer(nextReducer);
    });
  }

  if (window.Cypress) {
    window.Cypress.emit('emit:reduxStore', store);
  }

  return store;
}
