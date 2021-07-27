import React, { PureComponent } from 'react';
import Label from '../../components/widget/Labels/Label';
import PropTypes from 'prop-types';

class TableCellLabel extends PureComponent {
  render() {
    const { tableCellData, rowId } = this.props;
    const tableCellValues = tableCellData.value.values;
    const commaSeparatedList =
      tableCellValues.length > 1
        ? tableCellValues.map((item) => item.caption).join(', ')
        : '';

    return (
      <div>
        {tableCellData && tableCellValues[0] && (
          <div
            className="table-cell-label-wrapper"
            key={`${tableCellData.field}_${rowId}_0`}
          >
            <Label
              label={tableCellData.value.values[0]}
              readonly={true}
              hideCloseIcon={true}
            />
          </div>
        )}

        {tableCellData && tableCellValues.length > 1 && (
          <div
            data-toggle="tooltip"
            data-placement="bottom"
            title={commaSeparatedList}
            className="table-cell-label-show-more"
          >
            ...
          </div>
        )}
      </div>
    );
  }
}

TableCellLabel.propTypes = {
  tableCellData: PropTypes.object,
  rowId: PropTypes.string,
};

export default TableCellLabel;
