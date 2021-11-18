import * as types from '../../constants/PickingActionTypes';
import { original } from 'immer';
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
  const {
    wfProcessId,
    activityId,
    lineId,
    stepId,
    altStepId,
    scannedHUBarcode,
    qtyPicked,
    qtyRejectedReasonCode,
    qtyRejected,
  } = payload;

  console.log('Original:', original(draftState));

  const draftWFProcess = draftState[wfProcessId];

  const draftStep = altStepId
    ? draftWFProcess.activities[activityId].dataStored.lines[lineId].steps[stepId].altSteps.genSteps[altStepId]
    : draftWFProcess.activities[activityId].dataStored.lines[lineId].steps[stepId];
  draftStep.scannedHUBarcode = scannedHUBarcode;
  draftStep.qtyPicked = qtyPicked;
  draftStep.qtyRejectedReasonCode = qtyRejectedReasonCode;

  console.log('ALT_STEP_ID =>', altStepId);
  console.log('QtyRejected =====>', qtyRejected);

  if (!altStepId && qtyRejected) {
    draftState = generateAlternativeSteps({
      draftState,
      wfProcessId,
      activityId,
      lineId,
      stepId,
      qtyToAllocate: qtyRejected,
    });
  }

  updateStepStatus({
    draftWFProcess,
    activityId,
    lineId,
    stepId,
  });

  return draftState;
};

export const generateAlternativeSteps = ({ draftState, wfProcessId, activityId, lineId, stepId, qtyToAllocate }) => {
  const draftDataStored = draftState[wfProcessId].activities[activityId].dataStored;
  const draftDataStoredOrig = original(draftDataStored);
  const draftStep = draftDataStored.lines[lineId].steps[stepId];
  const { pickFromAlternatives: alternativesPool } = draftDataStoredOrig;

  console.log('qtyToAllocate ===>', qtyToAllocate);

  let qtyToAllocateRemaining = qtyToAllocate;

  for (let idx = 0; idx < alternativesPool.length; idx++) {
    deallocateQtyAvailable({ idx, stepId, draftDataStored });
  }

  for (let idx = 0; idx < alternativesPool.length; idx++) {
    if (qtyToAllocateRemaining === 0) {
      break;
    } else {
      const alternativesPoolItem = alternativesPool[idx];
      const qtyAvailableToAllocateInThisStep = computeQtyAvailableToAllocate({ alternativesPoolItem });

      console.log('qtyToAllocateRemaining =>', qtyToAllocateRemaining);
      console.log('qtyAvailableToAllocateInThisStep=>', qtyAvailableToAllocateInThisStep);

      const qtyToAllocateThisStep = Math.min(qtyToAllocateRemaining, qtyAvailableToAllocateInThisStep);
      console.log('AFTER_MIN: ', qtyToAllocateThisStep);

      draftStep.altSteps.genSteps[alternativesPoolItem.id] = {
        id: alternativesPoolItem.id,
        locatorName: alternativesPoolItem.locatorName,
        huBarcode: alternativesPoolItem.huBarcode,
        uom: alternativesPoolItem.uom,
        qtyAvailable: qtyToAllocateThisStep,
        qtyPicked: 0,
      };

      allocateQtyAvailable({
        idx,
        draftDataStored,
        stepId,
        qtyToAllocate: qtyToAllocateThisStep,
      });

      qtyToAllocateRemaining = qtyToAllocateRemaining - qtyToAllocateThisStep;
    }
  }

  return draftState;
};

const computeQtyAvailableToAllocate = ({ alternativesPoolItem }) => {
  return alternativesPoolItem.qtyAvailable - computeQtyAllocated({ alternativesPoolItem });
};

const computeQtyAllocated = ({ alternativesPoolItem }) => {
  if (!alternativesPoolItem.allocatedQtys) {
    return 0;
  }

  return Object.values(alternativesPoolItem.allocatedQtys).reduce((acc, qty) => acc + qty, 0);
};

const allocateQtyAvailable = ({ idx, draftDataStored, stepId, qtyToAllocate }) => {
  const alternativesPoolItemOrig = original(draftDataStored.pickFromAlternatives[idx]);
  if (!alternativesPoolItemOrig.allocatedQtys) {
    draftDataStored.pickFromAlternatives[idx].allocatedQtys = {};
  }
  draftDataStored.pickFromAlternatives[idx].allocatedQtys[stepId] = qtyToAllocate;
};

const deallocateQtyAvailable = ({ idx, stepId, draftDataStored }) => {
  const alternativesPoolItemOrig = original(draftDataStored.pickFromAlternatives[idx]);
  if (alternativesPoolItemOrig.allocatedQtys) {
    delete draftDataStored.pickFromAlternatives[idx].allocatedQtys[stepId];
  }
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
  console.log('qtyToPick=', draftStep.qtyToPick);
  console.log('   => diff=', draftStep.qtyToPick - draftStep.qtyPicked === 0);

  // NOTE: for now we consider a step completed if the user reported something on it

  const isStepCompleted =
    // Barcode is set
    !!(draftStep.scannedHUBarcode && draftStep.scannedHUBarcode.length > 0) &&
    // and is completely picked or a reject code is set
    (draftStep.qtyToPick - draftStep.qtyPicked === 0 || !!draftStep.qtyRejectedReasonCode);

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

export const computeLineStatus = ({ draftLine }) => {
  const stepItems = original(draftLine.steps);
  const stepIds = Object.keys(stepItems);

  if (stepIds.length > 0) {
    let countStepsCompleted = 0;
    for (let stepId of stepIds) {
      // let sumAltStepsQtysPicked = 0;
      // let remainingQty = stepItems[stepId].mainPickFrom.qtyRejected;

      const draftStep = draftLine.steps[stepId];
      let stepCompleteStatus = draftStep.completeStatus || CompleteStatus.NOT_STARTED;

      // let { genSteps } = stepItems[stepId].altSteps;
      // for (let altItem in genSteps) {
      //   sumAltStepsQtysPicked = sumAltStepsQtysPicked + genSteps[altItem].qtyPicked;
      // }

      // if (remainingQty - sumAltStepsQtysPicked === 0) {
      //   stepCompleteStatus = CompleteStatus.COMPLETED;
      //   draftLine.steps[stepId].completeStatus = stepCompleteStatus;
      // }

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

export const updateActivityStatusFromLines = ({ draftWFProcess, activityId }) => {
  const draftActivity = draftWFProcess.activities[activityId];
  draftActivity.dataStored.completeStatus = computeActivityStatusFromLines({ draftActivity });
  console.log(`Update activity [${activityId} ]: completeStatus=${draftActivity.dataStored.completeStatus}`);

  //
  // Rollup:
  updateUserEditable({ draftWFProcess });
};

export const computeActivityStatusFromLines = ({ draftActivity }) => {
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

const normalizePickingLines = (lines) => {
  return lines.map((line) => {
    return {
      ...line,
      steps: line.steps.reduce((accum, step) => {
        accum[step.pickingStepId] = step;
        accum[step.pickingStepId].altSteps = { qtyToPick: 0, genSteps: {} };

        // Mock generated steps - used for testing w/o real data
        // accum[step.pickingStepId].altSteps.genSteps = {
        //   1000819: {
        //     id: '1000819',
        //     locatorName: 'Hauptlager',
        //     huBarcode: '1000437',
        //     uom: 'Kg',
        //     qtyAvailable: 45,
        //     qtyPicked: 0,
        //   },
        //   1000820: {
        //     id: '1000820',
        //     locatorName: 'Hauptlager',
        //     huBarcode: '1000463',
        //     uom: 'Kg',
        //     qtyAvailable: 25,
        //     qtyPicked: 0,
        //   },
        // };

        return accum;
      }, {}),
    };
  });
};

registerHandler({
  componentType: COMPONENT_TYPE,
  normalizeComponentProps: ({ componentProps }) => {
    console.log('picking: normalizeComponentProps for ', componentProps);
    return {
      ...componentProps,
      lines: normalizePickingLines(componentProps.lines),
    };
  },
  computeActivityDataStoredInitialValue: ({ componentProps }) => {
    console.log('picking: computeActivityDataStoredInitialValue for ', componentProps);
    return { lines: componentProps.lines, pickFromAlternatives: componentProps.pickFromAlternatives };
  },

  mergeActivityDataStored: ({ componentType, draftActivityDataStored, fromActivity }) => {
    const { lines } = fromActivity.componentProps;
    console.log('componentType =>', componentType);
    let genSteps = {};

    // loop within steps
    for (let lineIdx = 0; lineIdx < lines.length; lineIdx++) {
      console.log('ABOUT_TO_SEND:', draftActivityDataStored.dataStored.lines[lineIdx]);
      // computeLineStatus({ draftLine: draftActivityDataStored.dataStored.lines[lineIdx] });

      for (let stepIdx = 0; stepIdx < lines[lineIdx].steps.length; stepIdx++) {
        let step = lines[lineIdx].steps[stepIdx];
        // let { qtyRejected } = lines[lineIdx].steps[stepIdx].mainPickFrom;

        // allocateQtys in sync with the data received from the BE
        let pickFromAlternatives = fromActivity.componentProps.pickFromAlternatives;
        for (let altKey in pickFromAlternatives) {
          if (step.pickFromAlternatives[pickFromAlternatives[altKey].id].qtyPicked > 0) {
            genSteps[pickFromAlternatives[altKey].id] = pickFromAlternatives[altKey];
            genSteps[pickFromAlternatives[altKey].id].qtyPicked =
              step.pickFromAlternatives[pickFromAlternatives[altKey].id].qtyPicked;
            pickFromAlternatives[altKey].allocatedQtys = {
              [pickFromAlternatives[altKey].id]: step.pickFromAlternatives[pickFromAlternatives[altKey].id].qtyPicked,
            };
          } else {
            pickFromAlternatives[altKey].allocatedQtys = {};
          }
        }
        draftActivityDataStored.dataStored.lines[lineIdx].steps[step.pickingStepId].pickFromAlternatives =
          pickFromAlternatives;

        draftActivityDataStored.dataStored.lines[lineIdx].steps[step.pickingStepId].altSteps.genSteps = genSteps;
      }
    }
    return draftActivityDataStored;
  },
});
