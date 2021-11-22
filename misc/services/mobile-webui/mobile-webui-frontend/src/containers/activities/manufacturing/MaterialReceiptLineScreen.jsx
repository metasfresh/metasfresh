import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';
import { push, go } from 'connected-react-router';
import counterpart from 'counterpart';

import { updateManufacturingReceiptQty, updateManufacturingReceipt } from '../../../actions/ManufacturingActions';
import PickQuantityButton from './PickQuantityButton';
import { toastError } from '../../../utils/toast';

class MaterialReceiptLineScreen extends PureComponent {
  handleQuantityChange = (qtyPicked) => {
    const { dispatch, wfProcessId, activityId, lineId, lineProps } = this.props;

    dispatch(updateManufacturingReceiptQty({ wfProcessId, activityId, lineId, qtyPicked }));

    if (lineProps.aggregateToLU) {
      dispatch(
        updateManufacturingReceipt({
          wfProcessId,
          activityId,
          lineId,
        })
      ).catch((axiosError) => toastError({ axiosError }));
    }

    dispatch(go(-1));
  };

  handleClick = () => {
    const { wfProcessId, activityId, lineId } = this.props;
    const { dispatch } = this.props;

    const location = `/workflow/${wfProcessId}/activityId/${activityId}/lineId/${lineId}/receipt/target`;
    dispatch(push(location));
  };

  render() {
    const {
      lineProps: { uom, qtyReceived, qtyToReceive, productName, aggregateToLU, currentReceivingHU },
    } = this.props;
    const caption = counterpart.translate('activities.mfg.receipts.receiveQty');
    let targetCaption = counterpart.translate('activities.mfg.receipts.receiveTarget');

    if (aggregateToLU) {
      targetCaption = aggregateToLU.newLU ? aggregateToLU.newLU.caption : aggregateToLU.existingLU.huBarcode;
    } else if (currentReceivingHU) {
      targetCaption = currentReceivingHU.huBarcode;
    }

    return (
      <div className="pt-2 section lines-screen-container">
        <div className="steps-container">
          <div className="buttons">
            <button className="button is-outlined complete-btn" disabled={false} onClick={this.handleClick}>
              <div className="full-size-btn">
                <div className="left-btn-side"></div>
                <div className="caption-btn">
                  <div className="rows">
                    <div className="row is-full pl-5">{targetCaption}</div>
                  </div>
                </div>
              </div>
            </button>
          </div>
          <PickQuantityButton
            qtyCurrent={qtyReceived}
            qtyTarget={qtyToReceive}
            isDisabled={aggregateToLU === null}
            onClick={this.handleQuantityChange}
            {...{ uom, productName, caption }}
          />
        </div>
      </div>
    );
  }
}

MaterialReceiptLineScreen.propTypes = {
  //
  // Props
  wfProcessId: PropTypes.string.isRequired,
  activityId: PropTypes.string.isRequired,
  lineProps: PropTypes.object.isRequired,
  lineId: PropTypes.string.isRequired,
  dispatch: PropTypes.func.isRequired,
};

export default MaterialReceiptLineScreen;
