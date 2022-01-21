import React, { PureComponent } from 'react';
import { withRouter } from 'react-router';
import { connect } from 'react-redux';
import BarcodeScannerComponent from '../../components/BarcodeScannerComponent';
import { startWorkflowRequest } from '../../api/launchers';
import { updateWFProcess } from '../../actions/WorkflowActions';
import { toastError } from '../../utils/toast';
import PropTypes from 'prop-types';
import { gotoWFProcessScreen } from '../../routes/workflow';

class WFLaunchersScanBarcodeScreen extends PureComponent {
  onBarcodeScanned = ({ scannedBarcode }) => {
    console.log('BARCODE: ', scannedBarcode);
    const { applicationId, updateWFProcess, gotoWFProcessScreen } = this.props;
    startWorkflowRequest({
      wfParameters: {
        applicationId,
        startByBarcode: scannedBarcode,
      },
    })
      .then((wfProcess) => {
        updateWFProcess({ wfProcess });
        gotoWFProcessScreen({ wfProcess });
      })
      .catch((axiosError) => toastError({ axiosError }));
  };

  render() {
    return <BarcodeScannerComponent onBarcodeScanned={this.onBarcodeScanned} />;
  }
}

WFLaunchersScanBarcodeScreen.propTypes = {
  //
  // Props
  applicationId: PropTypes.string.isRequired,
  //
  // Functions
  updateWFProcess: PropTypes.func.isRequired,
  gotoWFProcessScreen: PropTypes.func.isRequired,
};

const mapStateToProps = (state, { match }) => {
  const { applicationId } = match.params;

  return {
    applicationId,
  };
};

export default withRouter(
  connect(mapStateToProps, { updateWFProcess, gotoWFProcessScreen })(WFLaunchersScanBarcodeScreen)
);
