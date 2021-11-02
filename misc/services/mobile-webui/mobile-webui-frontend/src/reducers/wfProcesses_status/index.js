import { produce } from 'immer';
import { createSelector } from 'reselect';
import { forEach } from 'lodash';

import { workflowReducer } from './workflow';
import { scanReducer } from './scan';
import { pickingReducer } from './picking';

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

export const getActivitiesStatus = createSelector(
  (state, wfProcessId) => getActivities(state, wfProcessId),
  (activities) => {
    let status = '';

    forEach(activities, (activity) => {
      status = activity.dataStored.completeStatus;
    });

    return status;
  }
);

const reducer = produce((draftState, action) => {
  draftState = workflowReducer({ draftState, action });
  draftState = scanReducer({ draftState, action });
  draftState = pickingReducer({ draftState, action });

  return draftState;
}, {});

export default reducer;
