import * as types from '../../constants/PickingActionTypes';
import { original } from 'immer';
import { updateActivitiesStatus } from './utils';

export const pickingReducer = ({ draftState, action }) => {
  switch (action.type) {
    case types.UPDATE_PICKING_STEP_SCANNED_HU_BARCODE: {
      return reduceOnScannedHUBarcode(draftState, action.payload);
    }

    case types.UPDATE_PICKING_STEP_QTY: {
      return reduceOnUpdateQtyPicked(draftState, action.payload);
    }

    default: {
      return draftState;
    }
  }
};

const reduceOnScannedHUBarcode = (draftState, payload) => {
  const { wfProcessId, activityId, lineId, stepId, scannedHUBarcode } = payload;

  const draftWFProcess = draftState[wfProcessId];
  const draftStep = draftWFProcess.activities[activityId].dataStored.lines[lineId].steps[stepId];
  draftStep.scannedHUBarcode = scannedHUBarcode;

  updateStepStatus({
    draftWFProcess,
    activityId,
    lineId,
    stepId,
  });

  return draftState;
};

const reduceOnUpdateQtyPicked = (draftState, payload) => {
  const { wfProcessId, activityId, lineId, stepId, qtyPicked } = payload;

  const draftWFProcess = draftState[wfProcessId];
  const draftStep = draftWFProcess.activities[activityId].dataStored.lines[lineId].steps[stepId];
  draftStep.qtyPicked = qtyPicked;
  draftStep.isComplete = !!(qtyPicked && qtyPicked >= 0);

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

  draftStep.isComplete =
    !!(draftStep.scannedHUBarcode && draftStep.scannedHUBarcode.length > 0) &&
    !!(draftStep.qtyPicked && draftStep.qtyPicked >= 0);

  updateLineStatusFromSteps({ draftWFProcess, activityId, lineId });
};

const updateLineStatusFromSteps = ({ draftWFProcess, activityId, lineId }) => {
  const draftLine = draftWFProcess.activities[activityId].dataStored.lines[lineId];

  const stepIds = Object.keys(original(draftLine.steps));

  let isLineCompleted = true;
  for (let stepId of stepIds) {
    const draftStep = draftLine.steps[stepId];
    const isStepCompleted = !!draftStep.isComplete;

    if (!isStepCompleted) {
      isLineCompleted = false;
      break;
    }
  }

  draftLine.isComplete = isLineCompleted;

  updateActivityStatusFromLines({ draftWFProcess, activityId });
};

const updateActivityStatusFromLines = ({ draftWFProcess, activityId }) => {
  const draftActivity = draftWFProcess.activities[activityId];

  const lineIds = Object.keys(original(draftActivity.dataStored.lines));

  let isActivityCompleted = true;
  for (let lineId of lineIds) {
    const draftLine = draftActivity.dataStored.lines[lineId];
    const isLineCompleted = !!draftLine.isComplete;
    if (!isLineCompleted) {
      isActivityCompleted = false;
      break;
    }
  }

  draftActivity.dataStored.isComplete = isActivityCompleted;

  updateActivitiesStatus({ draftWFProcess });
};
