import { produce /*, original*/ } from 'immer';
import { createSelector } from 'reselect';
import { get } from 'lodash';

import {
  DELETE_QUICK_ACTIONS,
  FETCH_QUICK_ACTIONS,
  FETCH_QUICK_ACTIONS_FAILURE,
  FETCH_QUICK_ACTIONS_SUCCESS,
} from '../constants/ActionTypes';

export const initialActionsState = {};
export const initialSingleActionsState = {
  actions: [],
  pending: false,
  error: false,
};

export const getQuickActionsId = ({ windowId, viewId }) =>
  `${windowId}${viewId ? `-${viewId}` : ''}`;

const getQuickActionsData = (state, key) =>
  get(state, ['actionsHandler', key], initialSingleActionsState);

export const getQuickActions = createSelector(
  [getQuickActionsData],
  (actions) => actions
);

const reducer = produce((draftState, action) => {
  switch (action.type) {
    case FETCH_QUICK_ACTIONS: {
      const { id } = action.payload;

      draftState[id] = {
        ...initialSingleActionsState,
        pending: true,
      };

      return;
    }
    case FETCH_QUICK_ACTIONS_SUCCESS: {
      const { id, actions } = action.payload;

      draftState[id] = {
        actions,
        pending: false,
        error: false,
      };

      return;
    }
    case FETCH_QUICK_ACTIONS_FAILURE: {
      const { id } = action.payload;

      draftState[id].pending = false;
      draftState[id].error = true;

      return;
    }
    case DELETE_QUICK_ACTIONS: {
      const { id } = action.payload;

      if (draftState[id]) {
        delete draftState[id];
      }

      return;
    }
  }
}, initialActionsState);

export default reducer;
