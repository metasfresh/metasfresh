import * as types from '../../constants/PickingActionTypes';

export const pickingReducer = ({ draftState, action }) => {
  switch (action.type) {
    case types.UPDATE_PICKING_STEP_QTY: {
      const { wfProcessId, activityId, lineId, stepId, qtyPicked } = action.payload;

      draftState[wfProcessId].activities[activityId].dataStored.lines[lineId].steps[stepId].qtyPicked = qtyPicked;

      return draftState;
    }
    case types.UPDATE_PICKING_STEP_SCANNED_HU_BARCODE: {
      const { wfProcessId, activityId, lineId, stepId, scannedHUBarcode } = action.payload;

      const draftStep = draftState[wfProcessId].activities[activityId].dataStored.lines[lineId].steps[stepId];
      draftStep.scannedHUBarcode = scannedHUBarcode;

      return draftState;
    }
    default: {
      return draftState;
    }
  }
};
