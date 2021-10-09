import React, { Component } from 'react';
import PropTypes from 'prop-types';
import toast, { Toaster } from 'react-hot-toast';
import { connect } from 'react-redux';
import { withRouter } from 'react-router';
import { goBack } from 'connected-react-router';

import { getWorkflowProcessStatus } from '../../../reducers/wfProcesses_status';
import { setScannedBarcode } from '../../../actions/ScanActions';
import { updateWFProcess } from '../../../actions/WorkflowActions';
import { postScannedBarcode } from '../../../api';

import CodeScanner from './CodeScanner';

class ScanScreen extends Component {
  onBarcodeScanned = ({ scannedBarcode }) => {
    const { wfProcessId, activityId, setScannedBarcode, updateWFProcess, goBack } = this.props;

    setScannedBarcode({ wfProcessId, activityId, scannedBarcode });

    postScannedBarcode({ wfProcessId, activityId, scannedBarcode })
      .then((response) => {
        updateWFProcess({ wfProcess: response.data.endpointResponse });
        goBack();
      })
      .catch((err) => {
        console.log('postScannedBarcode failed: %o', err);
        setScannedBarcode({ wfProcessId, activityId, scannedBarcode: null });

        toast('Scanned code is invalid!', { type: 'error', style: { color: 'white' } });
      });
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
  const { workflowId, activityId } = match.params;
  const wfProcessStatus = getWorkflowProcessStatus(state, workflowId);

  return {
    wfProcessId: workflowId,
    activityId,
    activities: wfProcessStatus.activities, // we need this to enable the next activity post scanning
  };
};

ScanScreen.propTypes = {
  //
  // Props
  componentProps: PropTypes.object,
  wfProcessId: PropTypes.string.isRequired,
  activityId: PropTypes.string,
  activities: PropTypes.object,
  //
  // Actions
  setScannedBarcode: PropTypes.func.isRequired,
  updateWFProcess: PropTypes.func.isRequired,
  goBack: PropTypes.func.isRequired,
};

export default withRouter(
  connect(mapStateToProps, {
    setScannedBarcode,
    updateWFProcess,
    goBack,
  })(ScanScreen)
);
