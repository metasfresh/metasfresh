import React, { useEffect } from 'react';
import { useHistory, useRouteMatch } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import { pushHeaderEntry } from '../../../actions/HeaderActions';
import { getActivityById } from '../../../reducers/wfProcesses';
import BarcodeScannerComponent from '../../../components/BarcodeScannerComponent';
import { toastError, toastNotification } from '../../../utils/toast';

const ScanAndValidateScreen = () => {
  const {
    url,
    params: { workflowId: wfProcessId, activityId, validOptionIndex },
  } = useRouteMatch();
  const { activityCaption, userInstructions, validOptions } = useSelector((state) => {
    const activity = getActivityById(state, wfProcessId, activityId);
    return {
      activityCaption: activity?.caption,
      userInstructions: activity?.userInstructions,
      validOptions: activity?.dataStored?.validOptions,
    };
  });
  const dispatch = useDispatch();
  const history = useHistory();

  const onBarcodeScanned = ({ scannedBarcode }) => {
    if (validOptionIndex === undefined || !validOptions?.length) {
      toastError({ messageKey: 'activities.mfg.validateSourceLocator.noValidOption' });
      history.goBack();
      return;
    }

    if (scannedBarcode !== validOptions[validOptionIndex].qrCode) {
      toastError({ messageKey: 'activities.mfg.validateSourceLocator.qrDoesNotMatch' });
    } else {
      toastNotification({ messageKey: 'activities.mfg.validateSourceLocator.qrMatches' });
      history.goBack();
    }
  };

  useEffect(() => {
    dispatch(pushHeaderEntry({ location: url, caption: activityCaption, userInstructions }));
  }, [url, activityCaption, userInstructions]);

  return <BarcodeScannerComponent onResolvedResult={onBarcodeScanned} continuousRunning={true} />;
};

export default ScanAndValidateScreen;
