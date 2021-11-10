import * as types from '../../constants/DistributionActionTypes';
import * as CompleteStatus from '../../constants/CompleteStatus';
import { registerHandler } from './activityStateHandlers';
import { computeLineStatus, updateActivityStatusFromLines } from './picking';

const COMPONENT_TYPE = 'distribution/move';

export const distributionReducer = ({ draftState, action }) => {
  switch (action.type) {
    case types.UPDATE_DISTRIBUTION_STEP_QTY: {
      return reduceOnUpdateQtyPicked(draftState, action.payload);
    }

    default: {
      return draftState;
    }
  }
};

const reduceOnUpdateQtyPicked = (draftState, payload) => {
  const { wfProcessId, activityId, lineId, stepId, actualHUPicked, locatorBarcode, qtyPicked, qtyRejectedReasonCode } =
    payload;

  const draftWFProcess = draftState[wfProcessId];
  const draftStep = draftWFProcess.activities[activityId].dataStored.lines[lineId].steps[stepId];
  draftStep.actualHuPicked = actualHUPicked;
  draftStep.locatorBarcode = locatorBarcode;
  draftStep.qtyPicked = qtyPicked;
  draftStep.qtyRejectedReasonCode = qtyRejectedReasonCode;

  updateStepStatus({
    draftWFProcess,
    activityId,
    lineId,
    stepId,
  });

  return draftState;
};

const updateStepStatus = ({ draftWFProcess, activityId, lineId, stepId }) => {
  const draftStep = draftWFProcess.activities[activityId].dataStored.lines[lineId].steps[stepId];

  draftStep.completeStatus = computeStepStatus({ draftStep });
  console.log(`Update step [${activityId} ${lineId} ${stepId} ]: completeStatus=${draftStep.completeStatus}`);

  //
  // Rollup:
  updateLineStatusFromSteps({ draftWFProcess, activityId, lineId });
};

const computeStepStatus = ({ draftStep }) => {
  console.log('qtyPicked=', draftStep.qtyPicked);
  console.log('qtyToMove=', draftStep.qtyToMove);
  console.log('   => diff=', draftStep.qtyToMove - draftStep.qtyPicked === 0);

  const isStepCompleted =
    // Barcode is set
    !!(draftStep.actualHUPicked && draftStep.locatorBarcode) &&
    // and is completely picked or a reject code is set
    (draftStep.qtyToMove - draftStep.qtyPicked === 0 || !!draftStep.qtyRejectedReasonCode);

  return isStepCompleted ? CompleteStatus.COMPLETED : CompleteStatus.NOT_STARTED;
};

const updateLineStatusFromSteps = ({ draftWFProcess, activityId, lineId }) => {
  const draftLine = draftWFProcess.activities[activityId].dataStored.lines[lineId];
  draftLine.completeStatus = computeLineStatus({ draftLine });
  console.log(`Update line [${activityId} ${lineId} ]: completeStatus=${draftLine.completeStatus}`);

  //
  // Rollup:
  updateActivityStatusFromLines({ draftWFProcess, activityId });
};

const normalizeLines = (lines) => {
  return lines.map((line) => {
    return {
      ...line,
      steps: line.steps.reduce((accum, step) => {
        accum[step.id] = step;
        return accum;
      }, {}),
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
