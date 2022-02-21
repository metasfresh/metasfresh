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

export const changeClearanceStatus = (status) => {
  return {
    type: CHANGE_CLEARANCE_STATUS,
    payload: { status },
  };
};
