import { isResolvedProductUnpickable } from '../../../../containers/activities/picking/unpick/UnpickProductScanDialog';

describe('UnpickProductScanDialog — isResolvedProductUnpickable', () => {
  it('is true when productId present and backend says unpickable', () => {
    expect(isResolvedProductUnpickable({ productId: 1, unpickable: true, packedQty: 5 })).toBe(true);
  });

  it('honors the backend flag, NOT packedQty (drift case)', () => {
    // packedQty > 0 but the backend says NOT unpickable → must be false.
    // The old reimplementation (isPacked = packedQty > 0) would have proceeded here.
    expect(isResolvedProductUnpickable({ productId: 1, unpickable: false, packedQty: 5 })).toBe(false);
  });

  it('is false when productId is missing even if unpickable', () => {
    expect(isResolvedProductUnpickable({ productId: undefined, unpickable: true })).toBe(false);
  });

  it('is false for a null response', () => {
    expect(isResolvedProductUnpickable(null)).toBe(false);
  });
});
