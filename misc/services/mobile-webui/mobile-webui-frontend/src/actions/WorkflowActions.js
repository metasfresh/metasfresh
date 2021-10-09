import {
  START_WORKFLOW_PROCESS,
  CONTINUE_WORKFLOW_PROCESS,
  ADD_WORKFLOW_STATUS,
  SET_ACTIVITY_ENABLE_FLAG,
  SET_ACTIVITY_STATUS,
  UPDATE_WORKFLOW_PROCESS,
} from '../constants/ActionTypes';
import { startWorkflowRequest, continueWorkflowRequest } from '../api/launchers';

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
    return continueWorkflowRequest(wfProcessId)
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

/**
 * @method setActivityEnableFlag
 * @summary update the isActivityEnabled activity flag
 */
export function setActivityEnableFlag({ wfProcessId, activityId, isActivityEnabled }) {
  return {
    type: SET_ACTIVITY_ENABLE_FLAG,
    payload: { wfProcessId, activityId, isActivityEnabled },
  };
}

export function setActivityStatus({ wfProcessId, activityId, isComplete }) {
  return {
    type: SET_ACTIVITY_STATUS,
    payload: { wfProcessId, activityId, isComplete },
  };
}

export function updateWFProcess({ wfProcess }) {
  return {
    type: UPDATE_WORKFLOW_PROCESS,
    payload: wfProcess,
  };
}
