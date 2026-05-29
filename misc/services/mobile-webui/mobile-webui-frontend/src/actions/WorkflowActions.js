import { SET_ACTIVITY_PROCESSING, UPDATE_WORKFLOW_PROCESS } from '../constants/WorkflowActionTypes';

export function updateWFProcess({ wfProcess, parent }) {
  return {
    type: UPDATE_WORKFLOW_PROCESS,
    payload: { wfProcess, parent },
  };
}

export function setActivityProcessing({ wfProcessId, activityId, processing }) {
  return {
    type: SET_ACTIVITY_PROCESSING,
    payload: { wfProcessId, activityId, processing },
  };
}
