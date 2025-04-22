import React from 'react';
import { useDispatch } from 'react-redux';

import { toastError } from '../../utils/toast';
import { getWFProcessScreenLocation } from '../../routes/workflow_locations';
import { startWorkflowRequest } from '../../api/launchers';
import { updateWFProcess } from '../../actions/WorkflowActions';

import BarcodeScannerComponent from '../../components/BarcodeScannerComponent';
import { useScreenDefinition } from '../../hooks/useScreenDefinition';
import { appLaunchersLocation } from '../../routes/launchers';

const WFLaunchersScanBarcodeScreen = () => {
  const { applicationId, history } = useScreenDefinition({ back: appLaunchersLocation });

  const dispatch = useDispatch();
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
