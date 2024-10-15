// NOTE to dev: keep in sync with:
import { QRCODE_SEPARATOR } from './common';

export const QRCODE_TYPE_LOCATOR = 'LOC'; // keep in sync with org.adempiere.warehouse.qrcode.LocatorQRCodeJsonConverter.GLOBAL_QRCODE_TYPE

export const parseLocatorQRCodeString = (string) => {
  let remainingString = string;
  //
  // Type
  let type;
  {
    const idx = remainingString.indexOf(QRCODE_SEPARATOR);
    if (idx <= 0) {
      throw 'Invalid global QR code(1): ' + string;
    }
    type = remainingString.substring(0, idx);
    remainingString = remainingString.substring(idx + 1);
  }
  //
  // Version
  let version;
  {
    const idx = remainingString.indexOf(QRCODE_SEPARATOR);
    if (idx <= 0) {
      throw 'Invalid global QR code(2): ' + string;
    }
    version = remainingString.substring(0, idx);
    remainingString = remainingString.substring(idx + 1);
  }

  const payload = JSON.parse(remainingString);

  let payloadParsed;
  if (type === QRCODE_TYPE_LOCATOR && version === '1') {
    payloadParsed = parseQRCodePayload_Locator_v1(payload);
  } else {
    throw 'Invalid global QR code(3): ' + string;
  }

  return { ...payloadParsed, code: string };
};

const parseQRCodePayload_Locator_v1 = (payload) => {
  return { warehouseId: payload.warehouseId, locatorId: payload.locatorId, displayable: payload.caption };
};

export const toLocatorQRCodeString = (qrCode) => {
  //
  // Case: null/empty qrCode
  if (!qrCode) {
    return '';
  }
  //
  // Case: possible { code, displayable } QR code object
  else if (typeof qrCode === 'object') {
    if (qrCode.code) {
      return qrCode.code;
    } else {
      throw 'Invalid Locator QR Code because the "code" field is missing: ' + JSON.stringify(qrCode);
    }
  }
  //
  // Case: possible string
  else if (!Array.isArray(qrCode)) {
    return `${qrCode}`;
  }

  //
  // Unknown QR code format
  throw 'Invalid Locator QR Code: ' + JSON.stringify(qrCode);
};
