import { CLEAR_LOADED_DATA, HANDLING_UNIT_LOADED } from './actionTypes';

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
