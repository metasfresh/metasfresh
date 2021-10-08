import React, { Component } from 'react';
import PropTypes from 'prop-types';
import toast, { Toaster } from 'react-hot-toast';
import { connect } from 'react-redux';
import { withRouter } from 'react-router';
import { goBack } from 'connected-react-router';

import { getWorkflowProcessStatus } from '../../reducers/wfProcesses_status';
import { setScannedBarcode } from '../../actions/PickingActions';
import { setActivityEnableFlag, updateWFProcess } from '../../actions/WorkflowActions';
import { startScanning, stopScanning } from '../../actions/ScanActions';
import { postScannedBarcode } from '../../api/scanner';

import CodeScanner from './CodeScanner';

class ScanScreen extends Component {
  componentDidMount() {
    const { startScanning } = this.props;

    startScanning();
  }

  onBarcodeScanned = ({ scannedBarcode }) => {
    const {
      wfProcessId,
      activityId,
      stopScanning,
      setScannedBarcode,
      activities,
      updateWFProcess,
      setActivityEnableFlag,
      goBack,
    } = this.props;

    postScannedBarcode({ wfProcessId, activityId, scannedBarcode })
      .then((response) => {
        // update the scanned activity code in the store
        setScannedBarcode({ wfProcessId, activityId, scannedBarcode });

        updateWFProcess({ wfProcess: response.data.endpointResponse });

        // display a toast
        toast('Successful scanning!', { type: 'success', style: { color: 'white' } });

        // update the next activity
        const nextActivityId = Number(activityId) + 1;
        nextActivityId in activities &&
          setActivityEnableFlag({ wfProcessId, activityId: nextActivityId, isActivityEnabled: true });
      })
      .catch(() => {
        toast('Scanned code is invalid!', { type: 'error', style: { color: 'white' } });
      });

    stopScanning();

    goBack();
  };

  render() {
    const { activityId } = this.props;

    return (
      <div className="mt-0">
        <CodeScanner activityId={activityId} onDetection={this.onBarcodeScanned} />
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
  const { workflowId, activityId } = match.params;
  const wfProcessStatus = getWorkflowProcessStatus(state, workflowId);

  return {
    wfProcessId: workflowId,
    activityId,
    activities: wfProcessStatus.activities, // we need this to enable the next activity post scanning
  };
};

ScanScreen.propTypes = {
  componentProps: PropTypes.object,
  stopScanning: PropTypes.func.isRequired,
  startScanning: PropTypes.func.isRequired,
  setScannedBarcode: PropTypes.func.isRequired,
  wfProcessId: PropTypes.string.isRequired,
  activityId: PropTypes.string,
  setActivityEnableFlag: PropTypes.func.isRequired,
  updateWFProcess: PropTypes.func.isRequired,
  activities: PropTypes.object,
  goBack: PropTypes.func.isRequired,
};

export default withRouter(
  connect(mapStateToProps, {
    stopScanning,
    startScanning,
    setScannedBarcode,
    updateWFProcess,
    setActivityEnableFlag,
    goBack,
  })(ScanScreen)
);
