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
 * Returns the SAME array reference (as `merged`) if nothing was added (no unnecessary re-render).
 *
 * Within-batch dedup matters for RFID mass-scan: a single burst can re-read the same physical
 * crate's tag more than once, and a GRAI uniquely identifies one returnable asset — so a repeated
 * code in one batch is the same crate, not a second one, and must collapse to a single entry. This
 * same-buffer re-read case stays a SILENT no-op — it is never counted in `skipped`.
 *
 * `existingCodes` is a second exclusion set — GRAIs already assigned elsewhere (e.g. to another
 * crate of the same loading unit, per the server-side LU-wide GRAI dedupe) that this batch must
 * also drop, but — unlike a same-buffer re-read — the caller DOES want to know about it (to advance
 * a "N skipped" notice), so those codes are reported back in `skipped`. A code repeated within the
 * batch against `existingCodes` is only reported once (marked seen on first occurrence).
 *
 * `alreadySkipped` are codes a PRIOR call already reported in `skipped` and the caller is still
 * showing. They are treated exactly like same-buffer re-reads (silent no-op, never re-reported), so a
 * code that gets delivered more than once — e.g. the dual-reader race where `BarcodeScannerComponent`
 * and `useKeyboardBarcodeReader` both emit the same physical scan — is counted at most once. (Merged
 * codes need no such list because they already live in `prev`; a skipped code is dropped from `prev`,
 * so it needs its own memory to stay idempotent across calls.)
 *
 * @param {string[]} prev - existing GRAI list (accumulated, deduped)
 * @param {string[]} newGrais - GRAIs to add
 * @param {string[]} [existingCodes] - GRAIs to drop-and-report (already assigned elsewhere on the LU)
 * @param {string[]} [alreadySkipped] - codes already reported in a prior call's `skipped` (silent on repeat)
 * @returns {{merged: string[], skipped: string[]}} `merged` is the same ref as `prev` when nothing was
 *   added; `skipped` lists only the NEWLY-skipped codes (excludes `alreadySkipped`)
 */
export const mergeGraiArrays = (prev, newGrais, existingCodes = [], alreadySkipped = []) => {
  // `seen` = everything already decided: accumulated codes (prev) AND already-reported skips.
  const seen = new Set(prev);
  for (const g of alreadySkipped) {
    seen.add(g);
  }
  const existing = existingCodes.length ? new Set(existingCodes) : null;
  const toAdd = [];
  const skipped = [];
  for (const g of newGrais) {
    if (seen.has(g)) {
      // same-buffer re-read OR an already-reported LU-skip redelivered: silent no-op, never (re)counted
      continue;
    }
    if (existing && existing.has(g)) {
      seen.add(g); // avoid double-reporting a repeat of the same already-assigned code within this batch
      skipped.push(g);
      continue;
    }
    seen.add(g);
    toAdd.push(g);
  }
  const merged = toAdd.length === 0 ? prev : [...prev, ...toAdd];
  return { merged, skipped };
};
