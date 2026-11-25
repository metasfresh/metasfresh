import { MANUAL_POLICY } from '../../apps/picking/cuQtyEntry/cuQtyEntryPolicy';

export const POLICY = {
  MANUAL: MANUAL_POLICY,
  SCAN_EACH_CU: { labelRequired: true, packIdRequired: true, scanOnce: false },
  SCAN_ONCE_ENTER_QTY: { labelRequired: true, packIdRequired: false, scanOnce: true },
};
