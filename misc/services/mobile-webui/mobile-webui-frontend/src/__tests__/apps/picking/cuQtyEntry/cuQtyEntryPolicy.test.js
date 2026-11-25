import { getCUQtyEntryPolicy, MANUAL_POLICY } from '../../../../apps/picking/cuQtyEntry/cuQtyEntryPolicy';
import { POLICY } from '../../../../__testSupport__/picking/testPolicies';

describe('getCUQtyEntryPolicy', () => {
  it('reads the policy the backend published on the line', () => {
    expect(getCUQtyEntryPolicy({ cuQtyEntryPolicy: POLICY.SCAN_EACH_CU })).toBe(POLICY.SCAN_EACH_CU);
    expect(getCUQtyEntryPolicy({ cuQtyEntryPolicy: POLICY.SCAN_ONCE_ENTER_QTY })).toBe(POLICY.SCAN_ONCE_ENTER_QTY);
  });

  it.each([[undefined], [null], [{}], [{ cuQtyEntryPolicy: null }]])('%p: nothing required', (line) => {
    expect(getCUQtyEntryPolicy(line)).toBe(MANUAL_POLICY);
    expect(MANUAL_POLICY).toEqual({ labelRequired: false, packIdRequired: false, scanOnce: false });
  });
});
