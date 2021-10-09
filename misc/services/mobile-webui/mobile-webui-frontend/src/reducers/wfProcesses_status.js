import { original, produce } from 'immer';
import { createSelector } from 'reselect';
import { get } from 'lodash';

import * as types from '../constants/ActionTypes';

export const initialState = {};
const initialWFProcessState = {
  headerProperties: {
    entries: [],
  },
  activities: {},
  isSentToBackend: false,
};

const wfSelector = (state, wfProcessId) => state.wfProcesses_status[wfProcessId] || null;

export const getWorkflowProcessStatus = createSelector(wfSelector, (wfProcess) =>
  wfProcess ? wfProcess : initialWFProcessState
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
    draftActivities[fromActivity.activityId]['caption'] = fromActivity.caption;
    draftActivities[fromActivity.activityId]['componentProps'] = fromActivity.componentProps;
    draftActivities[fromActivity.activityId]['componentType'] = fromActivity.componentType;
  });
};

const extractActivitiesStatus = ({ wfProcess }) => {
  const activitiesStatus = Object.values(wfProcess.activities).reduce((acc, activity) => {
    acc[activity.activityId] = {
      activityId: activity.activityId,
      isComplete: activity.dataStored.isComplete,
    };
    return acc;
  }, {});

  console.log('Extracted %o from %o', activitiesStatus, wfProcess);

  return activitiesStatus;
};

const updateActivitiesStatus = ({ draftWFProcess, activitiesStatus }) => {
  console.log('Updating WF activities status from %o', activitiesStatus);

  let previousActivityStatus = null;
  Object.values(activitiesStatus).forEach((activityStatus) => {
    console.log('activityStatus: %o', activityStatus);
    console.log('previousActivityStatus: %o', previousActivityStatus);

    let isActivityEnabled;
    if (previousActivityStatus == null) {
      // First activity: always enabled
      isActivityEnabled = true;
      console.log('=> isActivityEnabled: %o (first activity)', isActivityEnabled);
    } else {
      isActivityEnabled = previousActivityStatus.isComplete;
      console.log('isActivityEnabled: %o (checked if prev activity was completed)', isActivityEnabled);
    }

    draftWFProcess.activities[activityStatus.activityId].dataStored.isActivityEnabled = isActivityEnabled;

    previousActivityStatus = activityStatus;
  });
};

const reducer = produce((draftState, action) => {
  console.log('---------------------------------------------------------------------');
  console.log('*** wfProcesses_status reducer: %o', action);

  switch (action.type) {
    case types.ADD_WORKFLOW_STATUS: {
      const { id: wfProcessId, headerProperties, activities } = action.payload;
      const current = draftState[wfProcessId] ? original(draftState[wfProcessId]).activities : null;

      const formattedActivities = activities.reduce((acc, activity) => {
        let tmpActivity = { ...activity }; // TODO: What's this for ?
        const activityId = activity.activityId;

        // each state is different depending on the activity componentType
        tmpActivity.dataStored = {};
        switch (activity.componentType) {
          case 'common/scanBarcode':
            // do nothing; we will handle it at component level
            break;
          case 'common/confirmButton':
            tmpActivity.dataStored = {
              isActivityEnabled: false,
              isComplete: false,
            };
            break;
          default:
            tmpActivity.dataStored = {
              isActivityEnabled: false,
              isComplete: false,
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

      const wfProcess = {
        headerProperties,
        activities: formattedActivities,
        isSentToBackend: false,
      };

      draftState[wfProcessId] = wfProcess;
      updateActivitiesStatus({
        draftWFProcess: draftState[wfProcessId],
        activitiesStatus: extractActivitiesStatus({ wfProcess }),
      });

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
      console.log('SET_ACTIVITY_ENABLE_FLAG: wfProcess:', wfProcessId);
      console.log('activityId:', activityId);
      console.log('isActivityEnabled:', isActivityEnabled);

      console.log('activityIds: %o', original(draftState[wfProcessId]));
      console.log('--------------------------------------');

      draftState[wfProcessId].activities[activityId].dataStored.isActivityEnabled = isActivityEnabled;

      return draftState;
    }

    case types.SET_ACTIVITY_STATUS: {
      const { wfProcessId, activityId, isComplete } = action.payload;
      const draftWFProcess = draftState[wfProcessId];
      const activitiesStatus = extractActivitiesStatus({ wfProcess: original(draftWFProcess) });

      draftWFProcess.activities[activityId].dataStored.isComplete = isComplete;
      activitiesStatus[activityId].isComplete = isComplete;

      updateActivitiesStatus({ draftWFProcess, activitiesStatus });

      return draftState;
    }

    case types.UPDATE_PICKING_STEP_QTY: {
      const { wfProcessId, activityId, lineId, stepId, qty } = action.payload;

      draftState[wfProcessId].activities[activityId].dataStored.lines[lineId].steps[stepId].qtyPicked = qty;

      return draftState;
    }
    case types.SET_SCANNED_BARCODE: {
      const { wfProcessId, activityId, scannedBarcode } = action.payload;

      const draftWFProcess = draftState[wfProcessId];

      draftWFProcess.activities[activityId].dataStored.scannedBarcode = scannedBarcode;

      // reset the barcode caption. it will be set back when we get it back from server
      draftWFProcess.activities[activityId].componentProps.barcodeCaption = null;

      return draftState;
    }
    case types.UPDATE_PICKING_STEP_SCANNED_HU_BARCODE: {
      const { wfProcessId, activityId, lineId, stepId, scannedHUBarcode } = action.payload;

      const draftStep = draftState[wfProcessId].activities[activityId].dataStored.lines[lineId].steps[stepId];
      draftStep.scannedHUBarcode = scannedHUBarcode;

      return draftState;
    }
    default:
      return draftState;
  }
}, initialState);

export default reducer;
