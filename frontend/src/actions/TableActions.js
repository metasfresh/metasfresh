import { List as iList, Map as iMap } from 'immutable';
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

function deleteTable(id) {
  return {
    type: types.DELETE_TABLE,
    payload: { id },
  };
}

// TODO: This should accept id, and return object with payload { id, others }
export function setActiveSort(data) {
  return {
    type: types.SET_ACTIVE_SORT,
    payload: data,
  };
}

function createTableData(rawData) {
  return {
    windowType: rawData.windowType || rawData.windowId,
    viewId: rawData.viewId,
    docId: rawData.id,
    tabId: rawData.tabId,
    headerProperties: rawData.headerProperties,
    headerElements: iList(rawData.elements),
    emptyText: rawData.emptyResultText,
    emptyHint: rawData.emptyResultHint,
    page: rawData.page,
    size: rawData.size,
    orderBy: rawData.orderBy,
    queryLimit: rawData.queryLimit,
    columns: iMap(rawData.columnsByFieldName),
    rows: iList(rawData.result),
    pageLength: rawData.pageLength,
    firstRow: rawData.firstRow,
  };
}

export function createGridTable(tableId, tableResponse) {
  return (dispatch, getState) => {
    const windowType = tableResponse.windowType || tableResponse.windowId;
    const tableLayout = getView(getState(), windowType).layout;
    const tableData = createTableData({ ...tableResponse, ...tableLayout });

    dispatch(createTable(tableId, tableData));
  };
}

export function updateGridTable(tableId, tableResponse) {
  return (dispatch, getState) => {
    const tableExists = getState().tables.get(tableId);
    const tableData = createTableData(tableResponse);

    if (tableExists) {
      dispatch(updateTable(tableId, tableData));
    } else {
      dispatch(createTable(tableId, tableData));
    }
  };
}

export function createTabTable(tableId, tableResponse) {
  return (dispatch) => {
    const tableData = createTableData(tableResponse);

    dispatch(createTable(tableId, tableData));
  };
}

export function updateTabTable(tableId, tableResponse) {
  return (dispatch, getState) => {
    const tableExists = getState().tables.get(tableId);
    const tableData = createTableData(tableResponse);

    if (tableExists) {
      dispatch(updateTable(tableId, tableData));
    } else {
      dispatch(createTable(tableId, tableData));
    }
  };
}
