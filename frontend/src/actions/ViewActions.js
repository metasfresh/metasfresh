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

import { createGridTable, updateGridTable } from './TablesActions';

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

export function fetchDocument({
  windowType,
  viewId,
  page,
  pageLength,
  orderBy,
  // for modals
  useViewId = false,
  //for filtering in modals
  modalId = null,
}) {
  return (dispatch) => {
    let identifier = useViewId ? viewId : windowType;

    if (useViewId && modalId) {
      identifier = modalId;
    }

    dispatch(fetchDocumentPending(identifier));

    return browseViewRequest({
      windowId: windowType,
      viewId,
      page,
      pageLength,
      orderBy,
    })
      .then((response) => {
        dispatch(fetchDocumentSuccess(identifier, response.data));

        const tableId = `${windowType}_${viewId}`;
        const tableData = { windowType, viewId, ...response.data };

        dispatch(updateGridTable(tableId, tableData));

        return Promise.resolve(response.data);
      })
      .catch((error) => {
        dispatch(fetchDocumentError(identifier, error));

        //show error message ?
        return Promise.resolve(error);
      });
  };
}

export function createView({
  windowType,
  viewType,
  filters,
  refDocType,
  refDocId,
  refTabId,
  refRowIds,
  inModalId,
}) {
  return (dispatch) => {
    const identifier = inModalId ? inModalId : windowType;

    dispatch(createViewPending(identifier));

    return createViewRequest({
      windowId: windowType,
      viewType,
      filters,
      refDocType,
      refDocId,
      refTabId,
      refRowIds,
    })
      .then((response) => {
        dispatch(createViewSuccess(identifier, response.data));

        const { viewId } = response.data;
        const tableId = `${windowType}_${viewId}`;
        const tableData = { windowType, viewId };

        dispatch(createGridTable(tableId, tableData));

        return Promise.resolve(response.data);
      })
      .catch((error) => {
        dispatch(createViewError(identifier, error));

        //show error message ?
        return Promise.resolve(error);
      });
  };
}

export function fetchLayout(
  windowType,
  viewType,
  viewProfileId = null,
  viewId = null
) {
  return (dispatch) => {
    const identifier = viewId ? viewId : windowType;

    dispatch(fetchLayoutPending(identifier));

    return getViewLayout(windowType, viewType, viewProfileId)
      .then((response) => {
        dispatch(fetchLayoutSuccess(identifier, response.data));

        // TODO: we could extract more table data from the layout response here
        // and have everything table-related in one branch of the state tree

        return Promise.resolve(response.data);
      })
      .catch((error) => {
        dispatch(fetchLayoutError(identifier, error));

        return Promise.resolve(error);
      });
  };
}

export function filterView(windowId, viewId, filters, useViewId = false) {
  return (dispatch) => {
    const identifier = useViewId ? viewId : windowId;

    dispatch(filterViewPending(identifier));

    return filterViewRequest(windowId, viewId, filters)
      .then((response) => {
        dispatch(filterViewSuccess(identifier, response.data));

        return Promise.resolve(response.data);
      })
      .catch((error) => {
        dispatch(filterViewError(identifier, error));

        return Promise.resolve(error);
      });
  };
}

export function fetchLocationConfig(windowId, viewId = null) {
  return (dispatch) => {
    const identifier = viewId ? viewId : windowId;

    return locationConfigRequest()
      .then((response) => {
        dispatch(fetchLocationConfigSuccess(identifier, response.data));
      })
      .catch((error) => {
        dispatch(fetchLocationConfigError(identifier, error));

        return Promise.resolve(error);
      });
  };
}
