import React, { useEffect } from 'react';
import { useHistory, useRouteMatch } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';

import { setScannedBarcode } from '../../../actions/ScanActions';
import { updateWFProcess } from '../../../actions/WorkflowActions';
import { pushHeaderEntry } from '../../../actions/HeaderActions';
import { postScannedBarcode } from '../../../api/scanner';
import { getActivityById } from '../../../reducers/wfProcesses';

import BarcodeScannerComponent from '../../../components/BarcodeScannerComponent';
import { fireWFActivityCompleted } from '../../../apps';
import { toastError } from '../../../utils/toast';

const ScanScreen = () => {
  const {
    url,
    params: { applicationId, workflowId: wfProcessId, activityId },
  } = useRouteMatch();

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
    dispatch(pushHeaderEntry({ location: url, caption: activityCaption, userInstructions }));
  }, [url, activityCaption, userInstructions]);

  useEffect(() => {
    if (useTheAlreadyScannedQrCode === 'true' && currentValue?.qrCode !== undefined) {
      onBarcodeScanned({ scannedBarcode: currentValue?.qrCode });
    }
  }, [useTheAlreadyScannedQrCode]);

  const history = useHistory();
  const onBarcodeScanned = ({ scannedBarcode }) => {
    //console.log('onBarcodeScanned', { scannedBarcode });
    if (validOptionIndex !== undefined && !validOptions?.length) {
      toastError({ messageKey: 'activities.mfg.validateSourceLocator.noValidOption' });
      history.goBack();
      return;
    }

    if (validOptionIndex !== undefined && scannedBarcode !== validOptions[validOptionIndex]?.qrCode) {
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

  return <BarcodeScannerComponent onResolvedResult={onBarcodeScanned} continuousRunning={true} />;
};

export default ScanScreen;
