import { produce } from 'immer';
import { createSelector } from 'reselect';
import { forEach } from 'lodash';

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

const selectActivities = createSelector(
  (state, wfProcessId) => getWfProcess(state, wfProcessId),
  (wfProcess) => (wfProcess ? wfProcess.activities : {})
);

const selectActivitiesStatuses = createSelector(
  (state, wfProcessId) => selectActivities(state, wfProcessId),
  (activities) => {
    const statuses = [];

    forEach(activities, (activity) => {
      statuses.push(activity.dataStored.completeStatus);
    });

    return statuses;
  }
);

export const activitiesNotStarted = createSelector(
  (state, wfProcessId) => selectActivitiesStatuses(state, wfProcessId),
  (activitiesStatuses) => {
    let notStarted = true;

    for (let i = 0; i < activitiesStatuses.length; i += 1) {
      if (activitiesStatuses[i] !== NOT_STARTED) {
        notStarted = false;

        break;
      }
    }

    return notStarted;
  }
);

export const getActivityById = (state, wfProcessId, activityId) => {
  return getWfProcess(state, wfProcessId)?.activities?.[activityId];
};

export const getLineById = (state, wfProcessId, activityId, lineId) => {
  const activity = getActivityById(state, wfProcessId, activityId);
  return activity != null ? activity.dataStored.lines[lineId] : null;
};

const getStepsMap = (state, wfProcessId, activityId, lineId) => {
  const line = getLineById(state, wfProcessId, activityId, lineId);
  return line != null && line.steps ? line.steps : {};
};

export const getSteps = (state, wfProcessId, activityId, lineId) => {
  const stepsById = getStepsMap(state, wfProcessId, activityId, lineId);
  return Object.values(stepsById);
};

export const getStepById = (state, wfProcessId, activityId, lineId, stepId) => {
  const stepsById = getStepsMap(state, wfProcessId, activityId, lineId);
  return stepsById[stepId];
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
