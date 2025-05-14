import * as CompleteStatus from '../../constants/CompleteStatus';
import { registerHandler } from './activityStateHandlers';
import { current, isDraft } from 'immer';
import { getStepsArrayFromLine } from './index';

const COMPONENT_TYPE = 'distribution/move';

export const distributionReducer = ({ draftState, action }) => {
  switch (action.type) {
    default: {
      return draftState;
    }
  }
};

const updateStepStatus = ({ draftStep }) => {
  draftStep.isPickedFrom = computeIsPickedFrom({ draftStep });
  draftStep.completeStatus = computeStepStatus({ draftStep });
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

const updateLineFromSteps = ({ draftLine }) => {
  draftLine.completeStatus = computeLineStatusFromSteps({ draftLine });

  let qtyPicked = 0;
  for (const step of getStepsArrayFromLine(draftLine)) {
    qtyPicked += step.qtyPicked;
  }
  draftLine.qtyPicked = qtyPicked;
};

const computeLineStatusFromSteps = ({ draftLine }) => {
  const stepIds = extractDraftMapKeys(draftLine.steps);

  const stepsStatuses = stepIds.reduce((accum, stepId) => {
    const draftStep = draftLine.steps[stepId];
    const stepCompleteStatus = draftStep.completeStatus || CompleteStatus.NOT_STARTED;
    if (!accum.includes(stepCompleteStatus)) {
      accum.push(stepCompleteStatus);
    }
    return accum;
  }, []);

  if (stepsStatuses.length === 0) {
    return CompleteStatus.NOT_STARTED;
  }

  return CompleteStatus.reduceFromCompleteStatuesUniqueArray(stepsStatuses);
};

const updateActivityStatusFromLines = ({ draftActivityDataStored }) => {
  draftActivityDataStored.completeStatus = computeActivityStatusFromLines({ draftActivityDataStored });
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

const normalizeLinesArray = (lines) => {
  return lines.reduce((accum, line) => {
    accum[line.lineId] = normalizeLine(line);
    return accum;
  }, {});
};

const normalizeLine = (line) => {
  return {
    ...line,
    steps: line.steps.reduce((accum, step) => {
      accum[step.id] = step;
      return accum;
    }, {}),
  };
};

const mergeActivityDataStored = ({ draftActivityDataStored, fromActivity }) => {
  draftActivityDataStored.isAlwaysAvailableToUser = fromActivity.isAlwaysAvailableToUser ?? false;

  //
  // Copy lines
  draftActivityDataStored.lines = normalizeLinesArray(fromActivity.componentProps.lines);

  //
  // Update all statuses
  const draftLines = draftActivityDataStored.lines;
  console.log('mergeActivityDataStored', { draftLines, fromActivity });
  for (let lineId of Object.keys(draftLines)) {
    const draftLine = draftLines[lineId];

    for (let stepId of Object.keys(draftLine.steps)) {
      const draftStep = draftLine.steps[stepId];
      updateStepStatus({ draftStep });
    }

    updateLineFromSteps({ draftLine });
  }
  updateActivityStatusFromLines({ draftActivityDataStored });

  return draftActivityDataStored;
};

registerHandler({
  componentType: COMPONENT_TYPE,
  normalizeComponentProps: ({ componentProps }) => {
    return {
      ...componentProps,
      lines: normalizeLinesArray(componentProps.lines),
    };
  },

  mergeActivityDataStored: mergeActivityDataStored,
});
