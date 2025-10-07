import React from 'react';
import BarcodeScannerComponent from '../../../components/BarcodeScannerComponent';
import { resolveHU, resolveLocator } from '../api';
import { useScreenDefinition } from '../../../hooks/useScreenDefinition';
import { inventoryJobLocation } from '../routes';
import { toastError } from '../../../utils/toast';

const STATUS_ScanLocator = 'ScanLocator';
const STATUS_ScanHU = 'ScanHU';
const STATUS_FillData = 'FillData';

const InventoryScanScreen = () => {
  const { wfProcessId, lineId } = useScreenDefinition({
    screenId: 'InventoryScanScreen',
    back: inventoryJobLocation,
  });

  const [status, setStatus] = React.useState(STATUS_ScanLocator);
  const [locatorQRCode, setLocatorQRCode] = React.useState();

  const onLocatorScanned = ({ scannedBarcode }) => {
    resolveLocator({ scannedBarcode, wfProcessId, lineId }).then((response) => {
      if (response.qrCode) {
        setLocatorQRCode(response.qrCode);
        setStatus(STATUS_ScanHU);
      } else {
        toastError({ messageKey: 'error.qrCode.invalid', context: { scannedBarcode, wfProcessId, lineId, response } });
      }
    });
  };

  const onHUScanned = ({ scannedBarcode }) => {
    console.log('**** onHUScanned', { scannedBarcode });
    resolveHU({ scannedBarcode, wfProcessId, lineId, locatorQRCode }).then((response) => {
      console.log('onBarcodeScanned', { response });
    });
  };

  console.log('InventoryScanScreen', { status, locatorQRCode });

  if (status === STATUS_ScanLocator) {
    return (
      <BarcodeScannerComponent
        key="scanLocator"
        inputPlaceholderText="Scan Locator"
        onResolvedResult={onLocatorScanned}
        continuousRunning={true}
      />
    );
  } else if (status === STATUS_ScanHU) {
    return (
      <BarcodeScannerComponent
        key="scanHU"
        inputPlaceholderText="Scan HU"
        onResolvedResult={onHUScanned}
        continuousRunning={true}
      />
    );
  } else if (status === STATUS_FillData) {
    return <div>Fill data</div>;
  }
};

export default InventoryScanScreen;
