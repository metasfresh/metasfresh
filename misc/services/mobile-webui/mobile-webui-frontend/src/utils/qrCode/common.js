import { trl } from '../translations';

export const QRCODE_SEPARATOR = '#';

export const parseQRCodeType = (qrCodeString) => {
  const idx = qrCodeString.indexOf(QRCODE_SEPARATOR);
  if (idx <= 0) {
    console.log('onResolvedResult: Cannot extract type from QRCode', { qrCodeString });
    throw trl('error.qrCode.invalid');
  }
  return qrCodeString.substring(0, idx);
};
