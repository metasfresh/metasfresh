import { SET_CLEAR_ALL_FILTER, CREATE_FILTER } from '../constants/FilterTypes';

export function clearAllFilters(data) {
  return {
    type: SET_CLEAR_ALL_FILTER,
    payload: data,
  };
}

/**
 * @method createFilter
 * @summary Add a new filter entry to the redux store
 */
export function createFilter({ id, data }) {
  return {
    type: CREATE_FILTER,
    payload: { id, data },
  };
}
