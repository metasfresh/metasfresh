import * as types from '../../constants/ManufacturingActionTypes';
import * as CompleteStatus from '../../constants/CompleteStatus';
import { registerHandler } from './activityStateHandlers';
import { updateUserEditable } from './utils';
import { current, isDraft } from 'immer';

const COMPONENT_TYPE = 'manufacturing/materialReceipt';

export const manufacturingReducer = ({ draftState, action }) => {
  switch (action.type) {
    case types.UPDATE_MANUFACTURING_RECEIPT_QTY: {
      return reduceOnUpdateQtyPicked(draftState, action.payload);
    }
    case types.UPDATE_MANUFACTURING_RECEIPT_TARGET: {
      return updateTarget(draftState, action.payload);
    }
    default: {
      return draftState;
    }
  }
};

const updateTarget = (draftState, payload) => {
  const { wfProcessId, activityId, lineId, target } = payload;

  const draftWFProcess = draftState[wfProcessId];
  const draftActivityLine = draftWFProcess.activities[activityId].dataStored.lines[lineId];

  if (target.huBarcode) {
    const productId = draftActivityLine.availableReceivingTargets.values[0].tuPIItemProductId;
    draftActivityLine.aggregateToLU = {
      existingLU: {
        huBarcode: target.huBarcode,
        tuPIItemProductId: productId,
      },
    };
  } else {
    draftActivityLine.aggregateToLU = {
      newLU: { ...target },
    };
  }

  updateLineStatus({
    draftWFProcess,
    activityId,
    lineId,
  });

  return draftState;
};

const reduceOnUpdateQtyPicked = (draftState, payload) => {
  const { wfProcessId, activityId, lineId, qtyPicked } = payload;

  const draftWFProcess = draftState[wfProcessId];
  const draftActivityLine = draftWFProcess.activities[activityId].dataStored.lines[lineId];

  if (qtyPicked) {
    draftActivityLine.userQtyReceived = qtyPicked;
    draftActivityLine.qtyReceived += qtyPicked;
  }

  updateLineStatus({
    draftWFProcess,
    activityId,
    lineId,
  });

  return draftState;
};

const updateLineStatus = ({ draftWFProcess, activityId, lineId }) => {
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
    draftActivityDataStored.lines.forEach((line) => {
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
  return lines.map((line) => {
    const aggregateToLU = line.aggregateToLU || null;
    return {
      ...line,
      aggregateToLU,
    };
  });
};

registerHandler({
  componentType: COMPONENT_TYPE,
  normalizeComponentProps: ({ componentProps }) => {
    console.log('normalizeComponentProps for ', componentProps);
    return {
      ...componentProps,
      lines: normalizeLines(componentProps.lines),
    };
  },
  computeActivityDataStoredInitialValue: ({ componentProps }) => {
    console.log('computeActivityDataStoredInitialValue for ', componentProps);
    return { lines: componentProps.lines };
  },
  computeActivityStatus,
  mergeActivityDataStored: ({ draftActivityDataStored }) => {
    draftActivityDataStored.isAlwaysAvailableToUser = true;
    return draftActivityDataStored;
  },
});
