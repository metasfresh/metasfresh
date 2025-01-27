import React from 'react';
import { useDispatch } from 'react-redux';
import { useHistory, useRouteMatch } from 'react-router-dom';
import BarcodeScannerComponent from '../../../components/BarcodeScannerComponent';
import { postDistributionPickFrom } from '../../../api/distribution';
import { parseQRCodeString, toQRCodeString } from '../../../utils/qrCode/hu';
import { updateWFProcess } from '../../../actions/WorkflowActions';
import { useDistributionLineProps, useHeaderUpdate } from './DistributionLineScreen';
import { trl } from '../../../utils/translations';

const DistributionLinePickFromScreen = () => {
  const dispatch = useDispatch();
  const history = useHistory();
  const {
    params: { workflowId: wfProcessId, activityId, lineId },
  } = useRouteMatch();

  useHeaderUpdate({ captionKey: 'activities.distribution.scanHU' });
  const { productId } = useDistributionLineProps({ wfProcessId, activityId, lineId });

  const resolveScannedBarcode = ({ scannedBarcode }) => {
    const parsedHUQRCode = parseQRCodeString(scannedBarcode);

    if (productId != null && parsedHUQRCode.productId != null && parsedHUQRCode.productId !== productId) {
      throw trl('activities.distribution.qrcode.differentProduct');
    }

    return { scannedBarcode };
  };
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

  return (
    <BarcodeScannerComponent
      resolveScannedBarcode={resolveScannedBarcode}
      onResolvedResult={onBarcodeScanned}
      continuousRunning={true}
    />
  );
};

export default DistributionLinePickFromScreen;
