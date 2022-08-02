import React from 'react';
import { useHistory, useRouteMatch } from 'react-router-dom';
import { useDispatch } from 'react-redux';

import { toastError } from '../../utils/toast';
import { getWFProcessScreenLocation } from '../../routes/workflow_locations';
import { startWorkflowRequest } from '../../api/launchers';
import { updateWFProcess } from '../../actions/WorkflowActions';

import BarcodeScannerComponent from '../../components/BarcodeScannerComponent';

const WFLaunchersScanBarcodeScreen = () => {
  const {
    params: { applicationId },
  } = useRouteMatch();

  const dispatch = useDispatch();
  const history = useHistory();
  const onBarcodeScanned = ({ scannedBarcode }) => {
    startWorkflowRequest({
      wfParameters: {
        applicationId,
        startByBarcode: scannedBarcode,
      },
    })
      .then((wfProcess) => {
        dispatch(updateWFProcess({ wfProcess }));
        history.push(getWFProcessScreenLocation({ applicationId, wfprocessId: wfProcess.id }));
      })
      .catch((axiosError) => toastError({ axiosError }));
  };

  return <BarcodeScannerComponent onResolvedResult={onBarcodeScanned} />;
};

export default WFLaunchersScanBarcodeScreen;
