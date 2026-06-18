import {
  parseGraiFromGs1Barcode,
  parseGraiFromRawInput,
  parseGraiArrayFromRawInput,
  isValidGrai,
  mergeGraiArrays,
  getAssignedGrais,
  getExtraGrais,
} from '../../utils/grai';

describe('grai list helpers', () => {
  // ---------------------------------------------------------------------------
  // mergeGraiArrays
  // ---------------------------------------------------------------------------
  describe('mergeGraiArrays', () => {
    it('appends new GRAIs not already in the list', () => {
      const prev = ['A', 'B'];
      const result = mergeGraiArrays(prev, ['C', 'D']);
      expect(result).toEqual(['A', 'B', 'C', 'D']);
    });

    it('deduplicates GRAIs already present', () => {
      const prev = ['A', 'B'];
      const result = mergeGraiArrays(prev, ['B', 'C']);
      expect(result).toEqual(['A', 'B', 'C']);
    });

    it('returns the SAME array reference when nothing new is added (no re-render)', () => {
      const prev = ['A', 'B'];
      const result = mergeGraiArrays(prev, ['A', 'B']);
      expect(result).toBe(prev);
    });

    it('handles empty prev', () => {
      expect(mergeGraiArrays([], ['X'])).toEqual(['X']);
    });

    it('handles empty newGrais', () => {
      const prev = ['A'];
      const result = mergeGraiArrays(prev, []);
      expect(result).toBe(prev);
    });

    it('handles both arrays empty', () => {
      const prev = [];
      const result = mergeGraiArrays(prev, []);
      expect(result).toBe(prev);
    });

    it('deduplicates duplicates WITHIN newGrais (RFID re-read of the same crate in one burst)', () => {
      // A GRAI uniquely identifies one returnable asset, so a code repeated within a single scan
      // burst is the same crate read twice — it must collapse to a single entry.
      expect(mergeGraiArrays([], ['X', 'X'])).toEqual(['X']);
      expect(mergeGraiArrays(['A'], ['B', 'A', 'B', 'C'])).toEqual(['A', 'B', 'C']);
    });
  });

  // ---------------------------------------------------------------------------
  // getAssignedGrais
  // ---------------------------------------------------------------------------
  describe('getAssignedGrais', () => {
    it('returns first tuCount elements when tuCount > 0', () => {
      expect(getAssignedGrais(['A', 'B', 'C', 'D'], 2)).toEqual(['A', 'B']);
    });

    it('returns all elements when tuCount is 0 (unlimited)', () => {
      expect(getAssignedGrais(['A', 'B', 'C'], 0)).toEqual(['A', 'B', 'C']);
    });

    it('returns all when fewer items than tuCount', () => {
      expect(getAssignedGrais(['A'], 5)).toEqual(['A']);
    });

    it('returns empty array for empty graiCodes with tuCount > 0', () => {
      expect(getAssignedGrais([], 3)).toEqual([]);
    });

    it('returns empty array for empty graiCodes with tuCount 0', () => {
      expect(getAssignedGrais([], 0)).toEqual([]);
    });
  });

  // ---------------------------------------------------------------------------
  // getExtraGrais
  // ---------------------------------------------------------------------------
  describe('getExtraGrais', () => {
    it('returns elements beyond tuCount when tuCount > 0', () => {
      expect(getExtraGrais(['A', 'B', 'C', 'D'], 2)).toEqual(['C', 'D']);
    });

    it('returns empty array when tuCount is 0 (unlimited — no overflow concept)', () => {
      expect(getExtraGrais(['A', 'B', 'C'], 0)).toEqual([]);
    });

    it('returns empty when all elements are within tuCount', () => {
      expect(getExtraGrais(['A', 'B'], 5)).toEqual([]);
    });

    it('returns empty for empty graiCodes with tuCount > 0', () => {
      expect(getExtraGrais([], 3)).toEqual([]);
    });

    it('returns empty for empty graiCodes with tuCount 0', () => {
      expect(getExtraGrais([], 0)).toEqual([]);
    });
  });

  // ---------------------------------------------------------------------------
  // assigned + extra are complementary slices
  // ---------------------------------------------------------------------------
  describe('getAssignedGrais + getExtraGrais are complementary', () => {
    it('assigned ++ extra == full list when tuCount > 0', () => {
      const grais = ['A', 'B', 'C', 'D', 'E'];
      const tuCount = 3;
      const assigned = getAssignedGrais(grais, tuCount);
      const extra = getExtraGrais(grais, tuCount);
      expect([...assigned, ...extra]).toEqual(grais);
    });

    it('assigned == full list and extra == [] when tuCount is 0', () => {
      const grais = ['A', 'B', 'C'];
      expect(getAssignedGrais(grais, 0)).toEqual(grais);
      expect(getExtraGrais(grais, 0)).toEqual([]);
    });
  });
});

describe('grai parsing', () => {
  // A real GS1 AI 8003 barcode (same one the HU-Manager GRAI E2E scans). Layout after the 8003 AI:
  // indicator(1) + 13-digit asset reference (7-digit company prefix + 5-digit asset type + check
  // digit) + serial. Canonical form drops the check digit: companyPrefix.assetType.serial.
  const GS1_AI8003_BARCODE = '800307613264003095100691412000';

  // ---------------------------------------------------------------------------
  // parseGraiFromGs1Barcode
  // ---------------------------------------------------------------------------
  describe('parseGraiFromGs1Barcode', () => {
    it('parses an AI 8003 barcode into canonical companyPrefix.assetType.serial', () => {
      const grai = parseGraiFromGs1Barcode(GS1_AI8003_BARCODE);
      expect(grai).not.toBeNull();
      const parts = grai.split('.');
      expect(parts).toHaveLength(3);
      const [companyPrefix, assetType, serial] = parts;
      // The 7-digit company-prefix split MUST match the backend (GS1_COMPANY_PREFIX_LENGTH = 7).
      expect(companyPrefix).toBe('7613264');
      expect(companyPrefix).toHaveLength(7);
      expect(assetType).toBe('00309');
      expect(serial.length).toBeGreaterThan(0);
    });

    it('returns null for a barcode without AI 8003 (a plain EAN-13)', () => {
      expect(parseGraiFromGs1Barcode('4006381333931')).toBeNull();
    });

    it('returns null for an AI 8003 with no serial (asset reference only)', () => {
      // 8003 + indicator(1) + 13 asset-ref digits, no trailing serial → below the min length.
      expect(parseGraiFromGs1Barcode('800307613264003095')).toBeNull();
    });

    it('returns null for garbage input (parser throws → caught)', () => {
      expect(parseGraiFromGs1Barcode('not-a-barcode')).toBeNull();
      expect(parseGraiFromGs1Barcode('')).toBeNull();
    });
  });

  // ---------------------------------------------------------------------------
  // parseGraiFromRawInput
  // ---------------------------------------------------------------------------
  describe('parseGraiFromRawInput', () => {
    it('passes an already-canonical GRAI through unchanged', () => {
      expect(parseGraiFromRawInput('7613204.00307.123456')).toBe('7613204.00307.123456');
    });

    it('trims surrounding whitespace before parsing', () => {
      expect(parseGraiFromRawInput('  7613204.00307.123456  ')).toBe('7613204.00307.123456');
    });

    it('parses a GS1 AI 8003 barcode to its canonical form', () => {
      expect(parseGraiFromRawInput(GS1_AI8003_BARCODE)).toBe(parseGraiFromGs1Barcode(GS1_AI8003_BARCODE));
    });

    it('returns null for input that is neither a GS1 barcode nor a canonical GRAI', () => {
      expect(parseGraiFromRawInput('hello')).toBeNull();
    });

    it('returns null for null / non-string input', () => {
      expect(parseGraiFromRawInput(null)).toBeNull();
      expect(parseGraiFromRawInput(undefined)).toBeNull();
      expect(parseGraiFromRawInput(123)).toBeNull();
    });
  });

  // ---------------------------------------------------------------------------
  // isValidGrai
  // ---------------------------------------------------------------------------
  describe('isValidGrai', () => {
    it('accepts a well-formed 3-part canonical GRAI', () => {
      expect(isValidGrai('7613204.00307.123456')).toBe(true);
    });

    it('rejects a wrong number of parts', () => {
      expect(isValidGrai('7613204.00307')).toBe(false);
      expect(isValidGrai('7613204.00307.123.456')).toBe(false);
    });

    it('rejects an empty part', () => {
      expect(isValidGrai('7613204..123456')).toBe(false);
      expect(isValidGrai('.00307.123456')).toBe(false);
      expect(isValidGrai('7613204.00307.')).toBe(false);
    });

    it('rejects null / non-string input', () => {
      expect(isValidGrai(null)).toBe(false);
      expect(isValidGrai(undefined)).toBe(false);
      expect(isValidGrai(123)).toBe(false);
    });
  });

  // ---------------------------------------------------------------------------
  // parseGraiArrayFromRawInput (RFID batch: newline/tab-separated)
  // ---------------------------------------------------------------------------
  describe('parseGraiArrayFromRawInput', () => {
    it('splits a newline/tab-separated batch and keeps only the valid GRAIs', () => {
      const raw = '7613204.00307.1\n7613204.00307.2\tnot-a-grai\n7613204.00307.3';
      expect(parseGraiArrayFromRawInput(raw)).toEqual(['7613204.00307.1', '7613204.00307.2', '7613204.00307.3']);
    });

    it('returns an empty array for empty / null input', () => {
      expect(parseGraiArrayFromRawInput('')).toEqual([]);
      expect(parseGraiArrayFromRawInput(null)).toEqual([]);
    });
  });
});
