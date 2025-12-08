import {
  getViewAttributesLayoutRequest,
  getViewAttributesRequest,
  patchViewAttributesRequest,
} from '../api';
import {
  DELETE_ATTRIBUTES,
  FETCH_ATTRIBUTES_DATA,
  FETCH_ATTRIBUTES_LAYOUT,
  PATCH_ATTRIBUTES,
  SET_ATTRIBUTES_DATA,
} from '../constants/ActionTypes';

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
