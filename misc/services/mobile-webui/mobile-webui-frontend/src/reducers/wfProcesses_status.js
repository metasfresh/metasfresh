import { produce } from 'immer';
import { createSelector } from 'reselect';
import { get } from 'lodash';

import * as types from '../constants/ActionTypes';

export const initialState = {};
const initialWorkflowState = {
  headerProperties: {
    entries: [],
  },
  isSentToBackend: false,
  activities: {},
};

const wfSelector = (state, id) => state.wfProcesses_status[id] || null;

export const getWorkflowProcessStatus = createSelector(wfSelector, (workflow) =>
  workflow ? workflow : initialWorkflowState
);

export const getWorkflowProcessActivityLine = (wfProcess, activityId, lineId) => {
  const { activities } = wfProcess;

  if (activities[activityId]) {
    const line = get(activities, [activityId, 'dataStored', 'lines', lineId]);

    if (line) {
      return line;
    }
  }

  return null;
};

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
              isActivityEnabled: true,
              isComplete: false,
              scannedCode: '',
            };
            break;
          case 'common/confirmButton':
            tmpActivity.dataStored = {
              isActivityEnabled: false,
              isComplete: false,
            };
            break;
          default:
            tmpActivity.dataStored = {
              isComplete: false,
              isActivityEnabled: false,
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
    case types.SET_ACTIVITY_ENABLE_FLAG: {
      const { wfProcessId, activityId, isActivityEnabled } = action.payload;
      console.log('wfProcess:', wfProcessId);
      console.log('activityId:', activityId);
      console.log('isActivityEnabled:', isActivityEnabled);

      draftState[wfProcessId].activities[activityId].dataStored.isActivityEnabled = isActivityEnabled;

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
    case types.UPDATE_PICKING_STEP_DETECTED_CODE: {
      const { wfProcessId, activityId, lineIndex, stepId, detectedCode } = action.payload;

      draftState[wfProcessId].activities[activityId].dataStored.lines[lineIndex].steps[stepId].detectedCode =
        detectedCode;

      return draftState;
    }
    default:
      return draftState;
  }
}, initialState);

export default reducer;
