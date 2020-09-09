import * as types from '../constants/FilterTypes';
import { produce } from 'immer';
export const initialFiltersBranchState = {};

export const initialFiltersLeafState = {
  clearAll: false,
  initialValuesNulled: new Map(),
  // activeFilter: null,
  // activeFiltersCaptions: null,
  // flatFiltersMap: null,
  // notValidFields: null,
  widgetShown: false,
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

// export default function filters(state = initialState, action) {
//   switch (action.type) {
//     case types.SET_CLEAR_ALL_FILTER: {
//       return { ...state, clearAll: action.payload };
//     }

//     default: {
//       return state;
//     }
//   }
// }

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
  }
}, initialFiltersBranchState);

export default reducer;
