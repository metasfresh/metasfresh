import * as types from '../../constants/PickingActionTypes';
import { isDraft, current } from 'immer';
import { updateUserEditable } from './utils';
import * as CompleteStatus from '../../constants/CompleteStatus';
import { registerHandler } from './activityStateHandlers';

const COMPONENT_TYPE = 'picking/pickProducts';

export const pickingReducer = ({ draftState, action }) => {
  switch (action.type) {
    case types.UPDATE_PICKING_STEP_QTY: {
      return reduceOnUpdateQtyPicked(draftState, action.payload);
    }

    default: {
      return draftState;
    }
  }
};

const reduceOnUpdateQtyPicked = (draftState, payload) => {
  const { wfProcessId, activityId, lineId, stepId, altStepId, qtyPicked, qtyRejected, qtyRejectedReasonCode } = payload;

  const draftWFProcess = draftState[wfProcessId];
  const draftActivityDataStored = draftWFProcess.activities[activityId].dataStored;
  const draftStep = draftActivityDataStored.lines[lineId].steps[stepId];

  const draftPickFrom = altStepId ? draftStep.pickFromAlternatives[altStepId] : draftStep.mainPickFrom;
  draftPickFrom.qtyPicked = qtyPicked;
  draftPickFrom.qtyRejected = qtyRejected;
  draftPickFrom.qtyRejectedReasonCode = qtyRejectedReasonCode;

  allocatePickingAlternatives({
    draftActivityDataStored,
    lineId,
    stepId,
  });

  updateStepStatusAndRollup({
    draftWFProcess,
    activityId,
    lineId,
    stepId,
  });

  return draftState;
};

const extractDraftMapKeys = (draftMap) => {
  return isDraft(draftMap) ? Object.keys(current(draftMap)) : Object.keys(draftMap);
};

const allocatePickingAlternatives = ({ draftActivityDataStored, lineId, stepId }) => {
  const draftStep = draftActivityDataStored.lines[lineId].steps[stepId];

  //
  // Deallocate everything related to this step
  deallocateQtyAvailable({ draftActivityDataStored, stepId });

  //
  // Iterate and consider already picked alternatives
  const alternativeIds = extractDraftMapKeys(draftStep.pickFromAlternatives);
  let qtyToAllocateRemaining = draftStep.mainPickFrom.qtyRejected;
  for (let alternativeId of alternativeIds) {
    const pickFromAlternative = draftStep.pickFromAlternatives[alternativeId];

    if (pickFromAlternative.qtyPicked || pickFromAlternative.qtyRejected) {
      allocateQtyAvailable({
        draftActivityDataStored,
        stepId,
        alternativeId,
        qtyToAllocate: pickFromAlternative.qtyPicked || 0,
      });

      pickFromAlternative.qtyToPick = pickFromAlternative.qtyPicked + pickFromAlternative.qtyRejected;
      pickFromAlternative.isAllocated = true;
      pickFromAlternative.isDisplayed = true;

      qtyToAllocateRemaining -= pickFromAlternative.qtyPicked;
    } else {
      pickFromAlternative.isAllocated = false;
      pickFromAlternative.isDisplayed = false;
    }
  }

  //
  // Iterate and allocate the remaining items (which were not already considered)
  for (let alternativeId of alternativeIds) {
    const pickFromAlternative = draftStep.pickFromAlternatives[alternativeId];
    //console.log(`>>> qtyToAllocateRemaining=${qtyToAllocateRemaining}, alternativeId=${alternativeId}, isAllocated=${pickFromAlternative.isAllocated}`);

    if (!pickFromAlternative.isAllocated) {
      if (qtyToAllocateRemaining <= 0) {
        pickFromAlternative.qtyToPick = 0;
        pickFromAlternative.isAllocated = true;
        pickFromAlternative.isDisplayed = false;
        //console.log('   => qtyToPick=ZERO, isDisplayed=false');
      } else {
        const qtyAvailableInPool = computeQtyAvailableToAllocate({
          draftActivityDataStored,
          alternativeId,
        });
        //console.log('qtyAvailableInPool=', qtyAvailableInPool);

        const qtyToAllocateThisStep = Math.min(qtyToAllocateRemaining, qtyAvailableInPool);
        //console.log('qtyToAllocateThisStep=', qtyToAllocateThisStep);

        allocateQtyAvailable({
          draftActivityDataStored,
          stepId,
          alternativeId,
          qtyToAllocate: qtyToAllocateThisStep,
        });

        pickFromAlternative.qtyToPick = qtyToAllocateThisStep;
        pickFromAlternative.isAllocated = true;
        pickFromAlternative.isDisplayed = true;

        qtyToAllocateRemaining -= qtyToAllocateThisStep;

        //console.log(`   => qtyToPick=${pickFromAlternative.qtyToPick}, isDisplayed=true`);
      }
    }
  }
};

const computeQtyAvailableToAllocate = ({ draftActivityDataStored, alternativeId }) => {
  const pickFromAlternativesPool = draftActivityDataStored.pickFromAlternativesPool;
  const poolItem = pickFromAlternativesPool[alternativeId];
  if (!poolItem) {
    return 0;
  }

  return poolItem.qtyAvailable - computeQtyAllocated(poolItem);
};

const computeQtyAllocated = (alternativesPoolItem) => {
  if (!alternativesPoolItem.allocatedQtys) {
    return 0;
  }

  return Object.values(alternativesPoolItem.allocatedQtys).reduce((acc, qty) => acc + qty, 0);
};

const allocateQtyAvailable = ({ draftActivityDataStored, stepId, alternativeId, qtyToAllocate }) => {
  const pickFromAlternativesPool = draftActivityDataStored.pickFromAlternativesPool;

  const poolItem = pickFromAlternativesPool[alternativeId];
  if (!poolItem.allocatedQtys) {
    poolItem.allocatedQtys = {};
  }

  poolItem.allocatedQtys[stepId] = qtyToAllocate;
};

const deallocateQtyAvailable = ({ draftActivityDataStored, stepId }) => {
  const pickFromAlternativesPool = draftActivityDataStored.pickFromAlternativesPool;
  const alternativeIds = extractDraftMapKeys(pickFromAlternativesPool);

  for (let alternativeId of alternativeIds) {
    const poolItem = pickFromAlternativesPool[alternativeId];
    if (poolItem.allocatedQtys && poolItem.allocatedQtys[stepId]) {
      poolItem.allocatedQtys[stepId] = 0;
    }
  }
};

const updateStepStatusAndRollup = ({ draftWFProcess, activityId, lineId, stepId }) => {
  const draftStep = draftWFProcess.activities[activityId].dataStored.lines[lineId].steps[stepId];
  updateStepStatus({ draftStep });

  //
  // Rollup:
  updateLineStatusFromStepsAndRollup({ draftWFProcess, activityId, lineId });
};

const updateStepStatus = ({ draftStep }) => {
  draftStep.completeStatus = computeStepStatus({ draftStep });
};

const computeStepStatus = ({ draftStep }) => {
  const statuses = [];

  const mainPickFromStatus = computePickFromStatus(draftStep.mainPickFrom);
  statuses.push(mainPickFromStatus);

  if (draftStep.pickFromAlternatives) {
    const alternativeIds = extractDraftMapKeys(draftStep.pickFromAlternatives);
    alternativeIds.forEach((alternativeId) => {
      const pickFromAlternative = draftStep.pickFromAlternatives[alternativeId];
      if (pickFromAlternative.isDisplayed) {
        const pickFromAlternativeStatus = computePickFromStatus(pickFromAlternative);
        if (!statuses.includes(pickFromAlternativeStatus)) {
          statuses.push(pickFromAlternativeStatus);
        }
      }
    });
  }

  return CompleteStatus.reduceFromCompleteStatuesUniqueArray(statuses);
};

export const computePickFromStatus = (pickFrom) => {
  return pickFrom.qtyPicked || pickFrom.qtyRejected ? CompleteStatus.COMPLETED : CompleteStatus.NOT_STARTED;
};

const updateLineStatusFromStepsAndRollup = ({ draftWFProcess, activityId, lineId }) => {
  const draftLine = draftWFProcess.activities[activityId].dataStored.lines[lineId];
  updateLineStatusFromSteps({ draftLine });

  //
  // Rollup:
  updateActivityStatusFromLinesAndRollup({ draftWFProcess, activityId });
};

const updateLineStatusFromSteps = ({ draftLine }) => {
  draftLine.completeStatus = computeLineStatusFromSteps({ draftLine });
};

const computeLineStatusFromSteps = ({ draftLine }) => {
  const stepIds = extractDraftMapKeys(draftLine.steps);

  const stepStatuses = [];
  stepIds.forEach((stepId) => {
    const draftStep = draftLine.steps[stepId];
    if (!stepStatuses.includes(draftStep.completeStatus)) {
      stepStatuses.push(draftStep.completeStatus);
    }
  });

  return CompleteStatus.reduceFromCompleteStatuesUniqueArray(stepStatuses);
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

//
//
// ----------------------------------------------------------------------------
//
//

const normalizePickingLines = (lines) => {
  return lines.map((line) => {
    return {
      ...line,
      steps: line.steps.reduce((accum, step) => {
        accum[step.pickingStepId] = step;
        return accum;
      }, {}),
    };
  });
};

const mergeActivityDataStoredAndAllocateAlternatives = ({ draftActivityDataStored, fromActivity }) => {
  draftActivityDataStored.isAlwaysAvailableToUser = fromActivity.isAlwaysAvailableToUser ?? false;

  //
  // Copy lines
  draftActivityDataStored.lines = normalizePickingLines(fromActivity.componentProps.lines);
  draftActivityDataStored.qtyRejectedReasons = fromActivity.componentProps.qtyRejectedReasons;

  //
  // Copy Pick From Alternatives Pool
  draftActivityDataStored.pickFromAlternatives = null;
  delete draftActivityDataStored.pickFromAlternatives;
  draftActivityDataStored.pickFromAlternativesPool = fromActivity.componentProps.pickFromAlternatives.reduce(
    (accum, pickFromAlternative) => {
      accum[pickFromAlternative.id] = pickFromAlternative;
      return accum;
    },
    {}
  );

  //
  // Allocate step alternatives against the pool
  const draftLines = draftActivityDataStored.lines;
  for (let lineIdx = 0; lineIdx < draftLines.length; lineIdx++) {
    const draftLine = draftLines[lineIdx];
    for (let stepId of Object.keys(draftLine.steps)) {
      allocatePickingAlternatives({
        draftActivityDataStored,
        lineId: lineIdx,
        stepId,
      });
      //
    }
  }

  //
  // Update all statuses
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
  normalizeComponentProps: () => {}, // don't add componentProps to state
  mergeActivityDataStored: ({ draftActivityDataStored, fromActivity }) => {
    mergeActivityDataStoredAndAllocateAlternatives({ draftActivityDataStored, fromActivity });
  },
});
