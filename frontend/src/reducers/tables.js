import { produce, original } from 'immer';
import { get, difference, forEach } from 'lodash';
import { createSelector } from 'reselect';

import * as types from '../constants/ActionTypes';
import { doesSelectionExist } from '../utils/documentListHelper';

export const initialTableState = {
  windowId: null,
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
  size: 0,

  //header columns
  headerElements: {},
  emptyText: null,
  emptyHint: null,
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
      const { id, rows, keyProperty } = action.payload;
      const currentSelected = draftState[id].selected;
      const selectionValid = doesSelectionExist({
        data: rows,
        selected: currentSelected,
      });
      let updatedSelected = {};

      if (!selectionValid) {
        updatedSelected = {
          selected: [rows[0][keyProperty]],
        };
      }

      draftState[id] = {
        ...draftState[id],
        rows,
        ...updatedSelected,
      };

      return;
    }

    case types.UPDATE_TAB_ROWS_DATA: {
      const {
        rows: { changed, removed },
        id,
      } = action.payload;
      let rows = original(draftState[id].rows);

      if (rows.length) {
        if (removed) {
          rows = rows.filter((row) => !removed[row.rowId]);
        }

        // find&replace updated rows (unfortunately it's a table so we'll have to traverse it)
        if (changed) {
          rows = rows.map((row) => {
            if (changed[row.rowId]) {
              row = { ...changed[row.rowId] };

              delete changed[row.rowId];

              return row;
            }
            return row;
          });
        }
      } else {
        rows = [];
      }
      // added rows
      forEach(changed, (value) => rows.push(value));

      draftState[id].rows = rows;

      return;
    }

    case types.UPDATE_TABLE_SELECTION: {
      const { id, selection, keyProperty } = action.payload;
      const rows = original(draftState[id].rows);

      const selectionValid = doesSelectionExist({
        data: rows,
        selected: selection,
        keyProperty,
      });

      if (selectionValid) {
        draftState[id].selected = selection;
      }

      return;
    }

    case types.DESELECT_TABLE_ITEMS: {
      const { id, selection } = action.payload;

      // just for precaution
      if (draftState[id]) {
        if (selection.length) {
          draftState[id].selected = difference(
            draftState[id].selected,
            selection
          );
        } else {
          draftState[id].selected = [];
        }
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
