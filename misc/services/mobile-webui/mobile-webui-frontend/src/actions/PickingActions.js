import {
  SWITCHOFF_LINES_VISIBILITY,
  UPDATE_PICKING_STEP_QTY,
  UPDATE_PICKING_SLOT_CODE,
  UPDATE_PICKING_STEP_DETECTED_CODE,
} from '../constants/ActionTypes';

/**
 * @method switchoffLinesVisibility
 * @summary sets the lines visibility to `false` and by doing this the steps will be visible
 */
export function switchoffLinesVisibility({ wfProcessId, activityId }) {
  return {
    type: SWITCHOFF_LINES_VISIBILITY,
    payload: { wfProcessId, activityId },
  };
}

/**
 * @method switchoffLinesVisibility
 * @summary sets the lines visibility to `false` and by doing this the steps will be visible
 */
export function updatePickingStepQty({ wfProcessId, activityId, lineIndex, stepId, qty }) {
  return {
    type: UPDATE_PICKING_STEP_QTY,
    payload: { wfProcessId, activityId, lineIndex, stepId, qty },
  };
}

/**
 * @method updatePickingStepDetectedCode
 * @summary sets the detected code for the step
 */
export function updatePickingStepDetectedCode({ wfProcessId, activityId, lineIndex, stepId, detectedCode }) {
  return {
    type: UPDATE_PICKING_STEP_DETECTED_CODE,
    payload: { wfProcessId, activityId, lineIndex, stepId, detectedCode },
  };
}

/**
 * @method updatePickingSlotCode
 * @summary update the scannedCode for a `common/scanBarcode` activity type with the detectedCode
 */
export function updatePickingSlotCode({ wfProcessId, activityId, detectedCode }) {
  return {
    type: UPDATE_PICKING_SLOT_CODE,
    payload: { wfProcessId, activityId, detectedCode },
  };
}
