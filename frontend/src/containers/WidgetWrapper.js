import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';

import { allowShortcut, disableShortcut } from '../actions/WindowActions';
import { getCellWidgetData } from '../utils/tableHelpers';
import { getTable } from '../reducers/tables';

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
  const { windowHandler, appHandler } = state;
  const {
    dataSource,
    fields,
    tableId,
    rowIndex,
    colIndex,
    isEditable,
    supportFieldEdit,
  } = props;
  const { data } = windowHandler.master;

  let widgetData = null;

  switch (dataSource) {
    case 'doc-status':
      widgetData = [
        {
          status: data.DocStatus || null,
          action: data.DocAction || null,
          displayed: true,
        },
      ];

      break;
    case 'element':
      widgetData = fields.reduce((result, item) => {
        data[item.field] && result.push(data[item.field]);

        return result;
      }, []);

      if (!widgetData.length) {
        widgetData = [{}];
      }

      break;
    case 'table': {
      const table = getTable(state, tableId);
      const rows = table.rows;
      const cells = rows[rowIndex];
      const columnItem = table.cols[colIndex];

      widgetData = getCellWidgetData(cells, columnItem, isEditable, supportFieldEdit);

      break;
    }
    default:
      widgetData = [{}];

      break;
  }

  return {
    widgetData,
    modalVisible: windowHandler.modal.visible,
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
