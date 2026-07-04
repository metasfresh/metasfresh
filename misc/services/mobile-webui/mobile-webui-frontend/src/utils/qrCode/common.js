import { checkPartialHUScannedCode } from './hu';

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

// How complete an in-progress scanned code is. Lets a keyboard/wedge reader keep waiting while
// a long multi-chunk QR code is still arriving, instead of flushing a fragment.
//
// The reader acts on each value as: COMPLETE_SCAN => force-complete now (no idle wait);
// PARTIAL_SCAN => hold back the idle flush; NOT_APPLICABLE => normal timing (Enter/idle as before).
//
// TERMINAL INVARIANT (per-format checks MUST honour it): return COMPLETE_SCAN ONLY when the code is
// unambiguously TERMINAL — there is NO way a continuation of the current string could produce a
// (different) valid scanned code. Because the reader force-completes on COMPLETE_SCAN, a
// non-terminal "looks complete now, but a longer string is also valid" format (e.g. a bare numeric
// m_hu_id where both "123" and "1234" parse) must NEVER return COMPLETE_SCAN — it uses
// PARTIAL_SCAN / NOT_APPLICABLE and relies on the Enter terminator / idle-flush instead.
export const ScanCompleteness = Object.freeze({
  NOT_APPLICABLE: 'NOT_APPLICABLE', // not a recognised streamed QR code => keep default behaviour
  PARTIAL_SCAN: 'PARTIAL_SCAN', //     a recognised QR code that is still arriving => keep waiting
  COMPLETE_SCAN: 'COMPLETE_SCAN', //   a recognised code that is complete AND terminal => flush now
});

// Per-format partial-scan classifiers. Extend as more streamed QR-code formats need
// partial-scan awareness. Each returns NOT_APPLICABLE when the code is not its format, and must
// honour the TERMINAL INVARIANT above before returning COMPLETE_SCAN.
const PARTIAL_SCAN_CHECKS = [checkPartialHUScannedCode];

// Classify how complete an in-progress scanned code is (see ScanCompleteness).
// MUST NOT throw: any unexpected error degrades to NOT_APPLICABLE (i.e. default reader behaviour).
export const checkPartialScannedCode = (scannedCode) => {
  try {
    if (!scannedCode) {
      return ScanCompleteness.NOT_APPLICABLE;
    }
    for (const check of PARTIAL_SCAN_CHECKS) {
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
