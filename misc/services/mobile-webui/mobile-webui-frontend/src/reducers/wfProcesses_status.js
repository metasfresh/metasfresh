import { produce } from 'immer';
import * as types from '../constants/ActionTypes';

export const initialState = {};

const reducer = produce((draftState, action) => {
  switch (action.type) {
    case types.ADD_WORKFLOW_STATUS: {
      const { id, headerProperties, activities } = action.payload;
      const formattedActivities = activities.reduce((acc, activity, idx) => {
        const tmpActivity = { activity };

        // each state is different depending on the activity componentType
        switch (activity.componentType) {
          case 'common/scanBarcode':
            tmpActivity.dataStored = {
              isComplete: false,
              scannedCode: '',
            };
            break;
          default:
            tmpActivity.dataStored = {
              isComplete: false,
              isLinesListVisible: true,
              lines: activity.componentProps.lines,
            };
        }

        acc[idx + 1] = tmpActivity;

        return acc;
      }, {});

      draftState[id] = {
        headerProperties,
        isSentToBackend: false,
        activities: formattedActivities,
      };

      return draftState;
    }
    case types.SWITCHOFF_LINES_VISIBILITY: {
      const { wfProcessId, activityId } = action.payload;

      draftState[wfProcessId].activities[activityId].dataStored.isLinesListVisible = false;

      return draftState;
    }
    case types.UPDATE_PICKING_STEP_QTY: {
      const { wfProcessId, activityId, lineIndex, stepId, qty } = action.payload;

      draftState[wfProcessId].activities[activityId].dataStored.lines[lineIndex].steps[stepId].qtyPicked = qty;

      return draftState;
    }
    case types.UPDATE_PICKING_SLOT_CODE: {
      const { wfProcessId, activityId, detectedCode } = action.payload;

      draftState[wfProcessId].activities[activityId].dataStored.scannedCode = detectedCode;

      return draftState;
    }
    default:
      return draftState;
  }
}, initialState);

export default reducer;
