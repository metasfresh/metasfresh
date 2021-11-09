import { original } from 'immer';

import * as types from '../../constants/DistributionActionTypes';
import { updateUserEditable } from './utils';
import * as CompleteStatus from '../../constants/CompleteStatus';
import { registerHandler } from './activityStateHandlers';

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
  const {
    wfProcessId,
    activityId,
    lineId,
    stepId,
    actualHUPicked,
    droppedToLocator,
    qtyPicked,
    qtyRejectedReasonCode,
  } = payload;

  const draftWFProcess = draftState[wfProcessId];
  const draftStep = draftWFProcess.activities[activityId].dataStored.lines[lineId].steps[stepId];
  draftStep.actualHuPicked = actualHUPicked;
  draftStep.droppedToLocator = droppedToLocator;
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
    !!(draftStep.actualHUPicked && draftStep.droppedToLocator) &&
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

const computeLineStatus = ({ draftLine }) => {
  const stepIds = Object.keys(original(draftLine.steps));

  if (stepIds.length > 0) {
    let countStepsCompleted = 0;
    for (let stepId of stepIds) {
      const draftStep = draftLine.steps[stepId];
      const stepCompleteStatus = draftStep.completeStatus || CompleteStatus.NOT_STARTED;

      if (stepCompleteStatus === CompleteStatus.COMPLETED) {
        countStepsCompleted++;
      }
    }

    if (countStepsCompleted === 0) {
      return CompleteStatus.NOT_STARTED;
    } else if (countStepsCompleted === stepIds.length) {
      return CompleteStatus.COMPLETED;
    } else {
      return CompleteStatus.IN_PROGRESS;
    }
  } else {
    // corner case, shall not happen: there are no steps in current line => consider it completed
    return CompleteStatus.COMPLETED;
  }
};

const updateActivityStatusFromLines = ({ draftWFProcess, activityId }) => {
  const draftActivity = draftWFProcess.activities[activityId];
  draftActivity.dataStored.completeStatus = computeActivityStatusFromLines({ draftActivity });
  console.log(`Update activity [${activityId} ]: completeStatus=${draftActivity.dataStored.completeStatus}`);

  //
  // Rollup:
  updateUserEditable({ draftWFProcess });
};

const computeActivityStatusFromLines = ({ draftActivity }) => {
  const lineIds = Object.keys(original(draftActivity.dataStored.lines));

  if (lineIds.length > 0) {
    let countLinesCompleted = 0;
    for (let lineId of lineIds) {
      const draftLine = draftActivity.dataStored.lines[lineId];
      const lineCompleteStatus = draftLine.completeStatus || CompleteStatus.NOT_STARTED;

      if (lineCompleteStatus === CompleteStatus.COMPLETED) {
        countLinesCompleted++;
      }
    }

    if (countLinesCompleted === 0) {
      return CompleteStatus.NOT_STARTED;
    } else if (countLinesCompleted === lineIds.length) {
      return CompleteStatus.COMPLETED;
    } else {
      return CompleteStatus.IN_PROGRESS;
    }
  } else {
    // corner case, shall not happen: there are no lines in current activity => consider it completed
    return CompleteStatus.COMPLETED;
  }
};

//
//
// ----------------------------------------------------------------------------
//
//

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
