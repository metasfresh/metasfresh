/*
 * #%L
 * ic114
 * %%
 * Copyright (C) 2024 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import {
  ATTR_barcodeType,
  ATTR_bestBeforeDate,
  ATTR_displayable,
  ATTR_isTUToBePickedAsWhole,
  ATTR_lotNo,
  ATTR_productId,
  ATTR_productNo,
  ATTR_weightNet,
  ATTR_weightNetUOM,
  BARCODE_TYPE_HU,
  BARCODE_TYPE_LMQ,
  QRCODE_SEPARATOR,
  toLocalDateString,
} from './common';
import { trl } from '../translations';
import { HU_ATTRIBUTE_BestBeforeDate, HU_ATTRIBUTE_LotNo, HU_ATTRIBUTE_WeightNet } from '../../constants/HUAttributes';
import { parseGS1CodeString } from './gs1';
import { parseEAN13CodeString } from './ean13';

export const QRCODE_TYPE_HU = 'HU';
export const QRCODE_TYPE_LEICH_UND_MEHL = 'LMQ';

export const toQRCodeDisplayable = (qrCode) => {
  //
  // Case: null/empty qrCode
  if (!qrCode) {
    return null;
  }
  //
  // Case: possible { code, displayable } QR code object
  else if (typeof qrCode === 'object') {
    if (qrCode.displayable) {
      return qrCode.displayable;
    } else if (qrCode.code) {
      return parseQRCodeString(qrCode.code).displayable;
    } else {
      throw 'Invalid QR Code: ' + JSON.stringify(qrCode);
    }
  }
  //
  // Case: possible string
  else if (!Array.isArray(qrCode)) {
    return parseQRCodeString(`${qrCode}`).displayable;
  }

  //
  // Unknown QR code format
  throw 'Invalid QR Code: ' + JSON.stringify(qrCode);
};

export const toQRCodeString = (qrCode) => {
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
      throw 'Invalid QR Code because the "code" field is missing: ' + JSON.stringify(qrCode);
    }
  }
  //
  // Case: possible string
  else if (!Array.isArray(qrCode)) {
    return `${qrCode}`;
  }

  //
  // Unknown QR code format
  throw 'Invalid QR Code: ' + JSON.stringify(qrCode);
};

export const toQRCodeObject = (qrCode) => {
  //
  // Case: null/empty qrCode
  if (!qrCode) {
    return null;
  }
  //
  // Case: possible { code, displayable } QR code object
  else if (typeof qrCode === 'object') {
    if (qrCode.code) {
      return {
        code: qrCode.code,
        displayable: qrCode.displayable || toQRCodeDisplayable(qrCode.code),
      };
    } else {
      throw 'Invalid QR Code because the "code" field is missing: ' + JSON.stringify(qrCode);
    }
  }
  //
  // Case: possible string
  else if (!Array.isArray(qrCode)) {
    const code = `${qrCode}`;
    return {
      code,
      displayable: toQRCodeDisplayable(code),
    };
  }

  //
  // Unknown QR code format
  throw 'Invalid QR Code: ' + JSON.stringify(qrCode);
};

// NOTE to dev: keep in sync with:
// de.metas.global_qrcodes.GlobalQRCode.ofString
// de.metas.handlingunits.qrcodes.model.HUQRCode
// de.metas.handlingunits.qrcodes.model.json.HUQRCodeJsonConverter.fromGlobalQRCode
export const parseQRCodeString = (string, returnFalseOnError) => {
  //console.trace('parseQRCodeString', { string, returnFalseOnError });
  const allResults = {};

  let result = parseGS1CodeString(string);
  allResults['gs1'] = result;

  if (result?.error) {
    result = parseEAN13CodeString(string);
    allResults['ean13'] = result;
  }

  if (result?.error) {
    result = parseQRCodeString_GlobalQRCode(string);
    allResults['globalQRCode'] = result;
  }

  //
  if (result && !result.error) {
    return { ...result, code: string };
  } else if (returnFalseOnError) {
    return false;
  } else {
    const errorMsg = result?.error ? result.error : trl('error.qrCode.invalid');
    console.log(`parseQRCodeString: ${errorMsg}`, { string, allResults });
    throw errorMsg;
  }
};

const parseQRCodeString_GlobalQRCode = (string) => {
  if (!string) {
    return {
      error: trl('error.qrCode.invalid'),
      errorDetail: 'empty string',
    };
  }

  let remainingString = string;

  //
  // Type
  let type;
  {
    const idx = remainingString.indexOf(QRCODE_SEPARATOR);
    if (idx <= 0) {
      return {
        error: trl('error.qrCode.invalid'),
        errorDetail: 'cannot extract type from Global QRCode',
      };
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
      return {
        error: trl('error.qrCode.invalid'),
        errorDetail: 'cannot extract version from Global QRCode',
      };
    }
    version = remainingString.substring(0, idx);
    remainingString = remainingString.substring(idx + 1);
  }

  if (type === QRCODE_TYPE_HU && version === '1') {
    const jsonPayload = JSON.parse(remainingString);
    return parseQRCodePayload_HU_v1(jsonPayload);
  } else if (type === QRCODE_TYPE_LEICH_UND_MEHL && version === '1') {
    return parseQRCodePayload_LeichMehl_v1(remainingString);
  } else {
    return {
      error: trl('error.qrCode.invalid'),
      errorDetail: 'unknown Global QRCode',
    };
  }
};

// NOTE to dev: keep in sync with:
// de.metas.handlingunits.qrcodes.model.json.v1.JsonConverterV1
const parseQRCodePayload_HU_v1 = (payload) => {
  const id = payload.id ? String(payload.id) : null;

  // Displayable code
  let displayable = id;
  {
    const idx = id?.lastIndexOf('-');
    if (idx > 0) {
      displayable = id.substring(idx + 1);
    } else if (payload.caption) {
      displayable = payload.caption;
    }
  }

  const result = { displayable };
  result[ATTR_barcodeType] = BARCODE_TYPE_HU;

  if (payload?.product?.id) {
    // IMPORTANT: convert it to string because all over in our code we assume IDs are strings.
    result[ATTR_productId] = payload?.product?.id.toString();
  }

  const weightNetAttribute = payload?.attributes?.find((attribute) => attribute?.code === HU_ATTRIBUTE_WeightNet);
  if (weightNetAttribute?.value != null) {
    // IMPORTANT: convert it to number (i.e. multiply with 1) because we consider weights are numbers
    result[ATTR_weightNet] = 1 * weightNetAttribute?.value;
  }

  const bestBeforeAttribute = payload?.attributes?.find((attribute) => attribute.code === HU_ATTRIBUTE_BestBeforeDate);
  if (bestBeforeAttribute?.value != null) {
    result[ATTR_bestBeforeDate] = bestBeforeAttribute.value;
  }

  const lotNumberAttribute = payload?.attributes?.find((attribute) => attribute.code === HU_ATTRIBUTE_LotNo);
  if (lotNumberAttribute?.value != null) {
    result[ATTR_lotNo] = lotNumberAttribute.value;
  }

  return result;
};

// NOTE to dev: keep in sync with:
// de.metas.handlingunits.qrcodes.leich_und_mehl.LMQRCodeParser
const LMQ_BEST_BEFORE_DATE_FORMAT = /^(\d{2}).(\d{2}).(\d{4})$/;
const parseQRCodePayload_LeichMehl_v1 = (payload) => {
  const result = { displayable: payload };
  result[ATTR_barcodeType] = BARCODE_TYPE_LMQ;

  const parts = payload.split('#');
  if (parts.length >= 1 && parts[0] != null) {
    // IMPORTANT: convert it to number (i.e. multiply with 1) because we consider weights are numbers
    result[ATTR_weightNet] = 1 * parts[0];
    result[ATTR_weightNetUOM] = 'kg'; // for LeichMehl it will always be kg
    result[ATTR_displayable] = '' + parts[0];
    result[ATTR_isTUToBePickedAsWhole] = true; // todo clean up needed!!!
  }
  if (parts.length >= 2) {
    const [, day, month, year] = LMQ_BEST_BEFORE_DATE_FORMAT.exec(parts[1]);
    result[ATTR_bestBeforeDate] = toLocalDateString({ year, month, day });
  }
  if (parts.length >= 3) {
    result[ATTR_lotNo] = parts[2];
  }
  if (parts.length >= 4) {
    result[ATTR_productNo] = parts[3];
  }

  return result;
};

export const isKnownQRCodeFormat = (qrCodeString) => {
  const returnFalseOnError = true;
  return !!parseQRCodeString(qrCodeString, returnFalseOnError);
};
