import { produce } from 'immer';
import { createSelector } from 'reselect';

import { NOT_STARTED } from '../../constants/CompleteStatus';
import { workflowReducer } from './workflow';
import { scanReducer } from './scan';
import { activityUserConfirmationReducer } from './confirmation';
import { pickingReducer } from './picking';
import { distributionReducer } from './distribution';
import { manufacturingReducer as manufacturingIssueReducer } from './manufacturing_issue';
import { manufacturingReducer as manufacturingReceiptReducer } from './manufacturing_receipt';
import { generateHUQRCodesReducer } from './generateHUQRCodes';

export const getWfProcess = (state, wfProcessId) => {
  if (!wfProcessId) {
    console.trace(`getWfProcess called with wfProcessId=${wfProcessId}`);
  }

  return state.wfProcesses_status[wfProcessId] || null;
};

export const selectWFProcessFromState = createSelector(
  (state, wfProcessId) => getWfProcess(state, wfProcessId),
  (wfProcess) =>
    wfProcess
      ? wfProcess
      : {
          headerProperties: {
            entries: [],
          },
          activities: {},
          isSentToBackend: false,
        }
);

export const getActivitiesInOrder = (wfProcess) => {
  const activityIdsInOrder = wfProcess.activityIdsInOrder ?? [];
  const activitiesById = wfProcess.activities ?? {};
  return activityIdsInOrder.map((activityId) => activitiesById[activityId]);
};

export const isWorkflowNotStarted = (wfProcess) => {
  const activitiesById = wfProcess?.activities ?? {};
  const activities = Object.values(activitiesById);

  for (let i = 0; i < activities.length; i += 1) {
    const activityStatus = activities[i].dataStored.completeStatus;
    if (activityStatus !== NOT_STARTED) {
      return false;
    }
  }

  return true;
};

export const getActivityById = (state, wfProcessId, activityId) => {
  return getWfProcess(state, wfProcessId)?.activities?.[activityId];
};

export const getLineByIdFromActivity = (activity, lineId) => {
  return activity?.dataStored?.lines?.[lineId];
};

export const getLineById = (state, wfProcessId, activityId, lineId) => {
  const activity = getActivityById(state, wfProcessId, activityId);
  return getLineByIdFromActivity(activity, lineId);
};

export const getStepsArrayFromLine = (line) => {
  const stepsById = line?.steps ?? {};
  return Object.values(stepsById);
};

export const getSteps = (state, wfProcessId, activityId, lineId) => {
  const line = getLineById(state, wfProcessId, activityId, lineId);
  return getStepsArrayFromLine(line);
};

export const getStepById = (state, wfProcessId, activityId, lineId, stepId) => {
  const line = getLineById(state, wfProcessId, activityId, lineId);
  return line?.steps?.[stepId];
};

export const getStepByIdFromActivity = (activity, lineId, stepId) => {
  const line = getLineByIdFromActivity(activity, lineId);
  return line?.steps?.[stepId];
};

export const getQtyRejectedReasonsFromActivity = (activity) => {
  return activity?.componentProps?.qtyRejectedReasons?.reasons ?? [];
};

const reducer = produce((draftState, action) => {
  draftState = workflowReducer({ draftState, action });
  draftState = scanReducer({ draftState, action });
  draftState = activityUserConfirmationReducer({ draftState, action });
  draftState = pickingReducer({ draftState, action });
  draftState = distributionReducer({ draftState, action });
  draftState = generateHUQRCodesReducer({ draftState, action });
  draftState = manufacturingIssueReducer({ draftState, action });
  draftState = manufacturingReceiptReducer({ draftState, action });

  return draftState;
}, {});

export default reducer;
