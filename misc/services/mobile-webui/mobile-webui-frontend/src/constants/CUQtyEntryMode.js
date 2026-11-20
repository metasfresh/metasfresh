/**
 * How the operator enters CU quantities when picking (`JsonPickingJobLine.cuQtyEntryMode`).
 * The codes are the backend's `CUQtyEntryMode` codes.
 */
export const CUQtyEntryMode = {
  MANUAL: 'MANUAL',
  SCAN_EACH_CU: 'SCAN_EACH_CU',
  SCAN_ONCE_ENTER_QTY: 'SCAN_ONCE_ENTER_QTY',
};

/**
 * What a mode means for the screens (the mirror of the backend's `CUQtyEntryPolicy`): ask the policy, never compare codes.
 * - isLabelRequired: a CU label is read (lot / best-before come from it, the box is open before booking)
 * - isPackIdRequired: scan each CU: the label must carry a pack ID, one scan books one CU
 * - isScanOnce: scan once, enter quantity: one label, then the count is typed
 */
const POLICY_MANUAL = Object.freeze({ isLabelRequired: false, isPackIdRequired: false, isScanOnce: false });
const POLICIES = {
  [CUQtyEntryMode.MANUAL]: POLICY_MANUAL,
  [CUQtyEntryMode.SCAN_EACH_CU]: Object.freeze({ isLabelRequired: true, isPackIdRequired: true, isScanOnce: false }),
  [CUQtyEntryMode.SCAN_ONCE_ENTER_QTY]: Object.freeze({
    isLabelRequired: true,
    isPackIdRequired: false,
    isScanOnce: true,
  }),
};

/** @returns the policy of the code; MANUAL's for an absent or unknown code */
export const getCUQtyEntryPolicy = (code) =>
  Object.prototype.hasOwnProperty.call(POLICIES, code) ? POLICIES[code] : POLICY_MANUAL;

/** @returns true for "scan once, enter quantity"; false for any other code and for an absent one */
export const isScanOnce = (code) => getCUQtyEntryPolicy(code).isScanOnce;

/** @returns true for "scan each CU"; false for any other code and for an absent one */
export const isScanEachCU = (code) => getCUQtyEntryPolicy(code).isPackIdRequired;

/** @returns true for the modes that read a CU label; false for MANUAL and for an absent / unknown code */
export const isLabelRequired = (code) => getCUQtyEntryPolicy(code).isLabelRequired;
