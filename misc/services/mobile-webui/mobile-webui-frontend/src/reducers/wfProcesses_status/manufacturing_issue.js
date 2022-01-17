import * as types from '../../constants/ManufacturingActionTypes';
import * as CompleteStatus from '../../constants/CompleteStatus';
import { registerHandler } from './activityStateHandlers';
import { current, isDraft } from 'immer';
import { updateUserEditable } from './utils';

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
  const { wfProcessId, activityId, lineId, stepId, qtyPicked, qtyRejectedReasonCode } = payload;

  const draftWFProcess = draftState[wfProcessId];
  const draftStep = draftWFProcess.activities[activityId].dataStored.lines[lineId].steps[stepId];

  draftStep.qtyIssued = qtyPicked;
  draftStep.qtyRejected = draftStep.qtyToIssue - qtyPicked;
  draftStep.qtyRejectedReasonCode = qtyRejectedReasonCode;

  updateStepStatusAndRollup({
    draftWFProcess,
    activityId,
    lineId,
    stepId,
  });

  return draftState;
};

const updateStepStatusAndRollup = ({ draftWFProcess, activityId, lineId, stepId }) => {
  const draftStep = draftWFProcess.activities[activityId].dataStored.lines[lineId].steps[stepId];

  draftStep.completeStatus = computeStepStatus({ draftStep });
  console.log(`Update step [${activityId} ${lineId} ${stepId} ]: completeStatus=${draftStep.completeStatus}`);

  //
  // Rollup:
  updateLineStatusFromStepsAndRollup({ draftWFProcess, activityId, lineId });
};

// @VisibleForTesting
export const computeStepStatus = ({ draftStep }) => {
  const isStepCompleted = !!draftStep.qtyIssued || !!draftStep.qtyRejectedReasonCode;
  return isStepCompleted ? CompleteStatus.COMPLETED : CompleteStatus.NOT_STARTED;
};

const updateLineStatusFromStepsAndRollup = ({ draftWFProcess, activityId, lineId }) => {
  const draftLine = draftWFProcess.activities[activityId].dataStored.lines[lineId];
  draftLine.completeStatus = computeLineStatusFromSteps({ draftLine });
  draftLine.qtyIssued = computeLineQtyIssuedFromSteps({ draftLine });
  console.log(`Update line [${activityId} ${lineId} ]: completeStatus=${draftLine.completeStatus}`);

  //
  // Rollup:
  updateActivityStatusFromLinesAndRollup({ draftWFProcess, activityId });
};

const computeLineStatusFromSteps = ({ draftLine }) => {
  const stepIds = extractDraftMapKeys(draftLine.steps);

  const stepStatuses = [];
  let hasCompletedAlternativeSteps = false;
  stepIds.forEach((stepId) => {
    const draftStep = draftLine.steps[stepId];

    const status = draftStep.completeStatus;
    const isAlternativeStep = draftStep.qtyToIssue === 0;
    if (!isAlternativeStep) {
      if (!stepStatuses.includes(status)) {
        stepStatuses.push(status);
      }
    } else {
      if (status === CompleteStatus.COMPLETED) {
        hasCompletedAlternativeSteps = true;
      }
    }
  });

  let lineStatus = CompleteStatus.reduceFromCompleteStatuesUniqueArray(stepStatuses);

  if (lineStatus === CompleteStatus.NOT_STARTED && hasCompletedAlternativeSteps) {
    lineStatus = CompleteStatus.COMPLETED;
  }

  return lineStatus;
};

// @VisibleForTesting
export const computeLineQtyIssuedFromSteps = ({ draftLine }) => {
  const steps = isDraft(draftLine) ? Object.values(current(draftLine.steps)) : Object.values(draftLine.steps);
  return steps.reduce((acc, { qtyIssued }) => acc + (qtyIssued ? qtyIssued : 0), 0);
};

const extractDraftMapKeys = (draftMap) => {
  return isDraft(draftMap) ? Object.keys(current(draftMap)) : Object.keys(draftMap);
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

// @VisibleForTesting
export const computeActivityStatus = ({ draftActivity }) => {
  const draftActivityDataStored = draftActivity.dataStored;
  if (draftActivityDataStored.lines) {
    draftActivityDataStored.lines.forEach((line) => {
      if (line.steps) {
        Object.values(line.steps).forEach((step) => {
          step.completeStatus = computeStepStatus({ draftStep: step });
        });

        line.completeStatus = computeLineStatusFromSteps({ draftLine: line });
      }
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
    return {
      ...componentProps,
      lines: normalizeLines(componentProps.lines),
    };
  },
  computeActivityDataStoredInitialValue: ({ componentProps }) => {
    return { lines: componentProps.lines };
  },
  computeActivityStatus,
});
