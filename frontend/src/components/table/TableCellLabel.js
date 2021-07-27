import React, { PureComponent } from 'react';
import Label from '../../components/widget/Labels/Label';
import PropTypes from 'prop-types';

class TableCellLabel extends PureComponent {
  render() {
    const { tableCellData, rowId } = this.props;
    return (
      <React.Fragment>
        {tableCellData &&
          tableCellData.value.values.map((item, index) => (
            <Label
              key={`${tableCellData.field}_${rowId}_${index}`}
              label={item}
              readonly={true}
              hideCloseIcon={true}
            />
          ))}
      </React.Fragment>
    );
  }
}

TableCellLabel.propTypes = {
  tableCellData: PropTypes.object,
  rowId: PropTypes.string,
};

export default TableCellLabel;
