// import { routerMiddleware } from 'react-router-redux';
// import { routerMiddleware } from 'connected-react-router';
import { applyMiddleware, compose, createStore } from 'redux';
// import { createStore } from 'redux-dynamic-reducer';
import thunk from 'redux-thunk';
import promiseMiddleware from 'redux-promise';

// import navigationMiddleware from './customMiddlewares';
// import rootReducer from '../reducers';
import { createRootReducer } from '../reducers';

export default function configureStore() {
  const composeEnhancer =
    window.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__ || compose;

  // const store = createStore(
  //   null,
  //   composeEnhancer(applyMiddleware(...middleware))
  // );

  const reducer = createRootReducer();

  const store = createStore(
    reducer,
    {},
    composeEnhancer(
      applyMiddleware(
        thunk,
        promiseMiddleware
        // navigationMiddleware,
        // routerMiddleware(history)
      )
    )
  );

  // store.attachReducers(rootReducer);
  // store.attachReducers(createRootReducer(history));

  if (module.hot) {
    module.hot.accept('../reducers', () => {
      // const nextReducer = rootReducer;
      const nextReducer = createRootReducer();
      store.replaceReducer(nextReducer);
    });
  }

  if (window.Cypress) {
    window.Cypress.emit('emit:reduxStore', store);
  }

  return store;
}
