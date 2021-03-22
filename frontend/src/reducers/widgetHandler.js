import { produce, original } from 'immer';
import { createSelector } from 'reselect';
import { get } from 'lodash';

// import {
//   DELETE_QUICK_ACTIONS,
//   FETCH_QUICK_ACTIONS,
//   FETCH_QUICK_ACTIONS_FAILURE,
//   FETCH_QUICK_ACTIONS_SUCCESS,
// } from '../constants/ActionTypes';

export const initialState = {
  attributes: {
    fields: [],
    elements: [],
  },
};
// export const initialSingleActionsState = {
//   actions: [],
//   pending: false,
//   error: false,
//   toDelete: false,
// };

// export const getQuickActionsId = ({ windowId, viewId }) =>
//   `${windowId}${viewId ? `-${viewId}` : ''}`;

// const getQuickActionsData = (state, key) =>
//   get(state, ['actionsHandler', key], initialSingleActionsState);

// export const getQuickActions = createSelector(
//   [getQuickActionsData],
//   (actions) => actions
// );

const reducer = produce((draftState, action) => {
  switch (action.type) {
    case 'FETCH_DLW_DATA': {
      const { fieldsByName } = action.payload;

      draftState.attributes.fields = fieldsByName;

      return;
    }
    case 'FETCH_DLW_LAYOUT': {
      const { elements } = action.payload;

      draftState.attributes.elements = elements;

      return;
    }

    case 'DELETE_DLW': {
      draftState.attributes = {
        fields: [],
        elements: [],
      };

      return;
    }

  //   case FETCH_QUICK_ACTIONS_SUCCESS: {
  //     const { id, actions } = action.payload;
  //     const current = original(draftState[id]);

  //     if (current.toDelete) {
  //       delete draftState[id];
  //     } else {
  //       draftState[id] = {
  //         ...initialSingleActionsState,
  //         actions,
  //       };
  //     }

  //     return;
  //   }
  }
}, initialState);

export default reducer;
