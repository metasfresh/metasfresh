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

    case types.UPDATE_ALT_PICKING_STEP_QTY: {
      return reduceOnAlternatePickingQty(draftState, action.payload);
    }

    default: {
      return draftState;
    }
  }
};

const reduceOnAlternatePickingQty = (draftState, payload) => {
  const { wfProcessId, activityId, lineId, stepId, altStepId, scannedHUBarcode, qtyPicked, qtyRejectedReasonCode } =
    payload;

  const draftWFProcess = draftState[wfProcessId];
  const draftAltStep =
    draftWFProcess.activities[activityId].dataStored.lines[lineId].steps[stepId].altSteps.genSteps[altStepId];
  draftAltStep.scannedHUBarcode = scannedHUBarcode;
  draftAltStep.qtyPicked = qtyPicked;
  draftAltStep.qtyRejectedReasonCode = qtyRejectedReasonCode;

  return draftState;
};

const reduceOnUpdateQtyPicked = (draftState, payload) => {
  const { wfProcessId, activityId, lineId, stepId, scannedHUBarcode, qtyPicked, qtyRejectedReasonCode } = payload;

  const draftWFProcess = draftState[wfProcessId];
  const draftStep = draftWFProcess.activities[activityId].dataStored.lines[lineId].steps[stepId];
  draftStep.scannedHUBarcode = scannedHUBarcode;
  draftStep.qtyPicked = qtyPicked;
  draftStep.qtyRejectedReasonCode = qtyRejectedReasonCode;

  // update here the remaining qtyToPick (diff remaining to be picked with alternative steps)

  // also generate the alternative steps and populate the `genSteps`

  // sync pickFromAlternatives

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
  console.log('qtyToPick=', draftStep.qtyToPick);
  console.log('   => diff=', draftStep.qtyToPick - draftStep.qtyPicked === 0);

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

const normalizePickingLines = (lines) => {
  return lines.map((line) => {
    return {
      ...line,
      steps: line.steps.reduce((accum, step) => {
        accum[step.pickingStepId] = step;
        accum[step.pickingStepId].altSteps = {};
        accum[step.pickingStepId].altSteps.qtyToPick = 0;
        // Mock generated steps
        accum[step.pickingStepId].altSteps.genSteps = {
          1000514: {
            id: '1000514',
            locatorName: 'Hauptlager',
            huBarcode: '1000425',
            uom: 'Kg',
            qtyAvailable: 45,
            qtyPicked: 0,
          },
          1000515: {
            id: '1000515',
            locatorName: 'Hauptlager',
            huBarcode: '1000451',
            uom: 'Kg',
            qtyAvailable: 25,
            qtyPicked: 0,
          },
        };

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
      lines: normalizePickingLines(componentProps.lines),
    };
  },
  computeActivityDataStoredInitialValue: ({ componentProps }) => {
    console.log('computeActivityDataStoredInitialValue for ', componentProps);
    return { lines: componentProps.lines, pickFromAlternatives: componentProps.pickFromAlternatives };
  },
});
