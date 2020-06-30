import PropTypes from 'prop-types';
import { Map as iMap } from 'immutable';
import Moment from 'moment-timezone';
import currentDevice from 'current-device';
import { getItemsByProperty, nullToEmptyStrings } from './index';
import { getSelectionInstant } from '../reducers/windowHandler';
import { viewState, getView } from '../reducers/viewHandler';
import { TIME_REGEX_TEST } from '../constants/Constants';
import { getCurrentActiveLocale } from './locale';

/**
 * @typedef {object} Props Component props
 * @prop {object} DLpropTypes
 */
const DLpropTypes = {
  // from parent
  windowType: PropTypes.string.isRequired,
  viewId: PropTypes.string,
  updateParentSelectedIds: PropTypes.func,
  page: PropTypes.number,
  sort: PropTypes.string,
  defaultViewId: PropTypes.string,

  referenceId: PropTypes.string,
  // TODO: Eventually this should be renamed to `refWindowId`
  refType: PropTypes.string,
  refDocumentId: PropTypes.string,
  refTabId: PropTypes.string,

  // from @connect
  selections: PropTypes.object.isRequired,
  childSelected: PropTypes.array.isRequired,
  parentSelected: PropTypes.array.isRequired,
  selected: PropTypes.array.isRequired,
  isModal: PropTypes.bool,
  inModal: PropTypes.bool,
  modal: PropTypes.object,
  rawModalVisible: PropTypes.bool,
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
    windowType,
    referenceId: queryReferenceId,
    refType: queryRefType,
    refDocumentId: queryRefDocumentId,
    refTabId: queryRefTabId,
  } = props;
  const identifier = isModal ? defaultViewId : windowType;
  let master = getView(state, identifier);

  if (!master) {
    master = viewState;
  }

  const sort = master.sort ? master.sort : querySort;
  const page = master.page ? master.page : parseInt(queryPage);
  let viewId = master.viewId ? master.viewId : queryViewId;

  // used for modals
  if (props.defaultViewId) {
    viewId = props.defaultViewId;
  }

  if (location.hash === '#notification') {
    viewId = null;
  }

  return {
    page,
    sort,
    viewId,
    reduxData: master,
    layout: master.layout,
    layoutPending: master.layoutPending,
    referenceId: queryReferenceId,
    refType: queryRefType,
    refDocumentId: queryRefDocumentId,
    refTabId: queryRefTabId,
    selections: state.windowHandler.selections,
    selected: getSelectionInstant(
      state,
      { ...props, windowId: props.windowType, viewId },
      state.windowHandler.selectionsHash
    ),
    childSelected:
      props.includedView && props.includedView.windowType
        ? getSelectionInstant(
            state,
            {
              ...props,
              windowId: props.includedView.windowType,
              viewId: props.includedView.viewId,
            },
            state.windowHandler.selectionsHash
          )
        : NO_SELECTION,
    parentSelected: props.parentWindowType
      ? getSelectionInstant(
          state,
          {
            ...props,
            windowId: props.parentWindowType,
            viewId: props.parentDefaultViewId,
          },
          state.windowHandler.selectionsHash
        )
      : NO_SELECTION,
    modal: state.windowHandler.modal,
    rawModalVisible: state.windowHandler.rawModal.visible,
    filters: state.filters,
    table: state.table,
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

const filtersToMap = function(filtersArray) {
  let filtersMap = iMap();

  if (filtersArray && filtersArray.length) {
    filtersArray.forEach((filter) => {
      filtersMap = filtersMap.set(filter.filterId, filter);
    });
  }
  return filtersMap;
};

const doesSelectionExist = function({
  data,
  selected,
  hasIncluded = false,
} = {}) {
  // When the rows are changing we should ensure
  // that selection still exist
  if (hasIncluded) {
    return true;
  }

  if (selected && selected[0] === 'all') {
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
    getItemsByProperty(rows, 'id', selected[0]).length
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
  filtersToMap,
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

function mergeColumnInfosIntoViewRow(columnInfosByFieldName, row) {
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

  return Object.assign({}, row, { fieldsByName });
}

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

function indexRows(rows, map) {
  for (const row of rows) {
    const { id, includedDocuments } = row;

    map[id] = row;

    if (includedDocuments) {
      indexRows(includedDocuments, map);
    }
  }

  return map;
}

function mapRows(rows, map, columnInfosByFieldName) {
  return rows.map((row) => {
    const { id, includedDocuments } = row;

    if (includedDocuments) {
      row.includedDocuments = mapRows(
        includedDocuments,
        map,
        columnInfosByFieldName
      );
    }

    const entry = map[id];

    if (entry) {
      return mergeColumnInfosIntoViewRow(columnInfosByFieldName, entry);
    } else {
      return row;
    }
  });
}

export function removeRows(rowsList, changedRows) {
  const removedRows = [];

  changedRows.forEach((id) => {
    const idx = rowsList.findIndex((row) => row.id === id);

    if (idx !== -1) {
      rowsList = rowsList.delete(idx);
      removedRows.push(id);
    }
  });

  return {
    rows: rowsList,
    removedRows,
  };
}

export function mergeRows({
  toRows,
  fromRows,
  columnInfosByFieldName = {},
  changedIds,
}) {
  if (!fromRows && !changedIds) {
    return {
      rows: toRows,
      removedRows: [],
    };
  } else if (!fromRows.length) {
    return removeRows(toRows, changedIds);
  }

  const fromRowsById = indexRows(fromRows, {});

  return {
    rows: mapRows(toRows, fromRowsById, columnInfosByFieldName),
    removedRows: [],
  };
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
export function getRowsData(rowData) {
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
export async function formatDateWithZeros(date) {
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

export function collapsedMap(node, isCollapsed, initialMap) {
  let collapsedMap = [];
  if (initialMap) {
    if (!isCollapsed) {
      initialMap.splice(
        initialMap.indexOf(node.includedDocuments[0]),
        node.includedDocuments.length
      );
      collapsedMap = initialMap;
    } else {
      initialMap.map((item) => {
        collapsedMap.push(item);
        if (item.id === node.id) {
          collapsedMap = collapsedMap.concat(node.includedDocuments);
        }
      });
    }
  } else {
    if (node.includedDocuments) {
      collapsedMap.push(node);
    }
  }

  return collapsedMap;
}
