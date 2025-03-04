import React from 'react';
import { useDispatch } from 'react-redux';
import BarcodeScannerComponent from '../../../components/BarcodeScannerComponent';
import { postDistributionPickFrom } from '../../../api/distribution';
import { parseQRCodeString, toQRCodeString } from '../../../utils/qrCode/hu';
import { updateWFProcess } from '../../../actions/WorkflowActions';
import { useDistributionLineProps, useDistributionScreenDefinition } from './DistributionLineScreen';
import { trl } from '../../../utils/translations';
import { distributionLineScreenLocation } from '../../../routes/distribution';

const DistributionLinePickFromScreen = () => {
  const { history, wfProcessId, activityId, lineId } = useDistributionScreenDefinition({
    captionKey: 'activities.distribution.scanHU',
    back: distributionLineScreenLocation,
  });

  const dispatch = useDispatch();

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
      history.goBack();
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
