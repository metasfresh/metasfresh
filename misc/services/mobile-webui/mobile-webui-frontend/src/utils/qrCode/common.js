import { trl } from '../translations';

export const ATTR_barcodeType = 'barcodeType';
export const ATTR_productId = 'productId';
export const ATTR_productNo = 'productNo';
export const ATTR_GTIN = 'GTIN';
export const ATTR_weightNet = 'weightNet';
export const ATTR_weightNetUOM = 'weightNetUOM';
export const ATTR_bestBeforeDate = 'bestBeforeDate';
export const ATTR_lotNo = 'lotNo';
export const ATTR_displayable = 'displayable';
export const ATTR_isTUToBePickedAsWhole = 'isTUToBePickedAsWhole';

export const BARCODE_TYPE_GS1 = 'GS1';
export const BARCODE_TYPE_EAN13 = 'EAN13';
export const BARCODE_TYPE_HU = 'HU'; // global HU QR code
export const BARCODE_TYPE_LMQ = 'LMQ'; // Leich+Mehl QR code

export const QRCODE_SEPARATOR = '#';

export const parseQRCodeType = (qrCodeString) => {
  const idx = qrCodeString.indexOf(QRCODE_SEPARATOR);
  if (idx <= 0) {
    console.log('onResolvedResult: Cannot extract type from QRCode', { qrCodeString });
    throw trl('error.qrCode.invalid');
  }
  return qrCodeString.substring(0, idx);
};

export const toLocalDateString = ({ year, month, day }) => {
  const yearInt = Number(year);
  const monthInt = Number(month);
  const dayInt = Number(day);
  return `${yearInt}-${monthInt < 10 ? '0' + monthInt : monthInt}-${dayInt < 10 ? '0' + dayInt : dayInt}`;
};

export const isBarcodeProductNoMatching = ({ expectedProductNo, barcodeProductNo, barcodeType }) => {
  // if expected nor barcode productNo was specified, consider products are matching
  if (!expectedProductNo || !barcodeProductNo) {
    return true;
  }

  // normalize productNo(s) before comparing
  const expectedProductNoStr = String(expectedProductNo);
  const barcodeProductNoStr = String(barcodeProductNo);

  if (barcodeType === BARCODE_TYPE_EAN13) {
    return expectedProductNoStr.startsWith(barcodeProductNoStr);
  } else {
    return expectedProductNoStr === barcodeProductNoStr;
  }
};
