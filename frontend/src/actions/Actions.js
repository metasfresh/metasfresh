import { quickActionsRequest, topActionsRequest } from '../api';
import {
  DELETE_QUICK_ACTIONS,
  TOP_ACTIONS_DELETE,
  FETCH_QUICK_ACTIONS,
  FETCH_QUICK_ACTIONS_FAILURE,
  FETCH_QUICK_ACTIONS_SUCCESS,
  TOP_ACTIONS_LOADING,
  TOP_ACTIONS_FAILURE,
  TOP_ACTIONS_SUCCESS,
} from '../constants/ActionTypes';

import { getQuickActionsId, getQuickActions } from '../reducers/actionsHandler';
import { getTable, getTableId } from '../reducers/tables';
import { getView } from '../reducers/viewHandler';

/*
 * @method fetchQuickActions
 * @summary Action creator that calls the quick actions fetch internally for
 * when we're updating the table selection 
 
 * @param {number} windowId
 * @param {string} viewId
 * @param {boolean} isModal
 * @param {string} viewProfileId
 */
export function fetchQuickActions({
  windowId,
  viewId,
  isModal,
  viewProfileId = null,
}) {
  return (dispatch, getState) => {
    let actionPromises = [null];
    const state = getState();
    const includedView = state.viewHandler.includedView.windowId
      ? state.viewHandler.includedView
      : null;

    if (includedView) {
      viewProfileId = viewProfileId || includedView.viewProfileId;

      const isParent = includedView.parentId === windowId;
      const isChild = includedView.windowId === windowId;

      if (isParent || isChild) {
        const requests = dispatch(
          getQuickActionRequests({
            windowId,
            viewId,
            includedView,
            viewProfileId,
            isModal,
            isParent,
          })
        );

        actionPromises = [].concat(requests);

        return Promise.all(actionPromises);
      }
    }

    const tableId = getTableId({ windowId, viewId });
    const table = getTable(state, tableId);

    actionPromises = [
      dispatch(
        requestQuickActions({
          windowId,
          viewId,
          selectedIds: table.selected,
          viewProfileId,
        })
      ),
    ];

    return Promise.all(actionPromises);
  };
}

/*
 * @method fetchIncludedQuickActions
 * @summary Action creator that calls the creates the requests for included quick actions
 *
 * @param {number} windowId
 * @param {string} viewId
 * @param {boolean} isParent
 * @param {object} includedView
 * @param {string} viewProfileId
 * @param {boolean} isModal
 */
function getQuickActionRequests({
  windowId,
  viewId,
  isParent,
  includedView,
  viewProfileId,
  isModal,
}) {
  return (dispatch, getState) => {
    const requestPromises = [];
    const state = getState();
    let [parentWindowId, parentViewId, childWindowId, childViewId] =
      Array(2).fill(null);

    if (isParent) {
      parentWindowId = windowId;
      parentViewId = viewId;
      childWindowId = includedView.windowId;
      childViewId = includedView.viewId;
    } else {
      parentWindowId = includedView.parentId;
      childWindowId = windowId;
      childViewId = viewId;
      parentViewId = getView(state, parentWindowId, isModal).viewId;
    }

    const parentTableId = getTableId({
      windowId: parentWindowId,
      viewId: parentViewId,
    });
    const parentTable = getTable(state, parentTableId);
    const parentView = {
      windowId: parentWindowId,
      viewId: parentViewId,
      selected: parentTable.selected,
    };

    const childTableId = getTableId({
      windowId: childWindowId,
      viewId: childViewId,
    });
    const childTable = getTable(state, childTableId);
    const childView = {
      windowId: childWindowId,
      viewId: childViewId,
      selected: childTable.selected,
    };

    // construct parent request
    requestPromises.push(
      dispatch(
        requestQuickActions({
          windowId: parentWindowId,
          viewId: parentViewId,
          viewProfileId,
          selectedIds: parentTable.selected,
          parentView: null,
          childView,
        })
      )
    );

    // construct child request
    requestPromises.push(
      dispatch(
        requestQuickActions({
          windowId: childWindowId,
          viewId: childViewId,
          viewProfileId,
          selectedIds: childTable.selected,
          parentView,
          childView: null,
        })
      )
    );

    return requestPromises;
  };
}

/**
 * @method requestQuickActions
 * @summary Fetches the quick actions
 *
 * @param {number} windowId
 * @param {string} viewId
 * @param {string} viewProfileId
 * @param {array} selectedIds
 * @param {object} childView
 * @param {object} parentView
 */
export function requestQuickActions({
  windowId,
  viewId,
  viewProfileId,
  selectedIds,
  childView,
  parentView,
}) {
  return (dispatch, getState) => {
    const id = getQuickActionsId({ windowId, viewId });
    const quickActions = getQuickActions(getState(), id);

    // don't fetch quick actions if there's already a pending request
    if (!quickActions.pending) {
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
    }

    return Promise.resolve(null);
  };
}

/**
 * @method deleteQuickActions
 * @summary Action creator to delete quick actions from the store
 *
 * @param {number} windowId
 * @param {string} viewId
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
export function deleteTopActions({ windowId, tabId, docId }) {
  return {
    type: TOP_ACTIONS_DELETE,
    payload: { windowId, tabId, docId },
  };
}

/**
 * @method fetchTopActions
 * @summary Fetches tab's top actions
 *
 * @param {number} windowId
 * @param {string} docId
 * @param {string} tabId
 */
export function fetchTopActions({ windowId, tabId, docId }) {
  return (dispatch) => {
    dispatch({
      type: TOP_ACTIONS_LOADING,
      payload: { windowId, tabId, docId },
    });

    return topActionsRequest(windowId, docId, tabId)
      .then((response) => {
        dispatch({
          type: TOP_ACTIONS_SUCCESS,
          payload: { windowId, tabId, docId, actions: response.data.actions },
        });

        return Promise.resolve(response.data.actions);
      })
      .catch((e) => {
        dispatch({
          type: TOP_ACTIONS_FAILURE,
          payload: { windowId, tabId, docId },
        });

        return Promise.reject(e);
      });
  };
}
