import { trl } from '../translations';
import {
  ATTR_barcodeType,
  ATTR_displayable,
  ATTR_isTUToBePickedAsWhole,
  ATTR_isUnique,
  ATTR_productNo,
  ATTR_weightNet,
  ATTR_weightNetUOM,
  BARCODE_TYPE_EAN13,
} from './common';

export const parseEAN13CodeString = (barcode) => {
  try {
    // Check if the barcode length is exactly 13
    if (barcode.length !== 13) {
      return {
        error: trl('error.qrCode.invalid'),
        errorDetail: 'Invalid barcode length. It must be 13 digits.',
      };
    }

    //
    // Validate the check digit
    const checksum = parseInt(barcode.charAt(12)); // Checksum digit (C)
    const checksumExpected = calculateCheckDigit(barcode.substring(0, 12));
    if (checksumExpected !== checksum) {
      return {
        error: trl('error.qrCode.invalid'),
        errorDetail: 'Invalid checksum. Expected ' + checksumExpected + ', got ' + checksum + '.',
      };
    }

    const barcodePrefix = barcode.substring(0, 2);

    // 28 - Variable-Weight barcodes
    let result;
    if (barcodePrefix === '28') {
      result = parseEAN13CodeString_VariableWeight({ barcode });
    }
    // 29 - Internal Use / Variable measure
    else if (barcodePrefix === '29') {
      result = parseEAN13CodeString_InternalUseOrVariableMeasure({ barcode });
    }
    // Parse regular product codes for other prefixes
    else {
      result = parseEAN13CodeString_Standard({ barcode });
    }

    return {
      [ATTR_barcodeType]: BARCODE_TYPE_EAN13,
      [ATTR_isUnique]: false,
      [ATTR_isTUToBePickedAsWhole]: false,
      ...result,
    };
  } catch (ex) {
    return {
      error: trl('error.qrCode.invalid'),
      errorDetail: 'Failed parsing EAN13 barcode: ' + ex,
    };
  }
};

const calculateCheckDigit = (barcodeWithoutChecksum) => {
  let oddSum = 0;
  let evenSum = 0;

  // Loop through barcode without the checksum
  for (let i = 0; i < barcodeWithoutChecksum.length; i++) {
    const digit = parseInt(barcodeWithoutChecksum.charAt(i));

    if (i % 2 === 0) {
      // Odd positions (1st, 3rd, 5th, etc.)
      oddSum += digit;
    } else {
      // Even positions (2nd, 4th, 6th, etc.)
      evenSum += digit;
    }
  }

  // Multiply the even positions' sum by 3
  evenSum *= 3;

  // Total sum
  const totalSum = oddSum + evenSum;

  // Calculate check digit (nearest multiple of 10 - total sum % 10)
  const nearestTen = Math.ceil(totalSum / 10) * 10;
  return nearestTen - totalSum;
};

const parseEAN13CodeString_VariableWeight = ({ barcode }) => {
  const productNo = barcode.substring(2, 7); // 5 digits for article code (AAAAA)

  const weightStr = barcode.substring(7, 12); // 5 digits for weight (GGGGG)
  // Interpret the weight (assume it's in grams or kilograms)
  const weightInKg = parseFloat(weightStr) / 1000; // Convert weight from grams to kilograms

  return {
    [ATTR_displayable]: '' + weightInKg + ' kg',
    [ATTR_productNo]: productNo,
    [ATTR_weightNet]: weightInKg,
    [ATTR_weightNetUOM]: 'kg', // for EAN13 it will always be kg
  };
};

const parseEAN13CodeString_InternalUseOrVariableMeasure = ({ barcode }) => {
  // 4 digits for article code (IIII),
  // The digit at index 6 does not belong to the product code. It can be used for other purposes. For the time being it's ignored.
  // see https://www.gs1.org/docs/barcodes/SummaryOfGS1MOPrefixes20-29.pdf, page 81 (2.71 GS1 Switzerland)
  const productNo = barcode.substring(2, 6);

  const weightStr = barcode.substring(7, 12); // 5 digits for weight (GGGGG)
  // Interpret the weight (assume it's in grams or kilograms)
  const weightInKg = parseFloat(weightStr) / 1000; // Convert weight from grams to kilograms

  return {
    [ATTR_displayable]: '' + weightInKg + ' kg',
    [ATTR_productNo]: productNo,
    [ATTR_weightNet]: weightInKg,
    [ATTR_weightNetUOM]: 'kg', // for EAN13 it will always be kg
  };
};

const parseEAN13CodeString_Standard = ({ barcode }) => {
  const manufacturerAndProductCode = barcode.substring(3, 12);
  return {
    [ATTR_displayable]: manufacturerAndProductCode,
    [ATTR_productNo]: manufacturerAndProductCode,
  };
};
