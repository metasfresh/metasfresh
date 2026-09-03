import { checkPartialHUScannedCode } from './hu';
import { ScanCompleteness } from './scanCompleteness';

export const ATTR_barcodeType = 'barcodeType';
export const ATTR_isUnique = 'isUnique';
export const ATTR_productId = 'productId';
export const ATTR_productNo = 'productNo';
export const ATTR_GTIN = 'GTIN';
export const ATTR_weightNet = 'weightNet';
export const ATTR_weightNetUOM = 'weightNetUOM';
export const ATTR_bestBeforeDate = 'bestBeforeDate';
export const ATTR_productionDate = 'productionDate';
export const ATTR_lotNo = 'lotNo';
export const ATTR_displayable = 'displayable';
export const ATTR_isTUToBePickedAsWhole = 'isTUToBePickedAsWhole';
export const ATTR_HUUnitType = 'huUnitType';

export const BARCODE_TYPE_GS1 = 'GS1';
export const BARCODE_TYPE_EAN13 = 'EAN13';
export const BARCODE_TYPE_HU = 'HU'; // global HU QR code
export const BARCODE_TYPE_LMQ = 'LMQ'; // Leich+Mehl QR code
export const BARCODE_TYPE_CUSTOM = 'CUSTOM';

export const QRCODE_SEPARATOR = '#';

export const parseQRCodeType = (qrCodeString) => {
  if (!qrCodeString) {
    return null;
  }

  const idx = qrCodeString.indexOf(QRCODE_SEPARATOR);
  if (idx <= 0) {
    return null;
  }

  return qrCodeString.substring(0, idx);
};

export const toLocalDateString = ({ year, month, day }) => {
  const yearInt = Number(year);
  const monthInt = Number(month);
  const dayInt = Number(day);

  // Validate:
  if (dayInt < 1 || dayInt > 31) throw `Invalid day: ${dayInt}`;
  if (monthInt < 1 || monthInt > 12) throw `Invalid month: ${monthInt}`;
  if (yearInt < 2000 || yearInt > 2500) throw `Invalid year: ${yearInt}`;

  return `${yearInt}-${monthInt < 10 ? '0' + monthInt : monthInt}-${dayInt < 10 ? '0' + dayInt : dayInt}`;
};

export const isBarcodeProductNoMatching = ({
  expectedProductNo,
  expectedGS1ProductCodes,
  barcodeProductNo,
  barcodeType,
}) => {
  console.log('isBarcodeProductNoMatching', {
    expectedProductNo,
    expectedGS1ProductCodes,
    barcodeProductNo,
    barcodeType,
  });
  // if no barcode productNo provided, then there is nothing to validate
  if (!barcodeProductNo) {
    return true;
  }

  // if no actual expectations provided then consider product is matching
  if (!expectedProductNo && !expectedGS1ProductCodes) {
    return true;
  }

  // normalize productNo(s) before comparing
  const expectedProductNoStr = String(expectedProductNo);
  const barcodeProductNoStr = String(barcodeProductNo);

  if (barcodeType === BARCODE_TYPE_EAN13) {
    const isProductValueMatching = expectedProductNoStr.startsWith(barcodeProductNoStr);
    return isProductValueMatching || isEAN13MatchingGS1ProductCodes({ barcodeProductNo, expectedGS1ProductCodes });
  } else {
    return expectedProductNoStr === barcodeProductNoStr;
  }
};

//
// Partial-scan (streamed QR code) completeness classification.
//

// ScanCompleteness is defined in ./scanCompleteness (a leaf module both ./common and ./hu import
// from), so the ENUM itself is not part of any cycle. NOTE: ./common and ./hu DO still import each
// other — common.js → checkPartialHUScannedCode, hu.js → the ATTR_*/QRCODE_* constants — a real
// circular dependency. It stays safe ONLY because every cross-module symbol is read inside a
// FUNCTION BODY (this file's lazy PARTIAL_SCAN_CHECKS list; hu.js's in-function "HU#" prefix), never
// at module top level, so neither module reads a half-initialised sibling during load. Imported
// above for internal use and re-exported here so existing importers of './common' keep working.
// See ./scanCompleteness for the full contract, incl. the TERMINAL INVARIANT that gates COMPLETE_SCAN.
export { ScanCompleteness };

// Classify how complete an in-progress scanned code is (see ScanCompleteness).
// MUST NOT throw: any unexpected error degrades to NOT_APPLICABLE (i.e. default reader behaviour).
//
// The per-format classifier list is built lazily HERE (at call time), NOT at module scope, on
// purpose: common.js imports checkPartialHUScannedCode from ./hu while ./hu imports constants back
// from ./common. Referencing the imported check only at call time — never at module-load — keeps
// that dependency out of the module-init order, so it can't silently degrade to NOT_APPLICABLE
// after a future reorder. Extend the list as more streamed QR-code formats need partial-scan
// awareness; each returns NOT_APPLICABLE when the code is not its format, and must honour the
// TERMINAL INVARIANT (see ./scanCompleteness) before returning COMPLETE_SCAN.
export const checkPartialScannedCode = (scannedCode) => {
  try {
    if (!scannedCode) {
      return ScanCompleteness.NOT_APPLICABLE;
    }
    const partialScanChecks = [checkPartialHUScannedCode];
    for (const check of partialScanChecks) {
      const result = check(scannedCode);
      if (result && result !== ScanCompleteness.NOT_APPLICABLE) {
        return result;
      }
    }
    return ScanCompleteness.NOT_APPLICABLE;
  } catch (error) {
    console.debug('checkPartialScannedCode: unexpected error, treating as NOT_APPLICABLE', { scannedCode, error });
    return ScanCompleteness.NOT_APPLICABLE;
  }
};

const isEAN13MatchingGS1ProductCodes = ({ barcodeProductNo, expectedGS1ProductCodes }) => {
  if (!expectedGS1ProductCodes) return false; // IMPORTANT: at this point, we consider missing expectations as not matching

  // TODO handle:
  // expectedGS1ProductCodes.gtin
  // expectedGS1ProductCodes.ean13
  // NOTE: atm is not really needed because when we reach this point we expect only variable weight (prefix 28, 29) EAN13 product codes

  if (
    expectedGS1ProductCodes.ean13ProductCode &&
    String(barcodeProductNo) === String(expectedGS1ProductCodes.ean13ProductCode)
  ) {
    return true;
  }

  return false;
};
