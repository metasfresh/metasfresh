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

import { getQuickActionsId, getQuickActions } from '../reducers/actionsHandler';
import { getTable } from '../reducers/tables';
import { getView } from '../reducers/viewHandler';

/*
 * @method getTableActions
 * @summary Action creator that calls the quick actions fetch internally for
 * when we're updating the table selection 
 
 * @param {string} id - table id
 * @param {number} windowId
 * @param {string} viewId
 */
export function getTableActions({ tableId, windowId, viewId, isModal }) {
  return (dispatch, getState) => {
    const state = getState();
    const table = getTable(state, tableId);
    const selectedIds = table.selected;
    const viewProfileId = null;

    dispatch(
      fetchQuickActions({
        windowId,
        viewId,
        viewProfileId,
        selectedIds,
      })
    );

    dispatch(
      fetchIncludedQuickActions({
        windowId,
        selectedIds,
        isModal,
      })
    );
  };
}

/*
 * @method fetchIncludedQuickActions
 * @summary Action creator that calls the quick actions fetch internally for parent/child
 * quick actions, when a table selection has changed
 *
 * @param {string} id - table id
 * @param {array} selectedIds
 * @param {boolean} isModal
 */
export function fetchIncludedQuickActions({ windowId, selectedIds, isModal }) {
  return (dispatch, getState) => {
    const state = getState();
    const includedView = state.viewHandler.includedView.windowId
      ? state.viewHandler.includedView
      : null;

    // we're only interested in included view's quick actions if it
    // actually exists
    if (includedView) {
      let fetch = false;
      let fetchWindowId = null;
      let fetchViewId = null;
      let parentView = null;
      let childView = null;
      const isParent = includedView.parentId === windowId;
      const isChild = includedView.windowId === windowId;

      if (isParent) {
        fetchWindowId = includedView.windowId;
        childView = getView(state, fetchWindowId, isModal);
        fetchViewId = childView.viewId;
        const childQuickActionsId = getQuickActionsId({
          windowId: fetchWindowId,
          viewId: fetchViewId,
        });
        const childQuickActions = getQuickActions(state, childQuickActionsId);

        // only update quick actions if they're not already requested
        if (!childQuickActions.pending) {
          fetch = true;
        }
      } else if (isChild) {
        fetchWindowId = includedView.parentId;
        parentView = getView(state, fetchWindowId, isModal);
        fetchViewId = parentView.viewId;
        const parentQuickActionsId = getQuickActionsId({
          windowId: fetchWindowId,
          viewId: fetchViewId,
        });
        const parentQuickActions = getQuickActions(state, parentQuickActionsId);

        if (!parentQuickActions.pending) {
          fetch = true;
        }
      }

      console.log('fetchIncludedQuickActions: ', isParent, isChild, childView, parentView, fetch, fetchWindowId, fetchViewId)

      if ((isParent || isChild) && fetch) {
        dispatch(
          fetchQuickActions({
            windowId: fetchWindowId,
            viewId: fetchViewId,
            viewProfileId: null,
            selectedIds,
            parentView,
            childView,
          })
        );
      }
    }
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
