import React, { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';

import { setScannedBarcode } from '../../../actions/ScanActions';
import { updateWFProcess } from '../../../actions/WorkflowActions';
import { updateHeaderEntry } from '../../../actions/HeaderActions';
import { postScannedBarcode } from '../../../api/scanner';
import { getActivityById } from '../../../reducers/wfProcesses';

import BarcodeScannerComponent from '../../../components/BarcodeScannerComponent';
import { fireWFActivityCompleted } from '../../../apps';
import { toastError } from '../../../utils/toast';
import Spinner from '../../../components/Spinner';
import * as uiTrace from '../../../utils/ui_trace';
import { useScreenDefinition } from '../../../hooks/useScreenDefinition';
import { getWFProcessScreenLocation } from '../../../routes/workflow_locations';

const ScanScreen = () => {
  const { history, url, applicationId, wfProcessId, activityId } = useScreenDefinition({
    screenId: 'ScanScreen',
    back: getWFProcessScreenLocation,
  });

  const queryParameters = new URLSearchParams(window.location.search);
  const useTheAlreadyScannedQrCode = queryParameters.get('resendQr');
  const validOptionIndex = queryParameters.get('validOptionIndex');

  const { activityCaption, userInstructions, currentValue, validOptions } = useSelector((state) => {
    const activity = getActivityById(state, wfProcessId, activityId);
    return {
      activityCaption: activity?.caption,
      userInstructions: activity?.userInstructions,
      currentValue: activity?.dataStored.currentValue,
      validOptions: activity?.dataStored?.validOptions,
    };
  });

  const dispatch = useDispatch();
  useEffect(() => {
    dispatch(updateHeaderEntry({ location: url, caption: activityCaption, userInstructions }));
  }, [url, activityCaption, userInstructions]);

  useEffect(() => {
    if (useTheAlreadyScannedQrCode === 'true' && currentValue?.qrCode !== undefined) {
      onBarcodeScanned({ scannedBarcode: currentValue?.qrCode });
    }
  }, [useTheAlreadyScannedQrCode, currentValue?.qrCode]);

  const onBarcodeScanned = ({ scannedBarcode }) => {
    //console.log('onBarcodeScanned', { scannedBarcode });
    uiTrace.trace({ eventName: 'barcodeScanned', scannedBarcode, wfProcessId, activityId });

    if (validOptionIndex != null && !validOptions?.length) {
      toastError({ messageKey: 'activities.mfg.validateSourceLocator.noValidOption' });
      history.goBack();
      return;
    }

    if (validOptionIndex != null && scannedBarcode !== validOptions[validOptionIndex]?.qrCode) {
      toastError({ messageKey: 'activities.mfg.validateSourceLocator.qrDoesNotMatch' });
      return;
    }

    dispatch(setScannedBarcode({ wfProcessId, activityId, scannedBarcode }));

    return postScannedBarcode({ wfProcessId, activityId, scannedBarcode })
      .then((wfProcess) => {
        //console.log('postScannedBarcode.then', { wfProcess });
        dispatch(updateWFProcess({ wfProcess }));

        dispatch(
          fireWFActivityCompleted({
            applicationId,
            wfProcessId,
            activityId,
            history,
            defaultAction: () => history.goBack(),
          })
        );
      })
      .catch((error) => {
        dispatch(setScannedBarcode({ wfProcessId, activityId, scannedBarcode: null }));

        throw {
          axiosError: error,
          fallbackMessageKey: 'activities.scanBarcode.invalidScannedBarcode',
        };
      });
  };

  if (useTheAlreadyScannedQrCode === 'true') {
    return <Spinner />;
  }

  return <BarcodeScannerComponent onResolvedResult={onBarcodeScanned} continuousRunning={true} />;
};

export default ScanScreen;
