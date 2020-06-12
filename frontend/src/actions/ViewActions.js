import {
  browseViewRequest,
  createViewRequest,
  filterViewRequest,
  getViewLayout,
  locationConfigRequest,
} from '../api';
import { getTableId } from '../reducers/tables';
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
import { createGridTable, updateGridTable } from './TableActions';

/**
 * @method resetView
 * @summary
 */
export function resetView(id) {
  return {
    type: RESET_VIEW,
    payload: { id },
  };
}

/**
 * @method deleteView
 * @summary
 */
export function deleteView(id) {
  return {
    type: DELETE_VIEW,
    payload: { id },
  };
}

/**
 * @method fetchDocumentPending
 * @summary
 */
function fetchDocumentPending(id) {
  return {
    type: FETCH_DOCUMENT_PENDING,
    payload: { id },
  };
}

/**
 * @method fetchDocumentSuccess
 * @summary
 */
function fetchDocumentSuccess(id, data) {
  return {
    type: FETCH_DOCUMENT_SUCCESS,
    payload: { id, data },
  };
}

/**
 * @method fetchDocumentError
 * @summary
 */
function fetchDocumentError(id, error) {
  return {
    type: FETCH_DOCUMENT_ERROR,
    payload: { id, error },
  };
}

/**
 * @method fetchLayoutPending
 * @summary
 */
function fetchLayoutPending(id) {
  return {
    type: FETCH_LAYOUT_PENDING,
    payload: { id },
  };
}

/**
 * @method fetchLayoutSuccess
 * @summary
 */
function fetchLayoutSuccess(id, layout) {
  return {
    type: FETCH_LAYOUT_SUCCESS,
    payload: { id, layout },
  };
}

/**
 * @method fetchLayoutError
 * @summary
 */
function fetchLayoutError(id, error) {
  return {
    type: FETCH_LAYOUT_ERROR,
    payload: { id, error },
  };
}

/**
 * @method createViewPending
 * @summary
 */
function createViewPending(id) {
  return {
    type: CREATE_VIEW,
    payload: { id },
  };
}

/**
 * @method createViewSuccess
 * @summary
 */
function createViewSuccess(id, data) {
  return {
    type: CREATE_VIEW_SUCCESS,
    payload: { id, viewId: data.viewId },
  };
}

/**
 * @method createViewError
 * @summary
 */
function createViewError(id, error) {
  return {
    type: CREATE_VIEW_ERROR,
    payload: { id, error },
  };
}

/**
 * @method filterViewPending
 * @summary
 */
function filterViewPending(id) {
  return {
    type: FILTER_VIEW_PENDING,
    payload: { id },
  };
}

/**
 * @method filterViewSuccess
 * @summary
 */
function filterViewSuccess(id, data) {
  return {
    type: FILTER_VIEW_SUCCESS,
    payload: { id, data },
  };
}

/**
 * @method filterViewError
 * @summary
 */
function filterViewError(id, error) {
  return {
    type: FILTER_VIEW_ERROR,
    payload: { id, error },
  };
}

/**
 * @method updateViewData
 * @summary
 */
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

/**
 * @method fetchLocationConfigSuccess
 * @summary
 */
function fetchLocationConfigSuccess(id, data) {
  return {
    type: FETCH_LOCATION_CONFIG_SUCCESS,
    payload: { id, data },
  };
}

/**
 * @method fetchLocationConfigError
 * @summary
 */
function fetchLocationConfigError(id, error) {
  return {
    type: FETCH_LOCATION_CONFIG_ERROR,
    payload: { id, error },
  };
}

/**
 * @method addLocationData
 * @summary
 */
export function addLocationData(id, locationData) {
  return {
    type: ADD_VIEW_LOCATION_DATA,
    payload: { id, locationData },
  };
}

// THUNK ACTIONS

/**
 * @method fetchDocument
 * @summary Get grid rows
 *
 * @param {*} windowType
 * @param {*} viewId
 * @param {number} page
 * @param {number} pageLength
 * @param {*} orderBy
 * @param {bool} useViewId - flag defining if we should be using the viewId, or not.
 * set to `true` for modals.
 * @param {*} modalId - used together with `useViewId`
 */
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

        const tableId = getTableId({ windowType, viewId });
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

/**
 * @method createView
 * @summary create a new grid view
 */
export function createView({
  windowType,
  viewType,
  filters,
  referenceId,
  refDocType,
  refDocumentId,
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
      referenceId,
      refDocType,
      refDocumentId,
      refTabId,
      refRowIds,
    })
      .then((response) => {
        dispatch(createViewSuccess(identifier, response.data));

        const { viewId } = response.data;
        const tableId = getTableId({ windowType, viewId });
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

/**
 * @method fetchLayout
 * @summary fetch layout data for the grid view
 */
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

        return Promise.resolve(response.data);
      })
      .catch((error) => {
        dispatch(fetchLayoutError(identifier, error));

        return Promise.resolve(error);
      });
  };
}

// TODO: Update table on filtering
/**
 * @method filterView
 * @summary filter grid view
 */
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

/**
 * @method fetchLocationConfig
 * @summary Get the location search configuration from the API
 */
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
