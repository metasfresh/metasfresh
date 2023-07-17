import { CLEAR_LOADED_DATA, HANDLING_UNIT_LOADED, CHANGE_CLEARANCE_STATUS } from './actionTypes';

export function huManagerReducer(applicationState = {}, action) {
  switch (action.type) {
    case CLEAR_LOADED_DATA: {
      return { ...applicationState, handlingUnitInfo: null };
    }
    case HANDLING_UNIT_LOADED: {
      const handlingUnitInfo = action.payload;
      return {
        ...applicationState,
        handlingUnitInfo,
      };
    }
    case CHANGE_CLEARANCE_STATUS: {
      const { clearanceNote, clearanceStatus } = action.payload;
      const hUI = { ...applicationState.handlingUnitInfo };

      if (clearanceNote != null) {
        hUI.clearanceNote = clearanceNote;
      }

      if (clearanceStatus) {
        hUI.clearanceStatus = {
          caption: clearanceStatus.caption,
          key: clearanceStatus.key,
        };
      }

      return {
        ...applicationState,
        handlingUnitInfo: { ...hUI },
      };
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
