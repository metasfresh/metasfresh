import React from 'react';
import { useScreenDefinition } from '../../../hooks/useScreenDefinition';
import { huConsolidationJobLocation, selectTargetScreenLocation } from '../routes';
import BarcodeScannerComponent from '../../../components/BarcodeScannerComponent';
import { parseQRCodeString, toQRCodeString } from '../../../utils/qrCode/hu';
import { useSetCurrentTarget } from '../actions/useSetCurrentTarget';
import { toastErrorFromObj } from '../../../utils/toast';

export const ScanHUConsolidationTargetScreen = () => {
  const { history, applicationId, wfProcessId } = useScreenDefinition({
    screenId: 'ScanHUConsolidationTargetScreen',
    captionKey: 'huConsolidation.ScanHUConsolidationTargetScreen.caption',
    back: selectTargetScreenLocation,
  });

  const setTarget = useSetCurrentTarget({ wfProcessId });

  const resolveScannedBarcode = ({ scannedBarcode }) => {
    return {
      huQRCode: parseQRCodeString(scannedBarcode),
    };
  };

  const onBarcodeScanned = ({ huQRCode }) => {
    const target = { luQRCode: toQRCodeString(huQRCode) };
    console.log('onBarcodeScanned', { huQRCode, target });
    setTarget({ target })
      .then(() => history.go(huConsolidationJobLocation({ applicationId, wfProcessId })))
      .catch(toastErrorFromObj);
  };

  return (
    <div className="section pt-2">
      <BarcodeScannerComponent
        continuousRunning={true}
        resolveScannedBarcode={resolveScannedBarcode}
        onResolvedResult={onBarcodeScanned}
      />
    </div>
  );
};
