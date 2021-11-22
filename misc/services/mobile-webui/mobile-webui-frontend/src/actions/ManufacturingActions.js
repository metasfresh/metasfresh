import {
  UPDATE_MANUFACTURING_ISSUE_QTY,
  UPDATE_MANUFACTURING_RECEIPT_QTY,
  UPDATE_MANUFACTURING_RECEIPT_TARGET,
  UPDATE_MANUFACTURING_RECEIPT,
} from '../constants/ManufacturingActionTypes';

import { selectWFProcessFromState } from '../reducers/wfProcesses_status';
import { manufacturingReqest } from '../api/manufacturing';

export function updateManufacturingIssueQty({
  wfProcessId,
  activityId,
  lineId,
  stepId,
  scannedHUBarcode,
  qtyPicked,
  qtyRejectedReasonCode,
}) {
  return {
    type: UPDATE_MANUFACTURING_ISSUE_QTY,
    payload: {
      wfProcessId,
      activityId,
      lineId,
      stepId,
      scannedHUBarcode,
      qtyPicked,
      qtyRejectedReasonCode,
    },
  };
}

export function updateManufacturingIssue({ wfProcessId, activityId, lineId, stepId }) {
  return (dispatch, getState) => {
    const state = getState();

    const wfProcess = selectWFProcessFromState(state, wfProcessId);
    const activity = wfProcess && wfProcess.activities ? wfProcess.activities[activityId] : null;
    const line = activity != null ? activity.dataStored.lines[lineId] : null;

    if (line) {
      const step = line.steps[stepId];
      const { id, scannedHUBarcode, qtyIssued, qtyRejected, qtyRejectedReasonCode } = step;
      const receiptObject = {
        issueTo: {
          issueStepId: id,
          huBarcode: scannedHUBarcode,
          qtyIssued,
          qtyRejected,
          qtyRejectedReasonCode,
        },
      };

      return manufacturingReqest({ wfProcessId, activityId, receiptObject });
    } else {
      return Promise.reject('No line found');
    }
  };
}

export function updateManufacturingReceiptTarget({ wfProcessId, activityId, lineId, target }) {
  return {
    type: UPDATE_MANUFACTURING_RECEIPT_TARGET,
    payload: { wfProcessId, activityId, lineId, target },
  };
}

export function updateManufacturingReceiptQty({ wfProcessId, activityId, lineId, qtyPicked }) {
  return {
    type: UPDATE_MANUFACTURING_RECEIPT_QTY,
    payload: { wfProcessId, activityId, lineId, qtyPicked },
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

      const { id, userQtyReceived, aggregateToLU } = line;
      const receiptObject = {
        receiveFrom: {
          lineId: id,
          qtyReceived: userQtyReceived,
          aggregateToLU,
        },
      };
      return manufacturingReqest({ wfProcessId, activityId, receiptObject }).then((resp) => {
        if (aggregateToLU.newLU) {
          dispatch(
            updateManufacturingReceiptTarget({ wfProcessId, activityId, lineId, target: { ...resp.data.existingLU } })
          );
        }
      });
    } else {
      return Promise.reject('No line found');
    }
  };
}
