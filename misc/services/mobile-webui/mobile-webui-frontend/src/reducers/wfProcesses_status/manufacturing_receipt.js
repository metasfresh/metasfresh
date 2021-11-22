import * as types from '../../constants/ManufacturingActionTypes';
import * as CompleteStatus from '../../constants/CompleteStatus';
import { registerHandler } from './activityStateHandlers';
import { updateActivityStatusFromLines, computeActivityStatusFromLines } from './picking';

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
  updateActivityStatusFromLines({ draftWFProcess, activityId });
};

const computeLineStatus = ({ qtyToReceive, qtyReceived, aggregateToLU, currentReceivingHU }) => {
  if (qtyToReceive === qtyReceived && aggregateToLU) {
    return CompleteStatus.COMPLETED;
  } else if (qtyToReceive === qtyReceived || aggregateToLU || currentReceivingHU) {
    return CompleteStatus.IN_PROGRESS;
  } else {
    return CompleteStatus.NOT_STARTED;
  }
};

const computeActivityStatus = ({ draftActivity }) => {
  if (draftActivity.dataStored.lines) {
    draftActivity.dataStored.lines.forEach((line) => {
      line.completeStatus = computeLineStatus(line);
    });
  }
  return computeActivityStatusFromLines({ draftActivity });
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
});
