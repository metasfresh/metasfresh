import * as types from '../constants/FilterTypes';
import { Map as iMap } from 'immutable';
import deepUnfreeze from 'deep-unfreeze';
import { fieldValueToString } from '../utils/tableHelpers';

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

/**
 * @method isFilterActive
 * @summary Check within the active filters array if filterId given as param is active
 * @param {string} filterId
 * @param {array} activeFilter
 */
export function isFilterActive({ filterId, filtersActive }) {
  if (filtersActive) {
    // filters with only defaultValues shouldn't be set to active
    const active = filtersActive.find(
      (item) => item.filterId === filterId && !item.defaultVal
    );

    return typeof active !== 'undefined';
  }

  return false;
}

/**
 * @method annotateFilters
 * @summary Creates caption for active filters to show when the widget is closed
 * @param {array} unannotatedFilters
 * @param {array} filtersActive
 */
export function annotateFilters({ unannotatedFilters, filtersActive }) {
  filtersActive = filtersActive ? filtersActive : [];

  return unannotatedFilters.map((unannotatedFilter) => {
    const parameter =
      unannotatedFilter.parameters && unannotatedFilter.parameters[0];
    const isActive = isFilterActive({
      filterId: unannotatedFilter.filterId,
      filtersActive,
    });
    const currentFilter = filtersActive
      ? filtersActive.find((f) => f.filterId === unannotatedFilter.filterId)
      : null;
    const activeParameter =
      parameter && isActive && currentFilter && currentFilter.parameters[0];

    const filterType =
      unannotatedFilter.parameters && activeParameter
        ? unannotatedFilter.parameters.find(
            (filter) => filter.parameterName === activeParameter.parameterName
          )
        : parameter && parameter.widgetType;

    const captionValue = activeParameter
      ? fieldValueToString({
          fieldValue: activeParameter.valueTo
            ? [activeParameter.value, activeParameter.valueTo]
            : activeParameter.value,
          fieldType: filterType,
        })
      : '';

    return {
      ...unannotatedFilter,
      captionValue,
      isActive,
    };
  });
}
