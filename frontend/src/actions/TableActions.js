import { List as iList, Map as iMap } from 'immutable';
import { reduce } from 'lodash';
import * as types from '../constants/ActionTypes';

import { getView } from '../reducers/viewHandler';

function createTable(id, data) {
  return {
    type: types.CREATE_TABLE,
    payload: { id, data },
  };
}

function updateTable(id, data) {
  return {
    type: types.UPDATE_TABLE,
    payload: { id, data },
  };
}

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

export function setActiveSortNEW(id, active) {
  return {
    type: types.SET_ACTIVE_SORT_NEW,
    payload: { id, active },
  };
}

function createTableData(rawData) {
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
      ? iMap(rawData.headerProperties)
      : undefined,
    headerElements: rawData.headerElements
      ? iList(rawData.headerElements)
      : undefined,
    orderBy: rawData.orderBy ? iList(rawData.orderBy) : undefined,
    columns: rawData.columns ? iList(rawData.columns) : undefined,
    rows: rawData.result ? iList(rawData.result) : undefined,
    defaultOrderBys: rawData.defaultOrderBys
      ? iList(rawData.defaultOrderBys)
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

/*
 * Create a new table entry for grids using data from the window layout (so not populated with data yet)
 */
export function createGridTable(tableId, tableResponse) {
  return (dispatch, getState) => {
    const windowType = tableResponse.windowType || tableResponse.windowId;
    const tableLayout = getView(getState(), windowType).layout;
    const tableData = createTableData({ ...tableResponse, ...tableLayout });

    dispatch(createTable(tableId, tableData));
  };
}

/*
 * Populate grid table with data and initial settings
 */
export function updateGridTable(tableId, tableResponse) {
  return (dispatch, getState) => {
    const tableExists = getState().tables.get(tableId);
    const tableData = createTableData({
      ...tableResponse,
      columns: Object.values(tableResponse.columnsByFieldName),
    });

    if (tableExists) {
      dispatch(updateTable(tableId, tableData));
    } else {
      dispatch(createTable(tableId, tableData));
    }
  };
}

/*
 * Create a new table entry for the details view when it's created, setting only the ids
 */
export function createTabTable(tableId, tableResponse) {
  return (dispatch) => {
    const tableData = createTableData(tableResponse);

    dispatch(createTable(tableId, tableData));
  };
}

/*
 * Update table entry for the details view with layout and data rows
 */
export function updateTabTable(tableId, tableResponse) {
  return (dispatch, getState) => {
    const tableExists = getState().tables.get(tableId);
    const tableData = createTableData({
      ...tableResponse,
      columns: tableResponse.elements,
    });

    if (tableExists) {
      dispatch(updateTable(tableId, tableData));
    } else {
      dispatch(createTable(tableId, tableData));
    }
  };
}

// TODO: selections, other small updates
