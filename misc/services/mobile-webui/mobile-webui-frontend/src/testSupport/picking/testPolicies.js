export const POLICY = {
  MANUAL: { labelRequired: false, packIdRequired: false, scanOnce: false },
  SCAN_EACH_CU: { labelRequired: true, packIdRequired: true, scanOnce: false },
  SCAN_ONCE_ENTER_QTY: { labelRequired: true, packIdRequired: false, scanOnce: true },
};
