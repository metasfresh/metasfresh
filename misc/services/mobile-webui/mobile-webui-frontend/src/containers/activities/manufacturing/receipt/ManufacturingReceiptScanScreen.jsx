import React from 'react';
import { useDispatch } from 'react-redux';
import { updateManufacturingLUReceiptTarget } from '../../../../actions/ManufacturingActions';

import BarcodeScannerComponent from '../../../../components/BarcodeScannerComponent';
import { parseQRCodeString } from '../../../../utils/qrCode/hu';
import { useScreenDefinition } from '../../../../hooks/useScreenDefinition';
import {
  manufacturingReceiptReceiveTargetScreen,
  manufacturingReceiptScreenLocation,
} from '../../../../routes/manufacturing_receipt';

const ManufacturingReceiptScanScreen = () => {
  const { history, wfProcessId, activityId, lineId } = useScreenDefinition({
    captionKey: 'activities.mfg.receipts.existingLU',
    back: manufacturingReceiptReceiveTargetScreen,
  });

  const dispatch = useDispatch();

  const resolveScannedBarcode = ({ scannedBarcode }) => {
    return {
      huQRCode: parseQRCodeString(scannedBarcode),
    };
  };

  const onBarcodeScanned = ({ huQRCode }) => {
    dispatch(
      updateManufacturingLUReceiptTarget({
        wfProcessId,
        activityId,
        lineId,
        target: { huQRCode },
      })
    );

    history.goTo(manufacturingReceiptScreenLocation);
  };

  return (
    <>
      <BarcodeScannerComponent resolveScannedBarcode={resolveScannedBarcode} onResolvedResult={onBarcodeScanned} />
    </>
  );
};

export default ManufacturingReceiptScanScreen;
