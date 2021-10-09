import { combineReducers } from '@reduxjs/toolkit';
import { connectRouter } from 'connected-react-router';
import appHandler from './appHandler';
import headers from './headers';
import launchers from './launchers';
import wfProcesses from './wfProcesses';
import scanner from './scanner';
import wfProcesses_status from './wfProcesses_status';

const createRootReducer = (history) =>
  combineReducers({
    router: connectRouter(history),
    appHandler,
    headers,
    scanner,
    launchers,
    wfProcesses,
    wfProcesses_status,
  });

export default createRootReducer;
