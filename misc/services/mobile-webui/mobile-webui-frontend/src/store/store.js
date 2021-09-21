import { createMemoryHistory } from 'history'
import { routerMiddleware } from 'connected-react-router'
import { applyMiddleware, compose, createStore } from 'redux'
import thunk from "redux-thunk"
import createRootReducer from '../reducers'

export const history = createMemoryHistory()
const composeEnhancer = compose;
  // window.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__ || compose;

export const store = function configureStore(preloadedState) {
  const store = createStore(
    createRootReducer(history),
    preloadedState, composeEnhancer(applyMiddleware(routerMiddleware(history), thunk)),
  )
  return store
}

