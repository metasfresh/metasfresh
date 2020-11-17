import { SET_CLEAR_ALL_FILTER } from '../constants/FilterTypes';

export function clearAllFilters(data) {
  return {
    type: SET_CLEAR_ALL_FILTER,
    payload: data,
  };
}
