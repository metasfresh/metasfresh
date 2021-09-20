import { combineReducers } from '@reduxjs/toolkit';
import { createBrowserHistory } from 'history'
import { connectRouter, routerMiddleware } from 'connected-react-router'
import { applyMiddleware, compose, createStore } from 'redux'
import thunk from "redux-thunk"


export const history = createBrowserHistory()

const createRootReducer = (history) => combineReducers({
  router: connectRouter(history),
})

export const store = function configureStore(preloadedState) {
  const store = createStore(
    createRootReducer(history),
    preloadedState, compose(applyMiddleware(routerMiddleware(history), thunk)),
  )
  return store
}

