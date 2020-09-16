import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';

import { allowShortcut, disableShortcut } from '../actions/WindowActions';
import { getCellWidgetData } from '../utils/tableHelpers';
import { getTable } from '../reducers/tables';
import {
  getMasterData,
  getMasterWidgetData,
  getMasterDocStatus,
} from '../reducers/windowHandler';

import MasterWidget from '../components/widget/MasterWidget';
import RawWidget from '../components/widget/RawWidget';

/**
 * @file Class based component.
 * @module WidgetWrapper
 * @extends PureComponent
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
  const { appHandler } = state;
  const {
    dataSource,
    tableId,
    rowIndex,
    colIndex,
    isEditable,
    supportFieldEdit,
    layoutId,
  } = props;
  const data = getMasterData(state);

  let widgetData = null;

  switch (dataSource) {
    case 'doc-status':
      widgetData = getMasterDocStatus(state);

      break;
    case 'element':
      widgetData = getMasterWidgetData(state, layoutId);

      break;
    case 'table': {
      const table = getTable(state, tableId);
      const rows = table.rows;
      const cells = rows[rowIndex];
      const columnItem = table.cols[colIndex];

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

  return {
    relativeDocId: data.ID && data.ID.value,
    widgetData,
    modalVisible: state.windowHandler.modal.visible,
    timeZone: appHandler.me.timeZone,
  };
};

WidgetWrapper.propTypes = {
  renderMaster: PropTypes.bool,
  dataSource: PropTypes.string.isRequired,
};

export default connect(
  mapStateToProps,
  {
    allowShortcut,
    disableShortcut,
  },
  null,
  { forwardRef: true }
)(WidgetWrapper);
