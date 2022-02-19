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
    return setClearanceStatusRequest({ huId, clearanceNote, clearanceStatus }).then(() => {
      dispatch({
        type: CHANGE_CLEARANCE_STATUS,
        payload: { clearanceNote, clearanceStatus },
      });

      return Promise.resolve();
    });
  };
};
