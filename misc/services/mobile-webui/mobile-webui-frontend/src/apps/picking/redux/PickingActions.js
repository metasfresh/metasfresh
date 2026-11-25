import { REMEMBER_CU_ENTRY } from './PickingActionTypes';

/** Remembers, per picking activity, the line the picker is working on in a CU quantity entry mode */
export function rememberCUEntry({ wfProcessId, activityId, lineId, lastTypedQty, label }) {
  return {
    type: REMEMBER_CU_ENTRY,
    payload: { wfProcessId, activityId, lineId, lastTypedQty, label },
  };
}
