import { combineReducers } from '@reduxjs/toolkit';
import { connectRouter } from 'connected-react-router';
import appHandler from './appHandler';
import launchers from './launchers';

const createRootReducer = (history) =>
  combineReducers({
    router: connectRouter(history),
    appHandler,
    launchers,
  });

export default createRootReducer;
