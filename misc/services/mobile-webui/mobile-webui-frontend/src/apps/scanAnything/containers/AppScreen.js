import React from 'react';
import BarcodeScannerComponent from '../../../components/BarcodeScannerComponent';
import { parseQRCodeType } from '../../../utils/qrCode/common';
import { QRCODE_TYPE_HU } from '../../../utils/qrCode/hu';
import { QRCODE_TYPE_WORKPLACE } from '../../../utils/qrCode/workplace';
import { getApplicationStartByQRCodeFunction } from '../../index';
import { useDispatch } from 'react-redux';
import { trl } from '../../../utils/translations';
import { QRCODE_TYPE_RESOURCE } from '../../../utils/qrCode/resource';
import { useApplicationInfoParameters } from '../../../reducers/applications';
import { APPLICATION_ID } from '../constants';
import { APPLICATION_ID as APPLICATION_ID_workplaceManager } from '../../workplaceManager/constants';
import { APPLICATION_ID as APPLICATION_ID_workstationManager } from '../../workstationManager/constants';
import { APPLICATION_ID as APPLICATION_ID_huManager } from '../../huManager/constants';

const qrCodeType2applicationId = {
  [QRCODE_TYPE_HU]: APPLICATION_ID_huManager,
  [QRCODE_TYPE_WORKPLACE]: APPLICATION_ID_workplaceManager,
  [QRCODE_TYPE_RESOURCE]: APPLICATION_ID_workstationManager,
};

const AppScreen = () => {
  const dispatch = useDispatch();
  const { handledApplicationIds } = useApplicationInfoParameters({ applicationId: APPLICATION_ID });
  console.log('', { handledApplicationIds, qrCodeType2applicationId });

  const onResolvedResult = ({ scannedBarcode }) => {
    const type = parseQRCodeType(scannedBarcode);

    const handlerApplicationId = qrCodeType2applicationId[type];
    if (!handlerApplicationId) {
      console.warn(`No handlerApplicationId found for QR code type '${type}`, {
        scannedBarcode,
        qrCodeType2applicationId,
      });
      throw trl('error.qrCode.invalid');
    }

    const startApplicationByQRCode = getApplicationStartByQRCodeFunction(handlerApplicationId);
    if (!startApplicationByQRCode) {
      console.warn(`No startApplicationByQRCode function found`, {
        handlerApplicationId,
        scannedBarcode,
        qrCodeType2applicationId,
      });
      throw trl('error.qrCode.invalid');
    }

    dispatch(startApplicationByQRCode({ qrCode: scannedBarcode, parent: APPLICATION_ID }));
  };

  return <BarcodeScannerComponent onResolvedResult={onResolvedResult} continuousRunning={true} />;
};

export default AppScreen;
