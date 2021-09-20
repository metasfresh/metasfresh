import { createBrowserHistory } from 'history'
import { routerMiddleware } from 'connected-react-router'
import { applyMiddleware, compose, createStore } from 'redux'
import thunk from "redux-thunk"
import createRootReducer from '../reducers'

export const history = createBrowserHistory()

export const store = function configureStore(preloadedState) {
  const store = createStore(
    createRootReducer(history),
    preloadedState, compose(applyMiddleware(routerMiddleware(history), thunk)),
  )
  return store
}

