import {
  getViewAttributesRequest,
  getViewAttributesLayoutRequest,
  patchViewAttributesRequest,
  initQuickInput,
  getLayout,
  patchRequest,
} from '../api';
import {
  FETCH_ATTRIBUTES_DATA,
  FETCH_ATTRIBUTES_LAYOUT,
  DELETE_ATTRIBUTES,
  PATCH_ATTRIBUTES,
  SET_ATTRIBUTES_DATA,
  FETCH_QUICKINPUT_DATA,
  FETCH_QUICKINPUT_LAYOUT,
  DELETE_QUICKINPUT,
  SET_QUICKINPUT_DATA,
  PATCH_QUICKINPUT_PENDING,
  PATCH_QUICKINPUT_DONE,
} from '../constants/ActionTypes';
import { parseToDisplay } from '../utils/documentListHelper';

//
// SELECTION ATTRIBUTES
//

/*
 * @method fetchViewAttributes
 * @summary Get data for attribute's fields
 *
 * @param {number} windowId
 * @param {string} viewId
 * @param {string} rowId
 */
export function fetchViewAttributes({ windowId, viewId, rowId }) {
  return (dispatch) => {
    return getViewAttributesRequest(windowId, viewId, rowId).then(
      ({ data }) => {
        dispatch({
          type: FETCH_ATTRIBUTES_DATA,
          payload: {
            fieldsByName: data.fieldsByName,
            id: data.id,
          },
        });
      }
    );
  };
}

/*
 * @method fetchViewAttributesLayout
 * @summary Get attributes layout and save it in the store
 *
 * @param {number} windowId
 * @param {string} viewId
 * @param {string} rowId
 */
export function fetchViewAttributesLayout({ windowId, viewId, rowId }) {
  return (dispatch) => {
    return getViewAttributesLayoutRequest(windowId, viewId, rowId).then(
      ({ data }) => {
        dispatch({
          type: FETCH_ATTRIBUTES_LAYOUT,
          payload: { elements: data.elements },
        });
      }
    );
  };
}

/*
 * @method patchViewAttributes
 * @summary Patch changes to field
 *
 * @param {number} windowId
 * @param {string} viewId
 * @param {string} rowId
 * @param {string} prop - field name
 * @param {any} value - field value
 */
export function patchViewAttributes({ windowId, viewId, rowId, prop, value }) {
  return (dispatch) => {
    return patchViewAttributesRequest(
      windowId,
      viewId,
      rowId,
      prop,
      value
    ).then(({ data }) => {
      if (data.length) {
        dispatch({
          type: PATCH_ATTRIBUTES,
          payload: { data: data[0].fieldsByName },
        });
      }
    });
  };
}

/*
 * @method setViewAttributesData
 * @summary change local value of an attribute's field
 *
 * @param {string} field - field name
 * @param {any} value
 */
export function setViewAttributesData({ field, value }) {
  return {
    type: SET_ATTRIBUTES_DATA,
    payload: { field, value },
  };
}

/*
 * @method deleteViewAttributes
 * @summary delete attributes data
 */
export function deleteViewAttributes() {
  return {
    type: DELETE_ATTRIBUTES,
  };
}

//
// TABLE QUICK INPUT
//

/**
 * @method deleteQuickInput
 * @summary Remove quick input's data
 */
export function deleteQuickInput() {
  return {
    type: DELETE_QUICKINPUT,
  };
}

/**
 * @method setQuickinputPending
 * @summary Set the `inProgress` flag of QuickInput to true
 */
export function setQuickinputPending() {
  return {
    type: PATCH_QUICKINPUT_PENDING,
  };
}

/**
 * @method setQuickinputDone
 * @summary Set the `inProgress` flag of QuickInput to false
 */
export function setQuickinputDone() {
  return {
    type: PATCH_QUICKINPUT_DONE,
  };
}

/**
 * @method setQuickinputData
 * @summary Save field changes locally
 *
 * @param {object} fieldData - object with field names as keys and their data as values
 */
export function setQuickinputData(fieldData) {
  return {
    type: SET_QUICKINPUT_DATA,
    payload: { fieldData },
  };
}

/*
 * @method patchQuickInput
 * @summary Save QuickInput changes on the server
 *
 * @param {number} windowId
 * @param {string} docId
 * @param {string} tabId
 * @param {string} prop
 * @param {any} value
 */
export function patchQuickInput({ windowId, docId, tabId, prop, value }) {
  return (dispatch, getState) => {
    const state = getState();
    const { id } = state.widgetHandler.quickInput;

    dispatch(setQuickinputPending());

    return patchRequest({
      entity: 'window',
      docType: windowId,
      docId,
      tabId,
      property: prop,
      value,
      subentity: 'quickInput',
      subentityId: id,
    }).then(({ data }) => {
      const fields = data[0] && data[0].fieldsByName;

      if (fields) {
        dispatch(setQuickinputData(fields));
      }

      dispatch(setQuickinputDone());
    });
  };
}
/*
 * @method fetchQuickInputData
 * @summary Get data for QuickInput's fields
 *
 * @param {number} windowId
 * @param {string} docId
 * @param {string} tabId
 */
export function fetchQuickInputData({ windowId, docId, tabId }) {
  return (dispatch) => {
    return initQuickInput('window', windowId, docId, tabId, 'quickInput').then(
      ({ data }) => {
        dispatch({
          type: FETCH_QUICKINPUT_DATA,
          payload: {
            data: parseToDisplay(data.fieldsByName),
            id: data.id,
          },
        });
      }
    );
  };
}

/*
 * @method fetchQuickInputLayout
 * @summary Get QuickInput's layout and save it in the store
 *
 * @param {number} windowId
 * @param {string} docId
 * @param {string} tabId
 */
export function fetchQuickInputLayout({ windowId, docId, tabId }) {
  return (dispatch) => {
    return getLayout('window', windowId, tabId, 'quickInput', docId).then(
      ({ data }) => {
        dispatch({
          type: FETCH_QUICKINPUT_LAYOUT,
          payload: {
            layout: data.elements,
          },
        });
      }
    );
  };
}
