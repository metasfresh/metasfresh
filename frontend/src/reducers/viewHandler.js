import { get, find } from 'lodash';
import { createSelector } from 'reselect';

import {
  ADD_VIEW_LOCATION_DATA,
  CREATE_VIEW,
  CREATE_VIEW_SUCCESS,
  CREATE_VIEW_ERROR,
  DELETE_VIEW,
  FETCH_DOCUMENT_ERROR,
  FETCH_DOCUMENT_PENDING,
  FETCH_DOCUMENT_SUCCESS,
  FETCH_LAYOUT_ERROR,
  FETCH_LAYOUT_PENDING,
  FETCH_LAYOUT_SUCCESS,
  FETCH_LOCATION_CONFIG_ERROR,
  FETCH_LOCATION_CONFIG_SUCCESS,
  FILTER_VIEW_PENDING,
  FILTER_VIEW_SUCCESS,
  FILTER_VIEW_ERROR,
  RESET_VIEW,
  SET_INCLUDED_VIEW,
  TOGGLE_INCLUDED_VIEW,
  UNSET_INCLUDED_VIEW,
  UPDATE_VIEW_DATA_ERROR,
  UPDATE_VIEW_DATA_SUCCESS,
} from '../constants/ActionTypes';

export const viewState = {
  layout: {
    activeTab: null,
  },
  layoutPending: false,
  layoutError: null,
  layoutNotFound: false,
  locationData: null,
  docId: null,
  type: null,
  viewId: null,
  windowId: null,
  filters: null,
  firstRow: 0,
  headerProperties: null,
  pageLength: 0,
  page: 1,
  description: null,
  sort: null,
  staticFilters: null,
  orderBy: null,
  queryLimit: null,
  queryLimitHit: null,
  mapConfig: null,
  notFound: false,
  pending: false,
  error: null,
  isActive: false,

  isShowIncluded: false,
  hasShowIncluded: false,
};

export const initialState = {
  views: {},
  modals: {},
  includedView: {
    viewId: null,
    windowId: null,
    viewProfileId: null,
    parentId: null,
  },
};

const selectView = (state, id, isModal) => {
  return isModal
    ? get(state, ['viewHandler', 'modals', id], viewState)
    : get(state, ['viewHandler', 'views', id], viewState);
};

const selectLocalView = (state, id, isModal) => {
  return isModal
    ? get(state, ['modals', id], viewState)
    : get(state, ['views', id], viewState);
};

export const getView = createSelector([selectView], (view) => view);

const getLocalView = createSelector([selectLocalView], (view) => view);

const getViewType = (isModal) => (isModal ? 'modals' : 'views');

/**
 * @method findViewByViewId
 * @summary searches for a specific view with viewId
 *
 * @param {object} state
 * @param {string} viewId
 */
export function findViewByViewId({ viewHandler }, viewId) {
  let view = find(viewHandler.views, (item) => item.viewId === viewId);

  if (!view) {
    view = find(viewHandler.modals, (item) => item.viewId === viewId);
  }

  return view;
}

export default function viewHandler(state = initialState, action) {
  if ((!action.payload || !action.payload.id) && action.type !== DELETE_VIEW) {
    return state;
  }

  switch (action.type) {
    // LAYOUT
    case FETCH_LAYOUT_PENDING: {
      const { id, isModal } = action.payload;
      const type = getViewType(isModal);
      const view = getLocalView(state, id, isModal);

      return {
        ...state,
        [`${type}`]: {
          ...state[`${type}`],
          [`${id}`]: {
            ...view,
            layoutPending: true,
            layoutNotFound: false,
          },
        },
      };
    }
    case FETCH_LAYOUT_SUCCESS: {
      const { id, layout, isModal } = action.payload;
      const type = getViewType(isModal);
      const view = getLocalView(state, id, isModal);

      return {
        ...state,
        [`${type}`]: {
          ...state[`${type}`],
          [`${id}`]: {
            ...view,
            layoutPending: false,
            layoutError: false,
            layout: {
              ...view.layout,
              ...layout,
            },
          },
        },
      };
    }
    case FETCH_LAYOUT_ERROR: {
      const { id, error, isModal } = action.payload;
      const type = getViewType(isModal);
      const view = getLocalView(state, id, isModal);

      return {
        ...state,
        [`${type}`]: {
          ...state.views,
          [`${id}`]: {
            ...view,
            layoutPending: false,
            layoutError: error,
            layoutNotFound: true,
          },
        },
      };
    }

    case FETCH_DOCUMENT_PENDING: {
      const { id, isModal, websocketRefresh } = action.payload;
      const type = getViewType(isModal);
      const view = getLocalView(state, id, isModal);
      // if a websocket event caused this data fetch, we're not setting
      // pending flag to true as in case of multiple refreshes in a short
      // period of time it will cause the spinner to flicker
      // https://github.com/metasfresh/me03/issues/6262#issuecomment-733527251
      const pending = websocketRefresh ? false : true;

      return {
        ...state,
        [`${type}`]: {
          ...state[`${type}`],
          [`${id}`]: {
            ...view,
            notFound: false,
            pending,
            error: null,
          },
        },
      };
    }
    case FETCH_DOCUMENT_SUCCESS: {
      const {
        id,
        data: {
          firstRow,
          headerProperties,
          pageLength,
          size,
          type,
          viewId,
          windowId,
          orderBy,
          queryLimit,
          queryLimitHit,
          staticFilters,
        },
        isModal,
      } = action.payload;
      const viewType = getViewType(isModal);

      //WTF prettier?
      //eslint-disable-next-line
      const page = size > 1 ? (firstRow / pageLength) + 1 : 1;
      const view = getLocalView(state, id, isModal);
      const viewState = {
        ...view,
        firstRow,
        headerProperties,
        pageLength,
        type,
        viewId,
        windowId,
        orderBy,
        page,
        staticFilters,
        queryLimit,
        queryLimitHit,
        pending: false,
      };

      return {
        ...state,
        [`${viewType}`]: {
          ...state[`${viewType}`],
          [`${id}`]: { ...viewState },
        },
      };
    }
    case UPDATE_VIEW_DATA_ERROR:
    case FETCH_DOCUMENT_ERROR: {
      const { id, error, isModal } = action.payload;
      const type = getViewType(isModal);
      const view = getLocalView(state, id, isModal);

      return {
        ...state,
        [`${type}`]: {
          ...state[`${type}`],
          [`${id}`]: {
            ...view,
            pending: false,
            notFound: true,
            error,
          },
        },
      };
    }

    // VIEW OPERATIONS
    case CREATE_VIEW: {
      const { id, isModal } = action.payload;
      const type = getViewType(isModal);
      const view = getLocalView(state, id, isModal);

      return {
        ...state,
        [`${type}`]: {
          ...state[`${type}`],
          [`${id}`]: {
            ...view,
            pending: true,
            error: null,
          },
        },
      };
    }
    case CREATE_VIEW_SUCCESS: {
      const { id, viewId, isModal } = action.payload;
      const type = getViewType(isModal);
      const view = getLocalView(state, id, isModal);

      return {
        ...state,
        [`${type}`]: {
          ...state[`${type}`],
          [`${id}`]: {
            ...view,
            viewId,
            // we don't change `pending` to false, since we'll be fetching data immediately
            // after that and for a split of a second we can trigger unnecessary duplicated
            // requests if pending will be falsy
            notFound: false,
          },
        },
      };
    }
    case CREATE_VIEW_ERROR: {
      const { id, error, isModal } = action.payload;
      const type = getViewType(isModal);
      const view = getLocalView(state, id, isModal);

      return {
        ...state,
        [`${type}`]: {
          ...state[`${type}`],
          [`${id}`]: {
            ...view,
            pending: false,
            notFound: true,
            error,
          },
        },
      };
    }

    case UPDATE_VIEW_DATA_SUCCESS: {
      const { id, data, isModal } = action.payload;
      const viewType = getViewType(isModal);
      const view = getLocalView(state, id, isModal);
      const viewState = {
        ...view,
        ...data,
        pending: false,
        error: null,
        notFound: false,
      };

      return {
        ...state,
        [`${viewType}`]: {
          ...state[`${viewType}`],
          [`${id}`]: { ...viewState },
        },
      };
    }

    case FILTER_VIEW_PENDING: {
      const { id, isModal } = action.payload;
      const type = getViewType(isModal);
      const view = getLocalView(state, id, isModal);

      return {
        ...state,
        [`${type}`]: {
          ...state[`${type}`],
          [`${id}`]: {
            ...view,
            notFound: false,
            pending: true,
            error: null,
          },
        },
      };
    }
    case FILTER_VIEW_SUCCESS: {
      const {
        id,
        data: { filters, viewId, size },
        isModal,
      } = action.payload;
      const type = getViewType(isModal);
      const view = getLocalView(state, id, isModal);

      // we're not setting `pending` to false, as it'll be reset
      // by fetching the document
      return {
        ...state,
        [`${type}`]: {
          ...state[`${type}`],
          [`${id}`]: {
            ...view,
            filters,
            viewId,
            size,
            // TODO: Should we always set it to 1 ?
            page: 1,
          },
        },
      };
    }
    case FILTER_VIEW_ERROR: {
      const { id, error, isModal } = action.payload;
      const type = getViewType(isModal);
      const view = getLocalView(state, id, isModal);

      return {
        ...state,
        [`${type}`]: {
          ...state[`${type}`],
          [`${id}`]: {
            ...view,
            pending: false,
            notFound: true,
            error,
          },
        },
      };
    }
    case ADD_VIEW_LOCATION_DATA: {
      const { id, locationData, isModal } = action.payload;
      const type = getViewType(isModal);
      const view = getLocalView(state, id, isModal);

      return {
        ...state,
        [`${type}`]: {
          ...state[`${type}`],
          [`${id}`]: {
            ...view,
            locationData,
          },
        },
      };
    }

    case FETCH_LOCATION_CONFIG_SUCCESS: {
      const { id, data, isModal } = action.payload;
      const type = getViewType(isModal);
      const view = getLocalView(state, id, isModal);

      if (data.provider) {
        return {
          ...state,
          [`${type}`]: {
            ...state[`${type}`],
            [`${id}`]: {
              ...view,
              mapConfig: data,
            },
          },
        };
      }

      return state;
    }
    case FETCH_LOCATION_CONFIG_ERROR: {
      const { id, error, isModal } = action.payload;
      const type = getViewType(isModal);
      const view = getLocalView(state, id, isModal);

      return {
        ...state,
        [`${type}`]: {
          ...state[`${type}`],
          [`${id}`]: {
            ...view,
            error,
          },
        },
      };
    }

    case TOGGLE_INCLUDED_VIEW: {
      const { id, showIncludedView, isModal } = action.payload;
      const type = getViewType(isModal);
      const view = getLocalView(state, id, isModal);

      if (view.windowId) {
        return {
          ...state,
          [`${type}`]: {
            ...state[`${type}`],
            [`${id}`]: {
              ...view,
              isShowIncluded: !!showIncludedView,
              hasShowIncluded: !!showIncludedView,
            },
          },
        };
      }

      return state;
    }
    case SET_INCLUDED_VIEW: {
      const { id, viewId, viewProfileId, parentId } = action.payload;

      return {
        ...state,
        includedView: {
          ...state.includedView,
          viewId,
          windowId: id,
          viewProfileId,
          parentId,
        },
      };
    }
    case UNSET_INCLUDED_VIEW: {
      const { windowId, viewId } = state.includedView;
      const { id: newWindowId, viewId: newViewId, forceClose } = action.payload;

      if (forceClose || (windowId === newWindowId && viewId === newViewId)) {
        // only close includedView if it hasn't changed since
        return {
          ...state,
          includedView: {
            viewId: null,
            windowId: null,
            viewProfileId: null,
            parentId: null,
          },
        };
      } else {
        return state;
      }
    }

    case DELETE_VIEW: {
      const { id, isModal } = action.payload;
      const type = getViewType(isModal);

      if (id) {
        delete state[`${type}`][id];
      }

      return state;
    }
    case RESET_VIEW: {
      const { id, isModal } = action.payload;
      const type = getViewType(isModal);
      const view = getLocalView(state, id, isModal);

      if (view) {
        return {
          ...state,
          [`${type}`]: {
            ...state[`${type}`],
            [`${id}`]: { ...viewState },
          },
        };
      } else {
        return state;
      }
    }
    default:
      return state;
  }
}
