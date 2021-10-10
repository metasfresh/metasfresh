import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { toastError } from '../../../utils/toast';
import ScreenToaster from '../../../components/ScreenToaster';
import { connect } from 'react-redux';
import { withRouter } from 'react-router';
import { goBack } from 'connected-react-router';

import { updatePickingStepScannedHUBarcode } from '../../../actions/PickingActions';

import CodeScanner from '../scan/CodeScanner';

class PickStepScanHUScreen extends Component {
  onBarcodeScanned = ({ scannedBarcode }) => {
    if (this.isEligibleHUBarcode(scannedBarcode)) {
      const { wfProcessId, activityId, lineId, stepId } = this.props;
      const { updatePickingStepScannedHUBarcode, goBack } = this.props;

      updatePickingStepScannedHUBarcode({
        wfProcessId,
        activityId,
        lineId,
        stepId,
        scannedHUBarcode: scannedBarcode,
      });

      goBack();
    } else {
      // show an error to user but keep scanning...
      toastError({ messageKey: 'activities.picking.notEligibleHUBarcode' });
    }
  };

  isEligibleHUBarcode = (scannedBarcode) => {
    const { eligibleHUBarcode } = this.props;
    console.log(`checking ${eligibleHUBarcode} vs ${scannedBarcode}`);
    return scannedBarcode === eligibleHUBarcode;
  };

  render() {
    return (
      <div className="mt-0">
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
  // Actions:
  updatePickingStepScannedHUBarcode: PropTypes.func.isRequired,
  goBack: PropTypes.func.isRequired,
};

export default withRouter(
  connect(mapStateToProps, {
    updatePickingStepScannedHUBarcode,
    goBack,
  })(PickStepScanHUScreen)
);
