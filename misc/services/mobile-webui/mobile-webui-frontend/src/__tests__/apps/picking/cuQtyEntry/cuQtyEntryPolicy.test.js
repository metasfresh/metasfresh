import {
  getCUQtyEntryPolicy,
  MANUAL_POLICY,
  toQtyEntryBehaviorProps,
} from '../../../../apps/picking/cuQtyEntry/cuQtyEntryPolicy';
import { POLICY } from '../../../../__testSupport__/picking/testPolicies';

describe('getCUQtyEntryPolicy', () => {
  it('reads the policy the backend published on the line', () => {
    expect(getCUQtyEntryPolicy({ cuQtyEntryPolicy: POLICY.SCAN_EACH_CU })).toBe(POLICY.SCAN_EACH_CU);
    expect(getCUQtyEntryPolicy({ cuQtyEntryPolicy: POLICY.SCAN_ONCE_ENTER_QTY })).toBe(POLICY.SCAN_ONCE_ENTER_QTY);
  });

  it.each([[undefined], [null], [{}], [{ cuQtyEntryPolicy: null }]])('%p: nothing required', (line) => {
    expect(getCUQtyEntryPolicy(line)).toBe(MANUAL_POLICY);
    expect(MANUAL_POLICY).toEqual({
      isLabelRequired: false,
      isPackIdRequired: false,
      isScanOnce: false,
      isOneCUPerLabel: false,
    });
  });
});

describe('toQtyEntryBehaviorProps', () => {
  it('maps each policy onto the behaviour switches of the shared quantity components', () => {
    expect(toQtyEntryBehaviorProps(POLICY.MANUAL)).toEqual({
      isRepeatedBooking: false,
      isBookedOnScan: false,
      isLotAndBestBeforeShownReadOnly: false,
      isSubmitOnEnter: true,
    });
    expect(toQtyEntryBehaviorProps(POLICY.SCAN_ONCE_ENTER_QTY)).toEqual({
      isRepeatedBooking: true,
      isBookedOnScan: false,
      isLotAndBestBeforeShownReadOnly: true,
      isSubmitOnEnter: false,
    });
    expect(toQtyEntryBehaviorProps(POLICY.SCAN_EACH_CU)).toEqual({
      isRepeatedBooking: false,
      isBookedOnScan: true,
      isLotAndBestBeforeShownReadOnly: true,
      isSubmitOnEnter: false,
    });
  });
  it('treats a missing policy as Manual', () => {
    expect(toQtyEntryBehaviorProps(undefined)).toEqual(toQtyEntryBehaviorProps(POLICY.MANUAL));
  });
});
