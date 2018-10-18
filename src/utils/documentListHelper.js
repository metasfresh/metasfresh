import PropTypes from 'prop-types';
import { push } from 'react-router-redux';
import { Map } from 'immutable';
import { getItemsByProperty, mapIncluded } from '../actions/WindowActions';
import { getSelection } from '../reducers/windowHandler';

const DLpropTypes = {
  // from parent
  windowType: PropTypes.string.isRequired,

  // from <DocList>
  updateParentSelectedIds: PropTypes.func,

  // from @connect
  dispatch: PropTypes.func.isRequired,
  selections: PropTypes.object.isRequired,
  childSelected: PropTypes.array.isRequired,
  parentSelected: PropTypes.array.isRequired,
  selected: PropTypes.array.isRequired,
  isModal: PropTypes.bool,
};

const DLcontextTypes = {
  store: PropTypes.object.isRequired,
};

const DLmapStateToProps = (state, props) => ({
  selections: state.windowHandler.selections,
  selected: getSelection({
    state,
    windowType: props.windowType,
    viewId: props.defaultViewId,
  }),
  childSelected:
    props.includedView && props.includedView.windowType
      ? getSelection({
          state,
          windowType: props.includedView.windowType,
          viewId: props.includedView.viewId,
        })
      : NO_SELECTION,
  parentSelected: props.parentWindowType
    ? getSelection({
        state,
        windowType: props.parentWindowType,
        viewId: props.parentDefaultViewId,
      })
    : NO_SELECTION,
  modal: state.windowHandler.modal,
});

const NO_SELECTION = [];
const NO_VIEW = {};
const PANEL_WIDTHS = ['1', '.2', '4'];

const filtersToMap = function(filtersArray) {
  let filtersMap = Map();

  if (filtersArray && filtersArray.length) {
    filtersArray.forEach(filter => {
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
    data.result &&
    data.result.map(item => {
      rows = rows.concat(mapIncluded(item));
    });

  return (
    data &&
    data.size &&
    data.result &&
    selected &&
    selected[0] &&
    getItemsByProperty(rows, 'id', selected[0]).length
  );
};

const redirectToNewDocument = (dispatch, windowType) => {
  dispatch(push(`/window/${windowType}/new`));
};

const getSortingQuery = (asc, field) => (asc ? '+' : '-') + field;

export {
  DLpropTypes,
  DLcontextTypes,
  DLmapStateToProps,
  NO_SELECTION,
  NO_VIEW,
  PANEL_WIDTHS,
  getSortingQuery,
  redirectToNewDocument,
  filtersToMap,
  doesSelectionExist,
};

// ROWS UTILS

export function mergeColumnInfosIntoViewRows(columnInfosByFieldName, rows) {
  if (!columnInfosByFieldName) {
    return rows;
  }

  return rows.map(row =>
    mergeColumnInfosIntoViewRow(columnInfosByFieldName, row)
  );
}

function mergeColumnInfosIntoViewRow(columnInfosByFieldName, row) {
  const fieldsByName = Object.values(row.fieldsByName)
    .map(viewRowField =>
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
  return rows.map(row => {
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

export function mergeRows({ toRows, fromRows, columnInfosByFieldName = {} }) {
  if (!fromRows) {
    return toRows;
  }

  const fromRowsById = indexRows(fromRows, {});

  return mapRows(toRows, fromRowsById, columnInfosByFieldName);
}
