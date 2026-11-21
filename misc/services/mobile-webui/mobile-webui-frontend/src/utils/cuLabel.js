import { parseGraiFromRawInput } from './grai';
import { parseCustomQRCode } from './qrCode/parseCustomQRCode';
import { BARCODE_TYPE_CUSTOM, isBarcodeProductNoMatching } from './qrCode/common';

export const SCAN_TYPE_GRAI = 'GRAI';
export const SCAN_TYPE_CU_LABEL = 'CU_LABEL';

const ERROR_noFormat = 'picking.cuLabel.noFormat';
const ERROR_formatMismatch = 'picking.cuLabel.formatMismatch';
const ERROR_noPackId = 'picking.cuLabel.noPackId';

const PART_TYPE_PACK_ID = 'PACK_ID';
const PART_TYPE_CONSTANT = 'CONSTANT';
const PART_TYPE_IGNORE = 'IGNORE';
// the constant marker in front of the pack ID, used only when the format has no PACK_ID part
const FALLBACK_PACK_ID_MARKER = '92';
const ERROR_differentProduct = 'activities.picking.qrcode.differentProduct';

/**
 * On a line with a CU quantity entry mode, a scanned string is either a GRAI (opens a box) or a CU label.
 *
 * @returns 'GRAI' when the string is a GRAI, else 'CU_LABEL'
 */
export const routeScannedCode = (string) => (parseGraiFromRawInput(string) ? SCAN_TYPE_GRAI : SCAN_TYPE_CU_LABEL);

const byStartPosition = (a, b) => a.startPosition - b.startPosition;

/**
 * The pack ID zone of the format: it starts at the constant marker directly in front of the PACK_ID part (or at that
 * part); a format without a PACK_ID part falls back to the constant "92" marker; with neither: undefined.
 * Same rule as the backend `PickingCULabelService.findPackIdZone`.
 *
 * @returns {{zoneStart, zoneEnd}|undefined}
 */
const findPackIdZone = (parts) => {
  const packIdPart = [...parts].filter((part) => part.type === PART_TYPE_PACK_ID).sort(byStartPosition)[0];
  return packIdPart ? zoneBeforePackIdPart(parts, packIdPart) : zoneOfFallbackMarker(parts);
};

const zoneBeforePackIdPart = (parts, packIdPart) => {
  const preceding = parts
    .filter((part) => part.startPosition < packIdPart.startPosition)
    .sort(byStartPosition)
    .pop();
  return {
    zoneStart: preceding?.type === PART_TYPE_CONSTANT ? preceding.startPosition : packIdPart.startPosition,
    zoneEnd: packIdPart.startPosition,
  };
};

const zoneOfFallbackMarker = (parts) => {
  const marker = [...parts]
    .filter((part) => part.type === PART_TYPE_CONSTANT && part.constantValue === FALLBACK_PACK_ID_MARKER)
    .sort(byStartPosition)[0];
  if (!marker) {
    return undefined;
  }
  const zoneStart = marker.startPosition;
  const zoneEnd = Math.min(
    ...parts.map((part) => part.startPosition).filter((start) => start > zoneStart),
    zoneStart + FALLBACK_PACK_ID_MARKER.length
  );
  return { zoneStart, zoneEnd };
};

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
 * (L0: a label without counter). Same rule as the backend `PickingCULabelService`.
 */
const isEndingBeforePackId = ({ string, format }) => {
  const zone = findPackIdZone(format.parts ?? []);
  return !!zone && string.length < zone.zoneEnd && isPrefixReadable({ string, format, zoneStart: zone.zoneStart });
};

/**
 * Reads a CU label with the line's effective format only (no GS1, EAN13 or global QR code).
 *
 * @param isPackIdRequired Scan each CU: the label must carry a pack ID, else `picking.cuLabel.noPackId` (also for a label
 * that ends before the "92" marker / the pack ID part)
 * @returns {{productNo, lotNo, bestBeforeDate, packId}} or {errorKey} (translation key)
 */
export const readCULabel = ({ string, format, isPackIdRequired = false }) => {
  if (!format) {
    return { errorKey: ERROR_noFormat };
  }

  const parsed = parseCustomQRCode({ string, format });
  if (!parsed || parsed.error) {
    return {
      errorKey: isPackIdRequired && isEndingBeforePackId({ string, format }) ? ERROR_noPackId : ERROR_formatMismatch,
    };
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
