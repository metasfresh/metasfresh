import React, { useEffect } from 'react';
import { useHistory, useRouteMatch } from 'react-router-dom';
import { useDispatch } from 'react-redux';

import { pushHeaderEntry } from '../../../../actions/HeaderActions';
import { updateManufacturingReceiptTarget } from '../../../../actions/ManufacturingActions';

import BarcodeScannerComponent from '../../../../components/BarcodeScannerComponent';
import { parseQRCodeString } from '../../../../utils/qrCode/hu';
import { trl } from '../../../../utils/translations';

const ManufacturingReceiptScanScreen = () => {
  const {
    url,
    params: { workflowId: wfProcessId, activityId, lineId },
  } = useRouteMatch();

  const dispatch = useDispatch();
  useEffect(() => {
    dispatch(
      pushHeaderEntry({
        location: url,
        caption: trl('activities.mfg.receipts.existingLU'),
      })
    );
  }, []);

  const resolveScannedBarcode = ({ scannedBarcode }) => {
    return {
      huQRCode: parseQRCodeString(scannedBarcode),
    };
  };

  const history = useHistory();
  const onBarcodeScanned = ({ huQRCode }) => {
    dispatch(
      updateManufacturingReceiptTarget({
        wfProcessId,
        activityId,
        lineId,
        target: { huQRCode },
      })
    );

    history.go(-2);
  };

  return (
    <>
      <BarcodeScannerComponent resolveScannedBarcode={resolveScannedBarcode} onResolvedResult={onBarcodeScanned} />
    </>
  );
};

export default ManufacturingReceiptScanScreen;
