import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { withRouter } from 'react-router';
import { go } from 'connected-react-router';

import { toastError } from '../../../utils/toast';
import { postStepPicked } from '../../../api/picking';
import { updatePickingStepQty } from '../../../actions/PickingActions';

import CodeScanner from '../scan/CodeScanner';
import PickQuantityPrompt from './PickQuantityPrompt';
import QtyReasonsView from './QtyReasonsView';

class PickStepScanHUScreen extends Component {
  constructor(props) {
    super(props);

    this.state = {
      promptVisible: false,
      reasonsPanelVisible: false,
      newQuantity: 0,
      scannedBarcode: null,
      qtyRejected: 0,
    };
  }

  onBarcodeScanned = ({ scannedBarcode }, closeCameraCallback) => {
    if (this.isEligibleHUBarcode(scannedBarcode)) {
      this.setState({ promptVisible: true, scannedBarcode });
      this.closeCameraCallback = closeCameraCallback;
    } else {
      // show an error to user but keep scanning...
      toastError({ messageKey: 'activities.picking.notEligibleHUBarcode' });
    }
  };

  onQtyPickedChanged = (qty) => {
    const {
      stepProps: { qtyToPick },
    } = this.props;

    const inputQty = parseInt(qty);
    if (isNaN(inputQty)) {
      return;
    }

    const isValidQty = this.validateQtyInput(inputQty);
    if (isValidQty) {
      this.setState({ newQuantity: inputQty });

      if (inputQty !== qtyToPick) {
        const qtyRejected = qtyToPick - inputQty;
        this.setState({ reasonsPanelVisible: true, qtyRejected });
      } else {
        this.pushUpdatedQuantity({ qty: inputQty });
      }

      this.setState({ promptVisible: false });
    } else {
      toastError({ messageKey: 'activities.picking.invalidQtyPicked' });
    }
  };

  hideReasonsPanel = (reason) => {
    this.setState({ reasonsPanelVisible: false });

    this.pushUpdatedQuantity({ qty: this.state.newQuantity, reason });
  };

  pushUpdatedQuantity = ({ qty = 0, reason = null }) => {
    const { updatePickingStepQty, wfProcessId, activityId, lineId, stepId, go } = this.props;
    const { scannedBarcode } = this.state;

    // TODO: This should be added to the same, not next level
    // pushHeaderEntry({
    //   location,
    //   values: [
    //     {
    //       caption: counterpart.translate('general.QtyPicked'),
    //       value: qtyPicked,
    //     },
    //   ],
    // });

    // TODO: We should only set the scanned barcode if the quantity is correct and user submitted any
    // potential reason to wrong quantity.

    postStepPicked({
      wfProcessId,
      activityId,
      stepId,
      huBarcode: scannedBarcode,
      qtyPicked: qty,
      qtyRejectedReasonCode: reason,
    })
      .then(() => {
        updatePickingStepQty({
          wfProcessId,
          activityId,
          lineId,
          stepId,
          scannedHUBarcode: scannedBarcode,
          qtyPicked: qty,
          qtyRejectedReasonCode: reason,
        });
        go(-2);
      })
      .catch((axiosError) => toastError({ axiosError }));
  };

  validateQtyInput = (numberInput) => {
    const {
      stepProps: { qtyToPick },
    } = this.props;

    return numberInput >= 0 && numberInput <= qtyToPick;
  };

  isEligibleHUBarcode = (scannedBarcode) => {
    const { eligibleHUBarcode } = this.props;

    console.log(`checking ${eligibleHUBarcode} vs ${scannedBarcode}`);
    return scannedBarcode === eligibleHUBarcode;
  };

  render() {
    const {
      stepProps: { qtyToPick, uom },
    } = this.props;
    const { promptVisible, reasonsPanelVisible, qtyRejected } = this.state;

    return (
      <div className="mt-0">
        {reasonsPanelVisible ? (
          <QtyReasonsView onHide={this.hideReasonsPanel} uom={uom} qtyRejected={qtyRejected} />
        ) : (
          <>
            {promptVisible ? <PickQuantityPrompt qtyToPick={qtyToPick} onQtyChange={this.onQtyPickedChanged} /> : null}
            <CodeScanner onBarcodeScanned={this.onBarcodeScanned} />
          </>
        )}
      </div>
    );
  }
}

const mapStateToProps = (state, { match }) => {
  const { workflowId: wfProcessId, activityId, lineId, stepId } = match.params;

  const stepProps = state.wfProcesses_status[wfProcessId].activities[activityId].dataStored.lines[lineId].steps[stepId];

  return {
    wfProcessId,
    activityId,
    lineId,
    stepId,
    stepProps,
    eligibleHUBarcode: stepProps.huBarcode,
  };
};

PickStepScanHUScreen.propTypes = {
  componentProps: PropTypes.object,
  wfProcessId: PropTypes.string.isRequired,
  activityId: PropTypes.string.isRequired,
  lineId: PropTypes.string.isRequired,
  stepId: PropTypes.string.isRequired,
  eligibleHUBarcode: PropTypes.string.isRequired,
  stepProps: PropTypes.object.isRequired,
  // Actions:
  go: PropTypes.func.isRequired,
  updatePickingStepQty: PropTypes.func.isRequired,
};

export default withRouter(
  connect(mapStateToProps, {
    updatePickingStepQty,
    go,
  })(PickStepScanHUScreen)
);
