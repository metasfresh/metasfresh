import currentDevice from 'current-device';
import PropTypes from 'prop-types';
import uuid from 'uuid/v4';

const EMPTY_ARRAY = [];

export const containerPropTypes = {
  // from @connect
  dispatch: PropTypes.func.isRequired,

  // from <DocumentList>
  autofocus: PropTypes.bool,
  rowEdited: PropTypes.bool,
  onSelectionChanged: PropTypes.func,
  onRowEdited: PropTypes.func,
  defaultSelected: PropTypes.array,
  disableOnClickOutside: PropTypes.func,
  limitOnClickOutside: PropTypes.bool,
  supportOpenRecord: PropTypes.bool,

  rows: PropTypes.array.isRequired,
  columns: PropTypes.array.isRequired,
  selected: PropTypes.array.isRequired,
};

export const componentPropTypes = {
  ...containerPropTypes,
  onSelect: PropTypes.func.isRequired,
  onSelectOne: PropTypes.func.isRequired,
  onSelectRange: PropTypes.func.isRequired,
  onSelectAll: PropTypes.func.isRequired,
  onDeselectAll: PropTypes.func.isRequired,
  onDeselect: PropTypes.func.isRequired,
};

export function constructorFn(props) {
  const { defaultSelected, rowEdited } = props;

  this.state = {
    // TODO: Check if the `defaultSelected` case is handled via redux properly
    // selected:
    //   defaultSelected && defaultSelected !== null
    //     ? defaultSelected
    //     : [undefined],
    listenOnKeys: true,
    contextMenu: {
      open: false,
      x: 0,
      y: 0,
      fieldName: null,
      supportZoomInto: false,
      supportFieldEdit: false,
    },
    dataHash: uuid(),
    promptOpen: false,
    isBatchEntry: false,
    // rows: [],
    collapsedRows: EMPTY_ARRAY,
    collapsedParentsRows: EMPTY_ARRAY,
    pendingInit: true,
    collapsedArrayMap: EMPTY_ARRAY,
    rowEdited: rowEdited,
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
