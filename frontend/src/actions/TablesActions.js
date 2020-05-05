import { List as iList, Map as iMap } from 'immutable';
import * as types from '../constants/ActionTypes';

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

function createTableData(rawData) {
  return {
    windowType: rawData.windowType || rawData.windowId,
    viewId: rawData.viewId,
    docId: rawData.id,
    tabId: rawData.tabId,
    headerProperties: rawData.headerProperties,
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
  return (dispatch) => {
    const tableData = createTableData(tableResponse);

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
    console.log('createTabTable: ', tableResponse)
    // const tableData = createTableData(tableResponse);

    // dispatch(createTable(tableId, tableData));
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
