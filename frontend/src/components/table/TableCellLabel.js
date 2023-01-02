import React, { PureComponent } from 'react';
import Label from '../../components/widget/Labels/Label';
import PropTypes from 'prop-types';
import { TBL_CELL_LABEL_MAX } from '../../constants/Constants';
class TableCellLabel extends PureComponent {
  /**
   * @method chopLabel
   * @summary chops the label caption with a number of chars specified as param - maxNum starting from first pos
   * @param {object} labelObj
   * @param {integer} maxNum
   */
  chopLabel = (labelObj, maxNum) => {
    let { caption } = labelObj;
    const resLabel = { ...labelObj };
    resLabel.caption =
      caption.length > maxNum ? caption.slice(0, maxNum) : caption;
    return resLabel;
  };

  render() {
    const { tableCellData, rowId } = this.props;
    const tableCellValues = tableCellData.value.values;
    const commaSeparatedList =
      tableCellValues.length > 1
        ? tableCellValues.map((item) => item.caption).join(', ')
        : '';

    return (
      <div className="table-cell-label-container">
        {tableCellData && tableCellValues[0] && (
          <div
            className="table-cell-label-wrapper"
            key={`${tableCellData.field}_${rowId}_0`}
          >
            <Label
              label={this.chopLabel(
                tableCellData.value.values[0],
                TBL_CELL_LABEL_MAX
              )}
              readonly={true}
              hideCloseIcon={true}
            />
          </div>
        )}

        {/* Case when there is one single label */}
        {tableCellData &&
          tableCellValues[0] &&
          tableCellData.value.values.length === 1 &&
          tableCellData.value.values[0].caption.length > TBL_CELL_LABEL_MAX && (
            <div
              data-toggle="tooltip"
              data-placement="bottom"
              title={tableCellData.value.values[0].caption}
              className="table-cell-label-show-more"
            >
              ...
            </div>
          )}

        {/* Case when have multiple labels, we render them separated by comma */}
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
