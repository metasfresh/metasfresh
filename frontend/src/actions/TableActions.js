import { reduce, cloneDeep } from 'lodash';
import * as types from '../constants/ActionTypes';
import { UPDATE_TABLE_SELECTION } from '../constants/actions/TableTypes';

import { getView } from '../reducers/viewHandler';

/**
 * @method createTable
 * @summary Add a new table entry to the redux store
 */
function createTable(id, data) {
  return {
    type: types.CREATE_TABLE,
    payload: { id, data },
  };
}

/**
 * @method updateTable
 * @summary Perform a major update (many values at once) of a table entry. Think
 * rows/columns
 */
function updateTable(id, data) {
  return {
    type: types.UPDATE_TABLE,
    payload: { id, data },
  };
}

/**
 * @method deleteTable
 * @summary Remove the table with specified `id` from the store
 */
export function deleteTable(id) {
  return {
    type: types.DELETE_TABLE,
    payload: { id },
  };
}

// TODO: Legacy version. Remove once we switch to rendering tables from redux
export function setActiveSort(data) {
  return {
    type: types.SET_ACTIVE_SORT,
    payload: data,
  };
}

/**
 * @method setActiveSortNEW
 * @summary Change the value of the `activeSort` setting for specified table
 * @todo rename to `setActiveSort` once we switch to tables driven by redux
 */
export function setActiveSortNEW(id, active) {
  return {
    type: types.SET_ACTIVE_SORT_NEW,
    payload: { id, active },
  };
}

// TODO: selections, other small updates

/**
 * @method createTableData
 * @summary Helper function to grab raw data and format/name it accordingly to
 * the values in the store.
 */
export function createTableData(rawData) {
  const dataObject = {
    windowType: rawData.windowType || rawData.windowId,
    viewId: rawData.viewId,
    docId: rawData.id,
    tabId: rawData.tabId,
    emptyText: rawData.emptyResultText,
    emptyHint: rawData.emptyResultHint,
    page: rawData.page,
    size: rawData.size,
    queryLimit: rawData.queryLimit,
    pageLength: rawData.pageLength,
    firstRow: rawData.firstRow,
    tabIndex: rawData.tabIndex,
    internalName: rawData.internalName,
    queryOnActivate: rawData.queryOnActivate,
    supportQuickInput: rawData.supportQuickInput,
    allowCreateNew: rawData.allowCreateNew,
    allowCreateNewReason: rawData.allowCreateNewReason,
    allowDelete: rawData.allowDelete,
    stale: rawData.stale,
    headerProperties: rawData.headerProperties
      ? rawData.headerProperties
      : undefined,
    headerElements: rawData.headerElements ? rawData.headerElements : undefined,
    orderBy: rawData.orderBy ? rawData.orderBy : undefined,

    // immer freezes objects to make them immutable, so we have to make a deep copy
    // of entries as otherwise we're just passing references to frozen objects
    columns: rawData.elements ? cloneDeep(rawData.elements) : undefined,
    rows: rawData.result ? cloneDeep(rawData.result) : undefined,
    defaultOrderBys: rawData.defaultOrderBys
      ? rawData.defaultOrderBys
      : undefined,
  };

  // we're removing any keys without a value ta make merging with the existing data
  // easier/faster
  return reduce(
    dataObject,
    (result, value, key) => {
      if (typeof value !== 'undefined') {
        result[key] = value;
      }
      return result;
    },
    {}
  );
}

// THUNK ACTION CREATORS

/*
 * Create a new table entry for grids using data from the window layout (so not populated with data yet)
 */
export function createGridTable(tableId, tableResponse) {
  return (dispatch, getState) => {
    const windowType = tableResponse.windowType || tableResponse.windowId;
    const tableLayout = getView(getState(), windowType).layout;
    const tableData = createTableData({ ...tableResponse, ...tableLayout });

    dispatch(createTable(tableId, tableData));

    return Promise.resolve(tableData);
  };
}

/*
 * Populate grid table with data and initial settings
 */
export function updateGridTable(tableId, tableResponse) {
  return (dispatch, getState) => {
    const state = getState();

    if (state.tables) {
      const tableExists = state.tables[tableId];

      if (tableExists) {
        const tableData = createTableData({
          ...tableResponse,
          headerElements: tableResponse.columnsByFieldName,
        });

        dispatch(updateTable(tableId, tableData));
      } else {
        const windowType = tableResponse.windowType || tableResponse.windowId;
        const tableLayout = getView(getState(), windowType).layout;
        const tableData = createTableData({
          ...tableResponse,
          ...tableLayout,
          headerElements: tableResponse.columnsByFieldName,
        });

        dispatch(createTable(tableId, tableData));
      }

      return Promise.resolve(true);
    }

    return Promise.resolve(false);
  };
}

/*
 * Create a new table entry for the details view when it's created, setting only the ids
 */
export function createTabTable(tableId, tableResponse) {
  return (dispatch) => {
    const tableData = createTableData(tableResponse);

    dispatch(createTable(tableId, tableData));

    return Promise.resolve(tableData);
  };
}

/*
 * Update table entry for the details view with layout and data rows
 */
export function updateTabTable(tableId, tableResponse) {
  return (dispatch, getState) => {
    const state = getState();

    if (state.tables) {
      const tableExists = state.tables[tableId];
      const tableData = createTableData(tableResponse);

      if (tableExists) {
        dispatch(updateTable(tableId, tableData));
      } else {
        dispatch(createTable(tableId, tableData));
      }

      return Promise.resolve(true);
    }

    return Promise.resolve(false);
  };
}

/**
 * Update table selection - select items
 */
export function updateTableSelection({ tableId, ids }) {
  return {
    type: UPDATE_TABLE_SELECTION,
    payload: { tableId, selection: ids },
  };
}
