import { combineReducers } from '@reduxjs/toolkit';
import { connectRouter } from 'connected-react-router';
import appHandler from './appHandler';
import headers from './headers';
import launchers from './launchers';
import update from './update';
import wfProcesses_status from './wfProcesses_status/index';

const createRootReducer = (history) =>
  combineReducers({
    update,
    router: connectRouter(history),
    appHandler,
    headers,
    launchers,
    wfProcesses_status,
  });

export default createRootReducer;
