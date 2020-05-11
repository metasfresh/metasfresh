import { Map as iMap, List as iList } from 'immutable';

import * as types from '../constants/ActionTypes';

export const tableState = {
  windowType: null,
  viewId: null,
  docId: null,
  tabId: null,
  selected: iList(),
  rows: iList(),
  // row columns
  columns: iList(),
  activeSort: false,
  headerProperties: iMap(),

  //header columns
  headerElements: iMap(),
  emptyText: null,
  emptyHint: null,
  page: 0,
  firstRow: 0,
  size: 0,
  orderBy: iList(),
  defaultOrderBys: iList(),
  pageLength: 0,
  queryLimit: 0,
  queryLimitHit: false,
  dataPending: false,
  dataError: false,
  tabIndex: 0,
  internalName: null,
  queryOnActivate: true,
  supportQuickInput: true,

  // includedTabsInfo
  allowCreateNew: true,
  allowCreateNewReason: null,
  allowDelete: true,
  stale: false,
};

// we store the length of the tables structure for the sake of testing and debugging
export const initialState = iMap({ length: 0 });

/*
 * Small helper function to generate the table id depending on the values (if viewId is
 * provided, we'll use only that for grids, and if not - it's a tab table so document id
 * and tab ids are expected ).
 */
export const getTableId = ({ windowType, viewId, docId, tabId }) => {
  return `${windowType}_${viewId ? `${viewId}` : `${docId}_${tabId}`}`;
};

export default function table(state = initialState, action) {
  switch (action.type) {
    // CRUD
    case types.CREATE_TABLE: {
      const { id, data } = action.payload;
      const newLength = state.get('length') + 1;

      return state.withMutations((map) => {
        map
          .set('length', newLength)
          .set(id, tableState)
          .mergeDeepIn([id], iMap(data));
      });
    }
    case types.UPDATE_TABLE: {
      const { id, data } = action.payload;

      return state.mergeDeepIn([id], data);
    }
    case types.DELETE_TABLE: {
      const { id } = action.payload;
      const newLength = state.get('length') - 1;

      return state.withMutations((map) => {
        map.set('length', newLength).delete(id);
      });
    }

    case types.SET_ACTIVE_SORT_NEW: {
      const { id, active } = action.payload;

      return state.mergeDeepIn([id], { activeSort: active });
    }
    default: {
      return state;
    }
  }
}
