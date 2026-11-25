import { parseGraiFromRawInput } from '../../../utils/grai';
import { parseCustomQRCode } from '../../../utils/qrCode/parseCustomQRCode';
import { BARCODE_TYPE_CUSTOM, isBarcodeProductNoMatching } from '../../../utils/qrCode/common';

/** Reads the scanned CU label of a picking line (CU quantity entry modes) */
export const SCAN_TYPE_GRAI = 'GRAI';
export const SCAN_TYPE_CU_LABEL = 'CU_LABEL';

const ERROR_noFormat = 'picking.cuLabel.noFormat';
const ERROR_formatMismatch = 'picking.cuLabel.formatMismatch';
const ERROR_noPackId = 'picking.cuLabel.noPackId';

const PART_TYPE_IGNORE = 'IGNORE';
const ERROR_differentProduct = 'activities.picking.qrcode.differentProduct';

export const routeScannedCode = (string) => (parseGraiFromRawInput(string) ? SCAN_TYPE_GRAI : SCAN_TYPE_CU_LABEL);

/** @returns true when the parts in front of the zone read the label */
const isPrefixReadable = ({ string, format, zoneStart }) => {
  // separators are ignored: the scanned code is trimmed, a trailing one is lost
  const partsBefore = (format.parts ?? []).filter(
    (part) => part.startPosition < zoneStart && part.type !== PART_TYPE_IGNORE
  );
  if (partsBefore.length === 0) {
    return false;
  }
  const parsed = parseCustomQRCode({ string, format: { ...format, parts: partsBefore } });
  return !!parsed && !parsed.error;
};

const isEndingBeforePackId = ({ string, format }) =>
  format.packIdZoneStart != null &&
  format.packIdZoneEnd != null &&
  string.length < format.packIdZoneEnd &&
  isPrefixReadable({ string, format, zoneStart: format.packIdZoneStart });

/** Reads a CU label with the line's effective format only (no GS1, EAN13 or global QR code) */
export const readCULabel = ({ string, format, isPackIdRequired = false }) => {
  if (!format) {
    return { errorKey: ERROR_noFormat };
  }

  const parsed = parseCustomQRCode({ string, format });
  if (!parsed || parsed.error) {
    if (isPackIdRequired && isEndingBeforePackId({ string, format })) {
      return { errorKey: ERROR_noPackId };
    }
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
