import { original, produce } from 'immer';
import { createCachedSelector } from 're-reselect';

import * as types from '../constants/ActionTypes';

const EMPTY_OBJECT = {};

export const initialFiltersBranchState = {};
export const initialFiltersLeafState = {
  widgetShown: false,
  filtersCaptions: {},
  notValidFields: null,
};

/**
 * @method getFilterFromState
 * @param {object} state - redux state
 * @param {*} filterId
 */
export function getFilterFromState(state, filterId) {
  return state.filters && state.filters[filterId]
    ? state.filters[filterId]
    : EMPTY_OBJECT;
}

/**
 * @method getCachedFilter
 * @summary cached selector for picking the filters
 *
 * @param {object} state - redux state
 * @param {string} filterId - id from the filters structure
 */
export const getCachedFilter = createCachedSelector(
  getFilterFromState,
  (filters) => filters
)((_state, filterId) => filterId);

/**
 * @method getEntityRelatedId
 * @summary Helper function to generate the filterId depending on the values passed
 * this can be further customised to match future entities/areas of the site. Currently is using the
 * same pattern for forming the tableId:
 *   [ depending on the values (if viewId is provided,
 *     we'll use only that for grids, and
 *     if not - it's a tab table so document id and tab ids are expected ]
 */
export const getEntityRelatedId = ({ windowId, viewId, docId, tabId }) => {
  return `${windowId}_${viewId ? viewId : `${docId}_${tabId}`}`;
};

const reducer = produce((draftState, action) => {
  switch (action.type) {
    case types.CREATE_FILTER: {
      const {
        id,
        data: { filterData, filtersActive, activeFiltersCaptions },
      } = action.payload;

      draftState[id] = {
        ...initialFiltersLeafState,
        filterData: filterData ? filterData : [],
        filtersActive,
        filtersCaptions: activeFiltersCaptions,
      };
      return;
    }
    case types.DELETE_FILTER: {
      const { id } = action.payload;

      if (draftState[id]) {
        delete draftState[id];
      }

      return;
    }
    case types.UPDATE_ACTIVE_FILTERS: {
      const { id, data } = action.payload;
      draftState[id].filtersActive = data;
      return;
    }
    case types.FILTER_UPDATE_WIDGET_SHOWN: {
      const { id, data } = action.payload;
      draftState[id].widgetShown = data;
      return;
    }
    case types.CLEAR_ALL_FILTERS: {
      const { id, data } = action.payload;

      if (!id) return;

      const currentFilters = original(draftState[id]);
      if (currentFilters) {
        const filtersAfterClearing = currentFilters.filtersActive
          ? currentFilters.filtersActive.filter(
              (filterItem) => filterItem.filterId !== data.filterId
            )
          : [];
        draftState[id].filtersActive = filtersAfterClearing;
      }
      return;
    }
    case types.UPDATE_FLAG_NOTVALIDFIELDS: {
      const { filterId, data } = action.payload;
      draftState[filterId].notValidFields = data;
      return;
    }
    case types.CLEAR_STATIC_FILTERS: {
      const { filterId, data } = action.payload;
      draftState[filterId].staticFilterCleared = data;
      return;
    }
  }
}, initialFiltersBranchState);

export default reducer;
