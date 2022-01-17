import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';
import { push, go } from 'connected-react-router';
import counterpart from 'counterpart';

import { updateManufacturingReceiptQty, updateManufacturingReceipt } from '../../../../actions/ManufacturingActions';
import { pushHeaderEntry } from '../../../../actions/HeaderActions';
import PickQuantityButton from './PickQuantityButton';
import { toastError } from '../../../../utils/toast';
import {
  manufacturingReceiptScreenLocation,
  manufacturingReceiptReceiveTargetScreen,
} from '../../../../routes/manufacturing_receipt';
import { withRouter } from 'react-router';
import { connect } from 'react-redux';
import { selectWFProcessFromState } from '../../../../reducers/wfProcesses_status';

class MaterialReceiptLineScreen extends PureComponent {
  componentDidMount() {
    const {
      dispatch,
      lineProps: { productName },
      wfProcessId,
      activityId,
      lineId,
    } = this.props;
    const location = manufacturingReceiptScreenLocation({ wfProcessId, activityId, lineId });

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
    const { dispatch, applicationId, wfProcessId, activityId, lineId } = this.props;
    const location = manufacturingReceiptReceiveTargetScreen({ applicationId, wfProcessId, activityId, lineId });

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

const mapStateToProps = (state, ownProps) => {
  const { applicationId, workflowId: wfProcessId, activityId, lineId } = ownProps.match.params;
  const wfProcess = selectWFProcessFromState(state, wfProcessId);
  const activity = wfProcess && wfProcess.activities ? wfProcess.activities[activityId] : null;
  const lineProps = activity != null ? activity.dataStored.lines[lineId] : null;

  return {
    applicationId,
    wfProcessId,
    activityId,
    lineId,
    lineProps,
  };
};

MaterialReceiptLineScreen.propTypes = {
  //
  // Props
  applicationId: PropTypes.string.isRequired,
  wfProcessId: PropTypes.string.isRequired,
  activityId: PropTypes.string.isRequired,
  lineId: PropTypes.string.isRequired,
  lineProps: PropTypes.object.isRequired,
  //
  dispatch: PropTypes.func.isRequired,
};

export default withRouter(connect(mapStateToProps)(MaterialReceiptLineScreen));
