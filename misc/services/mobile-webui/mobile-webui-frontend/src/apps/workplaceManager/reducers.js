import { SET_WORKPLACE } from './actionTypes';

export function workplaceManagerReducer(applicationState = {}, action) {
  switch (action.type) {
    case SET_WORKPLACE: {
      const workplaceInfo = action.payload;
      return { ...applicationState, workplaceInfo };
    }
    default: {
      return applicationState;
    }
  }
}

function getApplicationStateFromGlobalState(globalState) {
  const applicationState = globalState['applications/workplaceManager'];
  return applicationState || {};
}

export function getWorkplaceInfoFromGlobalState(globalState) {
  const applicationState = getApplicationStateFromGlobalState(globalState);
  return applicationState['workplaceInfo'];
}
