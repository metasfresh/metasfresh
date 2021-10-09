import * as types from '../constants/ActionTypes';

export const initialState = {
  sorting: {
    sort: '',
    windowType: null,
  },
  pagination: {
    page: 1,
    windowType: null,
  },
  viewId: {
    id: '',
    windowType: null,
  },
};

export default function listHandler(state = initialState, action) {
  switch (action.type) {
    case types.SET_LIST_ID:
      return Object.assign({}, state, {
        viewId: Object.assign({}, state.viewId, {
          id: action.viewId,
          windowType: action.windowType,
        }),
      });

    case types.SET_LIST_FILTERS:
      return Object.assign({}, state, {
        filters: action.filter != null ? [action.filter] : [],
        filtersWindowType: action.windowType,
      });

    case types.SET_LIST_SORTING:
      return Object.assign({}, state, {
        sorting: Object.assign({}, state.sorting, {
          sort: action.sort,
          windowType: action.windowType,
        }),
      });

    case types.SET_LIST_PAGINATION:
      return Object.assign({}, state, {
        pagination: Object.assign({}, state.pagination, {
          page: action.page,
          windowType: action.windowType,
        }),
      });

    default:
      return state;
  }
}
