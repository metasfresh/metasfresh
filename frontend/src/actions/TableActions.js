import { SET_ACTIVE_SORT } from '../constants/TableTypes';

export function setActiveSort(data) {
  return {
    type: SET_ACTIVE_SORT,
    payload: data,
  };
}
