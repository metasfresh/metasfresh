import { original, produce } from 'immer';
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

const mergeWFProcessToState = ({ draftWFProcess, fromWFProcess }) => {
  const { headerProperties, activities } = fromWFProcess;

  draftWFProcess = {
    ...draftWFProcess,
    headerProperties,
    isSentToBackend: true,
  };

  mergeActivitiesToState({
    draftActivities: draftWFProcess.activities,
    fromActivities: activities,
  });
};

const mergeActivitiesToState = ({ draftActivities, fromActivities }) => {
  fromActivities.forEach((fromActivity) => {
    console.log('fromActivity: %o', fromActivity);
    draftActivities[fromActivity.activityId]['caption'] = fromActivity.caption;
    draftActivities[fromActivity.activityId]['componentProps'] = fromActivity.componentProps;
    draftActivities[fromActivity.activityId]['componentType'] = fromActivity.componentType;
  });
};

const reducer = produce((draftState, action) => {
  switch (action.type) {
    case types.ADD_WORKFLOW_STATUS: {
      const { id, headerProperties, activities } = action.payload;
      const current = draftState[id] ? original(draftState[id]).activities : null;

      const formattedActivities = activities.reduce((acc, activity) => {
        let tmpActivity = { ...activity }; // TODO: What's this for ?
        const activityId = activity.activityId;

        // each state is different depending on the activity componentType
        switch (activity.componentType) {
          case 'common/scanBarcode':
            tmpActivity.dataStored = {
              isActivityEnabled: true,
              isComplete: false,
              scannedBarcode: '',
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

        // we might be continuing an existing workflow status
        const currentActivityTmp = current ? current[activityId] : null;

        if (currentActivityTmp) {
          tmpActivity = { ...tmpActivity, currentActivityTmp };
        }

        acc[activityId] = tmpActivity;

        return acc;
      }, {});

      draftState[id] = {
        headerProperties,
        isSentToBackend: false,
        activities: formattedActivities,
      };

      return draftState;
    }

    case types.UPDATE_WORKFLOW_PROCESS: {
      const wfProcess = action.payload;

      mergeWFProcessToState({
        draftWFProcess: draftState[wfProcess.id],
        fromWFProcess: wfProcess,
      });

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
    case types.SET_SCANNED_BARCODE: {
      const { wfProcessId, activityId, scannedBarcode } = action.payload;

      draftState[wfProcessId].activities[activityId].dataStored.scannedBarcode = scannedBarcode;

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
