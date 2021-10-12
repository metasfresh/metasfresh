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

class PickStepScanHUScreen extends Component {
  constructor(props) {
    super(props);

    this.state = {
      promptVisible: false,
    };
  }

  onBarcodeScanned = ({ scannedBarcode }, closeCameraCallback) => {
    if (this.isEligibleHUBarcode(scannedBarcode)) {
      // const { wfProcessId, activityId, lineId, stepId } = this.props;
      // const { updatePickingStepScannedHUBarcode, goBack } = this.props;

      // TODO: We should only set the scanned barcode if the quantity is correct and user submitted any
      // potential reason to wrong quantity.

      // updatePickingStepScannedHUBarcode({
      //   wfProcessId,
      //   activityId,
      //   lineId,
      //   stepId,
      //   scannedHUBarcode: scannedBarcode,
      // });

      this.setState({ promptVisible: true });
      this.closeCameraCallback = closeCameraCallback;

      // goBack();
    } else {
      // show an error to user but keep scanning...
      toastError({ messageKey: 'activities.picking.notEligibleHUBarcode' });
    }
  };

  onQtyPickedChanged = (qty) => {
    const { updatePickingStepQty, wfProcessId, activityId, lineId, stepId } = this.props;
    // const { goBack } = this.props;
    const qtyPicked = qty; //e.target.value;
    const inputQty = parseInt(qtyPicked);

    if (isNaN(inputQty)) {
      return;
    }

    const isValidQty = this.validateQtyInput(inputQty);

    if (isValidQty) {
      updatePickingStepQty({ wfProcessId, activityId, lineId, stepId, qtyPicked });
      postQtyPicked({ wfProcessId, activityId, stepId, qtyPicked });
      // TODO: handle the promise
      // in case of qtyRejectedReasons replace path with the url of `rejected reason selection` view
      // otherwise `goBack()`

      this.setState({ promptVisible: false });
    } else {
      toastError({ messageKey: 'activities.picking.invalidQtyPicked' });
    }
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
    const { promptVisible } = this.state;

    return (
      <div className="mt-0">
        {promptVisible ? <PickQuantityPrompt qtyToPick={qtyToPick} onQtyChange={this.onQtyPickedChanged} /> : null}
        <CodeScanner onBarcodeScanned={this.onBarcodeScanned} />
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
