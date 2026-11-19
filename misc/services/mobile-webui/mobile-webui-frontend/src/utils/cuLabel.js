import { parseGraiFromRawInput } from './grai';
import { parseCustomQRCode } from './qrCode/parseCustomQRCode';
import { BARCODE_TYPE_CUSTOM, isBarcodeProductNoMatching } from './qrCode/common';

export const SCAN_TYPE_GRAI = 'GRAI';
export const SCAN_TYPE_CU_LABEL = 'CU_LABEL';

const ERROR_noFormat = 'picking.cuLabel.noFormat';
const ERROR_formatMismatch = 'picking.cuLabel.formatMismatch';
const ERROR_differentProduct = 'activities.picking.qrcode.differentProduct';

/**
 * On a line with a CU quantity entry mode, a scanned string is either a GRAI (opens a box) or a CU label.
 *
 * @returns 'GRAI' when the string is a GRAI, else 'CU_LABEL'
 */
export const routeScannedCode = (string) => (parseGraiFromRawInput(string) ? SCAN_TYPE_GRAI : SCAN_TYPE_CU_LABEL);

/**
 * Reads a CU label with the line's effective format only (no GS1, EAN13 or global QR code).
 *
 * @returns {{productNo, lotNo, bestBeforeDate, packId}} or {errorKey} (translation key)
 */
export const readCULabel = ({ string, format }) => {
  if (!format) {
    return { errorKey: ERROR_noFormat };
  }

  const parsed = parseCustomQRCode({ string, format });
  if (!parsed || parsed.error) {
    return { errorKey: ERROR_formatMismatch };
  }

  return {
    productNo: parsed.productNo,
    lotNo: parsed.lotNo,
    bestBeforeDate: parsed.bestBeforeDate,
    packId: parsed.packId,
  };
};

/**
 * {@link readCULabel} plus the product check against the line's product.
 *
 * @returns {{productNo, lotNo, bestBeforeDate, packId, cuLabel}} (`cuLabel` = the raw label) or {errorKey}
 */
export const resolveCULabel = ({ string, format, expectedProductNo, expectedGS1ProductCodes }) => {
  const label = readCULabel({ string, format });
  if (label.errorKey) {
    return label;
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

/**
 * @param formats the activity's `customQRCodeFormats` (each carries `id` = `JsonScannableCodeFormat.id`)
 * @param id the line's `cuLabelFormatId` (null / undefined = no effective format)
 * @returns the format with that id, or undefined
 */
export const selectCULabelFormat = (formats, id) => {
  if (id == null) {
    return undefined;
  }
  return (formats ?? []).find((format) => format?.id === id);
};
