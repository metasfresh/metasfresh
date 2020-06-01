import produce from 'immer';
import { get, difference } from 'lodash';
import { createSelector } from 'reselect';

import * as types from '../constants/ActionTypes';
import { doesSelectionExist } from '../utils/documentListHelper';

export const initialTableState = {
  windowType: null,
  viewId: null,
  docId: null,
  tabId: null,
  keyProperty: null,
  selected: [],
  rows: [],
  collapsedRows: [],
  collapsedParentRows: [],
  collapsedArrayMap: [],
  // row columns
  columns: [],
  activeSort: false,
  headerProperties: {},

  //header columns
  headerElements: {},
  emptyText: null,
  emptyHint: null,

  //TODO: Ideally this should sit only in the DocumentList/Window layout
  // as table should only render rows, and not pagination
  page: 0,
  firstRow: 0,
  size: 0,
  orderBy: [],
  defaultOrderBys: [],
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

  expandedDepth: 0,
  collapsible: false,
  indentSupported: false,
};

// we store the length of the tables structure for the sake of testing and debugging
export const initialState = { length: 0 };

/**
 * @method getTableId
 * @summary Small helper function to generate the table id depending on the values (if viewId is
 * provided, we'll use only that for grids, and if not - it's a tab table so document id
 * and tab ids are expected ).
 */
export const getTableId = ({ windowId, viewId, docId, tabId }) => {
  return `${windowId}_${viewId ? viewId : `${docId}_${tabId}`}`;
};

/**
 * @method selectTableHelper
 * @summary selector function for `getTable`
 */
const selectTableHelper = (state, id) => {
  return get(state, ['tables', id], initialTableState);
};

/**
 * @method getTable
 * @summary Selector for getting table object by id from the state
 */
export const getTable = createSelector(
  [selectTableHelper],
  (table) => table
);

const getSelectionData = (state, tableId) => {
  return getTable(state, tableId).selected;
};

export const getSelection = createSelector(
  [getSelectionData],
  (items) => items
);

const reducer = produce((draftState, action) => {
  switch (action.type) {
    // CRUD
    case types.CREATE_TABLE: {
      const { id, data } = action.payload;
      const newLength = draftState.length + 1;
      let updatedSelected = {};

      if (data.rows) {
        updatedSelected = {
          selected: [data.rows[0][data.keyProperty]],
        };
      }

      draftState[id] = {
        ...initialTableState,
        ...data,
        ...updatedSelected,
      };
      draftState.length = newLength;

      return;
    }

    case types.UPDATE_TABLE: {
      const { id, data } = action.payload;

      const prevTableStruct = draftState[id]
        ? draftState[id]
        : initialTableState;
      let updatedSelected = {};

      if (data.rows) {
        updatedSelected = {
          selected: [data.rows[0][data.keyProperty]],
        };
      }

      draftState[id] = {
        ...prevTableStruct,
        ...data,
        ...updatedSelected,
      };

      return;
    }

    case types.DELETE_TABLE: {
      const { id } = action.payload;
      const newLength = draftState.length - 1;

      draftState.length = newLength;
      delete draftState[id];

      return;
    }

    case types.UPDATE_TABLE_DATA: {
      const { id, rows } = action.payload;
      const currentSelected = draftState[id].selected;
      const selectionValid = doesSelectionExist({
        data: rows,
        selected: currentSelected,
      });
      let updatedSelected = {};

      if (!selectionValid) {
        updatedSelected = {
          selected: rows[0],
        };
      }

      draftState[id] = {
        ...draftState[id],
        rows,
        ...updatedSelected,
      };

      return;
    }

    case types.UPDATE_TABLE_SELECTION: {
      const { id, selection } = action.payload;

      const selectionValid = doesSelectionExist({
        data: draftState[id].rows,
        selected: selection,
        // hasIncluded,
      });

      if (selectionValid) {
        draftState[id] = {
          ...draftState[id],
          selected: selection,
        };
      }

      /**
       * TODO: for Kuba to fix when refactoring the Table component => this
       * TODO: has to be fixed to just be draftState[id].selected = selection . For that pls make sure that this action is done when
       * TODO: data exists in the draftState[id] (table structure for the corresponding id already present)
       */
      return;
    }

    case types.DESELECT_TABLE_ITEMS: {
      const { id, selection } = action.payload;

      if (selection.length) {
        draftState[id].selected = difference(
          draftState[id].selected,
          selection
        );
      } else {
        draftState[id].selected = [];
      }

      return;
    }

    case types.COLLAPSE_TABLE_ROWS: {
      const {
        id,
        collapsedArrayMap,
        collapsedParentRows,
        collapsedRows,
      } = action.payload;
      const table = draftState[id];

      draftState[id] = {
        ...table,
        collapsedArrayMap: collapsedArrayMap,
        collapsedParentRows: collapsedParentRows,
        collapsedRows: collapsedRows,
      };

      return;
    }

    case types.SET_ACTIVE_SORT: {
      const { id, active } = action.payload;

      draftState[id].activeSort = active;
    }
  }
}, initialState);

export default reducer;
