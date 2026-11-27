import { FORGET_CU_ENTRY, REMEMBER_CU_ENTRY } from './PickingActionTypes';

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
