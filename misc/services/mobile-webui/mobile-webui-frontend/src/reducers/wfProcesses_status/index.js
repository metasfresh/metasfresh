import { produce } from 'immer';
import { createSelector } from 'reselect';
import { workflowReducer } from './workflow';
import { scanReducer } from './scan';
import { pickingReducer } from './picking';

export const selectWFProcessFromState = createSelector(
  (state, wfProcessId) => state.wfProcesses_status[wfProcessId] || null,
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

const reducer = produce((draftState, action) => {
  draftState = workflowReducer({ draftState, action });
  draftState = scanReducer({ draftState, action });
  draftState = pickingReducer({ draftState, action });
  return draftState;
}, {});

export default reducer;
