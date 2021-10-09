import React, { Component } from 'react';
import PropTypes from 'prop-types';
import toast, { Toaster } from 'react-hot-toast';
import { connect } from 'react-redux';
import { withRouter } from 'react-router';
import { goBack } from 'connected-react-router';

import { updatePickingStepScannedHUBarcode } from '../../actions/PickingActions';

import CodeScanner from './CodeScanner';

class PickingScanHUScreen extends Component {
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
      toast('Scanned code is invalid!', { type: 'error', style: { color: 'white' } });
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
        <Toaster
          position="bottom-center"
          toastOptions={{
            success: {
              style: {
                background: 'green',
              },
            },
            error: {
              style: {
                background: 'red',
              },
            },
          }}
        />
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

PickingScanHUScreen.propTypes = {
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
  })(PickingScanHUScreen)
);
