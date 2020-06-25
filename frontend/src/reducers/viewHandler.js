import { get } from 'lodash';
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
  TOGGLE_INCLUDED_VIEW,
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

export const initialState = { views: {} };

const selectView = (state, id) => {
  return get(state, ['viewHandler', 'views', id], viewState);
};

const selectLocalView = (state, id) => {
  return get(state, ['views', id], viewState);
};

export const getView = createSelector(
  [selectView],
  (view) => view
);

const getLocalView = createSelector(
  [selectLocalView],
  (view) => view
);

export default function viewHandler(state = initialState, action) {
  if ((!action.payload || !action.payload.id) && action.type !== DELETE_VIEW) {
    return state;
  }

  switch (action.type) {
    // LAYOUT
    case FETCH_LAYOUT_PENDING: {
      const { id } = action.payload;
      const view = getLocalView(state, id);

      return {
        ...state,
        views: {
          ...state.views,
          [`${id}`]: {
            ...view,
            layoutPending: true,
            layoutNotFound: false,
          },
        },
      };
    }
    case FETCH_LAYOUT_SUCCESS: {
      const { id, layout } = action.payload;
      const view = getLocalView(state, id);

      return {
        ...state,
        views: {
          ...state.views,
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
      const { id, error } = action.payload;
      const view = getLocalView(state, id);

      return {
        ...state,
        views: {
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
      const { id } = action.payload;
      const view = getLocalView(state, id);

      return {
        ...state,
        views: {
          ...state.views,
          [`${id}`]: {
            ...view,
            notFound: false,
            pending: true,
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
      } = action.payload;

      //WTF prettier?
      //eslint-disable-next-line
      const page = size > 1 ? (firstRow / pageLength) + 1 : 1;
      const view = getLocalView(state, id);
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
        views: {
          ...state.views,
          [`${id}`]: { ...viewState },
        },
      };
    }
    case FETCH_DOCUMENT_ERROR: {
      const { id, error } = action.payload;
      const view = getLocalView(state, id);

      return {
        ...state,
        views: {
          ...state.views,
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
      const { id } = action.payload;
      const view = getLocalView(state, id);

      return {
        ...state,
        views: {
          ...state.views,
          [`${id}`]: {
            ...view,
            pending: true,
            error: null,
          },
        },
      };
    }
    case CREATE_VIEW_SUCCESS: {
      const { id, viewId } = action.payload;
      const view = getLocalView(state, id);

      return {
        ...state,
        views: {
          ...state.views,
          [`${id}`]: {
            ...view,
            viewId,
            pending: false,
            notFound: false,
          },
        },
      };
    }
    case CREATE_VIEW_ERROR: {
      const { id, error } = action.payload;
      const view = getLocalView(state, id);

      return {
        ...state,
        views: {
          ...state.views,
          [`${id}`]: {
            ...view,
            pending: false,
            notFound: true,
            error,
          },
        },
      };
    }
    case FILTER_VIEW_PENDING: {
      const { id } = action.payload;
      const view = getLocalView(state, id);

      return {
        ...state,
        views: {
          ...state.views,
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
      } = action.payload;
      const view = getLocalView(state, id);

      return {
        ...state,
        views: {
          ...state.views,
          [`${id}`]: {
            ...view,
            filters,
            viewId,
            size,
            // TODO: Should we always set it to 1 ?
            page: 1,
            pending: false,
          },
        },
      };
    }
    case FILTER_VIEW_ERROR: {
      const { id, error } = action.payload;
      const view = getLocalView(state, id);

      return {
        ...state,
        views: {
          ...state.views,
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
      const { id, locationData } = action.payload;
      const view = getLocalView(state, id);

      return {
        ...state,
        views: {
          ...state.views,
          [`${id}`]: {
            ...view,
            locationData,
          },
        },
      };
    }

    case FETCH_LOCATION_CONFIG_SUCCESS: {
      const { id, data } = action.payload;
      const view = getLocalView(state, id);

      if (data.provider) {
        return {
          ...state,
          views: {
            ...state.views,
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
      const { id, error } = action.payload;
      const view = getLocalView(state, id);

      return {
        ...state,
        views: {
          ...state.views,
          [`${id}`]: {
            ...view,
            error,
          },
        },
      };
    }

    case TOGGLE_INCLUDED_VIEW: {
      const { id, showIncludedView } = action.payload;
      const view = getLocalView(state, id);

      return {
        ...state,
        views: {
          ...state.views,
          [`${id}`]: {
            ...view,
            isShowIncluded: !!showIncludedView,
            hasShowIncluded: !!showIncludedView,
          },
        },
      };
    }

    case DELETE_VIEW: {
      const id = action.payload.id;

      if (id) {
        delete state.views[id];

        return state;
      } else {
        return {
          ...state,
          views: {},
        };
      }
    }
    case RESET_VIEW: {
      const id = action.payload.id;
      const view = getLocalView(state, id);

      if (view) {
        return {
          ...state,
          views: {
            ...state.views,
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
