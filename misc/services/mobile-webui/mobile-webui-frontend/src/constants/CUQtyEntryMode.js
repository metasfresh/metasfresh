/**
 * How the operator enters CU quantities when picking (`JsonPickingJobLine.cuQtyEntryMode`).
 * The codes are the backend's `CUQtyEntryMode` codes.
 */
export const CUQtyEntryMode = {
  MANUAL: 'MANUAL',
  SCAN_EACH_CU: 'SCAN_EACH_CU',
  SCAN_ONCE_ENTER_QTY: 'SCAN_ONCE_ENTER_QTY',
};

/** @returns true for the modes that read a CU label; false for MANUAL and for an absent / unknown code */
export const isNewMode = (code) => code === CUQtyEntryMode.SCAN_EACH_CU || code === CUQtyEntryMode.SCAN_ONCE_ENTER_QTY;
