import { cloneDeep, find, get, reduce, uniqBy } from 'lodash';

import { flattenRows } from '../utils/documentListHelper';
import * as types from '../constants/ActionTypes';

import { fetchQuickActions } from './Actions';
import { showIncludedView } from './ViewActions';
import {
  deleteViewAttributes,
  fetchViewAttributes,
  fetchViewAttributesLayout,
} from './IndependentWidgetsActions';

import { getView } from '../reducers/viewHandler';
import { getSupportAttribute, getTable } from '../reducers/tables';

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
 * @method collapseRows
 * @summary Toggle table rows
 *
 * @param {string} tableId
 * @param {array} collapsedParentRows - main collapsed rows
 * @param {array} collapsedRows - descendants of collapsed rows
 */
function collapseRows({ tableId, collapsedParentRows, collapsedRows }) {
  return {
    type: types.COLLAPSE_TABLE_ROWS,
    payload: {
      id: tableId,
      collapsedParentRows,
      collapsedRows,
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
 * @summary Update single row
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
 * @method setTableNavigation
 * @summary Used to set the flag to enable/disable table navigation. Used by some widgets (like attributes)
 *
 * @param {string} id - table id
 * @param {boolean} active
 */
export function setTableNavigation(id, active) {
  return {
    type: types.SET_TABLE_NAVIGATION,
    payload: {
      id,
      active,
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
    pending: rawData.pending,
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
    orderBys: rawData.orderBys,
    defaultOrderBys: rawData.defaultOrderBys
      ? rawData.defaultOrderBys
      : undefined,
    expandedDepth: rawData.expandedDepth,
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
 * @method updateGridTable
 * @summary Populate grid table with data and initial settings
 *
 * @param {string} tableId - table id
 * @param {object} tableResponse - response data for the table
 */
export function fetchAttributes(tableId, tableResponse) {
  return (dispatch, getState) => {
    const state = getState();
    const tableData = state.tables[tableId]
      ? state.tables[tableId]
      : tableResponse;

    const { rows, supportAttribute, keyProperty, windowId, viewId, selected } =
      tableData;

    let rowId =
      selected && selected.length
        ? state.tables[tableId].selected[0]
        : tableResponse &&
          tableResponse.rows &&
          tableResponse.rows.length &&
          tableResponse.rows[0][keyProperty];

    if (supportAttribute && rowId) {
      const rowSupportAttribute = getSupportAttribute([rowId], rows);

      if (rowSupportAttribute) {
        dispatch(fetchViewAttributesLayout({ windowId, viewId, rowId }));
        dispatch(fetchViewAttributes({ windowId, viewId: viewId, rowId }));
      }
    } else {
      dispatch(deleteViewAttributes());
    }
  };
}

/**
 * @method deleteTable
 * @summary Remove the table with specified `id` from the store
 */
export function deleteTable(id) {
  return (dispatch) => {
    // remove old attributes data
    dispatch(deleteViewAttributes());
    dispatch({
      type: types.DELETE_TABLE,
      payload: { id },
    });
  };
}

/*
 * @method createGridTable
 * @summary Create a new table entry for grids using data from the window
 * layout (so not populated with data yet)
 */
export function createGridTable(tableId, tableResponse) {
  return (dispatch, getState) => {
    const isModal = !!tableResponse.modalId;
    const windowId = isModal
      ? tableResponse.modalId
      : tableResponse.windowType || tableResponse.windowId;
    const view = getView(getState(), windowId, isModal);
    const tableLayout = view.layout;
    const tableData = createTableData({
      ...tableResponse,
      ...tableLayout,
      ...extractEmptyResultTextAndHint({ tableResponse, tableLayout }),
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
    const tableExists = state.tables[tableId];
    const isModal = !!tableResponse.modalId;
    const windowId = isModal
      ? tableResponse.modalId
      : tableResponse.windowType || tableResponse.windowId;
    const view = getView(getState(), windowId, isModal);
    const tableLayout = view.layout;
    let tableData;

    if (tableExists) {
      const { indentSupported } = tableExists;
      const { collapsible, expandedDepth } = tableExists;
      tableData = createTableData({
        ...tableResponse,
        ...tableLayout,
        ...extractEmptyResultTextAndHint({ tableResponse, tableLayout }),
        headerElements: tableResponse.columnsByFieldName,
        keyProperty: 'id',
      });
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
    } else {
      tableData = createTableData({
        ...tableResponse,
        ...tableLayout,
        ...extractEmptyResultTextAndHint({ tableResponse, tableLayout }),
        headerElements: tableResponse.columnsByFieldName,
        keyProperty: 'id',
      });
      const { collapsible, expandedDepth, keyProperty, indentSupported } =
        tableData;

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
    }

    dispatch(fetchAttributes(tableId, tableData));

    return Promise.resolve(true);
  };
}

const extractEmptyResultTextAndHint = ({ tableResponse, tableLayout }) => {
  if (tableResponse?.emptyResultText) {
    return {
      emptyResultText: tableResponse.emptyResultText,
      emptyResultHint: tableResponse?.emptyResultHint || '',
    };
  } else if (tableLayout?.emptyResultText) {
    return {
      emptyResultText: tableLayout.emptyResultText,
      emptyResultHint: tableLayout?.emptyResultHint || '',
    };
  } else {
    return {
      emptyResultText: '',
      emptyResultHint: '',
    };
  }
};

/*
 * @method updateGridTableData
 * @summary Update grid table's rows and rebuild collapsed rows if necessary
 */
export function updateGridTableData({
  tableId,
  rows,
  preserveCollapsedStateToRowIds,
  customLayoutFlags,
}) {
  return (dispatch, getState) => {
    const state = getState();

    if (state.tables) {
      const table = state.tables[tableId];
      const { indentSupported, keyProperty } = table;

      if (rows.length && indentSupported) {
        rows = flattenRows(rows);
        // table rows are already flattened so we will end up with duplicates from
        // `includedDocuments` being flattened again
        rows = uniqBy(rows, 'id');
      }

      dispatch(updateTableData(tableId, rows, keyProperty));

      if (indentSupported) {
        dispatch(
          updateCollapsedRows({
            tableId,
            rows,
            preserveCollapsedStateToRowIds,
            customLayoutFlags,
          })
        );
      }

      return Promise.resolve(true);
    }

    return Promise.resolve(false);
  };
}

export function partialUpdateGridTableRows({ tableId, rowsToUpdate }) {
  return {
    type: types.PARTIAL_UPDATE_TABLE_DATA,
    payload: { tableId, rowsToUpdate },
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
export function updateTabTable({ tableId, tableResponse, pending }) {
  return (dispatch, getState) => {
    const state = getState();

    if (state.tables) {
      const tableExists = state.tables[tableId];
      const tableData = createTableData({
        ...(tableResponse ? tableResponse : {}),
        keyProperty: 'rowId',
        pending,
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
    let collapsedRows = [];
    let collapsedParentRows = [];

    if (collapsible && rows.length) {
      rows.forEach((row) => {
        if (row.indent.length >= expandedDepth && row.includedDocuments) {
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
        })
      );
    }
  };
}

/**
 * @method updateCollapsedRows
 * @summary Create a new table entry for grids using data from the window layout
 * (so not populated with data yet)
 *
 * @param {string} tableId
 * @param {array} rows
 * @param {array} preserveCollapsedStateToRowIds - array with ids of the rows to be changed that are sent with a websocket message
 *                                                (if it's not empty it will preserve the previous state of the tree)
 *                                                (if it's empty it will not preserve the state causing the tree to be uncollapsed )
 * @param {object} custom flags that come from the layout. i.e `uncollapseRowsOnChange`
 *        Note: this `uncollapseRowsOnChange` does not come yet from the backend response but if it was to be set to `true`
 *              it will force the rows to be uncollapsed. (`Create Purchase Order action` for a `Sales order line`)
 */
function updateCollapsedRows({
  tableId,
  rows,
  preserveCollapsedStateToRowIds,
  customLayoutFlags,
}) {
  const { uncollapseRowsOnChange } = customLayoutFlags;

  return (dispatch, getState) => {
    const state = getState();
    const table = state.tables[tableId];
    const {
      expandedDepth,
      keyProperty,
      collapsible,
      collapsedRows,
      collapsedParentRows,
    } = table;

    const hasPreservedStateToRowIds =
      preserveCollapsedStateToRowIds &&
      preserveCollapsedStateToRowIds.length > 0;

    if (collapsible) {
      let newCollapsedParentRows =
        hasPreservedStateToRowIds || !uncollapseRowsOnChange
          ? [...collapsedParentRows]
          : [];
      let newCollapsedRows =
        hasPreservedStateToRowIds || !uncollapseRowsOnChange
          ? [...collapsedRows]
          : [];

      if (rows.length) {
        rows.forEach((row) => {
          if (
            row.indent.length >= expandedDepth &&
            row.includedDocuments &&
            !collapsedParentRows.indexOf(row.id)
          ) {
            newCollapsedParentRows = !newCollapsedParentRows.includes(
              row[keyProperty]
            )
              ? newCollapsedParentRows.concat(row[keyProperty])
              : newCollapsedParentRows;
          } else if (
            row.indent.length > expandedDepth &&
            !collapsedRows.indexOf(row.id)
          ) {
            newCollapsedRows = !newCollapsedRows.includes(row[keyProperty])
              ? newCollapsedRows.concat(row[keyProperty])
              : newCollapsedRows;
          }
        });
      }

      dispatch(
        collapseRows({
          tableId,
          collapsedParentRows: newCollapsedParentRows,
          collapsedRows: newCollapsedRows,
          preserveCollapsedStateToRowIds,
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
    // immer in the reducer. In ideal case we should not reuse objects here.
    let collapsedParentRows = cloneDeep(table.collapsedParentRows);
    let collapsedRows = cloneDeep(table.collapsedRows);
    const { keyProperty } = table;

    const inner = (parentNode) => {
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
 * @param {number} windowId
 * @param {string} viewId
 * @param {boolean} isModal
 */
export function updateTableSelection({
  id,
  selection,
  keyProperty = 'id',
  windowId,
  viewId,
  isModal,
}) {
  return (dispatch) => {
    dispatch({
      type: types.UPDATE_TABLE_SELECTION,
      payload: { id, selection, keyProperty },
    });

    if (viewId) {
      return dispatch(
        handleToggleIncludedView({
          windowId,
          viewId,
          tableId: id,
          selection,
          isModal,
        })
      ).then(dispatch(fetchAttributes(id)));
    }

    return Promise.resolve(selection);
  };
}

/**
 * @method deselectTableRows
 * @summary deselect rows or deselect all if an empty `ids` array is provided
 *
 * @param {string} id - table id
 * @param {array} selection - array of items to deselect
 * @param {number} windowId
 * @param {string} viewId
 * @param {boolean} isModal
 */
export function deselectTableRows({
  id,
  selection,
  windowId,
  viewId,
  isModal,
}) {
  return (dispatch) => {
    dispatch({
      type: types.DESELECT_TABLE_ROWS,
      payload: { id, selection },
    });

    if (viewId) {
      return dispatch(
        handleToggleIncludedView({
          windowId,
          viewId,
          tableId: id,
          selection,
          isModal,
        })
      ).then(dispatch(fetchAttributes(id)));
    }

    return Promise.resolve(selection);
  };
}

/**
 * @method handleToggleIncludedView
 * @summary Depending on the parameters call the AC responsible for toggling
 * the included view.
 *
 * @param {number} windowId
 * @param {string} viewId
 * @param {string} tableId
 * @param {array} selection - array of selected/deselected items
 * @param {boolean} isModal
 */
function handleToggleIncludedView({
  windowId,
  viewId,
  tableId,
  selection,
  isModal,
}) {
  return (dispatch, getState) => {
    const state = getState();
    const viewLayout = getView(state, windowId, isModal).layout;
    const openIncludedViewOnSelect = !!viewLayout.includedView?.openOnSelect;

    //
    // Update included view status
    {
      const closeIncludedViewOnDeselected =
        viewLayout.includedView?.closeOnDeselect ?? true;
      const includedView = state.viewHandler.includedView;
      const { selected, keyProperty, rows } = getTable(state, tableId);

      let isShowIncludedView;
      let forceClose = false;
      let includedWindowId;
      let includedViewId;
      let includedViewStateChanged;

      // selection is empty, so we're closing the included view
      if (selected.length === 0 && includedView.parentId === windowId) {
        if (closeIncludedViewOnDeselected) {
          isShowIncludedView = false;
          forceClose = true;
          includedWindowId = includedView.windowId;
          includedViewId = includedView.viewId;
          includedViewStateChanged = true;
        } else {
          includedViewStateChanged = false;
        }
      } else {
        const itemId = selection[selection.length - 1];
        const item = find(rows, (row) => row[keyProperty] === itemId);

        isShowIncludedView = get(item, ['supportIncludedViews'], false);
        includedWindowId = isShowIncludedView
          ? get(item, ['includedView', 'windowId'], null)
          : null;
        includedViewId = isShowIncludedView
          ? get(item, ['includedView', 'viewId'], null)
          : null;

        includedViewStateChanged = true;
      }

      if (includedViewStateChanged) {
        dispatch(
          showIncludedView({
            id: windowId,
            showIncludedView: isShowIncludedView,
            forceClose,
            windowId: includedWindowId,
            viewId: includedViewId,
            isModal,
          })
        );
      }
    }

    dispatch(fetchQuickActions({ windowId, viewId, isModal }));

    return Promise.resolve(openIncludedViewOnSelect);
  };
}
