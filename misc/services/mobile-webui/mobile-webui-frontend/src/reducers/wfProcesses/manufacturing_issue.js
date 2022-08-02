import * as types from '../../constants/ManufacturingActionTypes';
import * as CompleteStatus from '../../constants/CompleteStatus';
import { registerHandler } from './activityStateHandlers';
import { current, isDraft } from 'immer';
import { updateUserEditable } from './utils';

const COMPONENT_TYPE = 'manufacturing/rawMaterialsIssue';

export const manufacturingReducer = ({ draftState, action }) => {
  switch (action.type) {
    case types.UPDATE_MANUFACTURING_ISSUE_QTY: {
      return reduceOnUpdateQtyIssued(draftState, action.payload);
    }

    default: {
      return draftState;
    }
  }
};

const reduceOnUpdateQtyIssued = (draftState, payload) => {
  const { wfProcessId, activityId, lineId, stepId, qtyPicked, qtyRejectedReasonCode } = payload;

  const draftWFProcess = draftState[wfProcessId];
  const draftStep = draftWFProcess.activities[activityId].dataStored.lines[lineId].steps[stepId];

  draftStep.qtyIssued = qtyPicked;
  draftStep.qtyRejected = draftStep.qtyToIssue - qtyPicked;
  draftStep.qtyRejectedReasonCode = qtyRejectedReasonCode;

  updateStepAndRollup({
    draftWFProcess,
    activityId,
    lineId,
    stepId,
  });

  return draftState;
};

const updateStepAndRollup = ({ draftWFProcess, activityId, lineId, stepId }) => {
  const draftStep = draftWFProcess.activities[activityId].dataStored.lines[lineId].steps[stepId];
  updateStep({ draftStep });
  console.log(`Update step [${activityId} ${lineId} ${stepId} ]: completeStatus=${draftStep.completeStatus}`);

  //
  // Rollup:
  updateLineFromStepsAndRollup({ draftWFProcess, activityId, lineId });
};

const updateStep = ({ draftStep }) => {
  draftStep.completeStatus = computeStepStatus({ draftStep });
};

// @VisibleForTesting
export const computeStepStatus = ({ draftStep }) => {
  const isStepCompleted = !!draftStep.qtyIssued || !!draftStep.qtyRejectedReasonCode;
  return isStepCompleted ? CompleteStatus.COMPLETED : CompleteStatus.NOT_STARTED;
};

const updateLineFromStepsAndRollup = ({ draftWFProcess, activityId, lineId }) => {
  const draftLine = draftWFProcess.activities[activityId].dataStored.lines[lineId];
  updateLineFromSteps({ draftLine });
  console.log(`Update line [${activityId} ${lineId} ]: completeStatus=${draftLine.completeStatus}`);

  //
  // Rollup:
  updateActivityFromLinesAndRollup({ draftWFProcess, activityId });
};

const updateLineFromSteps = ({ draftLine }) => {
  draftLine.completeStatus = computeLineStatusFromSteps({ draftLine });
  draftLine.qtyIssued = computeLineQtyIssuedFromSteps({ draftLine });
  draftLine.qtyToIssueRemaining = Math.max(draftLine.qtyToIssue - draftLine.qtyIssued, 0);
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

  let lineStatus =
    stepStatuses.length > 0
      ? CompleteStatus.reduceFromCompleteStatuesUniqueArray(stepStatuses)
      : CompleteStatus.NOT_STARTED;

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

const updateActivityFromLinesAndRollup = ({ draftWFProcess, activityId }) => {
  const draftActivity = draftWFProcess.activities[activityId];
  updateActivityFromLines({ draftActivityDataStored: draftActivity.dataStored });

  //
  // Rollup:
  updateUserEditable({ draftWFProcess });
};

const updateActivityFromLines = ({ draftActivityDataStored }) => {
  draftActivityDataStored.completeStatus = computeActivityStatusFromLines({ draftActivityDataStored });
};

// @VisibleForTesting
export const updateActivityBottomUp = ({ draftActivityDataStored }) => {
  if (draftActivityDataStored.lines) {
    draftActivityDataStored.lines.forEach((line) => {
      if (line.steps) {
        Object.values(line.steps).forEach((step) => updateStep({ draftStep: step }));
      }
      updateLineFromSteps({ draftLine: line });
    });
  }

  updateActivityFromLines({ draftActivityDataStored });
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
      productName: line.productName,
      uom: line.uom,
      weightable: line.weightable,
      qtyToIssue: line.qtyToIssue,
      qtyToIssueMin: line.qtyToIssueMin,
      qtyToIssueMax: line.qtyToIssueMax,
      qtyToIssueTolerancePerc: line.qtyToIssueTolerancePerc,
      steps: line.steps.reduce((accum, step) => {
        accum[step.id] = step;
        return accum;
      }, {}),
    };
  });
};

registerHandler({
  componentType: COMPONENT_TYPE,
  normalizeComponentProps: () => {}, // don't add componentProps to state
  mergeActivityDataStored: ({ draftActivityDataStored, fromActivity }) => {
    draftActivityDataStored.lines = normalizeLines(fromActivity.componentProps.lines);
    draftActivityDataStored.scaleDevice = fromActivity.componentProps.scaleDevice;
    updateActivityBottomUp({ draftActivityDataStored });
  },
});
