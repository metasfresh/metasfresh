import {
  UPDATE_MANUFACTURING_ISSUE_QTY,
  UPDATE_MANUFACTURING_RECEIPT_QTY,
  UPDATE_MANUFACTURING_RECEIPT_TARGET,
  UPDATE_MANUFACTURING_RECEIPT,
} from '../constants/ManufacturingActionTypes';

import { getWfProcess } from '../reducers/wfProcesses_status';
import { postManufacturingIssueEvent, postManufacturingReceiveEvent } from '../api/manufacturing';

export function updateManufacturingIssueQty({
  wfProcessId,
  activityId,
  lineId,
  stepId,
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
      qtyPicked,
      qtyRejectedReasonCode,
    },
  };
}

export function updateManufacturingIssue({ wfProcessId, activityId, lineId, stepId }) {
  return (dispatch, getState) => {
    const state = getState();

    const wfProcess = getWfProcess(state, wfProcessId);
    const activity = wfProcess && wfProcess.activities ? wfProcess.activities[activityId] : null;
    const line = activity != null ? activity.dataStored.lines[lineId] : null;

    if (line) {
      const step = line.steps[stepId];
      const { id, huBarcode, qtyIssued, qtyRejected, qtyRejectedReasonCode } = step;

      return postManufacturingIssueEvent({
        wfProcessId,
        activityId,
        issueTo: {
          issueStepId: id,
          huBarcode: huBarcode,
          qtyIssued,
          qtyRejected,
          qtyRejectedReasonCode,
        },
      });
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

    const wfProcess = getWfProcess(state, wfProcessId);
    const activity = wfProcess && wfProcess.activities ? wfProcess.activities[activityId] : null;
    const line = activity != null ? activity.dataStored.lines[lineId] : null;

    if (line) {
      dispatch({ type: UPDATE_MANUFACTURING_RECEIPT, payload: { ...line } });

      let aggregateToLU = null;
      if (line.aggregateToLU != null) {
        aggregateToLU = line.aggregateToLU;
      } else if (line.currentReceivingHU) {
        aggregateToLU = {
          existingLU: line.currentReceivingHU,
        };
      } else {
        // shall not happen
        console.log('No target found', line);
        return;
      }

      return postManufacturingReceiveEvent({
        wfProcessId,
        activityId,
        receiveFrom: {
          lineId: line.id,
          qtyReceived: line.userQtyReceived,
          aggregateToLU,
        },
      }).then((response) => {
        if (response.existingLU) {
          dispatch(
            updateManufacturingReceiptTarget({ wfProcessId, activityId, lineId, target: { ...response.existingLU } })
          );
        }
      });
    } else {
      return Promise.reject('No line found');
    }
  };
}
