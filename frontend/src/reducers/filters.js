import * as types from '../constants/FilterTypes';
import { produce, original } from 'immer';
export const initialFiltersBranchState = {};

export const initialFiltersLeafState = {
  initialValuesNulled: new Map(),
  widgetShown: false,
  filtersCaptions: {},
};

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
      const { id, data } = action.payload;
      const { filterData, filtersActive, flatFiltersMap } = data;
      draftState[id] = {
        ...initialFiltersLeafState,
        filterData: filterData ? filterData : [],
        filtersActive: filtersActive ? filtersActive : [],
        flatFiltersMap,
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
    case types.UPDATE_ACTIVE_FILTER: {
      const { id, data } = action.payload;
      draftState[id].filtersActive = data;
      return;
    }
    case types.UPDATE_WIDGET_SHOWN: {
      const { id, data } = action.payload;
      draftState[id].widgetShown = data;
      return;
    }
    case types.CLEAR_ALL_FILTERS: {
      const { id, data } = action.payload;
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
    case types.UPDATE_FILTERS_CAPTIONS: {
      const { id, filtersCaptions } = action.payload;
      draftState[id].filtersCaptions = filtersCaptions;
      return;
    }
  }
}, initialFiltersBranchState);

export default reducer;
