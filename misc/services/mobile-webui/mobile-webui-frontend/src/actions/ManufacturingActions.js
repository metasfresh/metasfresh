import { UPDATE_MANUFACTURING_ISSUE_QTY } from '../constants/ManufacturingActionTypes';

export function updateManufacturingIssueQty({ wfProcessId, activityId, lineId, qtyPicked }) {
  return {
    type: UPDATE_MANUFACTURING_ISSUE_QTY,
    payload: {
      wfProcessId,
      activityId,
      lineId,
      qtyPicked,
    },
  };
}
