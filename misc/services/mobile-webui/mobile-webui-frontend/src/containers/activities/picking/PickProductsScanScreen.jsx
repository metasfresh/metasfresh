import React from 'react';
import { getCustomQRCodeFormats, useWFActivity } from '../../../reducers/wfProcesses';
import { getNextEligibleLineToPick } from '../../../utils/picking';
import BarcodeScannerComponent from '../../../components/BarcodeScannerComponent';
import { pickingLineScanScreenLocation } from '../../../routes/picking';
import { convertScannedBarcodeToResolvedResult, NEXT_PickingJob } from './PickLineScanScreen';
import { useScreenDefinition } from '../../../hooks/useScreenDefinition';
import { getWFProcessScreenLocation } from '../../../routes/workflow_locations';
import { useMobileNavigation } from '../../../hooks/useMobileNavigation';

const PickProductsScanScreen = () => {
  const { applicationId, wfProcessId, activityId } = useScreenDefinition({
    screenId: 'PickProductsScanScreen',
    captionKey: 'activities.picking.scanQRCode',
    back: getWFProcessScreenLocation,
  });
  const { onBarcodeScanned } = usePickProductsScan({ applicationId, wfProcessId, activityId });

  return <BarcodeScannerComponent onResolvedResult={onBarcodeScanned} />;
};

export default PickProductsScanScreen;

//
//
// ----------------------------------------------------------
//
//

export const usePickProductsScan = ({ applicationId, wfProcessId, activityId }) => {
  const history = useMobileNavigation();
  const activity = useWFActivity({ wfProcessId, activityId });
  const customQRCodeFormats = getCustomQRCodeFormats({ activity });

  const onBarcodeScanned = async ({ scannedBarcode }) => {
    const { qrCode } = await convertScannedBarcodeToResolvedResult({
      scannedBarcode,
      customQRCodeFormats,
    });
    // console.log('onBarcodeScanned', { scannedBarcode, qrCode });

    const line = getNextEligibleLineToPick({ activity, productId: qrCode.productId });
    if (!line) {
      throw 'No matching lines found'; // TODO trl
    }

    const lineId = line.pickingLineId;
    console.log('onBarcodeScanned', { lineId, line, scannedBarcode });

    history.push(
      pickingLineScanScreenLocation({
        applicationId,
        wfProcessId,
        activityId,
        lineId,
        qrCode: scannedBarcode,
        next: NEXT_PickingJob,
      })
    );
  };

  return {
    onBarcodeScanned,
  };
};
