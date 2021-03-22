import {
  getViewAttributesRequest,
  getViewAttributesLayoutRequest,
  // patchViewAttributesRequest,
  // getViewAttributeDropdownRequest,
  // getViewAttributeTypeaheadRequest,
} from '../api';

export function fetchViewAttributes({ windowId, viewId, rowId }) {
  return (dispatch) => {
    console.log('FETCH_DLW_DATA');

    return getViewAttributesRequest(windowId, viewId, rowId).then((resp) => {
      dispatch({
        type: 'FETCH_DLW_DATA',
        payload: {
          fieldsByName: resp.data.fieldsByName,
        },
      });
    });
  };
}

export function fetchViewAttributesLayout({ windowId, viewId, rowId }) {
  return (dispatch) => {
    console.log('FETCH_DLW_LAYOUT');
    return getViewAttributesLayoutRequest(windowId, viewId, rowId).then(
      (resp) => {
        dispatch({
          type: 'FETCH_DLW_LAYOUT',
          payload: { elements: resp.data.elements },
        });
      }
    );
  };
}
