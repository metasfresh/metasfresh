import React, { useEffect } from 'react';

import BarcodeScannerComponent from '../../components/BarcodeScannerComponent';
import { useScreenDefinition } from '../../hooks/useScreenDefinition';
import { appLaunchersLocation } from '../../routes/launchers';
import { useFilterByQRCode } from './WFLaunchersScreen';

const WFLaunchersScanBarcodeScreen = () => {
  const { applicationId, history } = useScreenDefinition({
    screenId: 'WFLaunchersScanBarcodeScreen',
    back: appLaunchersLocation,
  });
  const { setFilterByQRCode } = useFilterByQRCode({ applicationId });

  //
  // Reset the current scanned barcode if any
  useEffect(() => {
    setFilterByQRCode(null);
  }, []);

  const onBarcodeScanned = ({ scannedBarcode }) => {
    setFilterByQRCode(scannedBarcode);
    history.goBack();
  };

  return <BarcodeScannerComponent onResolvedResult={onBarcodeScanned} />;
};

export default WFLaunchersScanBarcodeScreen;
