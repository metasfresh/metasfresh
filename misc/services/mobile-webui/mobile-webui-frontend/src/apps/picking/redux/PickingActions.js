import { FORGET_CU_ENTRY, FORGET_PENDING_SCAN, REMEMBER_CU_ENTRY, REMEMBER_PENDING_SCAN } from './PickingActionTypes';

/** Remembers, per picking activity, the count last typed on the line the picker is working on in Scan once. Only the count, no label. */
export function rememberCUEntry({ wfProcessId, activityId, lineId, lastTypedQty }) {
  return {
    type: REMEMBER_CU_ENTRY,
    payload: { wfProcessId, activityId, lineId, lastTypedQty },
  };
}

/** A new round on a line ("HU verpacken"): nothing is remembered from the last one */
export function forgetCUEntry({ wfProcessId, activityId }) {
  return {
    type: FORGET_CU_ENTRY,
    payload: { wfProcessId, activityId },
  };
}

/**
 * "Weiteres TU" on a line without a TU type leaves the scan screen for the TU list: keeps the scanned label and the count typed so far,
 * so the scan screen shows them again when the picker is back (nothing is scanned or typed twice).
 */
export function rememberPendingScan({ wfProcessId, activityId, lineId, scannedBarcode, qtyEntered }) {
  return {
    type: REMEMBER_PENDING_SCAN,
    payload: { wfProcessId, activityId, lineId, scannedBarcode, qtyEntered },
  };
}

/** The scan screen showed the kept scan again: it is used up */
export function forgetPendingScan({ wfProcessId, activityId }) {
  return {
    type: FORGET_PENDING_SCAN,
    payload: { wfProcessId, activityId },
  };
}
