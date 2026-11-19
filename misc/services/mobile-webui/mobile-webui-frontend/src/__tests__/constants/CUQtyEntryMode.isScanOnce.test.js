import { CUQtyEntryMode, isScanOnce } from '../../constants/CUQtyEntryMode';

describe('isScanOnce', () => {
  it('is true only for scan once, enter quantity', () => {
    expect(isScanOnce(CUQtyEntryMode.SCAN_ONCE_ENTER_QTY)).toBe(true);
    expect(isScanOnce(CUQtyEntryMode.SCAN_EACH_CU)).toBe(false);
    expect(isScanOnce(CUQtyEntryMode.MANUAL)).toBe(false);
    expect(isScanOnce(undefined)).toBe(false);
  });
});
