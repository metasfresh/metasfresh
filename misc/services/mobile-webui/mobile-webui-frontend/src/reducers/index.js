import { combineReducers } from '@reduxjs/toolkit';
import { connectRouter } from 'connected-react-router';
import appHandler from './appHandler';
import headers from './headers';
import launchers from './launchers';
import applications from './applications';
import update from './update';
import wfProcesses_status from './wfProcesses_status/index';

import { getApplicationReduxReducers } from '../apps/index';

const createRootReducer = (history) => {
  const appsReducers = getApplicationReduxReducers();

  const reducers = {
    ...appsReducers,
    update,
    router: connectRouter(history),
    appHandler,
    headers,
    applications,
    launchers,
    wfProcesses_status,
  };

  console.log('Registered app reducers', appsReducers);

  return combineReducers(reducers);
};
export default createRootReducer;
