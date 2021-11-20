import * as types from '../../constants/ManufacturingActionTypes';
import { registerHandler } from './activityStateHandlers';
import { computeLineStatus, updateActivityStatusFromLines } from './picking';

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
      existingHU: {
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

  draftActivityLine.qtyReceived = qtyPicked;

  updateLineStatus({
    draftWFProcess,
    activityId,
    lineId,
  });

  return draftState;
};

const updateLineStatus = ({ draftWFProcess, activityId, lineId }) => {
  const draftLine = draftWFProcess.activities[activityId].dataStored.lines[lineId];
  draftLine.completeStatus = computeLineStatus({ draftLine });
  console.log(`Update line [${activityId} ${lineId} ]: completeStatus=${draftLine.completeStatus}`);

  //
  // Rollup:
  updateActivityStatusFromLines({ draftWFProcess, activityId });
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
});
