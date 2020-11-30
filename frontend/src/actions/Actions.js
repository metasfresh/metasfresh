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
 
 * @param {string} tableId
 * @param {number} windowId
 * @param {string} viewId
 * @param {boolean} isModal
 */
export function getTableActions({ tableId, windowId, viewId, isModal }) {
  return (dispatch, getState) => {
    const actionPromises = [];
    const state = getState();
    const table = getTable(state, tableId);
    const selectedIds = table.selected;
    const { includedView } = state.viewHandler;
    const viewProfileId =
      includedView.windowId === windowId ? includedView.viewProfileId : null;
    let fetchActions = true;

    if (viewId) {
      const quickActionsId = getQuickActionsId({
        windowId,
        viewId,
      });
      const quickActions = getQuickActions(state, quickActionsId);
      fetchActions = quickActions.pending === false;
    }

    if (fetchActions) {
      actionPromises.push(dispatch(
        fetchQuickActions({
          windowId,
          viewId,
          viewProfileId,
          selectedIds,
        })
      ));;
    }

    actionPromises.push(dispatch(
      fetchIncludedQuickActions({
        windowId,
        selectedIds,
        isModal,
      })
    ));

    return Promise.all(actionPromises);
  };
}

/*
 * @method fetchIncludedQuickActions
 * @summary Action creator that calls the quick actions fetch internally for parent/child
 * quick actions, when a table selection has changed
 *
 * @param {string} windowId
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
      let [parentView, childView] = Array(2).fill(null);
      let fetch = false;
      const view = getView(state, windowId, isModal);
      const viewProfileId =
        includedView.windowId === windowId ? includedView.viewProfileId : null;

      const isParent = includedView.parentId === windowId;
      const isChild = includedView.windowId === windowId;

      if (isParent) {
        const childWindowId = includedView.windowId;
        childView = getView(state, childViewId, isModal);
        const childViewId = childView.viewId;
        const childQuickActionsId = getQuickActionsId({
          windowId: childWindowId,
          viewId: childViewId,
        });
        const childQuickActions = getQuickActions(state, childQuickActionsId);

        // only update quick actions if they're not already requested
        fetch = childQuickActions.pending === false;
      } else if (isChild) {
        const parentWindowId = includedView.parentId;
        parentView = getView(state, parentWindowId, isModal);
        const parentViewId = parentView.viewId;
        const parentQuickActionsId = getQuickActionsId({
          windowId: parentWindowId,
          viewId: parentViewId,
        });
        const parentQuickActions = getQuickActions(state, parentQuickActionsId);

        fetch = parentQuickActions.pending === false;
      }

      if ((isParent || isChild) && fetch) {
        return dispatch(
          fetchQuickActions({
            windowId,
            viewId: view.viewId,
            viewProfileId,
            selectedIds,
            parentView,
            childView,
          })
        );
      }
    }

    return Promise.resolve(false);
  };
}

/**
 * @method fetchQuickActions
 * @summary Fetches the quick actions
 *
 * @param {number} windowId
 * @param {string} viewId
 * @param {string} viewProfileId
 * @param {array} selectedIds
 * @param {object} childView
 * @param {object} parentView
 */
export function fetchQuickActions({
  windowId,
  viewId,
  viewProfileId,
  selectedIds,
  childView,
  parentView,
}) {
  return (dispatch, getState) => {
    let id = null;

    if (childView) {
      id = getQuickActionsId({
        windowId: childView.windowId,
        viewId: childView.viewId,
      });
    } else if (parentView) {
      id = getQuickActionsId({
        windowId: parentView.windowId,
        viewId: parentView.viewId,
      });
    } else {
      id = getQuickActionsId({ windowId, viewId });
    }

    const quickActions = getQuickActions(getState(), id);

    // we're checking if child/parent actions are not already fetching
    // in fetchIncludedQuickActions
    if (!quickActions.pending || childView || parentView) {
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
export function deleteTopActions() {
  return {
    type: DELETE_TOP_ACTIONS,
  };
}

/**
 * @method fetchTopActions
 * @summary Fetches tab's top actions
 *
 * @param {number} windowType
 * @param {string} docId
 * @param {string} tabId
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
