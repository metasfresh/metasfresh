import * as types from '../constants/FilterTypes';
import { Map as iMap } from 'immutable';

export function clearAllFilters(data) {
  return {
    type: types.SET_CLEAR_ALL_FILTER,
    payload: data,
  };
}

/**
 * @method createFilter
 * @summary Add a new filter entry to the redux store
 */
export function createFilter({ id, data }) {
  return {
    type: types.CREATE_FILTER,
    payload: { id, data },
  };
}

/**
 * @method deleteFilter
 * @summary Remove the filter with specified `id` from the store
 */
export function deleteFilter(id) {
  return {
    type: types.DELETE_FILTER,
    payload: { id },
  };
}

/**
 * @method updateActiveFilter
 * @summary Updates the activeFilter in the store for the corresponding entity id
 */
export function updateActiveFilter({ id, data }) {
  return {
    type: types.UPDATE_ACTIVE_FILTER,
    payload: { id, data },
  };
}

/**
 * @method filtersToMap
 * @summary creates a map with the filters fetched from the layout request
 */
export function filtersToMap(filtersArray) {
  let filtersMap = iMap();

  if (filtersArray && filtersArray.length) {
    filtersArray.forEach((filter) => {
      filtersMap = filtersMap.set(filter.filterId, filter);
    });
  }
  return filtersMap;
}
