import { SET_ACTIVITY_PROCESSING, UPDATE_WORKFLOW_PROCESS } from '../constants/WorkflowActionTypes';

export function updateWFProcess({ wfProcess }) {
  return {
    type: UPDATE_WORKFLOW_PROCESS,
    payload: { wfProcess },
  };
}

export function setActivityProcessing({ wfProcessId, activityId, processing }) {
  return {
    type: SET_ACTIVITY_PROCESSING,
    payload: { wfProcessId, activityId, processing },
  };
}
