// import _ from 'lodash';

import { quickActionsRequest, topActionsRequest } from '../api';
import {
  DELETE_QUICK_ACTIONS,
  DELETE_TOP_ACTIONS,
  // FETCHED_QUICK_ACTIONS,
  FETCH_QUICK_ACTIONS,
  FETCH_QUICK_ACTIONS_FAILURE,
  FETCH_QUICK_ACTIONS_SUCCESS,
  FETCH_TOP_ACTIONS,
  FETCH_TOP_ACTIONS_FAILURE,
  FETCH_TOP_ACTIONS_SUCCESS,
} from '../constants/ActionTypes';

import { getQuickActionsId } from '../reducers/actionsHandler';

/*
 * Action creator called when quick actions are successfully fetched
 */
// export function fetchQuickActions(windowId, id, data) {
//   return {
//     type: FETCHED_QUICK_ACTIONS,
//     payload: {
//       data,
//       windowId,
//       id,
//     },
//   };
// }

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
 * @async
 * @method renderCancelButton
 * @summary ToDo: Describe the method
 * @param {*} windowId
 * @param {*} viewId
 * @param {*} viewProfileId
 * @param {*} selected
 * @param {*} childView
 * @param {*} parentView
 * @param {*} resolve
 * @param {*} reject
 * @todo Rewrite this as an action creator
 */

/*
export async function fetchActions(
  windowId,
  viewId,
  viewProfileId,
  selected,
  childView,
  parentView //,
  // resolve,
  // reject
) {
  // const { fetchedQuickActions } = this.props;
  // if (!this.mounted) {
  //   return resolve();
  // }

  // if (windowId && viewId) {
    await quickActionsRequest(
      windowId,
      viewId,
      viewProfileId,
      selected,
      childView,
      parentView
    )
      .then((result) => {
        const [respRel, resp] = result;

        // if (this.mounted) {
        //   const currentActions =
        //     resp && resp.data ? resp.data.actions : respRel.data.actions;
        //   const relatedActions =
        //     resp && resp.data ? respRel.data.actions : null;

        //   if (
        //     (parentView.viewId || childView.viewId) &&
        //     relatedActions &&
        //     !_.isEmpty(parentView)
        //   ) {
        //     const windowType = parentView.windowType
        //       ? parentView.windowType
        //       : childView.windowType;
        //     const id = parentView.viewId
        //       ? parentView.viewId
        //       : childView.viewId;
        //     fetchedQuickActions(windowType, id, relatedActions);
        //   }

        //   // fetchedQuickActions(windowId, viewId, currentActions);

        //   return this.setState(
        //     {
        //       loading: false,
        //     },
        //     resolve
        //   );
        // }
      })
      .catch((e) => {
        // eslint-disable-next-line no-console
        console.error(e);

        // if (this.mounted) {
        //   return this.setState(
        //     {
        //       loading: false,
        //     },
        //     reject
        //   );
        // }
      });
  // } else {
    // if (this.mounted) {
    //   return this.setState(
    //     {
    //       loading: false,
    //     },
    //     resolve
    //   );
    // }
  // }
}
*/

/*
 * Action creator to delete quick actions from the store
 */
export function deleteQuickActions(windowId, id) {
  return {
    type: DELETE_QUICK_ACTIONS,
    payload: {
      windowId,
      id,
    },
  };
}

export function deleteTopActions() {
  return {
    type: DELETE_TOP_ACTIONS,
  };
}

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
