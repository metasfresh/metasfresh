import React from 'react';
import { useDispatch } from 'react-redux';
import { useHistory, useRouteMatch } from 'react-router-dom';
import BarcodeScannerComponent from '../../../components/BarcodeScannerComponent';
import { postDistributionPickFrom } from '../../../api/distribution';
import { toQRCodeString } from '../../../utils/qrCode/hu';
import { updateWFProcess } from '../../../actions/WorkflowActions';
import { useHeaderUpdate } from './DistributionLineScreen';

const DistributionLinePickFromScreen = () => {
  const dispatch = useDispatch();
  const history = useHistory();
  const {
    params: { workflowId: wfProcessId, activityId, lineId },
  } = useRouteMatch();

  useHeaderUpdate({ captionKey: 'activities.distribution.scanHU' });

  const onBarcodeScanned = ({ scannedBarcode }) => {
    return postDistributionPickFrom({
      wfProcessId,
      activityId,
      lineId,
      pickFrom: {
        qrCode: toQRCodeString(scannedBarcode),
      },
    }).then((wfProcess) => {
      dispatch(updateWFProcess({ wfProcess }));
      history.go(-1);
    });
  };

  return <BarcodeScannerComponent onResolvedResult={onBarcodeScanned} continuousRunning={true} />;
};

export default DistributionLinePickFromScreen;
