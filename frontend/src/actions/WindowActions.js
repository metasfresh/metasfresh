import axios from 'axios';
import counterpart from 'counterpart';
import currentDevice from 'current-device';

import history from '../services/History';

import {
  ACTIVATE_TAB,
  ALLOW_OUTSIDE_CLICK,
  ALLOW_SHORTCUT,
  CHANGE_INDICATOR_STATE,
  CLEAR_MASTER_DATA,
  CLOSE_FILTER_BOX,
  CLOSE_MODAL,
  CLOSE_RAW_MODAL,
  DISABLE_OUTSIDE_CLICK,
  DISABLE_SHORTCUT,
  INIT_DATA_SUCCESS,
  INIT_LAYOUT_SUCCESS,
  INIT_WINDOW,
  OPEN_FILTER_BOX,
  OPEN_MODAL,
  OPEN_RAW_MODAL,
  PATCH_FAILURE,
  PATCH_REQUEST,
  PATCH_SUCCESS,
  RESET_PRINTING_OPTIONS,
  SET_PRINTING_OPTIONS,
  SET_RAW_MODAL_DESCRIPTION,
  SET_RAW_MODAL_TITLE,
  SET_SPINNER,
  SORT_TAB,
  TOGGLE_OVERLAY,
  TOGGLE_PRINTING_OPTION,
  UNSELECT_TAB,
  UPDATE_DATA_FIELD_PROPERTY,
  UPDATE_DATA_INCLUDED_TABS_INFO,
  UPDATE_DATA_PROPERTY,
  UPDATE_DATA_SAVE_STATUS,
  UPDATE_DATA_VALID_STATUS,
  UPDATE_MASTER_DATA,
  UPDATE_MODAL,
  UPDATE_RAW_MODAL,
  UPDATE_TAB_LAYOUT,
} from '../constants/ActionTypes';
import { createView, patchViewAction } from './ViewActions';
import { PROCESS_NAME } from '../constants/Constants';
import { preFormatPostDATA, toggleFullScreen } from '../utils';
import {
  getInvalidDataItem,
  getScope,
  parseToDisplay,
} from '../utils/documentListHelper';

import {
  formatParentUrl,
  getData,
  getTabLayoutRequest,
  getTabRequest,
  patchRequest,
} from '../api';

import { getTableId } from '../reducers/tables';
import {
  addNotification,
  deleteNotification,
  setNotificationProgress,
} from './AppActions';
import { getWindowBreadcrumb } from './MenuActions';
import {
  updateCommentsPanel,
  updateCommentsPanelOpenFlag,
  updateCommentsPanelTextInput,
} from './CommentsPanelActions';
import {
  createTabTable,
  updateTableRowProperty,
  updateTabTable,
} from './TableActions';
import { inlineTabAfterGetLayout, patchInlineTab } from './InlineTabActions';
import { getPrintFile, getPrintUrl } from '../api/window';
import { STATIC_MODAL_TYPE_ChangeCurrentWorkplace } from '../components/app/ChangeCurrentWorkplace';

export function toggleOverlay(data) {
  return {
    type: TOGGLE_OVERLAY,
    data: data,
  };
}

export function openRawModal({ windowId, viewId, profileId, title }) {
  return {
    type: OPEN_RAW_MODAL,
    windowId,
    viewId,
    profileId,
    title,
  };
}

export function updateRawModal(windowType, data) {
  return {
    type: UPDATE_RAW_MODAL,
    windowId: windowType,
    data: { ...data },
  };
}

/**
 * @method setRawModalTitle
 * @summary Action creator that sets the title on the rawModal
 */
export function setRawModalTitle(title, windowType) {
  return {
    type: SET_RAW_MODAL_TITLE,
    payload: { windowType, title },
  };
}

/**
 * @method setRawModalDescription
 * @summary Action creator that sets the description on the rawModal
 */
export function setRawModalDescription(description, windowType) {
  return {
    type: SET_RAW_MODAL_DESCRIPTION,
    payload: { windowType, description },
  };
}

export function closeRawModal() {
  const isMobile =
    currentDevice.type === 'mobile' || currentDevice.type === 'tablet';

  if (isMobile) {
    toggleFullScreen();
  }

  return {
    type: CLOSE_RAW_MODAL,
  };
}

export function openFilterBox(boundingRect) {
  return {
    type: OPEN_FILTER_BOX,
    boundingRect,
  };
}

export function closeFilterBox() {
  return {
    type: CLOSE_FILTER_BOX,
  };
}

// TODO: Name collides with the value in the state
export function allowOutsideClick() {
  return {
    type: ALLOW_OUTSIDE_CLICK,
  };
}

export function disableOutsideClick() {
  return {
    type: DISABLE_OUTSIDE_CLICK,
  };
}

export function allowShortcut() {
  return {
    type: ALLOW_SHORTCUT,
  };
}

export function disableShortcut() {
  return {
    type: DISABLE_SHORTCUT,
  };
}

export function activateTab(scope, tabId) {
  return {
    type: ACTIVATE_TAB,
    scope,
    tabId,
  };
}

export function unselectTab(scope) {
  return {
    type: UNSELECT_TAB,
    scope,
  };
}

/**
 * @method setUpdatedTabLayout
 * @summary Action creator to update tab's layout data in the store
 *
 * @param {string} tabId
 * @param {object} layoutData
 */
function setUpdatedTabLayout(tabId, layoutData) {
  return {
    type: UPDATE_TAB_LAYOUT,
    payload: { tabId, layoutData },
  };
}

export function initLayoutSuccess(layout, scope) {
  return {
    type: INIT_LAYOUT_SUCCESS,
    layout: layout,
    scope: scope,
  };
}

// @VisibleForTesting
export function initDataSuccess({
  windowId,
  data,
  docId,
  includedTabsInfo,
  saveStatus,
  scope,
  standardActions,
  validStatus,
  websocket,
  hasComments,
  notFoundMessage,
  notFoundMessageDetail,
}) {
  return {
    type: INIT_DATA_SUCCESS,
    windowId,
    data,
    docId,
    includedTabsInfo,
    saveStatus,
    scope,
    standardActions,
    validStatus,
    websocket,
    hasComments,
    notFoundMessage,
    notFoundMessageDetail,
  };
}

function initDataNotFound({ windowId, message, messageDetail }) {
  return (dispose) => {
    dispose(getWindowBreadcrumb(windowId));
    dispose(
      initDataSuccess({
        data: {},
        docId: 'notfound',
        notFoundMessage: message,
        notFoundMessageDetail: messageDetail,
        includedTabsInfo: {},
        scope: 'master',
        saveStatus: { saved: true },
        standardActions: [],
        validStatus: {},
      })
    );
  };
}

export function updateMasterData(data) {
  return {
    type: UPDATE_MASTER_DATA,
    payload: data.fieldsByName,
  };
}

export function clearMasterData() {
  return {
    type: CLEAR_MASTER_DATA,
  };
}

export function sortTab({ scope, windowId, docId, tabId, field, asc }) {
  return {
    type: SORT_TAB,
    scope,
    windowId,
    docId,
    tabId,
    field,
    asc,
  };
}

function updateDataProperty(property, value, scope) {
  return {
    type: UPDATE_DATA_PROPERTY,
    property,
    value,
    scope,
  };
}

export function updateDataSaveStatus(scope, saveStatus) {
  return {
    type: UPDATE_DATA_SAVE_STATUS,
    scope,
    saveStatus,
  };
}

export function updateDataValidStatus(scope, validStatus) {
  return {
    type: UPDATE_DATA_VALID_STATUS,
    scope,
    validStatus,
  };
}

export function updateDataIncludedTabsInfo(scope, includedTabsInfo) {
  return {
    type: UPDATE_DATA_INCLUDED_TABS_INFO,
    scope,
    includedTabsInfo,
  };
}

export function updateDataFieldProperty(property, item, scope) {
  return {
    type: UPDATE_DATA_FIELD_PROPERTY,
    property: property,
    item: item,
    scope: scope,
  };
}

export function openModal({
  title = '',
  windowId,
  modalType,
  tabId = null,
  rowId = null,
  isAdvanced = false,
  viewId = null,
  viewDocumentIds = null,
  dataId = null,
  triggerField = null,
  parentViewId = null,
  parentViewSelectedIds = null,
  childViewId = null,
  childViewSelectedIds = null,
  staticModalType = null,
  parentWindowId = null,
  parentDocumentId = null,
  parentFieldId = null,
}) {
  const isMobile =
    currentDevice.type === 'mobile' || currentDevice.type === 'tablet';

  if (isMobile) {
    toggleFullScreen();
  }

  return {
    type: OPEN_MODAL,
    payload: {
      windowId,
      tabId,
      rowId,
      viewId,
      dataId,
      title,
      isAdvanced,
      viewDocumentIds,
      triggerField,
      modalType,
      staticModalType,
      parentViewId,
      parentViewSelectedIds,
      childViewId,
      childViewSelectedIds,
      parentWindowId,
      parentDocumentId,
      parentFieldId,
    },
  };
}

export function closeModal() {
  return {
    type: CLOSE_MODAL,
  };
}

export function updateModal(rowId, dataId) {
  return {
    type: UPDATE_MODAL,
    rowId,
    dataId,
  };
}

export function indicatorState(state) {
  return {
    type: CHANGE_INDICATOR_STATE,
    state: state,
  };
}

// THUNK ACTIONS

/*
 * @method fetchTab
 * @summary Action creator for fetching single tab's rows
 */
export function fetchTab({ tabId, windowId, docId, orderBy }) {
  return (dispatch) => {
    const tableId = getTableId({ windowId, tabId, docId });
    dispatch(updateTabTable({ tableId, pending: true }));
    return getTabRequest(tabId, windowId, docId, orderBy)
      .then(({ rows, orderBys }) => {
        dispatch(
          updateTabTable({
            tableId,
            tableResponse: { result: rows, orderBys },
            pending: false,
          })
        );

        return rows;
      })
      .catch((error) => {
        //show error message ?
        return Promise.reject(error);
      });
  };
}

/*
 * @method updateTabLayout
 * @summary Action creator for fetching and updating single tab's layout
 *
 * @param {string} windowId
 * @param {string} tabId
 */
export function updateTabLayout(windowId, tabId) {
  return (dispatch) => {
    return getTabLayoutRequest(windowId, tabId)
      .then((layout) => {
        dispatch(setUpdatedTabLayout(tabId, layout));

        return Promise.resolve(tabId);
      })
      .catch((error) => {
        return Promise.reject(error);
      });
  };
}

function getOrCreateData(windowId, docId, tabId, rowId = null, isAdvanced) {
  if (docId === 'NEW') {
    //New master document
    return patchRequest({
      entity: 'window',
      docType: windowId,
      docId,
    });
  } else if (rowId === 'NEW') {
    //New row document
    return patchRequest({
      entity: 'window',
      docType: windowId,
      docId,
      tabId,
      rowId,
    });
  } else if (rowId) {
    //Existing row document
    return getData({
      entity: 'window',
      docType: windowId,
      docId: docId,
      tabId: tabId,
      rowId: rowId,
      fetchAdvancedFields: isAdvanced,
    }).catch((e) => e);
  } else {
    //Existing master document
    return getData({
      entity: 'window',
      docType: windowId,
      docId: docId,
      fetchAdvancedFields: isAdvanced,
    });
  }
}

/*
 * @method createSearchWindow
 * @summary - special function that is used to get the window view information for the search and opens a modal with that view
 *            this is a hacky way of opening the window as this wasn't existing for the SEARCH type (check how `NEW` was opening)
 * param {object}
 */
export function createSearchWindow({
  windowId: windowType,
  docId,
  tabId,
  rowId,
  isModal,
  dispatch,
  title,
}) {
  dispatch(
    createView({
      windowId: windowType,
      viewType: 'grid',
      refDocumentId: docId,
      refTabId: tabId,
      refRowIds: [rowId],
      isModal,
    })
  )
    .then(({ windowId, viewId }) => {
      dispatch(openRawModal({ windowId, viewId, title }));
    })
    .finally(() => {
      dispatch(setSpinner(false));
    });
}

/*
 * Main method to generate window
 */
export function createWindow({
  windowId,
  docId,
  tabId,
  rowId,
  isModal,
  isAdvanced,
  disconnected,
  title,
  urlSearchParams,
}) {
  let documentId = docId || 'NEW';
  if (documentId.toLowerCase() === 'new') {
    documentId = 'NEW';
  }

  let disconnectedData = null;

  return async (dispatch) => {
    if (documentId === 'SEARCH') {
      // set the `showSpinner` flag to true to show the spinner while data is fetched
      dispatch(setSpinner(true));

      // use specific function for search window creation
      createSearchWindow({
        windowId,
        docId,
        tabId,
        rowId,
        isModal,
        isAdvanced,
        disconnected,
        dispatch,
        title,
      });
      return false;
    }

    dispatch({ type: INIT_WINDOW });

    //
    // Get layout but do not initialize the state yet.
    // This chain is really important, to do not re-render widgets on init.
    const layout = await getTabLayoutRequest(windowId, tabId, isAdvanced).catch(
      (e) => {
        console.log('get error while loading layout', { windowId, tabId, e });
        dispatch(initDataNotFound({ windowId }));
      }
    );
    if (!layout) {
      return;
    }

    return getOrCreateData(windowId, documentId, tabId, rowId, isAdvanced)
      .then((response) => {
        if (!response || !response.data) {
          return Promise.resolve(null);
        }
        // Note: this `documents` key comes only as a result of a PATCH, this is the reason this check is needed
        const data = response.data.documents
          ? response.data.documents[0]
          : response.data[0];
        const tabs = data.includedTabsInfo;
        let docId = data.id;

        // we don't create table for advanced edit
        if (tabs && !isAdvanced) {
          Object.values(tabs).forEach((tab) => {
            const tabId = tab.tabId || tab.tabid;
            const tableId = getTableId({ windowId, docId, tabId });
            const tableData = {
              windowType: windowId,
              docId,
              tabId,
              ...tab,
            };

            dispatch(createTabTable(tableId, tableData));
          });
        }

        if (documentId === 'NEW' && !isModal) {
          // redirect immediately, but preserve URL search params if any
          return history.replace(
            `/window/${windowId}/${docId}${urlSearchParams ?? ''}`
          );
        }

        let elem = 0;

        let responseDocuments = response.data.documents
          ? response.data.documents
          : response.data;

        responseDocuments.forEach((value, index) => {
          if (value.rowId === rowId) {
            elem = index;
          }
        });

        if (documentId === 'NEW') {
          dispatch(updateModal(null, docId));
          const { includedTabsInfo } = responseDocuments[0];
          includedTabsInfo &&
            dispatch(updateDataIncludedTabsInfo('master', includedTabsInfo));
        }

        // TODO: Is `elem` ever different than 0 ?
        docId = responseDocuments[elem].id;
        disconnected !== 'inlineTab' &&
          dispatch(
            initDataSuccess({
              windowId,
              data: parseToDisplay(responseDocuments[elem].fieldsByName),
              docId,
              saveStatus: data.saveStatus,
              scope: getScope(isModal),
              standardActions: data.standardActions,
              validStatus: data.validStatus,
              includedTabsInfo: data.includedTabsInfo,
              websocket: data.websocketEndpoint,
              hasComments: data.hasComments,
            })
          );

        if (isModal) {
          if (rowId === 'NEW') {
            /** special case of inlineTab - disconnectedData will be used for data feed */
            if (disconnected === 'inlineTab') {
              disconnectedData = responseDocuments[0];
            } else {
              dispatch(
                mapDataToState({
                  data: response.data,
                  isModal: false,
                  rowId: 'NEW',
                  docId,
                  windowType: windowId,
                })
              );
              dispatch(updateStatus(responseDocuments));
              dispatch(updateModal(data.rowId));
            }
          }
        } else {
          dispatch(getWindowBreadcrumb(windowId));
        }

        //
        // Layout
        {
          if (layout.tabs && !isAdvanced) {
            Object.values(layout.tabs).forEach((tab) => {
              const { tabId } = tab;
              dispatch(
                updateTabTable({
                  tableId: getTableId({
                    windowId,
                    docId,
                    tabId,
                  }),
                  tableResponse: {
                    windowType: windowId,
                    docId,
                    tabId,
                    ...tab,
                  },
                  pending: false,
                })
              );
            });
          }
          /** post get layout action triggered for the inlineTab case */
          if (disconnectedData && disconnected === 'inlineTab') {
            dispatch(
              inlineTabAfterGetLayout({ data: layout, disconnectedData })
            );
          } else {
            dispatch(initLayoutSuccess(layout, getScope(isModal)));
          }
        }
      })
      .catch((e) => {
        dispatch(
          initDataNotFound({
            windowId,
            message: layout.notFoundMessage,
            messageDetail: layout.notFoundMessageDetail,
          })
        );

        return { status: e.status, message: e.statusText };
      });
  };
}

const getChangelogUrl = function (windowId, docId, tabId, rowId) {
  let documentId = docId;

  if (!docId && rowId) {
    documentId = rowId[0];
  }

  return `${config.API_URL}/window/${windowId}${
    documentId ? `/${documentId}` : ''
  }${rowId && tabId ? `/${tabId}/${rowId}` : ''}/changeLog`;
};

export function fetchChangeLog(windowId, docId, tabId, rowId) {
  return (dispatch) => {
    const parentUrl = getChangelogUrl(windowId, docId, null, rowId);

    return axios.get(parentUrl).then(async (response) => {
      const data = response.data;
      let rowData = null;

      if (docId && rowId) {
        if (rowId.length === 1) {
          const childUrl = getChangelogUrl(windowId, docId, tabId, rowId);
          rowData = await axios.get(childUrl).then((resp) => resp.data);
        }
      }

      if (rowData) {
        data.rowsData = rowData;
      }

      dispatch(
        initDataSuccess({
          data,
          docId,
          scope: 'modal',
        })
      );
    });
  };
}

/**
 * this is 'generic' window action that allows calling APIs that do follow a specific pattern
 * in their path like /rest/api/documentView/{windowId}/{viewId}/{target}
 * Where target can be for example 'geoLocations' or 'comments'. This can be further
 * adapted to other REST paths by shaping the formatParentUrl function accordingly
 * windowId - windowId for which we want to apply/get changes
 * docId    - docId for which we want to apply/get changes
 * tabId    - tabId for which we want to apply/get changes
 * rowId    - rowId for which we want to apply/get changes
 * verb     - GET/POST for now
 * data     - data you want to be passed for a POST request
 * @param {object} param
 */
export function callAPI({ windowId, docId, tabId, rowId, target, verb, data }) {
  return (dispatch) => {
    const parentUrl = formatParentUrl({ windowId, docId, rowId, target });
    if (!parentUrl) return;
    dispatch(updateCommentsPanelOpenFlag(true));
    // -- GET call - adapt shape as needed
    if (verb === 'GET') {
      return axios.get(parentUrl).then(async (response) => {
        const data = response.data;
        let rowData = null;

        if (docId && rowId) {
          if (rowId.length === 1) {
            const childUrl = getChangelogUrl(windowId, docId, tabId, rowId);
            rowData = await axios.get(childUrl).then((resp) => resp.data);
          }
        }

        if (rowData) {
          data.rowsData = rowData;
        }
        // update corresponding target in the store - might be adapted for more separated entities
        if (target === 'comments') {
          dispatch(updateCommentsPanel(data));
        }
        // -- end updating corresponding target
        dispatch(
          initDataSuccess({
            data,
            docId,
            scope: 'modal',
          })
        );
      });
    }

    // -- actions dispatched on POST below
    if (verb === 'POST') {
      const dataToSend = preFormatPostDATA({ target, postData: { txt: data } });
      return axios.post(parentUrl, dataToSend).then(async (response) => {
        const data = response.data;
        if (target === 'comments') {
          dispatch(
            callAPI({
              windowId,
              docId,
              tabId,
              rowId,
              target,
              verb: 'GET',
            })
          );
          dispatch(updateCommentsPanelTextInput('')); // clear the input in the form
        }
        return data;
      });
    }
  };
}

export const patchWindow = ({
  windowId,
  documentId = 'NEW',
  tabId = null,
  rowId = null,
  fieldName,
  value,
}) => {
  return patch(
    'window', // entity
    windowId,
    documentId,
    tabId,
    rowId,
    fieldName,
    value,
    false, //isModal
    false, // isAdvanced
    null, // viewId
    false, // isEdit
    false // disconnected
  );
};

/*
 * Wrapper for patch request of widget elements
 * when responses should merge store
 * @todo TODO: This should return a promise
 */
export function patch(
  entity, // type, e.g. documentView
  windowType, // aka windowId
  id = 'NEW', // documentId
  tabId,
  rowId,
  property,
  value,
  isModal,
  isAdvanced,
  viewId,
  isEdit,
  disconnected
) {
  if (entity === 'documentView' && isModal) {
    return patchViewAction({
      windowId: windowType,
      viewId,
      rowId,
      fieldName: property,
      value,
    });
  }

  return async (dispatch) => {
    const symbol = Symbol();

    const options = {
      entity,
      docType: windowType,
      docId: id,
      tabId,
      rowId,
      property,
      value,
      isAdvanced,
      viewId,
      isEdit,
    };

    await dispatch({ type: PATCH_REQUEST, symbol, options });

    try {
      const response = await patchRequest(options);
      const data =
        response.data.documents instanceof Array
          ? response.data.documents
          : response.data;

      const invalidDataItem = getInvalidDataItem(data);

      const dataItem = invalidDataItem === null ? data[0] : invalidDataItem;

      const includedTabsInfo =
        dataItem && dataItem.includedTabsInfo
          ? dataItem.includedTabsInfo
          : undefined;

      // prevent recursion in merge
      data.documents &&
        data.documents.documents &&
        delete data.documents.documents;

      await dispatch(
        mapDataToState({
          data,
          isModal,
          rowId,
          id,
          windowType,
          isAdvanced,
          disconnected,
        })
      );

      // update the inlineTabsInfo if such information is present
      includedTabsInfo &&
        dispatch(updateDataIncludedTabsInfo('master', includedTabsInfo));

      if (
        dataItem &&
        dataItem.validStatus &&
        !dataItem.validStatus.valid &&
        (property === dataItem.validStatus.fieldName ||
          dataItem.validStatus.fieldName === undefined)
      ) {
        await dispatch({ type: PATCH_FAILURE, symbol });

        // Don't show the notification because we are showing the error message in Indicator component

        // const errorMessage = dataItem.validStatus.reason;
        // dispatch(
        //   addNotification(
        //     'Error: ' + errorMessage.split(' ', 4).join(' ') + '...',
        //     errorMessage,
        //     5000,
        //     'error',
        //     ''
        //   )
        // );
      } else {
        await dispatch({ type: PATCH_SUCCESS, symbol });

        return response.data;
      }
    } catch (error) {
      await dispatch({ type: PATCH_FAILURE, symbol });

      const response = await getData({
        entity: entity,
        docType: windowType,
        docId: id,
        tabId: tabId,
        rowId: rowId,
        fetchAdvancedFields: isAdvanced,
        viewId: viewId,
      });

      await dispatch(
        mapDataToState({
          data: response.data,
          isModal,
          rowId,
          id,
          windowType,
          isAdvanced,
          disconnected,
        })
      );
    }
  };
}

export function fireUpdateData({
  windowId,
  documentId,
  tabId,
  rowId,
  isModal,
  fetchAdvancedFields,
}) {
  return (dispatch) => {
    getData({
      entity: 'window',
      docType: windowId,
      docId: documentId,
      tabId: tabId,
      rowId: rowId,
      fetchAdvancedFields: fetchAdvancedFields,
    })
      .then((response) => {
        dispatch(
          mapDataToState({
            data: response.data,
            isModal,
            rowId,
            documentId,
            windowId,
            fetchAdvancedFields,
          })
        );
      })
      .catch((axiosError) => {
        if (is404(axiosError) && !tabId) {
          dispatch(initDataNotFound({ windowId }));
        }

        return axiosError;
      });
  };
}

function is404(axiosError) {
  return axiosError?.response?.status === 404;
}

// TODO: Check if all cases are valid. Especially if `scope` is `master`, or
// `key` is `includedTabsInfo`.
function updateData(doc, scope) {
  return (dispatch) => {
    Object.keys(doc).map((key) => {
      if (key === 'fieldsByName') {
        // update all data fields at once
        dispatch(updateDataProperty('data', doc[key], scope));
      } else if (key === 'includedTabsInfo') {
        dispatch(updateDataIncludedTabsInfo('master', doc[key]));
      } else {
        dispatch(updateDataProperty(key, doc[key], scope));
      }
    });
  };
}

function mapDataToState({ data, isModal, rowId, disconnected }) {
  return (dispatch) => {
    const dataArray = typeof data.splice === 'function' ? data : [data];

    dataArray.map((item, index) => {
      const parsedItem = item.fieldsByName
        ? {
            ...item,
            fieldsByName: parseToDisplay(item.fieldsByName),
          }
        : item;

      if (
        !(index === 0 && rowId === 'NEW') &&
        (!item.rowId || (isModal && item.rowId))
      ) {
        // used this trick to differentiate and have the correct path to patch endpoint when using the inlinetab within modal
        // otherwise the tabId is updated in the windowHandler.modal.tabId and then the endpoint for the PATCH in modal is altered
        disconnected !== 'inlineTab' &&
          dispatch(updateData(parsedItem, getScope(isModal && index === 0)));
      }
    });
  };
}

function updateStatus(responseData) {
  return (dispatch) => {
    const updateDispatch = (item) => {
      if (!item.rowId) {
        item.validStatus &&
          dispatch(updateDataValidStatus('master', item.validStatus));
        item.saveStatus &&
          dispatch(updateDataSaveStatus('master', item.saveStatus));
        // TODO: We probably don't need this anymore
        item.includedTabsInfo &&
          dispatch(updateDataIncludedTabsInfo('master', item.includedTabsInfo));
      }
    };

    if (Array.isArray(responseData)) {
      responseData.map((item) => {
        updateDispatch(item);
      });
    } else {
      updateDispatch(responseData);
    }
  };
}

/*
 * It updates store for single field value modification, like handleChange
 * in MasterWidget
 */
export function updatePropertyValue({
  windowId,
  docId,
  property,
  value,
  tabId,
  rowId,
  isModal,
  entity,
  tableId,
  disconnected,
  action,
  ret,
}) {
  return (dispatch) => {
    if (rowId) {
      const change = {
        fieldsByName: {
          [`${property}`]: {
            value,
          },
        },
      };
      // - for the `inlineTab` type we will update the corresponding branch in the store
      if (disconnected === 'inlineTab') {
        action === 'patch' &&
          dispatch(patchInlineTab({ ret, windowId, tabId, docId, rowId }));
        return false;
      }

      dispatch(updateTableRowProperty({ tableId, rowId, change }));
    } else if (!tabId || !rowId) {
      // modal's data is in `tables`
      if (!isModal) {
        dispatch(
          updateDataFieldProperty(property, { value }, getScope(isModal))
        );
      }
      if (isModal && entity !== PROCESS_NAME) {
        //update the master field too if exist
        dispatch(updateDataFieldProperty(property, { value }, 'master'));
      }
    }
  };
}

function handleUploadProgress(dispatch, notificationTitle, progressEvent) {
  let percentLeft = Math.min(
    Math.floor((progressEvent.loaded * 100) / progressEvent.total),
    98
  );

  dispatch(setNotificationProgress(notificationTitle, percentLeft));
}

export function attachFileAction(windowType, docId, data) {
  return (dispatch) => {
    const titlePending = counterpart.translate(
      'window.attachment.title.pending'
    );
    const titleDone = counterpart.translate('window.attachment.title.done');
    const titleError = counterpart.translate('window.attachment.title.error');
    const CancelToken = axios.CancelToken;
    const source = CancelToken.source();

    dispatch(
      addNotification(
        titlePending,
        counterpart.translate('window.attachment.uploading'),
        0,
        'primary',
        null,
        source
      )
    );

    const requestConfig = {
      onUploadProgress: handleUploadProgress.bind(this, dispatch, titlePending),
      cancelToken: source.token,
    };

    axios
      .post(
        `${config.API_URL}/window/${windowType}/${docId}/attachments`,
        data,
        requestConfig
      )
      .then(() =>
        dispatch(
          addNotification(
            titleDone,
            counterpart.translate('window.attachment.upload.success'),
            5000,
            'primary'
          )
        )
      )
      .finally(() => dispatch(deleteNotification(titlePending)))
      .catch((thrown) => {
        if (axios.isCancel(thrown)) {
          dispatch(
            addNotification(
              titleError,
              'Upload terminated by the user',
              5000,
              'warning',
              'disableMouse'
            )
          );
        } else {
          dispatch(
            addNotification(
              titleError,
              counterpart.translate('window.attachment.upload.error'),
              5000,
              'error'
            )
          );
        }
      });
  };
}

/**
 * @method setPrintingOptions
 * @summary - action. It updates the store with the printing options fetched from /rest/api/window/{windowId}/{documentId}/printingOptions
 * @param {object} data
 */
export function setPrintingOptions(data) {
  return {
    type: SET_PRINTING_OPTIONS,
    payload: data,
  };
}

/**
 * @method resetPrintingOptions
 * @summary - action. It reets the printing options in the store
 */
export function resetPrintingOptions() {
  return {
    type: RESET_PRINTING_OPTIONS,
  };
}

/**
 * @method togglePrintingOption
 * @summary - action. It toggles in the store the printing option truth value
 * @param {object} target
 */
export function togglePrintingOption(target) {
  return {
    type: TOGGLE_PRINTING_OPTION,
    payload: target,
  };
}

export function openPrintingOptionsModal({
  title,
  windowId,
  documentId,
  documentNo,
}) {
  return openModal({
    title,
    windowId,
    modalType: 'static',
    //viewId,
    viewDocumentIds: [documentNo],
    dataId: documentId,
    staticModalType: 'printing',
  });
}

export function openSelectCurrentWorkplaceModal() {
  return openModal({
    title: counterpart.translate('userDropdown.changeWorkplace.caption'),
    windowId: 'selectCurrentWorkplace',
    modalType: 'static',
    staticModalType: STATIC_MODAL_TYPE_ChangeCurrentWorkplace,
  });
}

/**
 * @method setSpinner
 * @summary - action. It sets the `showSpinner` in the store to the boolean value passed in the action
 * @param {boolean} data
 */
export function setSpinner(data) {
  return {
    type: SET_SPINNER,
    payload: data,
  };
}

export function printDocument({
  windowId,
  documentId,
  documentNo,
  options = {},
}) {
  const filename = `${windowId}_${documentNo ?? documentId}.pdf`;

  let isOpenInBrowser = true;
  if (options && options['PRINTER_OPTS_IsAlsoSendToBrowser'] !== undefined) {
    isOpenInBrowser = !!options['PRINTER_OPTS_IsAlsoSendToBrowser'];
  } else if (options && options['IsAlsoSendToBrowser'] !== undefined) {
    isOpenInBrowser = !!options['IsAlsoSendToBrowser'];
  } else {
    isOpenInBrowser = true;
  }

  if (isOpenInBrowser) {
    const url = getPrintUrl({ windowId, documentId, filename, options });
    window.open(url, '_blank');
    return Promise.resolve();
  } else {
    return getPrintFile({ windowId, documentId, filename, options });
  }
}
