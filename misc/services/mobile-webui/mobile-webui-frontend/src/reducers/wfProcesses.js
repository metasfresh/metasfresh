import * as types from '../constants/ActionTypes';
import { produce } from 'immer';

export const initialState = {};

const reducer = produce((draftState, action) => {
  switch (action.type) {
    case types.START_WORKFLOW_PROCESS: {
      const { id, headerProperties, activities } = action.payload;

      draftState[id] = { headerProperties, activities };

      return draftState;
    }
    case types.CONTINUE_WORKFLOW_PROCESS: {
      const { id, headerProperties, activities } = action.payload;

      draftState[id] = { headerProperties, activities };

      return draftState;
    }

    default:
      return draftState;
  }
}, initialState);

export default reducer;
