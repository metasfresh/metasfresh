import { produce } from 'immer';
import { createSelector } from 'reselect';
import * as types from '../constants/ActionTypes';

export const initialState = {};
const initialWorkflowState = {
  headerProperties: {
    entries: [],
  },
  activities: [],
};

const wfSelector = (state, id) => state.wfProcesses[id] || null;

export const getWorkflowProcess = createSelector(wfSelector, (workflow) =>
  workflow ? workflow : initialWorkflowState
);

const reducer = produce((draftState, action) => {
  switch (action.type) {
    case types.START_WORKFLOW_PROCESS:
    case types.CONTINUE_WORKFLOW_PROCESS:
    case types.UPDATE_WORKFLOW_PROCESS: {
      const { id, headerProperties, activities } = action.payload;

      draftState[id] = { headerProperties, activities };

      return draftState;
    }

    default:
      return draftState;
  }
}, initialState);

export default reducer;
