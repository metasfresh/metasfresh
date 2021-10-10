import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { withRouter } from 'react-router';
import { goBack } from 'connected-react-router';

import { toastError } from '../../../utils/toast';
import ScreenToaster from '../../../components/ScreenToaster';

import { selectWFProcessState } from '../../../reducers/wfProcesses_status/index';
import { setScannedBarcode } from '../../../actions/ScanActions';
import { updateWFProcess } from '../../../actions/WorkflowActions';
import { postScannedBarcode } from '../../../api/scanner';

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
      .catch((error) => {
        setScannedBarcode({ wfProcessId, activityId, scannedBarcode: null });

        toastError({
          axiosError: error,
          fallbackMessageKey: 'activities.scanBarcode.invalidScannedBarcode',
        });
      });
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
  const { workflowId, activityId } = match.params;
  const wfProcessStatus = selectWFProcessState(state, workflowId);

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
