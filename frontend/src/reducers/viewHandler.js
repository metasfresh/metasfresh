import { Map as iMap, List as iList } from 'immutable';
import { get } from 'lodash';

import {
  ADD_VIEW_LOCATION_DATA,
  FETCH_DOCUMENT_PENDING,
  FETCH_DOCUMENT_SUCCESS,
  FETCH_DOCUMENT_ERROR,
  FETCH_LAYOUT_PENDING,
  FETCH_LAYOUT_SUCCESS,
  FETCH_LAYOUT_ERROR,
  CREATE_VIEW,
  CREATE_VIEW_SUCCESS,
  CREATE_VIEW_ERROR,
  FILTER_VIEW_PENDING,
  FILTER_VIEW_SUCCESS,
  FILTER_VIEW_ERROR,
  UPDATE_VIEW_DATA,
  FETCH_LOCATION_CONFIG_SUCCESS,
  FETCH_LOCATION_CONFIG_ERROR,
  RESET_VIEW,
  DELETE_VIEW,
} from '../constants/ActionTypes';

export const viewState = {
  layout: {
    activeTab: null,
    pending: false,
    error: null,
    notfound: false,
  },
  // rowData is an immutable Map with tabId's as keys, and Lists as values.
  // List's elements are plain objects for now
  rowData: iMap(),
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
  size: 0,
  description: null,
  sort: null,
  staticFilters: null,
  orderBy: null,
  queryLimitHit: null,
  mapConfig: null,
  notfound: false,
  pending: false,
  error: null,
  isActive: false,
};

export const initialState = { views: {} };

const getView = (id, state) => {
  return get(state, ['views', id], viewState);
};

export default function viewHandler(state = initialState, action) {
  if ((!action.payload || !action.payload.id) && action.type !== DELETE_VIEW) {
    return state;
  }

  switch (action.type) {
    // LAYOUT
    case FETCH_LAYOUT_PENDING: {
      const { id } = action.payload;
      const view = getView(id, state);

      return {
        ...state,
        views: {
          ...state.views,
          [`${id}`]: {
            ...view,
            layout: {
              ...view.layout,
              notfound: false,
              pending: true,
            },
          },
        },
      };
    }
    case FETCH_LAYOUT_SUCCESS: {
      const { id, layout } = action.payload;
      const view = getView(id, state);

      return {
        ...state,
        views: {
          ...state.views,
          [`${id}`]: {
            ...view,
            layout: {
              ...view.layout,
              ...layout,
              error: null,
              pending: false,
            },
          },
        },
      };
    }
    case FETCH_LAYOUT_ERROR: {
      const { id, error } = action.payload;
      const view = getView(id, state);

      return {
        ...state,
        views: {
          ...state.views,
          [`${id}`]: {
            ...view,
            layout: {
              ...view.layout,
              notfound: true,
              error,
              pending: false,
            },
          },
        },
      };
    }

    case FETCH_DOCUMENT_PENDING: {
      const { id } = action.payload;
      const view = getView(id, state);

      return {
        ...state,
        views: {
          ...state.views,
          [`${id}`]: {
            ...view,
            notfound: false,
            pending: true,
            error: null,
          },
        },
      };
    }
    case FETCH_DOCUMENT_SUCCESS: {
      // TODO: Maybe just use `_.omit` to remove `result` ?
      const {
        id,
        data: {
          firstRow,
          headerProperties,
          pageLength,
          result,
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
      const view = getView(id, state);
      const viewState = {
        ...view,
        firstRow,
        headerProperties,
        pageLength,
        size,
        type,
        viewId,
        windowId,
        orderBy,
        page,
        staticFilters,
        queryLimit,
        queryLimitHit,
        rowData: iMap({ [`${action.payload.tabId || 1}`]: iList(result) }),
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
      const view = getView(id, state);

      return {
        ...state,
        views: {
          ...state.views,
          [`${id}`]: {
            ...view,
            pending: false,
            notfound: true,
            error,
          },
        },
      };
    }

    // VIEW OPERATIONS
    case CREATE_VIEW: {
      const { id } = action.payload;
      const view = getView(id, state);

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
      const view = getView(id, state);

      return {
        ...state,
        views: {
          ...state.views,
          [`${id}`]: {
            ...view,
            viewId,
            pending: false,
            notfound: false,
          },
        },
      };
    }
    case CREATE_VIEW_ERROR: {
      const { id, error } = action.payload;
      const view = getView(id, state);

      return {
        ...state,
        views: {
          ...state.views,
          [`${id}`]: {
            ...view,
            pending: false,
            notfound: true,
            error,
          },
        },
      };
    }
    case FILTER_VIEW_PENDING: {
      const { id } = action.payload;
      const view = getView(id, state);

      return {
        ...state,
        views: {
          ...state.views,
          [`${id}`]: {
            ...view,
            notfound: false,
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
      const view = getView(id, state);

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
      const view = getView(id, state);

      return {
        ...state,
        views: {
          ...state.views,
          [`${id}`]: {
            ...view,
            pending: false,
            notfound: true,
            error,
          },
        },
      };
    }
    case UPDATE_VIEW_DATA: {
      const { id, rows } = action.payload;
      const tabId = action.payload.tabId || '1';
      const view = getView(id, state);
      const updatedRowsData = view.rowData.set(tabId, iList(rows));

      return {
        ...state,
        views: {
          ...state.views,
          [`${id}`]: {
            ...view,
            rowData: updatedRowsData,
          },
        },
      };
    }
    case ADD_VIEW_LOCATION_DATA: {
      const { id, locationData } = action.payload;
      const view = getView(id, state);

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
      const view = getView(id, state);

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
      const view = getView(id, state);

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
      const view = getView(id, state);

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
