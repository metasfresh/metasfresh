import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';

import {
  openModal,
  patch,
  updatePropertyValue,
  allowShortcut,
  disableShortcut,
} from '../actions/WindowActions';
import { getCellWidgetData } from '../utils/tableHelpers';
import { getTable } from '../reducers/tables';
import {
  getData,
  getElementWidgetData,
  getElementWidgetFields,
  getMasterDocStatus,
  getProcessWidgetData,
  getProcessWidgetFields,
} from '../reducers/windowHandler';

import MasterWidget from '../components/widget/MasterWidget';
import RawWidget from '../components/widget/RawWidget';

/**
 * @file Class based component.
 * @module WidgetWrapper
 * @extends PureComponent
 * @summary this is a wrapper around widgets that's responsible for
 * fetching data to the component. Depending on the `dataSource` prop it
 * has a selection of strategies to get the data from the redux store. No
 * `MasterWidget` or `RawWidget` component should be rendered directly - always
 * wrap them in `WidgetWrapper` and if needed add another data selector.
 */
class WidgetWrapper extends PureComponent {
  render() {
    const { renderMaster } = this.props;

    if (renderMaster) {
      return <MasterWidget {...this.props} />;
    } else {
      return <RawWidget {...this.props} />;
    }
  }
}

const mapStateToProps = (state, props) => {
  const { appHandler, windowHandler } = state;
  const {
    dataSource,
    tableId,
    rowIndex,
    colIndex,
    isEditable,
    supportFieldEdit,
    layoutId,
    fields,
    isModal,
  } = props;
  const data = getData(state, isModal);

  let widgetData = null;
  let fieldsCopy = null;

  switch (dataSource) {
    case 'doc-status':
      widgetData = getMasterDocStatus(state);

      break;
    case 'modal':
    case 'element':
      widgetData = getElementWidgetData(state, isModal, layoutId);
      fieldsCopy = getElementWidgetFields(state, isModal, layoutId);

      break;
    case 'process':
      widgetData = getProcessWidgetData(state, true, layoutId);
      fieldsCopy = getProcessWidgetFields(state, true, layoutId);

      break;
    case 'table': {
      const table = getTable(state, tableId);
      const rows = table.rows;
      const cells = rows[rowIndex].fieldsByName;
      const columnItem = table.columns[colIndex];

      widgetData = getCellWidgetData(
        cells,
        columnItem,
        isEditable,
        supportFieldEdit
      );

      break;
    }
    default:
      widgetData = [{}];

      break;
  }

  let activeTab = null;

  if (windowHandler.master.layout) {
    activeTab = windowHandler.master.layout.activeTab;
  }

  return {
    relativeDocId: data.ID && data.ID.value,
    widgetData,
    activeTab,
    modalVisible: windowHandler.modal.visible,
    timeZone: appHandler.me.timeZone,
    fields: fieldsCopy || fields,
  };
};

/**
 * @typedef {object} Props Component props
 * @prop {*} actions
 * @prop {*} activeTab
 */
WidgetWrapper.propTypes = {
  renderMaster: PropTypes.bool,
  dataSource: PropTypes.string.isRequired,
  relativeDocId: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
  widgetData: PropTypes.array.isRequired,
  modalVisible: PropTypes.bool.isRequired,
  activeTab: PropTypes.string,
  timeZone: PropTypes.string,
  fields: PropTypes.array.isRequired,
  allowShortcut: PropTypes.func.isRequired,
  disableShortcut: PropTypes.func.isRequired,
  openModal: PropTypes.func.isRequired,
  patch: PropTypes.func.isRequired,
  updatePropertyValue: PropTypes.func.isRequired,
};

export default connect(
  mapStateToProps,
  {
    allowShortcut,
    disableShortcut,
    openModal,
    patch,
    updatePropertyValue,
  },
  null,
  { forwardRef: true }
)(WidgetWrapper);

export { WidgetWrapper };
