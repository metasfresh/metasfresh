import { UPDATE_MANUFACTURING_ISSUE_QTY, UPDATE_MANUFACTURING_RECEIPT_QTY, UPDATE_MANUFACTURING_RECEIPT_TARGET, } from '../constants/ManufacturingActionTypes';

import { getLineById, getWfProcess } from '../reducers/wfProcesses';
import { postManufacturingIssueEvent, postManufacturingReceiveEvent } from '../api/manufacturing';
import { toQRCodeString } from '../utils/qrCode/hu';

const updateManufacturingIssueQty = ({ wfProcessId, activityId, lineId, stepId, qtyPicked, qtyRejectedReasonCode }) => {
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
};

export const updateManufacturingIssue = ({
  wfProcessId,
  activityId,
  lineId,
  stepId,
  huWeightGrossBeforeIssue,
  qtyIssued,
  qtyRejected,
  qtyRejectedReasonCode,
}) => {
  return (dispatch, getState) => {
    const state = getState();

    const wfProcess = getWfProcess(state, wfProcessId);
    const activity = wfProcess && wfProcess.activities ? wfProcess.activities[activityId] : null;
    const line = activity != null ? activity.dataStored.lines[lineId] : null;

    if (line) {
      const step = line.steps[stepId];
      const { id, huQRCode } = step;

      return postManufacturingIssueEvent({
        wfProcessId,
        activityId,
        issueTo: {
          issueStepId: id,
          huQRCode: toQRCodeString(huQRCode),
          huWeightGrossBeforeIssue,
          qtyIssued,
          qtyRejected,
          qtyRejectedReasonCode,
        },
      }).then(() =>
        dispatch(
          updateManufacturingIssueQty({
            wfProcessId,
            activityId,
            lineId,
            stepId,
            qtyPicked: qtyIssued,
            qtyRejectedReasonCode,
          })
        )
      );
    } else {
      return Promise.reject('No line found');
    }
  };
};

export const updateManufacturingReceiptTarget = ({ wfProcessId, activityId, lineId, target }) => {
  return {
    type: UPDATE_MANUFACTURING_RECEIPT_TARGET,
    payload: { wfProcessId, activityId, lineId, target },
  };
};

export const updateManufacturingReceiptQty = ({ wfProcessId, activityId, lineId, qtyReceived }) => {
  console.log('updateManufacturingReceiptQty', { wfProcessId, activityId, lineId, qtyReceived });
  return (dispatch, getState) => {
    const aggregateToLU = getAggregateToLUTarget({ globalState: getState(), wfProcessId, activityId, lineId });
    return postManufacturingReceiveEvent({
      wfProcessId,
      activityId,
      receiveFrom: { lineId, aggregateToLU, qtyReceived },
    }) //
      .then((response) => {
        dispatch({ type: UPDATE_MANUFACTURING_RECEIPT_QTY, payload: { wfProcessId, activityId, lineId, qtyReceived } });
        dispatch(updateManufacturingReceiptTarget({ wfProcessId, activityId, lineId, target: response.existingLU }));
      });
  };
};

const getAggregateToLUTarget = ({ globalState, wfProcessId, activityId, lineId }) => {
  const line = getLineById(globalState, wfProcessId, activityId, lineId);
  if (line.aggregateToLU != null) {
    return line.aggregateToLU;
  } else if (line.currentReceivingHU) {
    return {
      existingLU: line.currentReceivingHU,
    };
  } else {
    // shall not happen
    throw 'No target found';
  }
};
