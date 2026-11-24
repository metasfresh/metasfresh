import { parseGraiFromRawInput } from '../../../utils/grai';
import { parseCustomQRCode } from '../../../utils/qrCode/parseCustomQRCode';
import { BARCODE_TYPE_CUSTOM, isBarcodeProductNoMatching } from '../../../utils/qrCode/common';

/**
 * Reads the scanned CU label of a picking line (CU quantity entry modes). Java counterpart: `PickingCULabelService`
 * (backend/de.metas.handlingunits.base/.../picking/job/service/PickingCULabelService.java) does the same checks again on the server
 * when the pick is booked; change them together. The format comes from the server with its pack ID zone
 * (`JsonScannableCodeFormat`, `ScannableCodeFormat.getPackIdZone()`).
 */
export const SCAN_TYPE_GRAI = 'GRAI';
export const SCAN_TYPE_CU_LABEL = 'CU_LABEL';

const ERROR_noFormat = 'picking.cuLabel.noFormat';
const ERROR_formatMismatch = 'picking.cuLabel.formatMismatch';
const ERROR_noPackId = 'picking.cuLabel.noPackId';

const PART_TYPE_IGNORE = 'IGNORE';
const ERROR_differentProduct = 'activities.picking.qrcode.differentProduct';

/**
 * On a line with a CU quantity entry mode, a scanned string is either a GRAI (opens a box) or a CU label.
 *
 * @returns 'GRAI' when the string is a GRAI, else 'CU_LABEL'
 */
export const routeScannedCode = (string) => (parseGraiFromRawInput(string) ? SCAN_TYPE_GRAI : SCAN_TYPE_CU_LABEL);

/** @returns true when the parts in front of the zone read the label: it was scanned right, it just has no pack ID */
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

/**
 * @returns true when the label is too short to hold the pack ID zone while every part in front of the zone reads
 * (a label without counter). The backend decides where the zone is (`packIdZoneStart` / `packIdZoneEnd` of the format,
 * `ScannableCodeFormat.getPackIdZone()`); this only compares lengths.
 */
const isEndingBeforePackId = ({ string, format }) =>
  format.packIdZoneStart != null &&
  format.packIdZoneEnd != null &&
  string.length < format.packIdZoneEnd &&
  isPrefixReadable({ string, format, zoneStart: format.packIdZoneStart });

/**
 * Reads a CU label with the line's effective format only (no GS1, EAN13 or global QR code).
 *
 * @param isPackIdRequired Scan each CU: the label must carry a pack ID, else `picking.cuLabel.noPackId` (also for a label
 * that ends before the format's pack ID zone)
 * @returns {{productNo, lotNo, bestBeforeDate, packId}} or {errorKey (translation key), errorDetails (the reason, when known)}
 */
export const readCULabel = ({ string, format, isPackIdRequired = false }) => {
  if (!format) {
    return { errorKey: ERROR_noFormat };
  }

  const parsed = parseCustomQRCode({ string, format });
  if (!parsed || parsed.error) {
    if (isPackIdRequired && isEndingBeforePackId({ string, format })) {
      return { errorKey: ERROR_noPackId };
    }
    // the reason why the label does not fit the format (which part, what was expected) is shown with the message
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

/**
 * {@link readCULabel} plus the product check against the line's product.
 *
 * @returns {{productNo, lotNo, bestBeforeDate, packId, cuLabel}} (`cuLabel` = the raw label) or {errorKey}
 */
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
