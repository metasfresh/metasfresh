import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { withRouter } from 'react-router';
import { goBack } from 'connected-react-router';

import { toastError } from '../../../utils/toast';

import { setScannedBarcode } from '../../../actions/ScanActions';
import { updateWFProcess } from '../../../actions/WorkflowActions';
import { postScannedBarcode } from '../../../api/scanner';

import BarcodeScannerComponent from '../../../components/BarcodeScannerComponent';

class ScanScreen extends Component {
  onBarcodeScanned = ({ scannedBarcode }) => {
    const { wfProcessId, activityId, setScannedBarcode, updateWFProcess, goBack } = this.props;

    setScannedBarcode({ wfProcessId, activityId, scannedBarcode });

    postScannedBarcode({ wfProcessId, activityId, scannedBarcode })
      .then((wfProcess) => {
        updateWFProcess({ wfProcess });
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
        <BarcodeScannerComponent onBarcodeScanned={this.onBarcodeScanned} />
      </div>
    );
  }
}

const mapStateToProps = (state, { match }) => {
  const { workflowId: wfProcessId, activityId } = match.params;

  return { wfProcessId, activityId };
};

ScanScreen.propTypes = {
  //
  // Props
  componentProps: PropTypes.object,
  wfProcessId: PropTypes.string.isRequired,
  activityId: PropTypes.string,
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
