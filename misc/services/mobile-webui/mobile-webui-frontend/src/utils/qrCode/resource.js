import { parseQRCodeType } from './common';

export const QRCODE_TYPE_RESOURCE = 'RES';

export const isResourceQRCode = (string) => {
  return parseQRCodeType(string) === QRCODE_TYPE_RESOURCE;
};
