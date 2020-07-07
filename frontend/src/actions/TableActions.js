import { reduce, cloneDeep } from 'lodash';

import * as types from '../constants/ActionTypes';
import { getView } from '../reducers/viewHandler';
import { getTable } from '../reducers/tables';
import { createCollapsedMap, flattenRows } from '../utils/documentListHelper';

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
 * initial load
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

/**
 * @method updateTableData
 * @summary Update table rows
 *
 * @param {string} id - table id
 * @param {array} rows - array of new/updated/removed rows
 * @param {string} keyProperty - `id` or `rowId` depending on the table type
 */
function updateTableData(id, rows, keyProperty) {
  return {
    type: types.UPDATE_TABLE_DATA,
    payload: { id, rows, keyProperty },
  };
}

/**
 * @method setActiveSort
 * @summary Change the value of the `activeSort` setting for specified table
 */
export function setActiveSort(id, active) {
  return {
    type: types.SET_ACTIVE_SORT,
    payload: { id, active },
  };
}

/**
 * @method deselectTableItems
 * @summary deselect items or deselect all if an empty `ids` array is provided
 */
export function deselectTableItems(id, selection) {
  return {
    type: types.DESELECT_TABLE_ITEMS,
    payload: { id, selection },
  };
}

/**
 * @method collapseRows
 * @summary Toggle table rows
 *
 * @param {string} tableId
 * @param {array} collapsedParentRows - main collapsed rows
 * @param {array} collapsedRows - descendants of collapsed rows
 * @param {array} collapsedArrayMap - all collapsed rows
 */
function collapseRows({
  tableId,
  collapsedParentRows,
  collapsedRows,
  collapsedArrayMap,
}) {
  return {
    type: types.COLLAPSE_TABLE_ROWS,
    payload: {
      id: tableId,
      collapsedParentRows,
      collapsedRows,
      collapsedArrayMap,
    },
  };
}

/**
 * @method updateTabRowsData
 * @summary Update rows in tab table
 *
 * @param {string} id - tableId
 * @param {array} rows
 */
export function updateTabRowsData(id, rows) {
  return {
    type: types.UPDATE_TAB_ROWS_DATA,
    payload: {
      id,
      // doing a deep copy because we're altering the changed/removed
      // arrays while merging the updated data
      rows: cloneDeep(rows),
    },
  };
}

/**
 * @method updateTableRowProperty
 * @summary Update table selection - select items
 *
 * @param {string} id - table id
 * @param {number} rowId - rowId
 * @param {object} change - updated part of the object
 */
export function updateTableRowProperty({ tableId, rowId, change }) {
  return {
    type: types.UPDATE_TABLE_ROW_PROPERTY,
    payload: {
      id: tableId,
      rowId,
      change,
    },
  };
}

/**
 * @method clearTableData
 * @summary Remove all rows
 *
 * @param {string} id - table id
 */
export function clearTableData(id) {
  return {
    type: types.CLEAR_TABLE_DATA,
    payload: {
      id,
    },
  };
}

/**
 * @method createTableData
 * @summary Helper function to grab raw data and format/name it accordingly to
 * the values in the store.
 */
export function createTableData(rawData) {
  const dataObject = {
    windowId: rawData.windowType || rawData.windowId,
    viewId: rawData.viewId,
    docId: rawData.id,
    tabId: rawData.tabId,
    keyProperty: rawData.keyProperty,
    emptyText: rawData.emptyResultText,
    emptyHint: rawData.emptyResultHint,
    size: rawData.size,
    firstRow: rawData.firstRow,
    headerProperties: rawData.headerProperties
      ? rawData.headerProperties
      : undefined,
    headerElements: rawData.headerElements ? rawData.headerElements : undefined,

    // immer freezes objects to make them immutable, so we have to make a deep copy
    // of entries as otherwise we're just passing references to frozen objects
    columns: rawData.elements ? cloneDeep(rawData.elements) : undefined,
    rows: rawData.result ? cloneDeep(rawData.result) : undefined,
    defaultOrderBys: rawData.defaultOrderBys
      ? rawData.defaultOrderBys
      : undefined,
    expandedDepth: rawData.expandedDepth,

    // TODO: We have both `supportTree` and `collapsible` in the layout response.
    collapsible: rawData.collapsible,
    indentSupported: rawData.supportTree,
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
 * @method createGridTable
 * @summary Create a new table entry for grids using data from the window
 * layout (so not populated with data yet)
 */
export function createGridTable(tableId, tableResponse) {
  return (dispatch, getState) => {
    let tableLayout = null;

    // modals are created when fetching layout
    if (tableResponse.layout) {
      tableLayout = tableResponse.layout;
    } else {
      const windowId = tableResponse.windowType || tableResponse.windowId;
      tableLayout = getView(getState(), windowId).layout;
    }

    const tableData = createTableData({
      ...tableResponse,
      ...tableLayout,
    });

    dispatch(createTable(tableId, tableData));

    return Promise.resolve(tableData);
  };
}

/*
 * @method updateGridTable
 * @summary Populate grid table with data and initial settings
 */
export function updateGridTable(tableId, tableResponse) {
  return (dispatch, getState) => {
    const state = getState();

    // this check is only for unit tests purposes
    if (state.tables) {
      const tableExists = state.tables[tableId];

      if (tableExists) {
        const { indentSupported } = tableExists;
        const tableData = createTableData({
          ...tableResponse,
          headerElements: tableResponse.columnsByFieldName,
          keyProperty: 'id',
        });
        const { collapsible, expandedDepth } = tableExists;
        const { keyProperty } = tableData;

        // Parse `rows` to add `indent` property
        if (tableData.rows.length && indentSupported) {
          tableData.rows = flattenRows(tableData.rows);
        }

        dispatch(updateTable(tableId, tableData));

        if (indentSupported) {
          dispatch(
            createCollapsedRows({
              tableId,
              rows: tableData.rows,
              collapsible,
              expandedDepth,
              keyProperty,
            })
          );
        }

        return Promise.resolve(true);
      } else {
        const windowType = tableResponse.windowType || tableResponse.windowId;
        const tableLayout = getView(getState(), windowType).layout;
        const tableData = createTableData({
          ...tableResponse,
          ...tableLayout,
          headerElements: tableResponse.columnsByFieldName,
          keyProperty: 'id',
        });
        const {
          collapsible,
          expandedDepth,
          keyProperty,
          indentSupported,
        } = tableData;

        if (tableData.rows && tableData.rows.length && indentSupported) {
          tableData.rows = flattenRows(tableData.rows);
        }

        dispatch(createTable(tableId, tableData));

        if (indentSupported) {
          dispatch(
            createCollapsedRows({
              tableId,
              rows: tableData.rows,
              collapsible,
              expandedDepth,
              keyProperty,
            })
          );
        }

        return Promise.resolve(true);
      }
    }

    return Promise.resolve(false);
  };
}

/*
 * @method updateGridTableData
 * @summary Update grid table's rows and rebuild collapsed rows if necessary
 */
export function updateGridTableData(tableId, rows) {
  return (dispatch, getState) => {
    const state = getState();

    if (state.tables) {
      const table = state.tables[tableId];
      const { collapsible, expandedDepth, keyProperty } = table;

      if (rows.length && collapsible) {
        rows = flattenRows(rows);
      }

      dispatch(updateTableData(tableId, rows, keyProperty));

      if (collapsible) {
        dispatch(
          createCollapsedRows({
            tableId,
            rows,
            collapsible,
            expandedDepth,
            keyProperty,
          })
        );
      }

      return Promise.resolve(true);
    }

    return Promise.resolve(false);
  };
}

/*
 * @method createTabTable
 * @summary Create a new table entry for the details view when it's created,
 * setting only the ids
 */
export function createTabTable(tableId, tableResponse) {
  return (dispatch) => {
    const tableData = createTableData({
      ...tableResponse,
    });

    dispatch(createTable(tableId, tableData));

    return Promise.resolve(tableData);
  };
}

/*
 * @method updateTabTable
 * @summary Update table entry for the details view with layout and data rows
 */
export function updateTabTable(tableId, tableResponse) {
  return (dispatch, getState) => {
    const state = getState();

    if (state.tables) {
      const tableExists = state.tables[tableId];
      const tableData = createTableData({
        ...tableResponse,
        keyProperty: 'rowId',
      });

      if (tableData.rows && tableData.rows.length) {
        tableData.rows = flattenRows(tableData.rows);
      }

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

/*
 * @method updateTabTableData
 * @summary Update tab table's rows for tab table/details view
 */
export function updateTabTableData(tableId, rows) {
  return (dispatch, getState) => {
    const state = getState();

    if (state.tables) {
      const table = getTable(state, tableId);
      const { keyProperty } = table;

      dispatch(updateTableData(tableId, rows, keyProperty));

      return Promise.resolve(true);
    }

    return Promise.resolve(false);
  };
}

/**
 * @method createCollapsedRows
 * @summary Create a new table entry for grids using data from the window layout
 * (so not populated with data yet)
 *
 * @param {string} tableId
 * @param {array} rows
 * @param {bool} collapsible
 * @param {number} expandedDepth
 * @param {string} keyProperty - `id` or `rowId` depending on the table type
 */
function createCollapsedRows({
  tableId,
  rows,
  collapsible,
  expandedDepth,
  keyProperty,
}) {
  return (dispatch) => {
    let collapsedArrayMap = [];
    let collapsedParentRows = [];
    let collapsedRows = [];

    if (collapsible && rows.length) {
      rows.forEach((row) => {
        if (row.indent.length >= expandedDepth && row.includedDocuments) {
          collapsedArrayMap = collapsedArrayMap.concat(createCollapsedMap(row));
          collapsedParentRows = collapsedParentRows.concat(row[keyProperty]);
        } else if (row.indent.length > expandedDepth) {
          collapsedRows = collapsedRows.concat(row[keyProperty]);
        }
      });
    }

    if (collapsible) {
      dispatch(
        collapseRows({
          tableId,
          collapsedParentRows,
          collapsedRows,
          collapsedArrayMap,
        })
      );
    }
  };
}

/*
 * @method collapseTableRow
 * @summary Toggle collapse state of a single row and it's descendants
 */
export function collapseTableRow({ tableId, collapse, node }) {
  return (dispatch, getState) => {
    const table = getTable(getState(), tableId);

    // TODO: We're cloning those arrays, because they're frozen by
    // immer in the reducer. In ideal case we should not reuse objects
    // neither here nor in `createCollapsedMap`.
    let collapsedParentRows = cloneDeep(table.collapsedParentRows);
    let collapsedRows = cloneDeep(table.collapsedRows);
    let collapsedArrayMap = cloneDeep(table.collapsedArrayMap);
    const { keyProperty } = table;

    const inner = (parentNode) => {
      collapsedArrayMap = createCollapsedMap(
        parentNode,
        collapse,
        collapsedArrayMap
      );

      if (collapse) {
        collapsedParentRows.splice(
          collapsedParentRows.indexOf(parentNode[keyProperty]),
          1
        );
      } else {
        if (collapsedParentRows.indexOf(parentNode[keyProperty]) > -1) return;

        collapsedParentRows = collapsedParentRows.concat(
          parentNode[keyProperty]
        );
      }

      parentNode.includedDocuments &&
        parentNode.includedDocuments.map((childNode) => {
          if (collapse) {
            collapsedRows.splice(
              collapsedRows.indexOf(childNode[keyProperty]),
              1
            );
          } else {
            if (collapsedRows.indexOf(childNode[keyProperty]) > -1) return;

            collapsedRows = collapsedRows.concat(childNode[keyProperty]);
            childNode.includedDocuments && inner(childNode);
          }
        });
    };

    inner(node);

    const returnData = {
      tableId,
      collapsedRows,
      collapsedParentRows,
      collapsedArrayMap,
    };

    dispatch(collapseRows(returnData));

    return Promise.resolve(returnData);
  };
}

/**
 * @method updateTableSelection
 * @summary Update table selection - select items
 *
 * @param {string} id - table id
 * @param {array} selection - array of selected items. This will be validated
 * for existence in the rows data by the reducer, but not for duplication
 * @param {string} keyProperty=id - `id` or `rowId` depending on the table type
 */
export function updateTableSelection(id, selection, keyProperty = 'id') {
  return (dispatch) => {
    dispatch({
      type: types.UPDATE_TABLE_SELECTION,
      payload: { id, selection, keyProperty },
    });

    return Promise.resolve(selection);
  };
}
