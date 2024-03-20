import * as types from '../constants/ActionTypes';

export function clearAllFilters({ filterId, data }) {
  return {
    type: types.CLEAR_ALL_FILTERS,
    payload: { id: filterId, data },
  };
}

/**
 * @method createFilter
 * @summary Add a new filter entry to the redux store
 */
export function createFilter({ filterId, data }) {
  return {
    type: types.CREATE_FILTER,
    payload: { id: filterId, data },
  };
}

/**
 * @method deleteFilter
 * @summary Remove the filter with specified `id` from the store
 */
export function deleteFilter(filterId) {
  return {
    type: types.DELETE_FILTER,
    payload: { id: filterId },
  };
}

/**
 * @method updateNotValidFields
 * @summary updates in the store the notValidFields flag
 */
export function updateNotValidFields({ filterId, data }) {
  return {
    type: types.UPDATE_FLAG_NOTVALIDFIELDS,
    payload: { filterId, data },
  };
}

/**
 * @method updateActiveFilters
 * @summary Updates the activeFilters in the store for the corresponding entity id
 */
export function updateActiveFilters({ filterId, data }) {
  return {
    type: types.UPDATE_ACTIVE_FILTERS,
    payload: { id: filterId, data },
  };
}

/**
 * @method updateFilterWidgetShown
 * @summary Updates the widgetShown in the store for the corresponding entity id with a boolean value
 */
export function updateFilterWidgetShown({ filterId, data }) {
  return {
    type: types.FILTER_UPDATE_WIDGET_SHOWN,
    payload: { id: filterId, data },
  };
}

/**
 * @method clearStaticFilters
 * @summary Clears the existing static filters for a filter branch in the redux store
 */
export function clearStaticFilters({ filterId, data }) {
  return {
    type: types.CLEAR_STATIC_FILTERS,
    payload: { filterId, data },
  };
}
