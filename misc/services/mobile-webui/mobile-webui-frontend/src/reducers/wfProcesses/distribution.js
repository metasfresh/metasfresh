import * as types from '../../constants/DistributionActionTypes';
import * as CompleteStatus from '../../constants/CompleteStatus';
import { registerHandler } from './activityStateHandlers';
import { current, isDraft } from 'immer';
import { updateUserEditable } from './utils';

const COMPONENT_TYPE = 'distribution/move';

export const distributionReducer = ({ draftState, action }) => {
  switch (action.type) {
    case types.UPDATE_DISTRIBUTION_PICK_FROM: {
      return reduceOnUpdatePickFrom(draftState, action.payload);
    }

    case types.UPDATE_DISTRIBUTION_DROP_TO: {
      return reduceOnDropTo(draftState, action.payload);
    }

    default: {
      return draftState;
    }
  }
};

const reduceOnUpdatePickFrom = (draftState, payload) => {
  const { wfProcessId, activityId, lineId, stepId, qtyPicked, qtyRejectedReasonCode } = payload;
  const draftWFProcess = draftState[wfProcessId];
  const draftStep = draftWFProcess.activities[activityId].dataStored.lines[lineId].steps[stepId];

  draftStep.qtyPicked = qtyPicked;
  draftStep.qtyRejectedReasonCode = qtyRejectedReasonCode;

  updateStepStatusAndRollup({
    draftWFProcess,
    activityId,
    lineId,
    stepId,
  });

  return draftState;
};

const reduceOnDropTo = (draftState, payload) => {
  const { wfProcessId, activityId, lineId, stepId } = payload;

  const draftWFProcess = draftState[wfProcessId];
  const draftStep = draftWFProcess.activities[activityId].dataStored.lines[lineId].steps[stepId];

  draftStep.droppedToLocator = true;

  updateStepStatusAndRollup({
    draftWFProcess,
    activityId,
    lineId,
    stepId,
  });

  return draftState;
};

const updateStepStatus = ({ draftStep }) => {
  draftStep.isPickedFrom = computeIsPickedFrom({ draftStep });
  draftStep.completeStatus = computeStepStatus({ draftStep });
};

const updateStepStatusAndRollup = ({ draftWFProcess, activityId, lineId, stepId }) => {
  const draftStep = draftWFProcess.activities[activityId].dataStored.lines[lineId].steps[stepId];
  updateStepStatus({ draftStep });

  //
  // Rollup:
  updateLineStatusFromStepsAndRollup({ draftWFProcess, activityId, lineId });
};

const computeStepStatus = ({ draftStep }) => {
  const isPickedFrom = computeIsPickedFrom({ draftStep });
  if (isPickedFrom) {
    return draftStep.droppedToLocator ? CompleteStatus.COMPLETED : CompleteStatus.IN_PROGRESS;
  } else {
    return CompleteStatus.NOT_STARTED;
  }
};

const computeIsPickedFrom = ({ draftStep }) => {
  return draftStep.qtyPicked !== 0 || !!draftStep.qtyRejectedReasonCode;
};

const updateLineStatusFromSteps = ({ draftLine }) => {
  draftLine.completeStatus = computeLineStatusFromSteps({ draftLine });
};

const updateLineStatusFromStepsAndRollup = ({ draftWFProcess, activityId, lineId }) => {
  const draftLine = draftWFProcess.activities[activityId].dataStored.lines[lineId];
  updateLineStatusFromSteps({ draftLine });

  //
  // Rollup:
  updateActivityStatusFromLinesAndRollup({ draftWFProcess, activityId });
};

export const computeLineStatusFromSteps = ({ draftLine }) => {
  const stepIds = extractDraftMapKeys(draftLine.steps);

  const stepsStatuses = stepIds.reduce((accum, stepId) => {
    const draftStep = draftLine.steps[stepId];
    const stepCompleteStatus = draftStep.completeStatus || CompleteStatus.NOT_STARTED;
    if (!accum.includes(stepCompleteStatus)) {
      accum.push(stepCompleteStatus);
    }
    return accum;
  }, []);

  return CompleteStatus.reduceFromCompleteStatuesUniqueArray(stepsStatuses);
};

const updateActivityStatusFromLines = ({ draftActivityDataStored }) => {
  draftActivityDataStored.completeStatus = computeActivityStatusFromLines({ draftActivityDataStored });
};

const updateActivityStatusFromLinesAndRollup = ({ draftWFProcess, activityId }) => {
  const draftActivityDataStored = draftWFProcess.activities[activityId].dataStored;
  updateActivityStatusFromLines({ draftActivityDataStored });

  //
  // Rollup:
  updateUserEditable({ draftWFProcess });
};

const extractDraftMapKeys = (draftMap) => {
  return isDraft(draftMap) ? Object.keys(current(draftMap)) : Object.keys(draftMap);
};

const computeActivityStatusFromLines = ({ draftActivityDataStored }) => {
  const lineIds = extractDraftMapKeys(draftActivityDataStored.lines);

  const linesStatuses = lineIds.reduce((accum, lineId) => {
    const draftLine = draftActivityDataStored.lines[lineId];
    const stepCompleteStatus = draftLine.completeStatus || CompleteStatus.NOT_STARTED;
    if (!accum.includes(stepCompleteStatus)) {
      accum.push(stepCompleteStatus);
    }
    return accum;
  }, []);

  return CompleteStatus.reduceFromCompleteStatuesUniqueArray(linesStatuses);
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

const mergeActivityDataStored = ({ draftActivityDataStored, fromActivity }) => {
  draftActivityDataStored.isAlwaysAvailableToUser = fromActivity.isAlwaysAvailableToUser ?? false;

  //
  // Copy lines
  draftActivityDataStored.lines = normalizeLines(fromActivity.componentProps.lines);

  //
  // Update all statuses
  const draftLines = draftActivityDataStored.lines;
  for (let lineIdx = 0; lineIdx < draftLines.length; lineIdx++) {
    const draftLine = draftLines[lineIdx];

    for (let stepId of Object.keys(draftLine.steps)) {
      const draftStep = draftLine.steps[stepId];
      updateStepStatus({ draftStep });
    }

    updateLineStatusFromSteps({ draftLine });
  }
  updateActivityStatusFromLines({ draftActivityDataStored });

  return draftActivityDataStored;
};

registerHandler({
  componentType: COMPONENT_TYPE,
  normalizeComponentProps: ({ componentProps }) => {
    return {
      ...componentProps,
      lines: normalizeLines(componentProps.lines),
    };
  },

  mergeActivityDataStored: mergeActivityDataStored,
});
