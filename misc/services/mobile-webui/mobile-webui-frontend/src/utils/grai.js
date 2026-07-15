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

    // The 12-digit base is split into a fixed 7-digit company prefix + the remaining asset type,
    // matching the backend parser (de.metas.handlingunits.grai.GRAI.GS1_COMPANY_PREFIX_LENGTH = 7).
    // GS1 allows variable-length company prefixes, but this scan path is calibrated for the 7-digit
    // prefixes in use; the frontend and backend MUST stay on the same split so a scanned GRAI parses
    // to the same canonical value on both sides.
    const COMPANY_PREFIX_LENGTH = 7;
    const companyPrefix = base.substring(0, COMPANY_PREFIX_LENGTH);
    const assetType = base.substring(COMPANY_PREFIX_LENGTH);

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

/**
 * Validate a GRAI string in dot-separated format.
 * Expected format: {companyPrefix}.{assetType}.{serial}
 *
 * @param {string} graiString
 * @returns {boolean}
 */
export const isValidGrai = (graiString) => {
  if (!graiString || typeof graiString !== 'string') return false;

  const parts = graiString.split('.');
  if (parts.length !== 3) return false;

  const [companyPrefix, assetType, serial] = parts;
  return companyPrefix.length > 0 && assetType.length > 0 && serial.length > 0;
};

//
// GRAI list helpers (shared between picking and HU-Manager hooks)
//

/**
 * Return the GRAIs that fill the assigned slots (first `tuCount` entries).
 * When tuCount is 0 (unknown/unlimited), all captured GRAIs are "assigned".
 *
 * @param {string[]} graiCodes - current accumulated list
 * @param {number} tuCount - expected number of TUs (0 = unlimited)
 * @returns {string[]}
 */
export const getAssignedGrais = (graiCodes, tuCount) => (tuCount > 0 ? graiCodes.slice(0, tuCount) : graiCodes);

/**
 * Return the GRAIs beyond the assigned slots (overflow / extras).
 * When tuCount is 0, there are no extras.
 *
 * @param {string[]} graiCodes - current accumulated list
 * @param {number} tuCount - expected number of TUs (0 = unlimited)
 * @returns {string[]}
 */
export const getExtraGrais = (graiCodes, tuCount) => (tuCount > 0 ? graiCodes.slice(tuCount) : []);

/**
 * Merge newGrais into the existing list, deduplicating by value — both against the existing list
 * AND within newGrais itself. Preserves existing order; appends new items at the end.
 * Returns the same array reference if nothing was added (no unnecessary re-render).
 *
 * Within-batch dedup matters for RFID mass-scan: a single burst can re-read the same physical
 * crate's tag more than once, and a GRAI uniquely identifies one returnable asset — so a repeated
 * code in one batch is the same crate, not a second one, and must collapse to a single entry.
 *
 * @param {string[]} prev - existing GRAI list
 * @param {string[]} newGrais - GRAIs to add
 * @returns {string[]}
 */
export const mergeGraiArrays = (prev, newGrais) => {
  const seen = new Set(prev);
  const toAdd = [];
  for (const g of newGrais) {
    if (!seen.has(g)) {
      seen.add(g);
      toAdd.push(g);
    }
  }
  if (toAdd.length === 0) return prev;
  return [...prev, ...toAdd];
};
