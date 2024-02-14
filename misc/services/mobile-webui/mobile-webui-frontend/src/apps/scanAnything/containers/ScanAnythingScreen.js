import React from 'react';
import BarcodeScannerComponent from '../../../components/BarcodeScannerComponent';
import { parseQRCodeType } from '../../../utils/qrCode/common';
import { QRCODE_TYPE_HU } from '../../../utils/qrCode/hu';
import { QRCODE_TYPE_WORKPLACE } from '../../../utils/qrCode/workplace';
import { getApplicationStartByQRCodeFunction } from '../../index';
import { useDispatch } from 'react-redux';
import { trl } from '../../../utils/translations';

const ScanAnythingScreen = () => {
  const dispatch = useDispatch();

  const onResolvedResult = ({ scannedBarcode }) => {
    const type = parseQRCodeType(scannedBarcode);

    let startApplicationByQRCode;
    switch (type) {
      case QRCODE_TYPE_HU: {
        startApplicationByQRCode = getApplicationStartByQRCodeFunction('huManager');
        break;
      }
      case QRCODE_TYPE_WORKPLACE: {
        startApplicationByQRCode = getApplicationStartByQRCodeFunction('workplaceManager');
        break;
      }
      default: {
        console.log('onResolvedResult: Invalid QR Code type', { type, scannedBarcode });
        throw trl('error.qrCode.invalid');
      }
    }

    // console.log('onResolvedResult', { type, startApplicationByQRCode, scannedBarcode });

    if (startApplicationByQRCode) {
      dispatch(startApplicationByQRCode({ qrCode: scannedBarcode }));
    }
  };

  return <BarcodeScannerComponent onResolvedResult={onResolvedResult} continuousRunning={true} />;
};

export default ScanAnythingScreen;
