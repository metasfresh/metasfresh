import { quickActionsRequest, topActionsRequest } from '../api';
import {
  DELETE_QUICK_ACTIONS,
  DELETE_TOP_ACTIONS,
  FETCH_QUICK_ACTIONS,
  FETCH_QUICK_ACTIONS_FAILURE,
  FETCH_QUICK_ACTIONS_SUCCESS,
  FETCH_TOP_ACTIONS,
  FETCH_TOP_ACTIONS_FAILURE,
  FETCH_TOP_ACTIONS_SUCCESS,
} from '../constants/ActionTypes';

import { getQuickActionsId } from '../reducers/actionsHandler';
import { getTable } from '../reducers/tables';

/*
 * @method getTableActions
 * @summary Action creator that calls the quick actions fetch internally for
 * when we're updating the table selection 
 
 * @param {string} id - table id
 * @param {number} windowId
 * @param {string} viewId
 */
export function getTableActions({ tableId, windowId, viewId }) {
  return (dispatch, getState) => {
    const state = getState();
    const table = getTable(state, tableId);
    const selectedIds = table.selected;

    const viewProfileId = null;
    const childView = {};
    const parentView = {};

    dispatch(
      fetchQuickActions({
        windowId,
        viewId,
        viewProfileId,
        selectedIds,
        childView,
        parentView,
      })
    );
  };
}

/**
 * @method fetchQuickActions
 * @summary Fetches the quick actions
 *
 * @param {*} windowId
 * @param {*} viewId
 * @param {*} viewProfileId
 * @param {*} selectedIds
 * @param {*} childView
 * @param {*} parentView
 */
export function fetchQuickActions({
  windowId,
  viewId,
  viewProfileId,
  selectedIds,
  childView,
  parentView,
}) {
  return (dispatch) => {
    const id = getQuickActionsId({ windowId, viewId });

    dispatch({
      type: FETCH_QUICK_ACTIONS,
      payload: { id },
    });

    return quickActionsRequest({
      windowId,
      viewId,
      viewProfileId,
      selectedIds,
      childView,
      parentView,
    })
      .then((response) => {
        const actions = response.data.actions;
        dispatch({
          type: FETCH_QUICK_ACTIONS_SUCCESS,
          payload: {
            id,
            actions,
          },
        });

        return Promise.resolve(actions);
      })
      .catch((e) => {
        dispatch({
          type: FETCH_QUICK_ACTIONS_FAILURE,
          payload: { id },
        });

        return Promise.reject(e);
      });
  };
}

/**
 * @method deleteQuickActions
 * @summary Action creator to delete quick actions from the store
 *
 * @param {*} windowId
 * @param {*} viewId
 */
export function deleteQuickActions(windowId, viewId) {
  const id = getQuickActionsId({ windowId, viewId });

  return {
    type: DELETE_QUICK_ACTIONS,
    payload: { id },
  };
}

/**
 * @method deleteTopActions
 * @summary Deletes tab's actions
 */
export function deleteTopActions() {
  return {
    type: DELETE_TOP_ACTIONS,
  };
}

/**
 * @method fetchTopActions
 * @summary Fetches tab's top actions
 *
 * @param {*} windowType
 * @param {*} docId
 * @param {*} tabId
 */
export function fetchTopActions(windowType, docId, tabId) {
  return (dispatch) => {
    dispatch({
      type: FETCH_TOP_ACTIONS,
    });

    return topActionsRequest(windowType, docId, tabId)
      .then((response) => {
        dispatch({
          type: FETCH_TOP_ACTIONS_SUCCESS,
          payload: response.data.actions,
        });

        return Promise.resolve(response.data.actions);
      })
      .catch((e) => {
        dispatch({
          type: FETCH_TOP_ACTIONS_FAILURE,
        });

        return Promise.reject(e);
      });
  };
}
