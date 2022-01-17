import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';
import { push } from 'connected-react-router';
import { connect } from 'react-redux';

import { manufacturingReceiptScreenLocation } from '../../../../routes/manufacturing_receipt';
import ButtonWithIndicator from '../../../../components/ButtonWithIndicator';
import ButtonQuantityProp from '../../../../components/ButtonQuantityProp';

class MaterialReceiptLineButton extends PureComponent {
  handleClick = () => {
    const { push, applicationId, wfProcessId, activityId, lineId } = this.props;
    const location = manufacturingReceiptScreenLocation({ applicationId, wfProcessId, activityId, lineId });

    push(location);
  };

  render() {
    const { caption, uom, qtyReceived, qtyToReceive, completeStatus, applicationId, lineId, isUserEditable } =
      this.props;

    return (
      <button
        key={lineId}
        className="button is-outlined complete-btn"
        disabled={!isUserEditable}
        onClick={this.handleClick}
      >
        <ButtonWithIndicator caption={caption} completeStatus={completeStatus}>
          <ButtonQuantityProp
            qtyCurrent={qtyReceived}
            qtyTarget={qtyToReceive}
            uom={uom}
            applicationId={applicationId}
            subtypeId="receipts"
          />
        </ButtonWithIndicator>
      </button>
    );
  }
}

MaterialReceiptLineButton.propTypes = {
  //
  // Props
  applicationId: PropTypes.string.isRequired,
  wfProcessId: PropTypes.string.isRequired,
  activityId: PropTypes.string.isRequired,
  lineId: PropTypes.string.isRequired,
  caption: PropTypes.string.isRequired,
  isUserEditable: PropTypes.bool.isRequired,
  completeStatus: PropTypes.string.isRequired,
  uom: PropTypes.string.isRequired,
  qtyReceived: PropTypes.number.isRequired,
  qtyToReceive: PropTypes.number.isRequired,
  //
  // Actions
  push: PropTypes.func.isRequired,
};

export default connect(null, { push })(MaterialReceiptLineButton);
