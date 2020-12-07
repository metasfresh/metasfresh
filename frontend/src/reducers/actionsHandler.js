import { produce, original } from 'immer';
import { createSelector } from 'reselect';
import { get } from 'lodash';

import {
  DELETE_QUICK_ACTIONS,
  FETCH_QUICK_ACTIONS,
  FETCH_QUICK_ACTIONS_FAILURE,
  FETCH_QUICK_ACTIONS_SUCCESS,
} from '../constants/ActionTypes';

export const initialState = {};
export const initialSingleActionsState = {
  actions: [],
  pending: false,
  error: false,
  // because the quickactions are fetched asynchronously
  // we might end up in a situation, when we've already removed
  // them but the request didn't finish yet. Then on successfull response
  // we'll have the new instance added anyway. To avoid that, we set this flag
  // on deleting a pending quickactions item and remove it completely
  // when the response comes in.
  toDelete: false,
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
      const current = original(draftState[id]);

      if (current.toDelete) {
        delete draftState[id];
      } else {
        draftState[id] = {
          ...initialSingleActionsState,
          actions,
        };
      }

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
      const current = original(draftState[id]);

      if (draftState[id]) {
        // we don't want to delete a pending request, because
        // on success a new entry in the actions list will be created. So instead
        // we store this information and remove it when needed
        if (current.pending) {
          draftState[id] = {
            ...draftState[id],
            toDelete: true,
          };
        } else {
          delete draftState[id];
        }
      }

      return;
    }
  }
}, initialState);

export default reducer;
