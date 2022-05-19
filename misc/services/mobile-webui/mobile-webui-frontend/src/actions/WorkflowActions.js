import { UPDATE_WORKFLOW_PROCESS } from '../constants/WorkflowActionTypes';

export function updateWFProcess({ wfProcess }) {
  return {
    type: UPDATE_WORKFLOW_PROCESS,
    payload: { wfProcess },
  };
}
