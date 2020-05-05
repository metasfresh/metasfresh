import {
  browseViewRequest,
  createViewRequest,
  filterViewRequest,
  getViewLayout,
  locationConfigRequest,
} from '../api';

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

export function resetView(id) {
  return {
    type: RESET_VIEW,
    payload: { id },
  };
}

export function deleteView(id) {
  return {
    type: DELETE_VIEW,
    payload: { id },
  };
}

function fetchDocumentPending(id) {
  return {
    type: FETCH_DOCUMENT_PENDING,
    payload: { id },
  };
}

function fetchDocumentSuccess(id, data) {
  return {
    type: FETCH_DOCUMENT_SUCCESS,
    payload: { id, data },
  };
}

function fetchDocumentError(id, error) {
  return {
    type: FETCH_DOCUMENT_ERROR,
    payload: { id, error },
  };
}

function fetchLayoutPending(id) {
  return {
    type: FETCH_LAYOUT_PENDING,
    payload: { id },
  };
}

function fetchLayoutSuccess(id, layout) {
  return {
    type: FETCH_LAYOUT_SUCCESS,
    payload: { id, layout },
  };
}

function fetchLayoutError(id, error) {
  return {
    type: FETCH_LAYOUT_ERROR,
    payload: { id, error },
  };
}

function createViewPending(id) {
  return {
    type: CREATE_VIEW,
    payload: { id },
  };
}

function createViewSuccess(id, data) {
  return {
    type: CREATE_VIEW_SUCCESS,
    payload: { id, viewId: data.viewId },
  };
}

function createViewError(id, error) {
  return {
    type: CREATE_VIEW_ERROR,
    payload: { id, error },
  };
}

function filterViewPending(id) {
  return {
    type: FILTER_VIEW_PENDING,
    payload: { id },
  };
}

function filterViewSuccess(id, data) {
  return {
    type: FILTER_VIEW_SUCCESS,
    payload: { id, data },
  };
}

function filterViewError(id, error) {
  return {
    type: FILTER_VIEW_ERROR,
    payload: { id, error },
  };
}

export function updateViewData(id, rows, tabId) {
  return {
    type: UPDATE_VIEW_DATA,
    payload: {
      id,
      rows,
      tabId,
    },
  };
}

function fetchLocationConfigSuccess(id, data) {
  return {
    type: FETCH_LOCATION_CONFIG_SUCCESS,
    payload: { id, data },
  };
}

function fetchLocationConfigError(id, error) {
  return {
    type: FETCH_LOCATION_CONFIG_ERROR,
    payload: { id, error },
  };
}

export function addLocationData(id, locationData) {
  return {
    type: ADD_VIEW_LOCATION_DATA,
    payload: { id, locationData },
  };
}

// THUNK ACTIONS

export function fetchDocument(windowId, viewId, page, pageLength, orderBy) {
  return (dispatch) => {
    dispatch(fetchDocumentPending(windowId));

    return browseViewRequest({ windowId, viewId, page, pageLength, orderBy })
      .then((response) => {
        dispatch(fetchDocumentSuccess(windowId, response.data));

        return Promise.resolve(response.data);
      })
      .catch((error) => {
        dispatch(fetchDocumentError(windowId, error));

        //show error message ?
        return Promise.resolve(error);
      });
  };
}

export function createView(
  windowId,
  viewType,
  filters,
  refDocType,
  refDocId,
  refTabId,
  refRowIds
) {
  return (dispatch) => {
    dispatch(createViewPending(windowId));

    return createViewRequest({
      windowId,
      viewType,
      filters,
      refDocType,
      refDocId,
      refTabId,
      refRowIds,
    })
      .then((response) => {
        dispatch(createViewSuccess(windowId, response.data));

        return Promise.resolve(response.data);
      })
      .catch((error) => {
        dispatch(createViewError(error));

        //show error message ?
        return Promise.resolve(error);
      });
  };
}

export function fetchLayout(windowId, viewType, viewProfileId = null) {
  return (dispatch) => {
    dispatch(fetchLayoutPending(windowId));

    return getViewLayout(windowId, viewType, viewProfileId)
      .then((response) => {
        dispatch(fetchLayoutSuccess(windowId, response.data));

        return Promise.resolve(response.data);
      })
      .catch((error) => {
        dispatch(fetchLayoutError(windowId, error));

        return Promise.resolve(error);
      });
  };
}

export function filterView(windowId, viewId, filters) {
  return (dispatch) => {
    dispatch(filterViewPending(windowId));

    return filterViewRequest(windowId, viewId, filters)
      .then((response) => {
        dispatch(filterViewSuccess(windowId, response.data));

        return Promise.resolve(response.data);
      })
      .catch((error) => {
        dispatch(filterViewError(windowId, error));

        return Promise.resolve(error);
      });
  };
}

export function fetchLocationConfig(windowId) {
  return (dispatch) => {
    return locationConfigRequest()
      .then((response) => {
        dispatch(fetchLocationConfigSuccess(windowId, response.data));
      })
      .catch((error) => {
        dispatch(fetchLocationConfigError(windowId, error));

        return Promise.resolve(error);
      });
  };
}
