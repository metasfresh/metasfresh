import {
  UPDATE_MANUFACTURING_ISSUE_QTY,
  UPDATE_MANUFACTURING_RECEIPT_QTY,
  UPDATE_MANUFACTURING_RECEIPT_TARGET,
} from '../constants/ManufacturingActionTypes';

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

export function updateManufacturingReceiptTarget({ wfProcessId, activityId, lineId, target }) {
  return {
    type: UPDATE_MANUFACTURING_RECEIPT_TARGET,
    payload: { wfProcessId, activityId, lineId, target },
  };
}

export function updateManufacturingReceiptQty({ wfProcessId, activityId, lineId, quantity }) {
  return {
    type: UPDATE_MANUFACTURING_RECEIPT_QTY,
    payload: { wfProcessId, activityId, lineId, quantity },
  };
}
