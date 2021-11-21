import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';
import MaterialReceiptLineButton from './MaterialReceiptLineButton';

class MaterialReceiptActivity extends PureComponent {
  render() {
    const {
      activityState: {
        componentProps: { lines },
        dataStored: { isUserEditable },
      },
    } = this.props;

    return (
      <div className="mfg-materialReceipt-activity-container mt-5">
        {lines.map((line, lineIndex) => {
          return (
            <MaterialReceiptLineButton key={lineIndex} productName={line.productName} isUserEditable={isUserEditable} />
          );
        })}
      </div>
    );
  }
}

MaterialReceiptActivity.propTypes = {
  wfProcessId: PropTypes.string,
  activityId: PropTypes.string,
  componentProps: PropTypes.object,
  activityState: PropTypes.object,
};

export default MaterialReceiptActivity;
