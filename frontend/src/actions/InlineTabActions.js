import { fetchTab } from './WindowActions';
import { getLayout, getData } from '../api';
import { INLINE_TAB_SHOW_MORE_FROM } from '../constants/Constants';
import {
  UPDATE_INLINE_TAB_ITEM_FIELDS,
  UPDATE_INLINE_TAB_WRAPPER_FIELDS,
  SET_INLINE_TAB_WRAPPER_DATA,
  SET_INLINE_TAB_LAYOUT_AND_DATA,
  SET_INLINE_TAB_ADD_NEW,
  SET_INLINE_TAB_SHOW_MORE,
  SET_INLINE_TAB_ITEM_PROP,
  UPDATE_INLINE_TAB_DATA,
} from '../constants/ActionTypes';

/*
 * @method updateInlineTabItemFields
 * @summary Action creator for updating the fields for the `InlineTab` Item
 *
 * @param {string} inlineTabId
 * @param {string} rowId
 * @param {object} fieldsByName
 */
export function updateInlineTabItemFields({ inlineTabId, fieldsByName }) {
  return {
    type: UPDATE_INLINE_TAB_ITEM_FIELDS,
    payload: { inlineTabId, fieldsByName },
  };
}

/*
 * @method updateInlineTabWrapperFields
 * @summary Action creator for updating the fields for the `InlineTab` Wrapper
 *
 * @param {string} inlineTabWrapperId
 * @param {string} rowId
 * @param {object} fieldsByName
 */
export function updateInlineTabWrapperFields({
  inlineTabWrapperId,
  rowId,
  response,
}) {
  return {
    type: UPDATE_INLINE_TAB_WRAPPER_FIELDS,
    payload: { inlineTabWrapperId, rowId, response },
  };
}

/*
 * Action creator called to set the inlineTabWrapper branch in the redux store with the data payload
 */
export function setInlineTabWrapperData({ inlineTabWrapperId, data }) {
  return {
    type: SET_INLINE_TAB_WRAPPER_DATA,
    payload: { inlineTabWrapperId, data },
  };
}

/*
 * Action creator called to set the showMore value for the corresponding inlineTabWrapperId
 */
export function setInlineTabShowMore({ inlineTabWrapperId, showMore }) {
  return {
    type: SET_INLINE_TAB_SHOW_MORE,
    payload: { inlineTabWrapperId, showMore },
  };
}

/*
 * Action creator called to set the inlineTab branch in the redux store with the data payload
 */
export function setInlineTabLayoutAndData({ inlineTabId, data }) {
  return {
    type: SET_INLINE_TAB_LAYOUT_AND_DATA,
    payload: { inlineTabId, data },
  };
}

/*
 * Action creator called to set the inlineTab AddNew form related data in the store, visible is for toggling the visibility
 */
export function setInlineTabAddNew({ visible, windowId, tabId, rowId, docId }) {
  return {
    type: SET_INLINE_TAB_ADD_NEW,
    payload: { visible, windowId, tabId, rowId, docId },
  };
}

/*
 * Action creator called to set the inlineTab item property to a specific value
 */
export function setInlineTabItemProp({ inlineTabId, targetProp, targetValue }) {
  return {
    type: SET_INLINE_TAB_ITEM_PROP,
    payload: { inlineTabId, targetProp, targetValue },
  };
}

/*
 * @method fetchInlineTabWrapperData
 * @summary Action creator for fetching the data for the `InlineTab` Wrapper (note: wrapper not the inline tab item!)
 *
 * @param {string} windowId
 * @param {string} tabId
 * @param {string} docId
 * @param {string} query
 */
export function fetchInlineTabWrapperData({
  windowId,
  tabId,
  docId,
  query,
  rowId,
  postDeletion,
}) {
  return (dispatch) => {
    dispatch(fetchTab({ tabId, windowId, docId, query })).then((tabData) => {
      /** - if we have the rowId it means we have a new record addition, so we put that at the end of the array - only if this doesn't happen as a result of deletion */
      const inlineTabWrapperSelector = `${windowId}_${tabId}_${docId}`;
      if (rowId && !postDeletion) {
        const lastAdditionIndex = tabData.findIndex(
          (item) => item.rowId === rowId
        );
        if (lastAdditionIndex) {
          const tempData = tabData[lastAdditionIndex];
          tabData.splice(lastAdditionIndex, 1);
          tabData.splice(tabData.length, 0, tempData);
        }
        dispatch(
          setInlineTabShowMore({
            inlineTabWrapperId: inlineTabWrapperSelector,
            showMore: false,
          })
        );
      }

      dispatch(
        setInlineTabWrapperData({
          inlineTabWrapperId: inlineTabWrapperSelector,
          data: tabData,
        })
      );
      /** when we don't have the rowId then we are in the case of the normal rendering, we set the flag to show more (if criteria is met) */
      !rowId &&
        dispatch(
          setInlineTabShowMore({
            inlineTabWrapperId: inlineTabWrapperSelector,
            showMore: tabData.length > INLINE_TAB_SHOW_MORE_FROM ? true : false,
          })
        );
    });
  };
}

/*
 * @method getInlineTabLayoutAndData
 * @summary Action creator for fetching and updating the layout and data for the `inlineTab`
 *
 * @param {string} windowId
 * @param {string} tabId
 * @param {string} docId
 * @param {string} rowId
 */
export function getInlineTabLayoutAndData({ windowId, tabId, docId, rowId }) {
  return (dispatch) => {
    getLayout('window', windowId, tabId, null, null, false).then(
      ({ data: layoutData }) => {
        getData({
          entity: 'window',
          docType: windowId,
          docId,
          tabId,
          fetchAdvancedFields: false,
        }).then(({ data: respFields }) => {
          const { result } = respFields;
          const wantedData = result.filter((item) => item.rowId === rowId);
          dispatch(
            setInlineTabLayoutAndData({
              inlineTabId: `${windowId}_${tabId}_${rowId}`,
              data: { layout: layoutData, data: wantedData[0] },
            })
          );
        });
      }
    );
  };
}

/**
 * @method inlineTabAfterGetLayout
 * @summary triggers post layout fetch actions that will update the inline tab layout and data and the inlineTab addNew sub-branch
 * @param {object} data - the layout fetched previously with getLayout
 * @param {object} disconnectedData - result from initWindow (add new case for inlineTab) - see in the WindowActions what it contains (for debug)
 */
export function inlineTabAfterGetLayout({ data, disconnectedData }) {
  return (dispatch) => {
    const inlineTabTargetId = `${disconnectedData.windowId}_${disconnectedData.tabId}_${disconnectedData.rowId}`;
    dispatch(
      setInlineTabLayoutAndData({
        inlineTabId: inlineTabTargetId,
        data: { layout: data, data: disconnectedData },
      })
    );
    dispatch(
      setInlineTabAddNew({
        visible: true,
        docId: disconnectedData.id,
        windowId: disconnectedData.windowId,
        tabId: disconnectedData.tabId,
        rowId: disconnectedData.rowId,
      })
    );
  };
}

/**
 * @method patchInlineTab
 * @summary after an a patch action that targeted a field belonging to a inlineTab we execute this method that will update the
 *          updateInlineTabWrapperFields and updateInlineTabItemFields
 * @param {object} ret - this is what the `PATCH` returned from the xhr call
 * @param {string} windowId
 * @param {string} tabId
 * @param {string} docId
 * @param {string} rowId
 */
export function patchInlineTab({ ret, windowId, tabId, docId, rowId }) {
  return (dispatch) => {
    ret.then((response) => {
      const respDocuments =
        response && response.documents ? response.documents : response;
      if (respDocuments && respDocuments[0]) {
        const { validStatus } = respDocuments[0];
        const inlineTabId = `${windowId}_${tabId}_${rowId}`;

        if (validStatus) {
          dispatch(
            dispatch({
              type: UPDATE_INLINE_TAB_DATA,
              payload: {
                inlineTabId,
                data: { validStatus },
              },
            })
          );
        }

        dispatch(
          updateInlineTabWrapperFields({
            inlineTabWrapperId: `${windowId}_${tabId}_${docId}`,
            rowId,
            response: respDocuments[0],
          })
        );
        dispatch(
          updateInlineTabItemFields({
            inlineTabId: `${windowId}_${tabId}_${rowId}`,
            fieldsByName: respDocuments[0].fieldsByName,
          })
        );
      }
    });
  };
}
