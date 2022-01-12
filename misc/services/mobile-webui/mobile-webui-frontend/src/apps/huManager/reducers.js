import { CLEAR_LOADED_DATA, HANDLING_UNIT_LOADED } from './actionTypes';

export function huManagerReducer(applicationState = {}, action) {
  switch (action.type) {
    case CLEAR_LOADED_DATA: {
      return { ...applicationState, handlingUnitInfo: null };
    }
    case HANDLING_UNIT_LOADED: {
      return { ...applicationState, handlingUnitInfo: action.payload };
    }
    default:
      return applicationState;
  }
}

function getApplicationStateFromGlobalState(globalState) {
  const applicationState = globalState['applications/huManager'];
  return applicationState || {};
}

export function getHandlingUnitInfoFromGlobalState(globalState) {
  const applicationState = getApplicationStateFromGlobalState(globalState);
  return applicationState['handlingUnitInfo'];
}
