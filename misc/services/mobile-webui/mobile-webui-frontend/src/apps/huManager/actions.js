import { setClearanceStatusRequest } from './api';

import { CLEAR_LOADED_DATA, HANDLING_UNIT_LOADED, CHANGE_CLEARANCE_STATUS } from './actionTypes';

export const clearLoadedData = () => {
  return {
    type: CLEAR_LOADED_DATA,
  };
};

export const handlingUnitLoaded = ({ handlingUnitInfo }) => {
  return {
    type: HANDLING_UNIT_LOADED,
    payload: handlingUnitInfo,
  };
};

export const changeClearanceStatus = ({ huId, clearanceNote, clearanceStatus }) => {
  return (dispatch) => {
    if (clearanceStatus.key) {
      return setClearanceStatusRequest({ huId, clearanceNote, clearanceStatus: clearanceStatus.key }).then(() => {
        dispatch({
          type: CHANGE_CLEARANCE_STATUS,
          payload: { clearanceNote, clearanceStatus },
        });

        return Promise.resolve();
      });
    }
    return Promise.reject();
  };
};
