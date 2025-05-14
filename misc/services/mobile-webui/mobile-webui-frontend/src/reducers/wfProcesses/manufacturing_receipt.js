import * as types from '../../constants/ManufacturingActionTypes';
import * as CompleteStatus from '../../constants/CompleteStatus';
import { registerHandler } from './activityStateHandlers';
import { updateUserEditable } from './utils';
import { current, isDraft } from 'immer';
import { getLineByIdFromWFProcess } from './index';
import { toQRCodeObject } from '../../utils/qrCode/hu';

const COMPONENT_TYPE = 'manufacturing/materialReceipt';

export const manufacturingReducer = ({ draftState, action }) => {
  switch (action.type) {
    case types.UPDATE_MANUFACTURING_LU_RECEIPT_TARGET: {
      return reduceOnUpdateLUReceiptTarget(draftState, action.payload);
    }
    case types.UPDATE_MANUFACTURING_TU_RECEIPT_TARGET: {
      return reduceOnUpdateTUReceiptTarget(draftState, action.payload);
    }
    case types.UPDATE_MANUFACTURING_RECEIPT_QTY: {
      return reduceOnUpdateQtyReceived(draftState, action.payload);
    }
    default: {
      return draftState;
    }
  }
};

const reduceOnUpdateLUReceiptTarget = (draftState, { wfProcessId, activityId, lineId, target }) => {
  return reduceOnUpdateReceiptTarget(draftState, { wfProcessId, activityId, lineId, luTarget: target });
};

const reduceOnUpdateTUReceiptTarget = (draftState, { wfProcessId, activityId, lineId, target }) => {
  return reduceOnUpdateReceiptTarget(draftState, { wfProcessId, activityId, lineId, tuTarget: target });
};

const reduceOnUpdateReceiptTarget = (draftState, { wfProcessId, activityId, lineId, luTarget, tuTarget }) => {
  const draftWFProcess = draftState[wfProcessId];
  const draftActivityLine = getLineByIdFromWFProcess(draftWFProcess, activityId, lineId);

  if (luTarget) {
    draftActivityLine.aggregateToLU = getAggregateToLU({ draftActivityLine, luTarget });
    draftActivityLine.aggregateToTU = null;
  } else if (tuTarget) {
    draftActivityLine.aggregateToLU = null;
    draftActivityLine.aggregateToTU = getAggregateToTU({ tuTarget });
  } else {
    draftActivityLine.aggregateToLU = null;
    draftActivityLine.aggregateToTU = null;
  }

  updateLineStatusAndRollup({
    draftWFProcess,
    activityId,
    lineId,
  });

  return draftState;
};

const getAggregateToLU = ({ draftActivityLine, luTarget }) => {
  if (luTarget.huQRCode) {
    const tuPIItemProductId = draftActivityLine.availableReceivingTargets.values[0].tuPIItemProductId;
    return {
      existingLU: {
        huQRCode: toQRCodeObject(luTarget.huQRCode),
        tuPIItemProductId,
      },
    };
  } else {
    return {
      newLU: { ...luTarget },
    };
  }
};

const getAggregateToTU = ({ tuTarget }) => {
  return {
    newTU: { ...tuTarget },
  };
};

const reduceOnUpdateQtyReceived = (draftState, { wfProcessId, activityId, lineId, qtyReceived }) => {
  if (qtyReceived > 0) {
    const draftWFProcess = draftState[wfProcessId];
    const draftActivityLine = getLineByIdFromWFProcess(draftWFProcess, activityId, lineId);

    draftActivityLine.qtyReceived = qtyReceived;

    updateLineStatusAndRollup({
      draftWFProcess,
      activityId,
      lineId,
    });
  }

  return draftState;
};

const updateLineStatusAndRollup = ({ draftWFProcess, activityId, lineId }) => {
  const draftLine = draftWFProcess.activities[activityId].dataStored.lines[lineId];

  draftLine.completeStatus = computeLineStatus(draftLine);
  console.log(`Update line [${activityId} ${lineId} ]: completeStatus=${draftLine.completeStatus}`);

  //
  // Rollup:
  updateActivityStatusFromLinesAndRollup({ draftWFProcess, activityId });
};

const updateActivityStatusFromLinesAndRollup = ({ draftWFProcess, activityId }) => {
  const draftActivity = draftWFProcess.activities[activityId];
  updateActivityStatusFromLines({ draftActivityDataStored: draftActivity.dataStored });

  //
  // Rollup:
  updateUserEditable({ draftWFProcess });
};

const updateActivityStatusFromLines = ({ draftActivityDataStored }) => {
  draftActivityDataStored.completeStatus = computeActivityStatusFromLines({ draftActivityDataStored });
};

const computeLineStatus = ({ qtyToReceive, qtyReceived }) => {
  const qtyToReceiveRemaining = qtyToReceive - qtyReceived;
  if (qtyToReceiveRemaining <= 0) {
    return CompleteStatus.COMPLETED;
  } else if (qtyReceived > 0) {
    return CompleteStatus.IN_PROGRESS;
  } else {
    return CompleteStatus.NOT_STARTED;
  }
};

const computeActivityStatus = ({ draftActivity }) => {
  const draftActivityDataStored = draftActivity.dataStored;
  if (draftActivityDataStored.lines) {
    const lineIds = extractDraftMapKeys(draftActivityDataStored.lines);
    lineIds.forEach((lineId) => {
      const line = draftActivityDataStored.lines[lineId];
      line.completeStatus = computeLineStatus(line);
    });
  }
  return computeActivityStatusFromLines({ draftActivityDataStored });
};

const computeActivityStatusFromLines = ({ draftActivityDataStored }) => {
  const lineIds = extractDraftMapKeys(draftActivityDataStored.lines);

  const lineStatuses = [];
  lineIds.forEach((lineId) => {
    const draftLine = draftActivityDataStored.lines[lineId];
    const lineCompleteStatus = draftLine.completeStatus || CompleteStatus.NOT_STARTED;
    if (!lineStatuses.includes(lineCompleteStatus)) {
      lineStatuses.push(lineCompleteStatus);
    }
  });

  return CompleteStatus.reduceFromCompleteStatuesUniqueArray(lineStatuses);
};

const extractDraftMapKeys = (draftMap) => {
  return isDraft(draftMap) ? Object.keys(current(draftMap)) : Object.keys(draftMap);
};

const normalizeLines = (lines) => {
  return lines.reduce((accum, line) => {
    const aggregateToLU = line.aggregateToLU || null;
    accum[line.id] = {
      ...line,
      aggregateToLU,
    };
    return accum;
  }, {});
};

registerHandler({
  componentType: COMPONENT_TYPE,
  normalizeComponentProps: () => {}, // don't add componentProps to state
  computeActivityStatus,
  mergeActivityDataStored: ({ draftActivityDataStored, fromActivity }) => {
    draftActivityDataStored.lines = normalizeLines(fromActivity.componentProps.lines);
    draftActivityDataStored.isAlwaysAvailableToUser = fromActivity.isAlwaysAvailableToUser ?? true;
    return draftActivityDataStored;
  },
});
