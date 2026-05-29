import GS1BarcodeParser from 'gs1-barcode-parser-mod';

/**
 * Parse GRAI from a GS1 barcode string containing AI 8003.
 *
 * Per GS1 General Specifications, AI 8003 (GRAI) has a fixed-length asset
 * reference of 14 digits after the AI: 1 padding digit + 13-digit asset
 * reference (GS1 Company Prefix + Item Reference + check digit at the end).
 * An optional alphanumeric serial (0..16 chars) may follow.
 *
 * Returned in the GS1 EPCIS "Pure Identity" URI canonical form
 * `companyPrefix(7).assetType(5).serial` — the GS1 check digit at GTIN-13
 * position 13 is dropped from the canonical to match `urn:epc:id:grai:`.
 * Kept in sync with the backend parser in
 * `de.metas.handlingunits.grai.GRAI.parseGS1AI8003`.
 *
 * @param {string} barcodeString - Raw barcode string
 * @returns {string|null} Dot-separated GRAI or null
 */
export const parseGraiFromGs1Barcode = (barcodeString) => {
  try {
    const parsedBarcode = GS1BarcodeParser.parseBarcode(barcodeString);
    const graiElement = parsedBarcode.parsedCodeItems?.find((element) => element?.ai === '8003');

    if (!graiElement) return null;

    const graiData = '' + graiElement.data;
    // Minimum meaningful length: 14 (asset reference) + 1 (at least one serial char) = 15.
    if (graiData.length < 15) return null;

    // Position 0: padding digit (skip)
    // Positions 1-12: 12-digit asset reference base (company prefix + asset type, no check digit)
    // Position 13: GS1 check digit (skip — not part of the EPCIS Pure Identity URI canonical)
    // Position 14+: serial reference
    const base = graiData.substring(1, 13);
    const serial = graiData.substring(14);

    // For GRAI, the company prefix is typically 7 digits (GS1 standard for most European prefixes)
    // We use a heuristic: GS1 prefixes starting with 76 (Switzerland) are 7 digits
    const companyPrefix = base.substring(0, 7);
    const assetType = base.substring(7);

    if (!serial) return null;

    return `${companyPrefix}.${assetType}.${serial}`;
  } catch (e) {
    // Not a valid GS1 barcode
    return null;
  }
};

/**
 * Try to parse a GRAI from raw input. First tries GS1 barcode parsing,
 * then checks if it's already in dot-separated format.
 *
 * @param {string} inputString - Raw input (barcode or manual entry)
 * @returns {string|null} Dot-separated GRAI or null
 */
export const parseGraiFromRawInput = (inputString) => {
  if (!inputString || typeof inputString !== 'string') return null;

  const trimmed = inputString.trim();

  // Try GS1 barcode first
  const gs1Result = parseGraiFromGs1Barcode(trimmed);
  if (gs1Result) return gs1Result;

  // Check if already in dot-separated format
  if (isValidGrai(trimmed)) return trimmed;

  return null;
};

/**
 * Validate a GRAI string in dot-separated format.
 * Expected format: {companyPrefix}.{assetType}.{serial}
 *
 * @param {string} graiString
 * @returns {boolean}
 */
/**
 * Parse a raw input string that may contain one or more GRAIs
 * separated by newlines or tabs (e.g., RFID scanner batch output).
 *
 * @param {string} rawInput
 * @returns {string[]} array of canonical GRAI strings
 */
export const parseGraiArrayFromRawInput = (rawInput) => {
  if (!rawInput || typeof rawInput !== 'string') return [];

  return rawInput
    .split(/[\n\r\t]+/)
    .map((s) => s.trim())
    .filter(Boolean)
    .map(parseGraiFromRawInput)
    .filter(Boolean);
};

export const isValidGrai = (graiString) => {
  if (!graiString || typeof graiString !== 'string') return false;

  const parts = graiString.split('.');
  if (parts.length !== 3) return false;

  const [companyPrefix, assetType, serial] = parts;
  return companyPrefix.length > 0 && assetType.length > 0 && serial.length > 0;
};
