import * as types from '../../constants/ManufacturingActionTypes';
import * as CompleteStatus from '../../constants/CompleteStatus';
import { registerHandler } from './activityStateHandlers';
import { computeLineStatus, updateActivityStatusFromLines, computeActivityStatusFromLines } from './picking';

const COMPONENT_TYPE = 'manufacturing/rawMaterialsIssue';

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
  const { wfProcessId, activityId, lineId, stepId, qtyPicked } = payload;

  const draftWFProcess = draftState[wfProcessId];
  const draftActivityStep = draftWFProcess.activities[activityId].dataStored.lines[lineId].steps[stepId];

  draftActivityStep.qtyIssued = qtyPicked;

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
  console.log('qtyIssued=', draftStep.qtyIssued);
  console.log('qtyToIssue=', draftStep.qtyToIssue);
  console.log('   => diff=', draftStep.qtyToIssue - draftStep.qtyIssued === 0);

  const isStepCompleted =
    draftStep.qtyIssued !== null &&
    // is completely picked or a reject code is set
    (draftStep.qtyToIssue - draftStep.qtyIssued === 0 || !!draftStep.qtyRejectedReasonCode);

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

const computeActivityStatus = ({ draftActivity }) => {
  if (draftActivity.dataStored.lines) {
    draftActivity.dataStored.lines.forEach((line) => {
      if (line.steps) {
        Object.values(line.steps).forEach((step) => {
          step.completeStatus = computeStepStatus({ draftStep: step });
        });

        line.completeStatus = computeLineStatus({ draftLine: line });
      }
    });
  }
  return computeActivityStatusFromLines({ draftActivity });
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
  computeActivityStatus,
});
