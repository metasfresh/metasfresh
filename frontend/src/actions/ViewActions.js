import {
  browseViewRequest,
  createViewRequest,
  filterViewRequest,
  getViewLayout,
  locationConfigRequest,
  headerPropertiesRequest,
} from '../api';
import { getTableId } from '../reducers/tables';
import { getView } from '../reducers/viewHandler';

import {
  ADD_VIEW_LOCATION_DATA,
  CREATE_VIEW,
  CREATE_VIEW_SUCCESS,
  CREATE_VIEW_ERROR,
  DELETE_VIEW,
  FETCH_DOCUMENT_PENDING,
  FETCH_DOCUMENT_SUCCESS,
  FETCH_DOCUMENT_ERROR,
  FETCH_LAYOUT_PENDING,
  FETCH_LAYOUT_SUCCESS,
  FETCH_LAYOUT_ERROR,
  FILTER_VIEW_PENDING,
  FILTER_VIEW_SUCCESS,
  FILTER_VIEW_ERROR,
  FETCH_LOCATION_CONFIG_SUCCESS,
  FETCH_LOCATION_CONFIG_ERROR,
  RESET_VIEW,
  TOGGLE_INCLUDED_VIEW,
  UPDATE_VIEW_DATA_ERROR,
  UPDATE_VIEW_DATA_SUCCESS,
} from '../constants/ActionTypes';

import { createGridTable, updateGridTable, deleteTable } from './TableActions';
import { setListIncludedView, closeListIncludedView } from './ListActions';

/**
 * @method resetView
 * @summary
 */
export function resetView(id, isModal) {
  return {
    type: RESET_VIEW,
    payload: { id, isModal },
  };
}

/**
 * @method deleteView
 * @summary
 */
export function deleteView(id, isModal) {
  return {
    type: DELETE_VIEW,
    payload: { id, isModal },
  };
}

/**
 * @method fetchDocumentPending
 * @summary
 */
function fetchDocumentPending(id, isModal) {
  return {
    type: FETCH_DOCUMENT_PENDING,
    payload: { id, isModal },
  };
}

/**
 * @method fetchDocumentSuccess
 * @summary
 */
function fetchDocumentSuccess(id, data, isModal) {
  return {
    type: FETCH_DOCUMENT_SUCCESS,
    payload: { id, data, isModal },
  };
}

/**
 * @method fetchDocumentError
 * @summary
 */
function fetchDocumentError(id, error, isModal) {
  return {
    type: FETCH_DOCUMENT_ERROR,
    payload: { id, error, isModal },
  };
}

/**
 * @method fetchLayoutPending
 * @summary
 */
function fetchLayoutPending(id, isModal) {
  return {
    type: FETCH_LAYOUT_PENDING,
    payload: { id, isModal },
  };
}

/**
 * @method fetchLayoutSuccess
 * @summary
 */
function fetchLayoutSuccess(id, layout, isModal) {
  return {
    type: FETCH_LAYOUT_SUCCESS,
    payload: { id, layout, isModal },
  };
}

/**
 * @method fetchLayoutError
 * @summary
 */
function fetchLayoutError(id, error, isModal) {
  return {
    type: FETCH_LAYOUT_ERROR,
    payload: { id, error, isModal },
  };
}

/**
 * @method createViewPending
 * @summary
 */
function createViewPending(id, isModal) {
  return {
    type: CREATE_VIEW,
    payload: { id, isModal },
  };
}

/**
 * @method createViewSuccess
 * @summary
 */
function createViewSuccess(id, data, isModal) {
  return {
    type: CREATE_VIEW_SUCCESS,
    payload: { id, viewId: data.viewId, isModal },
  };
}

/**
 * @method createViewError
 * @summary
 */
function createViewError(id, error, isModal) {
  return {
    type: CREATE_VIEW_ERROR,
    payload: { id, error, isModal },
  };
}

/**
 * @method filterViewPending
 * @summary
 */
function filterViewPending(id, isModal) {
  return {
    type: FILTER_VIEW_PENDING,
    payload: { id, isModal },
  };
}

/**
 * @method filterViewSuccess
 * @summary
 */
function filterViewSuccess(id, data, isModal) {
  return {
    type: FILTER_VIEW_SUCCESS,
    payload: { id, data, isModal },
  };
}

/**
 * @method filterViewError
 * @summary
 */
function filterViewError(id, error, isModal) {
  return {
    type: FILTER_VIEW_ERROR,
    payload: { id, error, isModal },
  };
}

/**
 * @method fetchLocationConfigSuccess
 * @summary
 */
function fetchLocationConfigSuccess(id, data, isModal) {
  return {
    type: FETCH_LOCATION_CONFIG_SUCCESS,
    payload: { id, data, isModal },
  };
}

/**
 * @method fetchLocationConfigError
 * @summary error when fetching geolocation config
 */
function fetchLocationConfigError(id, error, isModal) {
  return {
    type: FETCH_LOCATION_CONFIG_ERROR,
    payload: { id, error, isModal },
  };
}

/**
 * @method addLocationData
 * @summary save geolocation data in the store
 */
export function addViewLocationData(id, locationData, isModal) {
  return {
    type: ADD_VIEW_LOCATION_DATA,
    payload: { id, locationData, isModal },
  };
}

/**
 * @method toggleIncludedView
 * @summary sets internal hasIncluded/isIncluded values
 */
export function toggleIncludedView(id, showIncludedView, isModal) {
  return {
    type: TOGGLE_INCLUDED_VIEW,
    payload: { id, showIncludedView, isModal },
  };
}

/**
 * @method toggleIncludedView
 * @summary success when updating view's properties
 */
export function updateViewSuccess(id, data, isModal) {
  return {
    type: UPDATE_VIEW_DATA_SUCCESS,
    payload: { id, data, isModal },
  };
}

/**
 * @method toggleIncludedView
 * @summary failure when updating view's properties
 */
export function updateViewError(id, error, isModal) {
  return {
    type: UPDATE_VIEW_DATA_ERROR,
    payload: { id, error, isModal },
  };
}

// THUNK ACTIONS

/**
 * @method fetchDocument
 * @summary Get grid rows when the view already exists
 *
 * @param {*} windowId
 * @param {*} viewId
 * @param {number} page
 * @param {number} pageLength
 * @param {*} orderBy
 * @param {bool} isModal - flag defining if the view is in modal or not.
 * Set to `true` for modals because otherwise if using `windowId` we would have a collision
 * with the underlaying window (as they both have the same `windowId`) so we store modal
 * views in `modals` instead of regular `views`
 */
export function fetchDocument({
  windowId,
  viewId,
  page,
  pageLength,
  orderBy,
  isModal = false,
}) {
  return (dispatch, getState) => {
    dispatch(fetchDocumentPending(windowId, isModal));

    return browseViewRequest({
      windowId,
      viewId,
      page,
      pageLength,
      orderBy,
    })
      .then((response) => {
        dispatch(fetchDocumentSuccess(windowId, response.data, isModal));

        const tableId = getTableId({ windowId, viewId });
        const tableData = { windowId, viewId, ...response.data };

        // we use this in table ACs to differentiate between a table in modal and
        // regular grid
        if (isModal) {
          tableData.modalId = windowId;
        }

        dispatch(updateGridTable(tableId, tableData));

        const state = getState();
        const view = getView(state, windowId, isModal);
        const openIncludedViewOnSelect =
          view.layout &&
          view.layout.includedView &&
          view.layout.includedView.openOnSelect;

        if (
          openIncludedViewOnSelect &&
          response.data.result &&
          response.data.result.length
        ) {
          const row = response.data.result[0];
          const includedWindowId = row.supportIncludedViews
            ? state.listHandler.includedView.windowType ||
              row.includedView.windowType ||
              row.includedView.windowId
            : null;
          const includedViewId = row.supportIncludedViews
            ? state.listHandler.includedView.viewId || row.includedView.viewId
            : null;

          dispatch(
            showIncludedView({
              id: windowId,
              showIncludedView: row.supportIncludedViews,
              windowId: includedWindowId,
              viewId: includedViewId,
              isModal,
            })
          );
        }

        return Promise.resolve(response.data);
      })
      .catch((error) => {
        dispatch(fetchDocumentError(windowId, error, isModal));

        //show error message ?
        return Promise.reject(error);
      });
  };
}

/**
 * @method createView
 * @summary create a new grid view
 */
export function createView({
  windowId,
  viewType,
  filters,
  referenceId,
  refDocType,
  refDocumentId,
  refTabId,
  refRowIds,
  isModal,
}) {
  return (dispatch) => {
    dispatch(createViewPending(windowId, isModal));

    return createViewRequest({
      windowId,
      viewType,
      filters,
      referenceId,
      refDocType,
      refDocumentId,
      refTabId,
      refRowIds,
    })
      .then((response) => {
        dispatch(createViewSuccess(windowId, response.data, isModal));

        const { viewId } = response.data;
        const tableId = getTableId({ windowId, viewId });
        const tableData = { windowId, viewId };

        if (isModal) {
          tableData.modalId = windowId;
        }

        dispatch(createGridTable(tableId, tableData));

        return Promise.resolve(response.data);
      })
      .catch((error) => {
        dispatch(createViewError(windowId, error, isModal));

        //show error message ?
        return Promise.reject(error);
      });
  };
}

/**
 * @method fetchLayout
 * @summary fetch layout data for the grid view
 */
export function fetchLayout(
  windowId,
  viewType,
  viewProfileId = null,
  isModal = false
) {
  return (dispatch) => {
    dispatch(fetchLayoutPending(windowId, isModal));

    return getViewLayout(windowId, viewType, viewProfileId)
      .then((response) => {
        dispatch(fetchLayoutSuccess(windowId, response.data, isModal));

        return Promise.resolve(response.data);
      })
      .catch((error) => {
        dispatch(fetchLayoutError(windowId, error, isModal));

        return Promise.reject(error);
      });
  };
}

/**
 * @method filterView
 * @summary filter grid view
 */
export function filterView(windowId, viewId, filters, isModal = false) {
  return (dispatch) => {
    dispatch(filterViewPending(windowId, isModal));

    return filterViewRequest(windowId, viewId, filters)
      .then((response) => {
        dispatch(filterViewSuccess(windowId, response.data, isModal));

        // remove table, so that we won't add filtered rows to the previous data
        const tableId = getTableId({ windowId, viewId });

        dispatch(deleteTable(tableId));

        return Promise.resolve(response.data);
      })
      .catch((error) => {
        dispatch(filterViewError(windowId, error, isModal));

        return Promise.reject(error);
      });
  };
}

/**
 * @method fetchLocationConfig
 * @summary Get the location search configuration from the API
 */
export function fetchLocationConfig(windowId, isModal = false) {
  return (dispatch) => {
    return locationConfigRequest()
      .then((response) => {
        dispatch(fetchLocationConfigSuccess(windowId, response.data, isModal));
      })
      .catch((error) => {
        dispatch(fetchLocationConfigError(windowId, error, isModal));

        return Promise.reject(error);
      });
  };
}

/**
 * @method showIncludedView
 * @summary ToDo: Describe the method.
 */
export function showIncludedView({
  id,
  showIncludedView,
  windowId,
  viewId,
  forceClose,
  isModal,
} = {}) {
  return (dispatch) => {
    if (id) {
      dispatch(toggleIncludedView(id, showIncludedView, isModal));
    }

    if (showIncludedView) {
      dispatch(setListIncludedView({ windowType: windowId, viewId }));
    }

    if (!showIncludedView) {
      dispatch(
        closeListIncludedView({ windowType: windowId, viewId, forceClose })
      );
    }
  };
}

/**
 * @method fetchHeaderProperties
 * @summary Request view's header properties
 */
export function fetchHeaderProperties({ windowId, viewId, isModal = false }) {
  return (dispatch) => {
    dispatch(fetchDocumentPending(windowId, isModal));

    return headerPropertiesRequest({ windowId, viewId })
      .then((response) => {
        const updatedData = {
          headerProperties: response.data,
        };
        dispatch(updateViewSuccess(windowId, updatedData, isModal));
      })
      .catch((error) => {
        dispatch(updateViewError(windowId, error, isModal));

        return Promise.reject(error);
      });
  };
}
