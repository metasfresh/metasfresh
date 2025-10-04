import React from 'react';
import BarcodeScannerComponent from '../../../components/BarcodeScannerComponent';
import { resolveHU, resolveLocator } from '../api';
import { useScreenDefinition } from '../../../hooks/useScreenDefinition';
import { inventoryJobLocation } from '../routes';

const STATUS_ScanLocator = 'ScanLocator';
const STATUS_ScanHU = 'ScanHU';
const STATUS_FillData = 'FillData';

const InventoryScanScreen = () => {
  const { wfProcessId, lineId } = useScreenDefinition({
    screenId: 'InventoryScanScreen',
    back: inventoryJobLocation,
  });

  const [status] = React.useState(STATUS_ScanLocator);

  const onLocatorScanned = ({ scannedBarcode }) => {
    resolveLocator({ scannedBarcode, wfProcessId, lineId }).then((response) => {
      console.log('onBarcodeScanned', { response });
    });
  };

  const onHUScanned = ({ scannedBarcode }) => {
    resolveHU({ scannedBarcode, wfProcessId, lineId }).then((response) => {
      console.log('onBarcodeScanned', { response });
    });
  };

  if (status === STATUS_ScanLocator) {
    return (
      <BarcodeScannerComponent
        inputPlaceholderText="Scan Locator"
        onResolvedResult={onLocatorScanned}
        continuousRunning={true}
      />
    );
  } else if (status === STATUS_ScanHU) {
    return (
      <BarcodeScannerComponent inputPlaceholderText="Scan HU" onResolvedResult={onHUScanned} continuousRunning={true} />
    );
  } else if (status === STATUS_FillData) {
    return <div>Fill data</div>;
  }
};

export default InventoryScanScreen;
