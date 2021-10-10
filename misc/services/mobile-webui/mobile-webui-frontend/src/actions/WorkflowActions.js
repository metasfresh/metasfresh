import {
  START_WORKFLOW_PROCESS,
  CONTINUE_WORKFLOW_PROCESS,
  ADD_WORKFLOW_STATUS,
  UPDATE_WORKFLOW_PROCESS,
} from '../constants/WorkflowActionTypes';
import { startWorkflowRequest, getWorkflowRequest } from '../api/launchers';

/**
 * @method startWorkflow
 * @summary
 */
export function startWorkflow({ wfParameters }) {
  return (dispatch) => {
    return startWorkflowRequest({ wfParameters })
      .then(({ data }) => {
        const { endpointResponse } = data;

        dispatch({
          type: START_WORKFLOW_PROCESS,
          payload: { ...endpointResponse },
        });

        dispatch({
          type: ADD_WORKFLOW_STATUS,
          payload: { ...endpointResponse },
        });

        return Promise.resolve(data);
      })
      .catch((err) => {
        Promise.reject(err);
      });
  };
}

/**
 * @method continueWorkflow
 * @summary
 */
export function continueWorkflow(wfProcessId) {
  return (dispatch) => {
    return getWorkflowRequest(wfProcessId)
      .then(({ data }) => {
        const { endpointResponse } = data;

        dispatch({
          type: CONTINUE_WORKFLOW_PROCESS,
          payload: { ...endpointResponse },
        });

        dispatch({
          type: ADD_WORKFLOW_STATUS,
          payload: { ...endpointResponse },
        });

        return Promise.resolve(data);
      })
      .catch((err) => {
        Promise.reject(err);
      });
  };
}

export function updateWFProcess({ wfProcess }) {
  return {
    type: UPDATE_WORKFLOW_PROCESS,
    payload: wfProcess,
  };
}
