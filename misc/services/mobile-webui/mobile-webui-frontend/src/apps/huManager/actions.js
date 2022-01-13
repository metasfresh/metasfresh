import { CLEAR_LOADED_DATA, HANDLING_UNIT_LOADED } from './actionTypes';

export function clearLoadedData() {
  return {
    type: CLEAR_LOADED_DATA,
  };
}

export function handlingUnitLoaded({ handlingUnitInfo }) {
  return {
    type: HANDLING_UNIT_LOADED,
    payload: handlingUnitInfo,
  };
}
