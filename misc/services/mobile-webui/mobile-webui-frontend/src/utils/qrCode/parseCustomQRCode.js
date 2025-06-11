import {
  ATTR_barcodeType,
  ATTR_bestBeforeDate,
  ATTR_displayable,
  ATTR_lotNo,
  ATTR_productNo,
  ATTR_weightNet,
  ATTR_weightNetUOM,
  BARCODE_TYPE_CUSTOM,
} from './common';
import { DateTime } from 'luxon';

const DEFAULT_weightDecimalPoint = 3;
const DEFAULT_dateFormat = 'yyMMdd';

export const parseCustomQRCode = ({ string, format }) => {
  if (!format) {
    return {
      error: `No custom format provided`,
    };
  }
  if (!format.parts || format.parts.length === 0) {
    return {
      error: `Custom format '${format?.name}' has no parts configured`,
      format,
    };
  }

  try {
    let result = {
      [ATTR_barcodeType]: BARCODE_TYPE_CUSTOM,
    };

    for (const formatPart of format.parts) {
      const partResult = parseCustomQRCodePart({ string, formatPart });
      if (partResult?.error) {
        return {
          error: partResult.error,
          formatPart,
          format,
          string,
        };
      }

      result = { ...result, ...partResult };
    }

    result[ATTR_displayable] = computeDisplayable({ result, string });

    return result;
  } catch (error) {
    return {
      error: error?.message,
      exception: error,
      format,
    };
  }
};

const parseCustomQRCodePart = ({ string, formatPart }) => {
  const valueStr = extractAsString({
    string,
    startPosition: formatPart.startPosition,
    endPosition: formatPart.endPosition,
  });
  if (valueStr === null) {
    return { error: `Cannot extract part ${this}` };
  }
  // console.log('parseCustomQRCodePart', { formatPart, valueStr, string });

  switch (formatPart.type) {
    case 'IGNORE':
      break;
    case 'PRODUCT_CODE':
      return {
        [ATTR_productNo]: valueStr,
      };
    case 'WEIGHT_KG':
      return {
        [ATTR_weightNet]: parseNumber({
          string: valueStr,
          decimalPoint: formatPart.decimalPoint ?? DEFAULT_weightDecimalPoint,
        }),
        [ATTR_weightNetUOM]: 'kg',
      };
    case 'LOT':
      return {
        [ATTR_lotNo]: trimLeadingZeros(valueStr),
      };
    case 'BEST_BEFORE_DATE':
      return {
        [ATTR_bestBeforeDate]: parseLocalDate({
          string: valueStr,
          dateFormat: formatPart.dateFormat ?? DEFAULT_dateFormat,
        }),
      };
    default:
      return {
        error: `Unknown type: ${formatPart.type}`,
      };
  }
};

const extractAsString = ({ string, startPosition, endPosition }) => {
  const startIndex = startPosition - 1;
  const endIndex = endPosition;

  if (endIndex > string.length) {
    return null;
  }

  return string.substring(startIndex, endIndex);
};

const trimLeadingZeros = (string) => {
  if (!string) return string;
  return string.replace(/^0+/, '');
};

// visible for testing
export const parseNumber = ({ string, decimalPoint = 0 }) => {
  if (typeof string !== 'string' || isNaN(Number(string))) {
    throw new Error(`Invalid number format: "${string}"`);
  }

  if (typeof decimalPoint !== 'number' || decimalPoint < 0 || !Number.isInteger(decimalPoint)) {
    throw new Error(`Invalid decimalPoint: "${decimalPoint}"`);
  }

  const number = Number(string); // Parse the string to a number (removing leading zeros)

  if (decimalPoint === 0) {
    return number; // No decimal point adjustment needed
  }

  // Divide the number by 10^decimalPoint to apply the decimal point position
  const factor = Math.pow(10, decimalPoint);
  return number / factor;
};

// visible for testing
export const parseLocalDate = ({ string, dateFormat }) => {
  if (typeof string !== 'string' || typeof dateFormat !== 'string') {
    throw new Error('Both string and format must be strings');
  }

  const date = DateTime.fromFormat(string, dateFormat);
  if (!date) {
    throw Error('Cannot parse date: ' + string + ' with format: ' + dateFormat + '');
  }
  if (!date.isValid) {
    throw Error(
      'Cannot parse date: ' +
        string +
        ' with format ' +
        dateFormat +
        ' because it is not valid ' +
        date.invalidReason +
        ''
    );
  }

  return date.toFormat('yyyy-MM-dd');
};

const computeDisplayable = ({ result, string }) => {
  if (result[ATTR_weightNet] != null && result[ATTR_weightNetUOM] != null) {
    return `${result[ATTR_weightNet]} ${result[ATTR_weightNetUOM]}`;
  } else if (result[ATTR_productNo]) {
    return result[ATTR_productNo];
  } else if (result[ATTR_lotNo]) {
    return result[ATTR_lotNo];
  } else if (result[ATTR_bestBeforeDate]) {
    return result[ATTR_bestBeforeDate];
  } else {
    return string;
  }
};
