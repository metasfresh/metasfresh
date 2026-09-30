import { SET_ACTIVITY_PROCESSING, UPDATE_WORKFLOW_PROCESS } from '../constants/WorkflowActionTypes';

export function updateWFProcess({ wfProcess, parent }) {
  return {
    type: UPDATE_WORKFLOW_PROCESS,
    // `timestamp` records when this process was last updated locally. It is stamped here in the
    // action creator (not in the reducer) so the reducer stays pure. It lets the
    // POPULATE_LAUNCHERS_COMPLETE reducer tell a genuinely stale launchers snapshot from one that
    // simply predates a just-started process (see reducers/wfProcesses/workflow.js).
    payload: { wfProcess, parent, timestamp: Date.now() },
  };
}

export function setActivityProcessing({ wfProcessId, activityId, processing }) {
  return {
    type: SET_ACTIVITY_PROCESSING,
    payload: { wfProcessId, activityId, processing },
  };
}
