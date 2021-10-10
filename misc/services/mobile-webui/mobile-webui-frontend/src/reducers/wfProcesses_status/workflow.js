import * as types from '../../constants/WorkflowActionTypes';
import { mergeWFProcessToState } from './utils';

export const workflowReducer = ({ draftState, action }) => {
  switch (action.type) {
    case types.ADD_WORKFLOW_STATUS:
    case types.UPDATE_WORKFLOW_PROCESS: {
      const fromWFProcess = action.payload;

      let draftWFProcess = draftState[fromWFProcess.id];
      if (!draftWFProcess) {
        draftWFProcess = {
          id: fromWFProcess.id,
          activities: {},
        };
      }

      mergeWFProcessToState({
        draftWFProcess: draftWFProcess,
        fromWFProcess,
      });

      draftState[fromWFProcess.id] = draftWFProcess;
      return draftState;
    }

    default: {
      return draftState;
    }
  }
};
