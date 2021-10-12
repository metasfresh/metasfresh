import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { withRouter } from 'react-router';
import { goBack } from 'connected-react-router';

import { toastError } from '../../../utils/toast';
import { postQtyPicked } from '../../../api/picking';
import { updatePickingStepScannedHUBarcode, updatePickingStepQty } from '../../../actions/PickingActions';

import ScreenToaster from '../../../components/ScreenToaster';
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
        this.setState({ reasonsPanelVisible: true });
      } else {
        this.pushUpdatedQuantity();
      }

      this.setState({ promptVisible: false });
    } else {
      toastError({ messageKey: 'activities.picking.invalidQtyPicked' });
    }
  };

  hideReasonsPanel = (reason) => {
    const { goBack } = this.props;
    this.setState({ reasonsPanelVisible: false });

    this.pushUpdatedQuantity(reason);
    goBack();
  };

  pushUpdatedQuantity = (reason = null) => {
    const { updatePickingStepQty, wfProcessId, activityId, lineId, stepId } = this.props;
    const { newQuantity, scannedBarcode } = this.state;
    const { updatePickingStepScannedHUBarcode } = this.props;

    console.log('PUSH: ', newQuantity);

    // TODO: We should only set the scanned barcode if the quantity is correct and user submitted any
    // potential reason to wrong quantity.

    updatePickingStepScannedHUBarcode({
      wfProcessId,
      activityId,
      lineId,
      stepId,
      scannedHUBarcode: scannedBarcode,
    });

    updatePickingStepQty({ wfProcessId, activityId, lineId, stepId, qtyPicked: newQuantity });
    postQtyPicked({ wfProcessId, activityId, stepId, qtyPicked: newQuantity, qtyRejectedReasonCode: reason });
    // TODO: handle the promise
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
      stepProps: { qtyToPick },
    } = this.props;
    const { promptVisible, reasonsPanelVisible } = this.state;

    return (
      <div className="mt-0">
        {reasonsPanelVisible ? (
          <QtyReasonsView onHide={this.hideReasonsPanel} />
        ) : (
          <>
            {promptVisible ? <PickQuantityPrompt qtyToPick={qtyToPick} onQtyChange={this.onQtyPickedChanged} /> : null}
            <CodeScanner onBarcodeScanned={this.onBarcodeScanned} />
          </>
        )}
        <ScreenToaster />
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
  updatePickingStepScannedHUBarcode: PropTypes.func.isRequired,
  goBack: PropTypes.func.isRequired,
  updatePickingStepQty: PropTypes.func.isRequired,
};

export default withRouter(
  connect(mapStateToProps, {
    updatePickingStepQty,
    updatePickingStepScannedHUBarcode,
    goBack,
  })(PickStepScanHUScreen)
);
