import { produce } from 'immer';
import { createSelector } from 'reselect';
import { forEach } from 'lodash';

import { NOT_STARTED } from '../../constants/CompleteStatus';
import { workflowReducer } from './workflow';
import { scanReducer } from './scan';
import { activityUserConfirmationReducer } from './confirmation';
import { pickingReducer } from './picking';
import { distributionReducer } from './distribution';
import { manufacturingReducer } from './manufacturing';
import { manufacturingReducer as manufacturingReceiptReducer } from './manufacturing_receipt';

const getWfProcess = (state, wfProcessId) => state.wfProcesses_status[wfProcessId] || null;

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

const getActivities = createSelector(
  (state, wfProcessId) => getWfProcess(state, wfProcessId),
  (wfProcess) => (wfProcess ? wfProcess.activities : {})
);

const getActivitiesStatuses = createSelector(
  (state, wfProcessId) => getActivities(state, wfProcessId),
  (activities) => {
    const statuses = [];

    forEach(activities, (activity) => {
      statuses.push(activity.dataStored.completeStatus);
    });

    return statuses;
  }
);

export const activitiesNotStarted = createSelector(
  (state, wfProcessId) => getActivitiesStatuses(state, wfProcessId),
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

const reducer = produce((draftState, action) => {
  draftState = workflowReducer({ draftState, action });
  draftState = scanReducer({ draftState, action });
  draftState = activityUserConfirmationReducer({ draftState, action });
  draftState = pickingReducer({ draftState, action });
  draftState = distributionReducer({ draftState, action });
  draftState = manufacturingReducer({ draftState, action });
  draftState = manufacturingReceiptReducer({ draftState, action });

  return draftState;
}, {});

export default reducer;
