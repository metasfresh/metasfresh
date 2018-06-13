import { routerMiddleware } from 'react-router-redux';
import { applyMiddleware, compose } from 'redux';
import { createStore } from 'redux-dynamic-reducer';
import thunk from 'redux-thunk';
import promiseMiddleware from 'redux-promise';

import rootReducer from '../reducers';

export default function configureStore(history) {
  const middleware = [thunk, promiseMiddleware, routerMiddleware(history)];
  const store = createStore(
    null,
    compose(
      applyMiddleware(...middleware),
      window.devToolsExtension ? window.devToolsExtension() : f => f
    )
  );

  store.attachReducers(rootReducer);

  if (module.hot) {
    module.hot.accept('../reducers', () => {
      const nextReducer = rootReducer;
      store.replaceReducer(nextReducer);
    });
  }

  if (window.Cypress) {
    window.Cypress.emit('emit:reduxStore', store);
  }

  return store;
}
