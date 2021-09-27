import { combineReducers } from '@reduxjs/toolkit';
import { connectRouter } from 'connected-react-router';
import appHandler from './appHandler';
import launchers from './launchers';
import wfProcesses from './wfProcesses';

const createRootReducer = (history) =>
  combineReducers({
    router: connectRouter(history),
    appHandler,
    launchers,
    wfProcesses,
  });

export default createRootReducer;
