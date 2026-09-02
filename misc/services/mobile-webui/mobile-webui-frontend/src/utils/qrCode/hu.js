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
  ATTR_HUUnitType,
  ATTR_isTUToBePickedAsWhole,
  ATTR_isUnique,
  ATTR_lotNo,
  ATTR_productId,
  ATTR_productNo,
  ATTR_weightNet,
  ATTR_weightNetUOM,
  BARCODE_TYPE_HU,
  BARCODE_TYPE_LMQ,
  parseQRCodeType,
  QRCODE_SEPARATOR,
  toLocalDateString,
} from './common';
import { ScanCompleteness } from './scanCompleteness';
import { trl } from '../translations';
import { HU_ATTRIBUTE_BestBeforeDate, HU_ATTRIBUTE_LotNo, HU_ATTRIBUTE_WeightNet } from '../../constants/HUAttributes';
import { parseGS1CodeString } from './gs1';
import { parseEAN13CodeString } from './ean13';
import { parseCustomQRCode } from './parseCustomQRCode';
import { ContextualError } from '../ContextualError';
import { errorToString } from '../toast';

export const QRCODE_TYPE_HU = 'HU';
export const QRCODE_TYPE_LEICH_UND_MEHL = 'LMQ';

export const isHUQRCode = (string) => {
  return parseQRCodeType(string) === QRCODE_TYPE_HU;
};

export const toQRCodeDisplayableNoFail = (qrCode) => {
  try {
    return toQRCodeDisplayable(qrCode);
  } catch (error) {
    console.debug('toQRCodeDisplayableNoFail: got error', error);
    return `${qrCode}`;
  }
};

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
      displayable: toQRCodeDisplayableNoFail(code),
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
export const parseQRCodeString = (param1_string, param2_returnFalseOnError) => {
  let string;
  let returnFalseOnError;
  let customQRCodeFormats = [];
  let checkOnlyPreciseFormats = false;
  if (typeof param1_string === 'object' && param1_string !== null) {
    const obj = param1_string;
    string = obj.string;
    returnFalseOnError = obj.returnFalseOnError ?? false;
    customQRCodeFormats = obj.customQRCodeFormats ?? [];
    checkOnlyPreciseFormats = obj.checkOnlyPreciseFormats ?? false;
  } else {
    string = param1_string;
    returnFalseOnError = param2_returnFalseOnError;
  }
  // console.trace('parseQRCodeString', { string, returnFalseOnError, customQRCodeFormats });

  const allResults = {};
  let result = { error: 'initial' };

  try {
    if (customQRCodeFormats?.length > 0) {
      for (const format of customQRCodeFormats) {
        result = parseCustomQRCode({ string, format });
        allResults['custom - ' + format.name] = result;
        if (!result?.error) {
          break;
        }
      }
    }

    if (result?.error && !checkOnlyPreciseFormats) {
      // NOTE: GS1 is considered not a precise format because it might match a simple M_HU_ID/Value code as a LotNo GS1.
      result = parseGS1CodeString(string);
      allResults['gs1'] = result;
    }

    if (result?.error) {
      result = parseEAN13CodeString(string);
      allResults['ean13'] = result;
    }

    if (result?.error) {
      result = parseQRCodeString_GlobalQRCode(string);
      allResults['globalQRCode'] = result;
    }

    // console.log('allResults: ' + JSON.stringify(allResults, null, 2));
  } catch (error) {
    console.trace('Got error while parsing QR code string:', string, { error });
    result.error = trl('error.qrCode.invalid');
    delete result.errorDetail;
    result.errorCause = errorToString(error);
  }

  //
  if (result && !result.error) {
    return { ...result, code: string };
  } else if (returnFalseOnError) {
    return false;
  } else {
    const errorMsg = result?.error ? result.error : trl('error.qrCode.invalid');
    const errorContext = { ...result, string, allResults, errorCause: result?.errorCause };
    console.trace('parseQRCodeString:', errorMsg, errorContext);
    throw new ContextualError(errorMsg, errorContext);
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
  result[ATTR_isUnique] = true;
  result[ATTR_HUUnitType] = payload?.packingInfo?.huUnitType;

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
  result[ATTR_isUnique] = false;

  const parts = payload.split('#');
  if (parts.length >= 1 && parts[0] != null) {
    // IMPORTANT: convert it to number (i.e. multiply with 1) because we consider weights are numbers
    result[ATTR_weightNet] = 1 * parts[0];
    result[ATTR_weightNetUOM] = 'kg'; // for LeichMehl it will always be kg
    result[ATTR_displayable] = '' + parts[0];
    result[ATTR_isTUToBePickedAsWhole] = true; // todo clean up needed!!!
  }
  if (parts.length >= 2) {
    result[ATTR_bestBeforeDate] = parseLMQBestBeforeDate(parts[1]);
  }
  if (parts.length >= 3) {
    result[ATTR_lotNo] = parts[2];
  }
  if (parts.length >= 4) {
    result[ATTR_productNo] = parts[3];
  }

  return result;
};

const parseLMQBestBeforeDate = (string) => {
  const parts = LMQ_BEST_BEFORE_DATE_FORMAT.exec(string);
  if (!parts) throw new Error(`Invalid Best Before date: ${string}`);

  const [, day, month, year] = parts;
  return toLocalDateString({ year, month, day });
};

export const isKnownQRCodeFormat = (qrCodeString) => {
  const returnFalseOnError = true;
  return !!parseQRCodeString(qrCodeString, returnFalseOnError);
};

// "TYPE#VERSION#<json>" => the <json> payload, or null while both separators haven't arrived yet.
const extractGlobalQRCodeJsonPayload = (scannedCode) => {
  const firstSeparatorIdx = scannedCode.indexOf(QRCODE_SEPARATOR);
  if (firstSeparatorIdx <= 0) {
    return null;
  }
  const secondSeparatorIdx = scannedCode.indexOf(QRCODE_SEPARATOR, firstSeparatorIdx + 1);
  if (secondSeparatorIdx < 0) {
    return null;
  }
  return scannedCode.substring(secondSeparatorIdx + 1);
};

// Classify an in-progress scanned code w.r.t. the HU global QR code format (see ScanCompleteness):
//   NOT_APPLICABLE - it is not (nor becoming) an HU global QR code
//   PARTIAL_SCAN   - it looks like an HU QR code but the JSON payload has not fully arrived yet
//   COMPLETE_SCAN  - it is an HU QR code whose JSON payload is complete (closed) AND terminal
// Completeness is decided by JSON.parse of the payload after "HU#<version>#": the real JSON parser
// handles nesting, escaping and literal braces inside string values, and throws while the payload
// is still arriving. MUST NOT throw (hard requirement) — any error stays PARTIAL_SCAN for an
// identified HU code (the reader keeps waiting, bounded by its abandon timer).
//
// TERMINAL INVARIANT (see ScanCompleteness in ./common): a complete HU#<v>#{json} IS terminal —
// appending any character makes it invalid JSON, so returning COMPLETE_SCAN (force-complete) is
// safe here and correctly SEPARATES back-to-back scans (e.g. the "...}]}LMQ#1#..." merges seen in
// prod when a second code is delivered right after the HU QR) instead of merging them.
// NOTE for future per-format checks: return COMPLETE_SCAN ONLY for a TERMINAL code. A non-terminal
// format (e.g. a bare numeric m_hu_id where both "123" and "1234" are valid) must NEVER return
// COMPLETE_SCAN — use PARTIAL_SCAN / NOT_APPLICABLE and rely on the Enter terminator / idle-flush.
export const checkPartialHUScannedCode = (scannedCode) => {
  try {
    if (!scannedCode || typeof scannedCode !== 'string') {
      return ScanCompleteness.NOT_APPLICABLE;
    }
    // Applicable if it already looks like an HU QR code (HU#...) OR is still receiving the leading
    // "HU#" prefix itself (e.g. a chunk gap landed at 'H' / 'HU'). Compute the "HU#" prefix HERE,
    // inside the function — NOT at module top level: common.js and hu.js import each other (a real
    // cycle), so reading the imported QRCODE_SEPARATOR at hu.js *load* time can observe an undefined
    // value from a partially-initialised common.js, depending on which module loads first. By the
    // time this function runs, common.js is fully initialised, so the read is always correct.
    const huQrCodePrefix = QRCODE_TYPE_HU + QRCODE_SEPARATOR; // "HU#"
    if (!isHUQRCode(scannedCode) && !huQrCodePrefix.startsWith(scannedCode)) {
      return ScanCompleteness.NOT_APPLICABLE;
    }
    const jsonPayload = extractGlobalQRCodeJsonPayload(scannedCode);
    if (jsonPayload) {
      JSON.parse(jsonPayload); // throws while still arriving => caught below => PARTIAL_SCAN
      return ScanCompleteness.COMPLETE_SCAN;
    }
    return ScanCompleteness.PARTIAL_SCAN;
  } catch (error) {
    // The normal "payload still arriving" case (JSON.parse throws) plus any unexpected error:
    // both are safe as PARTIAL_SCAN for an identified HU code.
    return ScanCompleteness.PARTIAL_SCAN;
  }
};
