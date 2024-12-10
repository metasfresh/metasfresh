import { trl } from '../translations';
import {
  ATTR_displayable,
  ATTR_isTUToBePickedAsWhole,
  ATTR_productNo,
  ATTR_weightNet,
  ATTR_weightNetUOM,
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

    // Ensure the first two digits are "28" as a prefix
    if (barcode.substring(0, 2) !== '28') {
      return {
        error: trl('error.qrCode.invalid'),
        errorDetail: "Invalid barcode prefix. Expected '28'.",
      };
    }

    // Extract components from the barcode
    const productNo = barcode.substring(2, 7); // 5 digits for article code (AAAAA)
    const weightStr = barcode.substring(7, 12); // 5 digits for weight (GGGGG)
    const checksum = parseInt(barcode.charAt(12)); // Checksum digit (C)

    // Validate the check digit
    const checksumExpected = calculateCheckDigit(barcode.substring(0, 12));
    if (checksumExpected !== checksum) {
      return {
        error: trl('error.qrCode.invalid'),
        errorDetail: 'Invalid checksum. Barcode may be incorrect.',
      };
    }

    // Interpret the weight (assume it's in grams or kilograms)
    const weightInKg = parseFloat(weightStr) / 1000; // Convert weight from grams to kilograms

    const result = {};
    result[ATTR_displayable] = '' + weightInKg + ' kg';
    result[ATTR_productNo] = productNo;
    result[ATTR_weightNet] = weightInKg;
    result[ATTR_weightNetUOM] = 'kg'; // for LeichMehl it will always be kg
    result[ATTR_isTUToBePickedAsWhole] = true; // todo clean up needed!!!
    return result;
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
