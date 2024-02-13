export const QRCODE_SEPARATOR = '#';

export const parseQRCodeType = (qrCodeString) => {
  const idx = qrCodeString.indexOf(QRCODE_SEPARATOR);
  if (idx <= 0) {
    throw 'Invalid global QR code: ' + qrCodeString;
  }
  return qrCodeString.substring(0, idx);
};
