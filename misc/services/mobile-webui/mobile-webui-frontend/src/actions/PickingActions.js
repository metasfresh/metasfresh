import { REMEMBER_CU_ENTRY } from '../constants/PickingActionTypes';

/**
 * Remembers, per picking activity, the line the picker is working on in a CU quantity entry mode: the last typed count
 * and the label the next booking uses. One memory per activity, so another line starts empty.
 */
export function rememberCUEntry({ wfProcessId, activityId, lineId, lastTypedQty, label }) {
  return {
    type: REMEMBER_CU_ENTRY,
    payload: { wfProcessId, activityId, lineId, lastTypedQty, label },
  };
}
