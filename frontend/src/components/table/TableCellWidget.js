import React, { PureComponent } from 'react';
import TableCellLabel from './TableCellLabel';
import PropTypes from 'prop-types';

class TableCellWidget extends PureComponent {
  renderContent = () => {
    const { tdValue, widgetType } = this.props;

    switch (widgetType) {
      case 'Labels':
        return <TableCellLabel {...this.props} />;
      default:
        return tdValue;
    }
  };

  render() {
    return <React.Fragment>{this.renderContent()}</React.Fragment>;
  }
}

TableCellWidget.propTypes = {
  tdValue: PropTypes.any,
  widgetType: PropTypes.string,
};

export default TableCellWidget;
