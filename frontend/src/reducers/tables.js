import { Map as iMap, List as iList } from 'immutable';

import * as types from '../constants/ActionTypes';

export const tableState = {
  windowType: null,
  viewId: null,
  docId: null,
  tabId: null,
  selected: iList(),
  rows: iList(),

  // TODO: We need to figure out how to handle the fact, that columns for
  // grid come as an object, while for the details view (tabs) as a list with
  // more data.
  columns: iList(),
  activeSort: false,
  headerProperties: iMap(),
  headerElements: iList(),
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
        map
          .set('length', newLength)
          .delete(id);
      });
    }
    default: {
      return state;
    }
  }
}
