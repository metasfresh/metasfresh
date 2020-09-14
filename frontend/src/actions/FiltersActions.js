import * as types from '../constants/FilterTypes';
import { Map as iMap } from 'immutable';
import deepUnfreeze from 'deep-unfreeze';

export function clearAllFilters({ id, data }) {
  return {
    type: types.CLEAR_ALL_FILTERS,
    payload: { id, data },
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
 * @method updateWidgetShown
 * @summary Updates the widgetShown in the store for the corresponding entity id with a boolean value
 */
export function updateWidgetShown({ id, data }) {
  return {
    type: types.UPDATE_WIDGET_SHOWN,
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

/**
 * @method getFlatFiltersMap
 * @summary Creates a flatFilterMap out of passed filterData array
 */
export function getFlatFiltersMap({ filterData }) {
  const flatFiltersMap = {};
  filterData.forEach((filter) => {
    if (filter.parameters) {
      filter.parameters.forEach((parameter) => {
        const { parameterName, widgetType } = parameter;
        flatFiltersMap[`${filter.filterId}-${parameterName}`] = { widgetType };
      });
    } else if (filter.includedFilters) {
      filter.includedFilters.forEach((includedFilter) => {
        if (includedFilter.parameters) {
          includedFilter.parameters.forEach((parameter) => {
            const { parameterName, widgetType } = parameter;
            flatFiltersMap[`${includedFilter.filterId}-${parameterName}`] = {
              widgetType,
            };
          });
        }
      });
    }
  });

  return flatFiltersMap;
}

/**
 * @method filtersActiveContains
 * @summary returns a boolean value depending on the presence of the key withing the activeFilters passed array
 */
export function filtersActiveContains({ filtersActive, key }) {
  if (filtersActive.lenght === 0) return false;
  const isPresent = filtersActive.filter((item) => item.filterId === key);
  return isPresent.length ? true : false;
}

/**
 * @method setNewFiltersActive
 * @summary returns a new array with filters that are going to be the active ones
 */
export function setNewFiltersActive({ storeActiveFilters, filterToAdd }) {
  storeActiveFilters = deepUnfreeze(storeActiveFilters);
  if (
    !storeActiveFilters.length ||
    !foundAmongActiveFilters({ storeActiveFilters, filterToAdd })
  ) {
    storeActiveFilters.push(filterToAdd);
  } else {
    storeActiveFilters.forEach((activeFilter, index) => {
      if (activeFilter.filterId === filterToAdd.filterId) {
        storeActiveFilters[index] = filterToAdd;
      }
    });
  }
  return storeActiveFilters;
}

/**
 * @method foundAmongActiveFilters
 * @summary checks that the filterToAdd is found among the storeActiveFilters
 */
function foundAmongActiveFilters({ storeActiveFilters, filterToAdd }) {
  let isPresent = false;
  storeActiveFilters.forEach((item) => {
    if (item.filterId === filterToAdd.filterId) isPresent = true;
  });
  return isPresent;
}
