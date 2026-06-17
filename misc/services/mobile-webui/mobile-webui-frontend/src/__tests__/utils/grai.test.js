import { mergeGraiArrays, getAssignedGrais, getExtraGrais } from '../../utils/grai';

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
