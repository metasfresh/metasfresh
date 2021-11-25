import * as types from '../../constants/DistributionActionTypes';
import * as CompleteStatus from '../../constants/CompleteStatus';
import { registerHandler } from './activityStateHandlers';
import { isDraft, original } from 'immer';
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

const updateStepStatusAndRollup = ({ draftWFProcess, activityId, lineId, stepId }) => {
  const draftStep = draftWFProcess.activities[activityId].dataStored.lines[lineId].steps[stepId];

  draftStep.isPickedFrom = computeIsPickedFrom({ draftStep });
  draftStep.completeStatus = computeStepStatus({ draftStep });
  console.log(`Update step [${activityId} ${lineId} ${stepId} ]: completeStatus=${draftStep.completeStatus}`);

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

const updateLineStatusFromStepsAndRollup = ({ draftWFProcess, activityId, lineId }) => {
  const draftLine = draftWFProcess.activities[activityId].dataStored.lines[lineId];
  draftLine.completeStatus = computeLineStatusFromSteps({ draftLine });
  console.log(`Update line [${activityId} ${lineId} ]: completeStatus=${draftLine.completeStatus}`);

  //
  // Rollup:
  updateActivityStatusFromLines({ draftWFProcess, activityId });
};

export const computeLineStatusFromSteps = ({ draftLine }) => {
  const stepIds = Object.keys(isDraft(draftLine) ? original(draftLine.steps) : draftLine.steps);

  const stepsStatuses = stepIds.reduce((accum, stepId) => {
    const draftStep = draftLine.steps[stepId];
    const stepCompleteStatus = draftStep.completeStatus || CompleteStatus.NOT_STARTED;
    if (!accum.includes(stepCompleteStatus)) {
      accum.push(stepCompleteStatus);
    }
    return accum;
  }, []);

  if (stepsStatuses.length === 0) {
    // corner case, shall not happen: there are no steps in current line => consider it completed
    return CompleteStatus.COMPLETED;
  } else if (stepsStatuses.length === 1) {
    return stepsStatuses[0];
  } else {
    return CompleteStatus.IN_PROGRESS;
  }
};

export const updateActivityStatusFromLines = ({ draftWFProcess, activityId }) => {
  const draftActivity = draftWFProcess.activities[activityId];
  draftActivity.dataStored.completeStatus = computeActivityStatusFromLines({ draftActivity });
  console.log(`Update activity [${activityId} ]: completeStatus=${draftActivity.dataStored.completeStatus}`);

  //
  // Rollup:
  updateUserEditable({ draftWFProcess });
};

export const computeActivityStatusFromLines = ({ draftActivity }) => {
  const lineIds = Object.keys(
    isDraft(draftActivity) ? original(draftActivity.dataStored.lines) : draftActivity.dataStored.lines
  );

  const linesStatuses = lineIds.reduce((accum, lineId) => {
    const draftLine = draftActivity.dataStored.lines[lineId];
    const stepCompleteStatus = draftLine.completeStatus || CompleteStatus.NOT_STARTED;
    if (!accum.includes(stepCompleteStatus)) {
      accum.push(stepCompleteStatus);
    }
    return accum;
  }, []);

  if (linesStatuses.length === 0) {
    // corner case, shall not happen: there are no steps in current line => consider it completed
    return CompleteStatus.COMPLETED;
  } else if (linesStatuses.length === 1) {
    return linesStatuses[0];
  } else {
    return CompleteStatus.IN_PROGRESS;
  }
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
  computeActivityStatus: ({ draftActivity }) => {
    for (let index in draftActivity.dataStored.lines) {
      const draftLine = draftActivity.dataStored.lines[index];

      const stepIds = Object.keys(draftLine.steps);
      for (const stepId of stepIds) {
        const draftStep = draftLine.steps[stepId];
        draftStep.isPickedFrom = computeIsPickedFrom({ draftStep });
        draftStep.completeStatus = computeStepStatus({ draftStep });
      }

      draftLine.completeStatus = computeLineStatusFromSteps({ draftLine });
    }

    draftActivity.dataStored.completeStatus = computeActivityStatusFromLines({ draftActivity });

    return draftActivity.dataStored.completeStatus;
  },
});
