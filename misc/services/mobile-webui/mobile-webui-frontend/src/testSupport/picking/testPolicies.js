// Test data, not used by the app. The CU quantity entry policy of each mode as the backend publishes it on a picking line (`JsonCUQtyEntryPolicy`);
// the rules themselves live in the backend (`CUQtyEntryPolicy`), whose unit tests assert these values.
export const POLICY = {
  MANUAL: { labelRequired: false, packIdRequired: false, scanOnce: false },
  SCAN_EACH_CU: { labelRequired: true, packIdRequired: true, scanOnce: false },
  SCAN_ONCE_ENTER_QTY: { labelRequired: true, packIdRequired: false, scanOnce: true },
};
