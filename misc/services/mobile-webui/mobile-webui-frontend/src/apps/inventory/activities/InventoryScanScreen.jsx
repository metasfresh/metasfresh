import React from 'react';
import BarcodeScannerComponent from '../../../components/BarcodeScannerComponent';
import { resolveHU } from '../api';
import { useScreenDefinition } from '../../../hooks/useScreenDefinition';
import { inventoryJobLocation } from '../routes';

const InventoryScanScreen = () => {
  const { wfProcessId, lineId } = useScreenDefinition({
    screenId: 'InventoryScanScreen',
    back: inventoryJobLocation,
  });

  const onBarcodeScanned = ({ scannedBarcode }) => {
    resolveHU({ scannedCode: scannedBarcode, wfProcessId, lineId }).then((response) => {
      console.log('onBarcodeScanned', { response });
    });
  };

  return <BarcodeScannerComponent onResolvedResult={onBarcodeScanned} continuousRunning={true} />;
};

export default InventoryScanScreen;
