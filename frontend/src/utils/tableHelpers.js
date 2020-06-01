import currentDevice from 'current-device';
import PropTypes from 'prop-types';

export const containerPropTypes = {
  // from <DocumentList>
  // TODO: This needs to be fixed in all child components
  // windowId: PropTypes.number,
  // docId: PropTypes.number,
  viewId: PropTypes.string,
  tabId: PropTypes.string,
  autofocus: PropTypes.bool,
  rowEdited: PropTypes.bool,
  onSelectionChanged: PropTypes.func,
  onRowEdited: PropTypes.func,
  defaultSelected: PropTypes.array,
  limitOnClickOutside: PropTypes.bool,
  supportOpenRecord: PropTypes.bool,

  // from redux
  rows: PropTypes.array.isRequired,
  columns: PropTypes.array.isRequired,
  selected: PropTypes.array.isRequired,
  collapsedParentRows: PropTypes.array.isRequired,
  collapsedRows: PropTypes.array.isRequired,
  collapsedArrayMap: PropTypes.array.isRequired,
  allowShortcut: PropTypes.bool,
  allowOutsideClick: PropTypes.bool,
  modalVisible: PropTypes.bool,
  isGerman: PropTypes.bool,
  activeSort: PropTypes.bool,

  // action creators
  collapseTableRow: PropTypes.func.isRequired,
  deleteLocal: PropTypes.func.isRequired,
  deselectTableItems: PropTypes.func.isRequired,
  openModal: PropTypes.func.isRequired,
  updateTableSelection: PropTypes.func.isRequired,
};

export const componentPropTypes = {
  ...containerPropTypes,
  onSelect: PropTypes.func.isRequired,
  onGetAllLeaves: PropTypes.func.isRequired,
  onSelectAll: PropTypes.func.isRequired,
  onDeselectAll: PropTypes.func.isRequired,
  onDeselect: PropTypes.func.isRequired,
};

export function constructorFn(props) {
  const { rowEdited } = props;

  this.state = {
    listenOnKeys: true,
    contextMenu: {
      open: false,
      x: 0,
      y: 0,
      fieldName: null,
      supportZoomInto: false,
      supportFieldEdit: false,
    },
    promptOpen: false,
    isBatchEntry: false,
    pendingInit: true,

    // TODO: This is not read anywhere. Check if the solution works or needs
    // adjustments
    rowEdited,
    tableRefreshToggle: false,
  };
}

export function getSizeClass(col) {
  const { widgetType, size } = col;
  const lg = ['List', 'Lookup', 'LongText', 'Date', 'DateTime', 'Time'];
  const md = ['Text', 'Address', 'ProductAttributes'];

  if (size) {
    switch (size) {
      case 'S':
        return 'td-sm';
      case 'M':
        return 'td-md';
      case 'L':
        return 'td-lg';
      case 'XL':
        return 'td-xl';
      case 'XXL':
        return 'td-xxl';
    }
  } else {
    if (lg.indexOf(widgetType) > -1) {
      return 'td-lg';
    } else if (md.indexOf(widgetType) > -1) {
      return 'td-md';
    } else {
      return 'td-sm';
    }
  }
}

export function handleCopy(e) {
  e.preventDefault();

  const cell = e.target;
  const textValue = cell.value || cell.textContent;

  e.clipboardData.setData('text/plain', textValue);
}

export function handleOpenNewTab({ windowId, rowIds }) {
  if (!rowIds) {
    return;
  }

  rowIds.forEach((rowId) => {
    window.open(`/window/${windowId}/${rowId}`, '_blank');
  });
}

export function shouldRenderColumn(column) {
  if (
    !column.restrictToMediaTypes ||
    column.restrictToMediaTypes.length === 0
  ) {
    return true;
  }

  const deviceType = currentDevice.type;
  let mediaType = 'tablet';

  if (deviceType === 'mobile') {
    mediaType = 'phone';
  } else if (deviceType === 'desktop') {
    mediaType = 'screen';
  }

  return column.restrictToMediaTypes.indexOf(mediaType) !== -1;
}
