import * as types from '../../constants/ManufacturingActionTypes';
import { registerHandler } from './activityStateHandlers';
import { computeLineStatus, updateActivityStatusFromLines } from './picking';

const COMPONENT_TYPE = 'manufacturing/rawMaterialIssue';

export const manufacturingReducer = ({ draftState, action }) => {
  switch (action.type) {
    case types.UPDATE_MANUFACTURING_ISSUE_QTY: {
      return reduceOnUpdateQtyPicked(draftState, action.payload);
    }

    default: {
      return draftState;
    }
  }
};

const reduceOnUpdateQtyPicked = (draftState, payload) => {
  const { wfProcessId, activityId, lineId, qtyIssued } = payload;

  console.log('PAYLOAD: ', payload);

  const draftWFProcess = draftState[wfProcessId];
  const draftActivityLine = draftWFProcess.activities[activityId].dataStored.lines[lineId];

  draftActivityLine.qtyIssued = qtyIssued;

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

registerHandler({
  componentType: COMPONENT_TYPE,
  normalizeComponentProps: ({ componentProps }) => {
    console.log('normalizeComponentProps for ', componentProps);
    return {
      ...componentProps,
    };
  },
  computeActivityDataStoredInitialValue: ({ componentProps }) => {
    console.log('computeActivityDataStoredInitialValue for ', componentProps);
    return { lines: componentProps.lines };
  },
});
