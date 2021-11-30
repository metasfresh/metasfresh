import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';
import { push, go } from 'connected-react-router';
import counterpart from 'counterpart';

import { updateManufacturingReceiptQty, updateManufacturingReceipt } from '../../../actions/ManufacturingActions';
import { pushHeaderEntry } from '../../../actions/HeaderActions';
import PickQuantityButton from './PickQuantityButton';
import { toastError } from '../../../utils/toast';
import { getLocation } from '../../../utils';

class MaterialReceiptLineScreen extends PureComponent {
  componentDidMount() {
    const {
      dispatch,
      lineProps: { productName },
    } = this.props;
    const location = getLocation(this.props);

    dispatch(
      pushHeaderEntry({
        location,
        values: [
          {
            caption: counterpart.translate('activities.mfg.ProductName'),
            value: productName,
            bold: true,
          },
        ],
      })
    );
  }

  handleQuantityChange = (qtyPicked) => {
    const {
      dispatch,
      wfProcessId,
      activityId,
      lineId,
      lineProps: { aggregateToLU, currentReceivingHU },
    } = this.props;

    // shall not happen
    if (aggregateToLU || currentReceivingHU) {
      console.log('skip receiving qty because there is no target');
    }

    dispatch(updateManufacturingReceiptQty({ wfProcessId, activityId, lineId, qtyPicked }));

    dispatch(
      updateManufacturingReceipt({
        wfProcessId,
        activityId,
        lineId,
      })
    ).catch((axiosError) => toastError({ axiosError }));

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

    let allowReceivingQty = false;
    let targetCaption = counterpart.translate('activities.mfg.receipts.receiveTarget');
    if (aggregateToLU) {
      targetCaption = aggregateToLU.newLU ? aggregateToLU.newLU.caption : aggregateToLU.existingLU.huBarcode;
      allowReceivingQty = true;
    } else if (currentReceivingHU) {
      targetCaption = currentReceivingHU.huBarcode;
      allowReceivingQty = true;
    }

    return (
      <div className="pt-2 section lines-screen-container">
        <div className="steps-container">
          <div className="buttons">
            <button className="button is-outlined complete-btn" disabled={false} onClick={this.handleClick}>
              <div className="full-size-btn">
                <div className="left-btn-side" />
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
            qtyTarget={qtyToReceive - qtyReceived}
            isDisabled={!allowReceivingQty}
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
