import GS1BarcodeParser from 'gs1-barcode-parser-mod';
import {
  ATTR_barcodeType,
  ATTR_bestBeforeDate,
  ATTR_displayable,
  ATTR_GTIN,
  ATTR_isTUToBePickedAsWhole,
  ATTR_lotNo,
  ATTR_weightNet,
  ATTR_weightNetUOM,
  BARCODE_TYPE_GS1,
  toLocalDateString,
} from './common';
import { trl } from '../translations';

export const parseGS1CodeString = (string) => {
  try {
    const parsedBarcode = GS1BarcodeParser.parseBarcode(string);
    //console.log('parseQRCodeString_GS1', { string, parsedBarcode });
    //parsedBarcode.parsedCodeItems?.map((element) => console.log('', { element }));

    const result = {};

    // GTIN
    {
      const element = parsedBarcode.parsedCodeItems?.find((element) => element?.ai?.startsWith('01'));
      // console.log('parseQRCodeString_GS1', { gtinElement: element });
      if (element) {
        const gtin = '' + element.data;
        result[ATTR_GTIN] = gtin;
        result[ATTR_displayable] = gtin;
      }
    }

    // Weight
    {
      const element = parsedBarcode.parsedCodeItems?.find((element) => element?.ai?.startsWith('310'));
      if (element) {
        // IMPORTANT: convert it to number (i.e. multiply with 1) because we consider weights are numbers
        result[ATTR_weightNet] = 1 * element.data;
        result[ATTR_weightNetUOM] = 'kg';
        result[ATTR_isTUToBePickedAsWhole] = true; // todo clean up needed!!!
        result[ATTR_displayable] = '' + element.data + ' kg';
      }
    }

    // Best Before Date
    {
      const element = parsedBarcode.parsedCodeItems?.find((element) => element?.ai?.startsWith('15'));
      //console.log('parseQRCodeString_GS1', { bestBeforeDateElement: element });
      if (element) {
        result[ATTR_bestBeforeDate] = parseLocalDate(element.data);
      }
    }

    // Lot Number
    {
      const element = parsedBarcode.parsedCodeItems?.find((element) => element?.ai?.startsWith('10'));
      // console.log('parseQRCodeString_GS1', { lotNumberElement: element });
      if (element) {
        result[ATTR_lotNo] = '' + element.data;
      }
    }

    if (Object.keys(result).length <= 0) {
      return {
        error: trl('error.qrCode.invalid'),
        errorDetail: 'Invalid GS1 or the GS1 does not contain any of the required elements',
      };
    }

    result[ATTR_barcodeType] = BARCODE_TYPE_GS1;
    return { displayable: string, ...result };
  } catch (ex) {
    //console.log('parseQRCodeString_GS1: got error', { ex });
    return {
      error: trl('error.qrCode.invalid'),
      errorDetail: 'Failed parsing GS1 barcode: ' + ex,
    };
  }
};

const parseLocalDate = (string) => {
  if (!string) return null;
  const date = new Date(string);
  return toLocalDateString({ year: date.getFullYear(), month: date.getMonth() + 1, day: date.getDate() });
};
