import { current, isDraft } from 'immer';
import * as CompleteStatus from '../../constants/CompleteStatus';
import { registerHandler } from './activityStateHandlers';

const COMPONENT_TYPE = 'picking/pickProducts';

// export const pickingReducer = ({ draftState, action }) => {
//   return draftState;
// };

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
export const computePickFromStatus = (pickFrom) => {
  return pickFrom.qtyPicked || pickFrom.qtyRejected ? CompleteStatus.COMPLETED : CompleteStatus.NOT_STARTED;
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
  return lines.reduce((accum, line) => {
    accum[line.pickingLineId] = {
      ...line,
      steps: normalizePickingSteps(line.steps),
    };
    return accum;
  }, {});
};

const normalizePickingSteps = (steps) => {
  return steps.reduce((accum, step) => {
    accum[step.pickingStepId] = step;
    return accum;
  }, {});
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
  for (let lineId of Object.keys(draftLines)) {
    const draftLine = draftLines[lineId];
    for (let stepId of Object.keys(draftLine.steps)) {
      allocatePickingAlternatives({
        draftActivityDataStored,
        lineId,
        stepId,
      });
      //
    }
  }

  updateActivityStatusFromLines({ draftActivityDataStored });

  return draftActivityDataStored;
};

//
//
// ------------------------------------------------------------------------------------
//
//

registerHandler({
  componentType: COMPONENT_TYPE,
  normalizeComponentProps: () => {}, // don't add componentProps to state
  mergeActivityDataStored: ({ draftActivityDataStored, fromActivity }) => {
    mergeActivityDataStoredAndAllocateAlternatives({ draftActivityDataStored, fromActivity });
  },
});
