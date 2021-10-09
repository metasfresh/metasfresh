import { current, original, isDraft, produce } from 'immer';
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
  draftWFProcess.headerProperties = fromWFProcess.headerProperties;

  if (!draftWFProcess.activities) {
    draftWFProcess.activities = {};
  }

  mergeActivitiesToState({
    draftActivities: draftWFProcess.activities,
    fromActivities: fromWFProcess.activities,
  });

  console.log('AFTER MERGE: %o', isDraft(draftWFProcess) ? current(draftWFProcess) : draftWFProcess);
  console.log('fromWFProcess=%o', fromWFProcess);
};

const mergeActivitiesToState = ({ draftActivities, fromActivities }) => {
  fromActivities.forEach((fromActivity) => {
    if (!draftActivities[fromActivity.activityId]) {
      draftActivities[fromActivity.activityId] = { ...fromActivity };
    }

    const draftActivity = draftActivities[fromActivity.activityId];

    mergeActivityToState({ draftActivity, fromActivity });
  });
};

const mergeActivityToState = ({ draftActivity, fromActivity }) => {
  draftActivity.caption = fromActivity.caption;
  draftActivity.componentType = fromActivity.componentType;

  const componentPropsNormalized = normalizeComponentProps({
    componentType: fromActivity.componentType,
    componentProps: fromActivity.componentProps,
  });
  draftActivity.componentProps = componentPropsNormalized;

  if (!draftActivity.dataStored) {
    draftActivity.dataStored = computeActivityDataStoredInitialValue({
      componentType: fromActivity.componentType,
      componentProps: componentPropsNormalized,
    });
  }
};

const computeActivityDataStoredInitialValue = ({ componentType, componentProps }) => {
  switch (componentType) {
    case 'picking/pickProducts': {
      return {
        isActivityEnabled: false,
        isComplete: false,
        lines: componentProps.lines,
      };
    }
    default: {
      return {};
    }
  }
};

const normalizeComponentProps = ({ componentType, componentProps }) => {
  switch (componentType) {
    case 'picking/pickProducts': {
      return {
        ...componentProps,
        lines: normalizePickingLines(componentProps.lines),
      };
    }
    default: {
      return componentProps;
    }
  }
};

const normalizePickingLines = (lines) => {
  return lines.map((line) => {
    return {
      ...line,
      steps: line.steps.reduce((accum, step) => {
        accum[step.pickingStepId] = step;
        return accum;
      }, {}),
    };
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
  console.log('draftWFProcess=%o', draftWFProcess);

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
  // console.log('---------------------------------------------------------------------');
  // console.log('*** wfProcesses_status reducer: %o', action);

  switch (action.type) {
    case types.ADD_WORKFLOW_STATUS:
    case types.UPDATE_WORKFLOW_PROCESS: {
      const fromWFProcess = action.payload;

      let draftWFProcess = draftState[fromWFProcess.id];
      if (!draftWFProcess) {
        draftWFProcess = {
          id: fromWFProcess.id,
          activities: {},
        };
      }

      mergeWFProcessToState({
        draftWFProcess: draftWFProcess,
        fromWFProcess,
      });

      draftState[fromWFProcess.id] = draftWFProcess;
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

    case types.SET_SCANNED_BARCODE: {
      const { wfProcessId, activityId, scannedBarcode } = action.payload;
      const draftWFProcess = draftState[wfProcessId];
      const activitiesStatus = extractActivitiesStatus({ wfProcess: original(draftWFProcess) });

      draftWFProcess.activities[activityId].dataStored.scannedBarcode = scannedBarcode;
      // reset the barcode caption. it will be set back when we get it back from server
      draftWFProcess.activities[activityId].componentProps.barcodeCaption = null;

      const isComplete = !!scannedBarcode;
      draftWFProcess.activities[activityId].dataStored.isComplete = isComplete;
      activitiesStatus[activityId].isComplete = isComplete;

      updateActivitiesStatus({ draftWFProcess, activitiesStatus });
      return draftState;
    }

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
    default:
      return draftState;
  }
}, initialState);

export default reducer;
