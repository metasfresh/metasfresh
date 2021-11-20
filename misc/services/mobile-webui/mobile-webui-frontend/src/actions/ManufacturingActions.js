import {
  UPDATE_MANUFACTURING_ISSUE_QTY,
  UPDATE_MANUFACTURING_RECEIPT_QTY,
  UPDATE_MANUFACTURING_RECEIPT_TARGET,
  UPDATE_MANUFACTURING_RECEIPT,
} from '../constants/ManufacturingActionTypes';

import { selectWFProcessFromState } from '../reducers/wfProcesses_status';
import { manufacturingReceiptReqest } from '../api/manufacturing';

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

export function updateManufacturingReceipt({ wfProcessId, activityId, lineId }) {
  return (dispatch, getState) => {
    const state = getState();

    const wfProcess = selectWFProcessFromState(state, wfProcessId);
    const activity = wfProcess && wfProcess.activities ? wfProcess.activities[activityId] : null;
    const line = activity != null ? activity.dataStored.lines[lineId] : null;

    if (line) {
      dispatch({ type: UPDATE_MANUFACTURING_RECEIPT, payload: { ...line } });

      const { id, qtyReceived, aggregateToLU } = line;
      const receiptObject = {
        receiveFrom: {
          lineId: id,
          qtyReceived,
          aggregateToLU,
        },
      };
      manufacturingReceiptReqest({ wfProcessId, activityId, receiptObject }).then((resp) => {
        if (aggregateToLU.newLU) {
          dispatch(
            updateManufacturingReceiptTarget({ wfProcessId, activityId, lineId, target: { ...resp.data.existingLU } })
          );
        }
      });
    }
  };
}
