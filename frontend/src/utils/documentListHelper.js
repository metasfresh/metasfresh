import React from 'react';
import PropTypes from 'prop-types';
import Moment from 'moment-timezone';
import currentDevice from 'current-device';
import { toInteger } from 'lodash';

import { deepUnfreeze, getItemsByProperty, nullToEmptyStrings } from './index';
import { getView, viewState } from '../reducers/viewHandler';
import { getSelection, getTable, getTableId } from '../reducers/tables';
import { getCachedFilter, getEntityRelatedId } from '../reducers/filters';
import { TIME_REGEX_TEST } from '../constants/Constants';
import { getCurrentActiveLocale } from './locale';

const DEFAULT_PAGE_LENGTH = 20;

/**
 * @typedef {object} Props Component props
 * @prop {object} DLpropTypes
 */
const DLpropTypes = {
  // from parent
  windowId: PropTypes.string.isRequired,
  viewId: PropTypes.string,
  queryViewId: PropTypes.string,
  page: PropTypes.number,
  sort: PropTypes.string,
  defaultViewId: PropTypes.string,

  referenceId: PropTypes.string,
  // TODO: Eventually this should be renamed to `refWindowId`
  refType: PropTypes.string,
  refDocumentId: PropTypes.string,
  refTabId: PropTypes.string,

  // from @connect
  childSelected: PropTypes.array.isRequired,
  parentSelected: PropTypes.array.isRequired,
  isModal: PropTypes.bool,
  inModal: PropTypes.bool,
  modal: PropTypes.object,
  rawModalVisible: PropTypes.bool,

  resetView: PropTypes.func.isRequired,
  deleteView: PropTypes.func.isRequired,
  fetchDocument: PropTypes.func.isRequired,
  fetchLayout: PropTypes.func.isRequired,
  createView: PropTypes.func.isRequired,
  filterView: PropTypes.func.isRequired,
  deleteTable: PropTypes.func.isRequired,
  indicatorState: PropTypes.func.isRequired,
  setListPagination: PropTypes.func.isRequired,
  setListSorting: PropTypes.func.isRequired,
  setListId: PropTypes.func.isRequired,
  updateRawModal: PropTypes.func.isRequired,
  deselectTableRows: PropTypes.func.isRequired,
  fetchLocationConfig: PropTypes.func.isRequired,
};

/**
 * @typedef {object} Props Component context
 * @prop {object} DLcontextTypes
 */
const DLmapStateToProps = (state, props) => {
  const {
    page: queryPage,
    sort: querySort,
    viewId: queryViewId,
    isModal,
    defaultViewId,
    windowId,
    referenceId: queryReferenceId,
    refType: queryRefType,
    refDocumentId: queryRefDocumentId,
    refTabId: queryRefTabId,
  } = props;

  let master = getView(state, windowId, isModal);

  // use empty view's data. This is used in tests
  if (!master) {
    master = viewState;
  }

  // modals use viewId from layout data, and not from the url
  let viewId = master.viewId ? master.viewId : queryViewId;
  let sort = querySort || master.sort;
  let page = toInteger(queryPage) || master.page;
  // used for included views, so that we don't use sorting/pagination
  // of the parent view
  if (props.isIncluded) {
    sort = master.sort || sort;
    page = master.page || page;
  }

  // used for modals
  if (defaultViewId) {
    viewId = defaultViewId;
  }

  const tableId = getTableId({ windowId, viewId });
  const table = getTable(state, tableId);

  // TODO: Check if this is still a valid solution
  if (location.hash === '#notification') {
    viewId = null;
  }

  let childTableId = null;
  const childSelector = getSelection();
  const { includedView } = props;
  if (includedView && includedView.windowId) {
    childTableId = getTableId({
      windowId: includedView.windowId,
      viewId: includedView.viewId,
    });
  }

  let parentTableId = null;
  const parentSelector = getSelection();
  const { parentWindowType, parentDefaultViewId } = props;
  if (parentWindowType) {
    parentTableId = getTableId({
      windowId: parentWindowType,
      viewId: parentDefaultViewId,
    });
  }
  const filterId = getEntityRelatedId({ windowId, viewId });
  const filters = getCachedFilter(state, filterId);

  return {
    page,
    sort,
    viewId,
    queryViewId,
    table,
    viewData: master,
    layout: master.layout,
    layoutPending: master.layoutPending,
    referenceId: queryReferenceId,
    refType: queryRefType,
    refDocumentId: queryRefDocumentId,
    refTabId: queryRefTabId,
    childSelected: childSelector(state, childTableId),
    parentSelected: parentSelector(state, parentTableId),
    modal: state.windowHandler.modal,
    rawModalVisible: state.windowHandler.rawModal.visible,
    filters,
    filterId,
  };
};

const NO_SELECTION = [];
const NO_VIEW = {};
const PANEL_WIDTHS = ['1', '.2', '4'];
const GEO_PANEL_STATES = ['grid', 'all', 'map'];

// for mobile devices we only want to show either map or grid
if (currentDevice.type === 'mobile' || currentDevice.type === 'tablet') {
  GEO_PANEL_STATES.splice(1, 1);
}

/**
 * Check if current selection still exists in the provided data (used when
 * updates happen)
 * @todo TODO: rewrite this to not modify `initialMap`. This will also require
 * changes in TableActions
 */
const doesSelectionExist = function ({
  data,
  selected,
  keyProperty = 'id',
} = {}) {
  if (selected && selected[0] === 'all') {
    return true;
  }
  // if selection is empty and data exist, selection is valid
  if (selected && !selected.length && data) {
    return true;
  }

  let rows = [];

  data &&
    data.length &&
    data.map((item) => {
      rows = rows.concat(mapIncluded(item));
    });

  return (
    data &&
    data.length &&
    selected &&
    selected[0] &&
    getItemsByProperty(rows, keyProperty, selected[0]).length
  );
};

const getSortingQuery = (asc, field) => (asc ? '+' : '-') + field;

export {
  DLpropTypes,
  DLmapStateToProps,
  NO_SELECTION,
  NO_VIEW,
  PANEL_WIDTHS,
  GEO_PANEL_STATES,
  getSortingQuery,
  doesSelectionExist,
};

// ROWS UTILS

export function mergeColumnInfosIntoViewRows(columnInfosByFieldName, rows) {
  if (!columnInfosByFieldName) {
    return rows;
  }

  return rows.map((row) =>
    mergeColumnInfosIntoViewRow(columnInfosByFieldName, row)
  );
}

/**
 * @method mergeColumnInfosIntoViewRow
 * @summary add additional data to row's fields
 * @param {*} columnInfosByFieldName
 * @param {object} row
 */
function mergeColumnInfosIntoViewRow(columnInfosByFieldName, row) {
  // mainly guard for unit tests which are not defining fieldsByName property
  if (!row.fieldsByName) {
    return row;
  }

  const fieldsByName = Object.values(row.fieldsByName)
    .map((viewRowField) =>
      mergeColumnInfoIntoViewRowField(
        columnInfosByFieldName[viewRowField.field],
        viewRowField
      )
    )
    .reduce((acc, viewRowField) => {
      acc[viewRowField.field] = viewRowField;
      return acc;
    }, {});

  return { ...row, fieldsByName };
}

/**
 * @summary merge field's widget data of the row with additional data
 * @param {*} columnInfo
 * @param {object} viewRowField
 */
function mergeColumnInfoIntoViewRowField(columnInfo, viewRowField) {
  if (!columnInfo) {
    return viewRowField;
  }

  if (columnInfo.widgetType) {
    viewRowField['widgetType'] = columnInfo.widgetType;
  }

  // NOTE: as discussed with @metas-mk, at the moment we cannot apply the maxPrecision per page,
  // because it would puzzle the user.
  // if (columnInfo.maxPrecision && columnInfo.maxPrecision > 0) {
  //   viewRowField["precision"] = columnInfo.maxPrecision;
  // }

  return viewRowField;
}

/**
 * @summary create a key/value map of rows array with their ids as keys (including nested rows)
 */
function indexRowsRecursively(rows, map = {}) {
  for (const row of rows) {
    const { id, includedDocuments } = row;

    map[id] = row;

    if (includedDocuments) {
      indexRowsRecursively(includedDocuments, map);
    }
  }

  return map;
}

/**
 * @summary From requiredRowIds retain only those rowIds which are present in rows array or in any of its included rows
 */
export function retainExistingRowIds(rows, requiredRowIds) {
  if (!Array.isArray(rows) || rows.length <= 0) {
    return [];
  }
  if (!Array.isArray(requiredRowIds) || requiredRowIds.length <= 0) {
    return [];
  }

  const rowsById = indexRowsRecursively(rows);

  return requiredRowIds.filter((rowId) => rowId in rowsById);
}

/**
 * @summary merge existing rows with new rows
 */
export function mergeRows({
  toRows,
  changedIds,
  changedRows,
  columnInfosByFieldName = {},
}) {
  // unfreeze rows from the store
  toRows = deepUnfreeze(toRows);

  const changedRowsById = indexRowsRecursively(changedRows);

  const result = {
    hasChanges: false,
    rows: [],
    removedRowIds: [],
  };

  toRows.forEach((row) => {
    //
    // Case: row was changed or was deleted
    let resultingRow;
    let rowWasChanged = false;
    if (changedIds.includes(row.id)) {
      resultingRow = changedRowsById[row.id];
      if (resultingRow) {
        rowWasChanged = true;
        resultingRow = mergeColumnInfosIntoViewRow(
          columnInfosByFieldName,
          resultingRow
        );
      }
    }
    //
    // Case: row was not changed => preserve it as is
    else {
      rowWasChanged = false;
      resultingRow = row;
    }

    //
    // Recursively update the resulting row (if not already deleted)
    if (
      resultingRow != null && // not deleted
      Array.isArray(resultingRow.includedDocuments) &&
      resultingRow.includedDocuments.length > 0
    ) {
      const includedRowsResult = mergeRows({
        toRows: resultingRow.includedDocuments,
        changedIds,
        changedRows,
        columnInfosByFieldName,
      });

      if (includedRowsResult.hasChanges) {
        rowWasChanged = true;
        resultingRow = {
          ...resultingRow,
          includedDocuments: includedRowsResult.rows,
        };
      }
    }

    //
    // Update the result
    if (resultingRow != null) {
      result.rows.push(resultingRow);
      if (rowWasChanged) {
        result.hasChanges = true;
      }
    } else {
      result.removedRowIds.push(row.id);
      result.hasChanges = true;
    }
  });

  //
  // Optimization: if no changes, return the original rows
  if (!result.hasChanges) {
    return { hasChanges: false, rows: toRows, removedRowIds: [] };
  }

  return result;
}

export function getScope(isModal) {
  return isModal ? 'modal' : 'master';
}

export function parseToDisplay(fieldsByName) {
  return parseDateToReadable(nullToEmptyStrings(fieldsByName));
}

export function convertTimeStringToMoment(value) {
  if (value.match(TIME_REGEX_TEST)) {
    return Moment(value, 'hh:mm');
  }
  return value;
}

// This doesn't set the TZ anymore, as we're handling this globally/in datepicker
export function parseDateWithCurrentTimezone(value) {
  Moment.locale(getCurrentActiveLocale());
  if (value) {
    if (Moment.isMoment(value)) {
      return value;
    }

    value = convertTimeStringToMoment(value);
    return Moment(value);
  }
  return '';
}

function parseDateToReadable(fieldsByName) {
  const dateParse = ['Date', 'ZonedDateTime', 'Time', 'Timestamp'];

  return Object.keys(fieldsByName).reduce((acc, fieldName) => {
    const field = fieldsByName[fieldName];
    const isDateField = dateParse.indexOf(field.widgetType) > -1;

    acc[fieldName] =
      isDateField && field.value
        ? {
            ...field,
            value: parseDateWithCurrentTimezone(field.value),
          }
        : field;
    return acc;
  }, {});
}

/**
 * flatten array with 1 level deep max(with fieldByName)
 * from includedDocuments data
 */
export function flattenRows(rowData) {
  let data = [];
  rowData &&
    rowData.map((item) => {
      data = data.concat(mapIncluded(item));
    });

  return data;
}

/**
 * @method formatStringWithZeroSplitBy
 * @summary For a given string with a specified separator it formats the number within with zeros in front if they are below 10
 * @param {string} date
 * @param {string} notation
 */
function formatStringWithZeroSplitBy(date, notation) {
  const dateArr = date.split(notation);
  const frmArr = dateArr.map((itemDate, index) => {
    return dateArr.length > 2 &&
      itemDate &&
      index < 2 &&
      itemDate.length < 2 &&
      itemDate > 0 &&
      itemDate < 10
      ? '0' + itemDate
      : itemDate;
  });
  return frmArr.join(notation);
}

/**
 * @method formatDateWithZeros
 * @summary Format date with zeros if it's like dd.m.yyyy to dd.mm.yyyyy and similar for the case when / is the separator
 * @param {string} date
 */
export function formatDateWithZeros(date) {
  if (typeof date === 'string' && date.includes('.')) {
    return formatStringWithZeroSplitBy(date, '.');
  }

  if (typeof date === 'string' && date.includes('/')) {
    return formatStringWithZeroSplitBy(date, '/');
  }

  return date;
}

export function mapIncluded(node, indent, isParentLastChild = false) {
  let ind = indent ? indent : [];
  let result = [];

  // because immer freezes objects
  try {
    node = deepUnfreeze(node);
  } catch (e) {
    // deepUnfreze can't cope with Moment in some cases;
  }

  const nodeCopy = {
    ...node,
    indent: ind,
  };

  result = result.concat([nodeCopy]);

  if (isParentLastChild) {
    ind[ind.length - 2] = false;
  }

  if (node.includedDocuments) {
    for (let i = 0; i < node.includedDocuments.length; i++) {
      let copy = node.includedDocuments[i];
      copy.fieldsByName = parseToDisplay(copy.fieldsByName);
      if (i === node.includedDocuments.length - 1) {
        copy = {
          ...copy,
          lastChild: true,
        };
      }

      result = result.concat(
        mapIncluded(copy, ind.concat([true]), node.lastChild)
      );
    }
  }
  return result;
}

export function renderHeaderProperties(groups) {
  return groups.reduce((acc, group, idx) => {
    let cursor = 0;

    do {
      const entry = group.entries[cursor];

      acc.push(
        <span key={`${idx}_${cursor}`} className="optional-name">
          <p className="caption">{entry.caption}:</p>{' '}
          <p className="value">{entry.value}</p>
        </span>
      );

      cursor += 1;
    } while (cursor < group.entries.length);

    if (idx !== groups.length - 1) {
      acc.push(<span key={`${idx}_${cursor}`} className="separator" />);
    }

    return acc;
  }, []);
}

export const computePageLengthEffective = (pageLengthFromLayout) => {
  if (currentDevice.type === 'mobile' || currentDevice.type === 'tablet') {
    return 9999;
  }

  if (pageLengthFromLayout && pageLengthFromLayout > 0) {
    return pageLengthFromLayout;
  }

  return DEFAULT_PAGE_LENGTH;
};
