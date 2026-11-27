import { parseGraiFromRawInput } from '../../../utils/grai';
import { parseCustomQRCode } from '../../../utils/qrCode/parseCustomQRCode';
import { BARCODE_TYPE_CUSTOM, isBarcodeProductNoMatching } from '../../../utils/qrCode/common';

/** Reads the scanned CU label of a picking line (CU quantity entry modes) */
export const SCAN_TYPE_GRAI = 'GRAI';
export const SCAN_TYPE_CU_LABEL = 'CU_LABEL';

const ERROR_noFormat = 'picking.cuLabel.noFormat';
const ERROR_formatMismatch = 'picking.cuLabel.formatMismatch';
const ERROR_noPackId = 'picking.cuLabel.noPackId';

const ERROR_differentProduct = 'activities.picking.qrcode.differentProduct';

export const routeScannedCode = (string) => (parseGraiFromRawInput(string) ? SCAN_TYPE_GRAI : SCAN_TYPE_CU_LABEL);

/** Reads a CU label with the line's effective format only (no GS1, EAN13 or global QR code) */
export const readCULabel = ({ string, format, isPackIdRequired = false }) => {
  if (!format) {
    return { errorKey: ERROR_noFormat };
  }

  const parsed = parseCustomQRCode({ string, format });
  if (!parsed || parsed.error) {
    return { errorKey: ERROR_formatMismatch, errorDetails: parsed?.error };
  }
  if (isPackIdRequired && !parsed.packId) {
    return { errorKey: ERROR_noPackId };
  }

  return {
    productNo: parsed.productNo,
    lotNo: parsed.lotNo,
    bestBeforeDate: parsed.bestBeforeDate,
    packId: parsed.packId,
  };
};

/** {@link readCULabel} plus the product check against the line's product */
export const resolveCULabel = ({
  string,
  format,
  expectedProductNo,
  expectedGS1ProductCodes,
  isPackIdRequired = false,
}) => {
  const label = readCULabel({ string, format, isPackIdRequired });
  if (label.errorKey) {
    return label;
  }

  // like the backend: a label without a product number does not fit the line
  if (!label.productNo) {
    return { errorKey: ERROR_formatMismatch };
  }

  if (
    !isBarcodeProductNoMatching({
      expectedProductNo,
      expectedGS1ProductCodes,
      barcodeProductNo: label.productNo,
      barcodeType: BARCODE_TYPE_CUSTOM,
    })
  ) {
    return { errorKey: ERROR_differentProduct };
  }

  return { ...label, cuLabel: string };
};

export const selectCULabelFormat = (formats, id) => {
  if (id == null) {
    return undefined;
  }
  return (formats ?? []).find((format) => format?.id === id);
};
