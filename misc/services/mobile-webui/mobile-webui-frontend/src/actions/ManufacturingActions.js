import {
  UPDATE_MANUFACTURING_LU_RECEIPT_TARGET,
  UPDATE_MANUFACTURING_TU_RECEIPT_TARGET,
} from '../constants/ManufacturingActionTypes';

import { getLineById, getStepByIdFromLine } from '../reducers/wfProcesses';
import { postManufacturingIssueEvent, postManufacturingReceiveEvent } from '../api/manufacturing';
import { toQRCodeString } from '../utils/qrCode/hu';
import { updateWFProcess } from './WorkflowActions';

export const postManufacturingIssueEventThunk = ({
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
    const line = getLineById(getState(), wfProcessId, activityId, lineId);

    if (line) {
      const step = getStepByIdFromLine(line, stepId);
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
      }).then((wfProcess) => {
        return dispatch(updateWFProcess({ wfProcess }));
      });
    } else {
      return Promise.reject('No line found');
    }
  };
};

export const updateManufacturingLUReceiptTarget = ({ wfProcessId, activityId, lineId, target }) => {
  return {
    type: UPDATE_MANUFACTURING_LU_RECEIPT_TARGET,
    payload: { wfProcessId, activityId, lineId, target },
  };
};

export const updateManufacturingTUReceiptTarget = ({ wfProcessId, activityId, lineId, target }) => {
  return {
    type: UPDATE_MANUFACTURING_TU_RECEIPT_TARGET,
    payload: { wfProcessId, activityId, lineId, target },
  };
};

export const postManufacturingReceiveEventThunk = ({
  wfProcessId,
  activityId,
  lineId,
  qtyReceived,
  pickTo,
  catchWeight,
  catchWeightUom,
  bestBeforeDate,
  productionDate,
  lotNo,
  barcode, // i.e. the catch weight QR code
}) => {
  return (dispatch, getState) => {
    const { aggregateToLU, aggregateToTU } = getAggregateTarget({
      globalState: getState(),
      wfProcessId,
      activityId,
      lineId,
    });
    return postManufacturingReceiveEvent({
      wfProcessId,
      activityId,
      receiveFrom: {
        lineId,
        aggregateToLU,
        aggregateToTU,
        qtyReceived,
        catchWeight,
        catchWeightUomSymbol: catchWeightUom,
        bestBeforeDate,
        productionDate,
        lotNo,
        barcode,
      },
      pickTo,
    }) //
      .then((wfProcess) => {
        return dispatch(updateWFProcess({ wfProcess }));
      });
  };
};

const getAggregateTarget = ({ globalState, wfProcessId, activityId, lineId }) => {
  const line = getLineById(globalState, wfProcessId, activityId, lineId);
  if (line.aggregateToLU != null) {
    return { aggregateToLU: line.aggregateToLU };
  } else if (line.currentReceivingHU) {
    return {
      aggregateToLU: {
        existingLU: line.currentReceivingHU,
      },
    };
  } else if (line.aggregateToTU) {
    return { aggregateToTU: line.aggregateToTU };
  } else {
    // shall not happen
    throw 'No target found';
  }
};
