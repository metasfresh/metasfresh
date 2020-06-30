import axios from 'axios';
import counterpart from 'counterpart';
import { push, replace } from 'react-router-redux';
import currentDevice from 'current-device';
import { Set } from 'immutable';

import {
  ACTIVATE_TAB,
  ALLOW_SHORTCUT,
  ALLOW_OUTSIDE_CLICK,
  CHANGE_INDICATOR_STATE,
  CLEAR_MASTER_DATA,
  CLOSE_MODAL,
  CLOSE_PROCESS_MODAL,
  CLOSE_RAW_MODAL,
  CLOSE_FILTER_BOX,
  DELETE_QUICK_ACTIONS,
  DELETE_TOP_ACTIONS,
  DISABLE_SHORTCUT,
  DISABLE_OUTSIDE_CLICK,
  INIT_WINDOW,
  INIT_DATA_SUCCESS,
  INIT_LAYOUT_SUCCESS,
  FETCHED_QUICK_ACTIONS,
  FETCH_TOP_ACTIONS,
  FETCH_TOP_ACTIONS_FAILURE,
  FETCH_TOP_ACTIONS_SUCCESS,
  NO_CONNECTION,
  OPEN_FILTER_BOX,
  OPEN_MODAL,
  OPEN_RAW_MODAL,
  PATCH_FAILURE,
  PATCH_REQUEST,
  PATCH_SUCCESS,
  SET_RAW_MODAL_DESCRIPTION,
  SET_RAW_MODAL_TITLE,
  SORT_TAB,
  TOGGLE_OVERLAY,
  UNSELECT_TAB,
  UPDATE_DATA_FIELD_PROPERTY,
  UPDATE_DATA_INCLUDED_TABS_INFO,
  UPDATE_DATA_PROPERTY,
  UPDATE_DATA_SAVE_STATUS,
  UPDATE_DATA_VALID_STATUS,
  UPDATE_MASTER_DATA,
  UPDATE_MODAL,
  UPDATE_RAW_MODAL,
} from '../constants/ActionTypes';
import { PROCESS_NAME } from '../constants/Constants';

import {
  getData,
  patchRequest,
  getLayout,
  topActionsRequest,
  getProcessData,
  getTabRequest,
  startProcess,
  formatParentUrl,
} from '../api';
import { getTableId } from '../reducers/tables';
import {
  addNotification,
  setNotificationProgress,
  setProcessPending,
  setProcessSaved,
  deleteNotification,
} from './AppActions';
import { openFile } from './GenericActions';
import { setListIncludedView } from './ListActions';
import { getWindowBreadcrumb } from './MenuActions';
import {
  updateCommentsPanel,
  updateCommentsPanelTextInput,
  updateCommentsPanelOpenFlag,
} from './CommentsPanelActions';
import {
  createTabTable,
  updateTabTable,
  updateTableSelection,
  updateTableRowProperty,
} from './TableActions';
import { toggleFullScreen, preFormatPostDATA } from '../utils';
import { getScope, parseToDisplay } from '../utils/documentListHelper';

/*
 * Action creator called when quick actions are successfully fetched
 */
export function fetchedQuickActions(windowId, id, data) {
  return {
    type: FETCHED_QUICK_ACTIONS,
    payload: {
      data,
      windowId,
      id,
    },
  };
}

/*
 * Action creator to delete quick actions from the store
 */
export function deleteQuickActions(windowId, id) {
  return {
    type: DELETE_QUICK_ACTIONS,
    payload: {
      windowId,
      id,
    },
  };
}

export function deleteTopActions() {
  return {
    type: DELETE_TOP_ACTIONS,
  };
}

export function toggleOverlay(data) {
  return {
    type: TOGGLE_OVERLAY,
    data: data,
  };
}

export function openRawModal(windowId, viewId, profileId) {
  return {
    type: OPEN_RAW_MODAL,
    windowId: windowId,
    viewId: viewId,
    profileId: profileId,
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

export function initLayoutSuccess(layout, scope) {
  return {
    type: INIT_LAYOUT_SUCCESS,
    layout: layout,
    scope: scope,
  };
}

export function initDataSuccess({
  data,
  docId,
  includedTabsInfo,
  saveStatus,
  scope,
  standardActions,
  validStatus,
  websocket,
}) {
  return {
    data,
    docId,
    includedTabsInfo,
    saveStatus,
    scope,
    standardActions,
    type: INIT_DATA_SUCCESS,
    validStatus,
    websocket,
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

export function sortTab(scope, tabId, field, asc) {
  return {
    type: SORT_TAB,
    scope,
    tabId,
    field,
    asc,
  };
}

export function updateDataProperty(property, value, scope) {
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

export function noConnection(status) {
  return {
    type: NO_CONNECTION,
    status: status,
  };
}

export function openModal(
  title,
  windowId,
  modalType,
  tabId,
  rowId,
  isAdvanced,
  viewId,
  viewDocumentIds,
  dataId,
  triggerField,
  parentViewId,
  parentViewSelectedIds,
  childViewId,
  childViewSelectedIds,
  staticModalType
) {
  const isMobile =
    currentDevice.type === 'mobile' || currentDevice.type === 'tablet';

  if (isMobile) {
    toggleFullScreen();
  }

  return {
    type: OPEN_MODAL,
    windowType: windowId,
    tabId: tabId,
    rowId: rowId,
    viewId: viewId,
    dataId: dataId,
    title: title,
    isAdvanced: isAdvanced,
    viewDocumentIds: viewDocumentIds,
    triggerField: triggerField,
    modalType,
    staticModalType,
    parentViewId,
    parentViewSelectedIds,
    childViewId,
    childViewSelectedIds,
  };
}

export function closeProcessModal() {
  return {
    type: CLOSE_PROCESS_MODAL,
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

// INDICATOR ACTIONS

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
export function fetchTab({ tabId, windowId, docId, query }) {
  return (dispatch) => {
    return getTabRequest(tabId, windowId, docId, query)
      .then((response) => {
        const tableId = getTableId({ windowId, docId, tabId });
        const tableData = { result: response };

        dispatch(updateTabTable(tableId, tableData));

        return Promise.resolve(response);
      })
      .catch((error) => {
        //show error message ?
        return Promise.resolve(error);
      });
  };
}

export function initWindow(windowType, docId, tabId, rowId = null, isAdvanced) {
  return (dispatch) => {
    dispatch({
      type: INIT_WINDOW,
    });

    if (docId === 'NEW') {
      //New master document
      return patchRequest({
        entity: 'window',
        docType: windowType,
        docId,
      });
    } else {
      if (rowId === 'NEW') {
        //New row document
        return patchRequest({
          entity: 'window',
          docType: windowType,
          docId,
          tabId,
          rowId,
        });
      } else if (rowId) {
        //Existing row document
        return getData({
          entity: 'window',
          docType: windowType,
          docId: docId,
          tabId: tabId,
          rowId: rowId,
          fetchAdvancedFields: isAdvanced,
        }).catch((e) => {
          return e;
        });
      } else {
        //Existing master document
        return getData({
          entity: 'window',
          docType: windowType,
          docId: docId,
          fetchAdvancedFields: isAdvanced,
        }).catch((e) => {
          dispatch(getWindowBreadcrumb(windowType));
          dispatch(
            initDataSuccess({
              data: {},
              docId: 'notfound',
              includedTabsInfo: {},
              scope: 'master',
              saveStatus: { saved: true },
              standardActions: Set(),
              validStatus: {},
            })
          );

          return { status: e.status, message: e.statusText };
        });
      }
    }
  };
}

/*
 * Main method to generate window
 */
export function createWindow(
  windowType,
  documentId = 'NEW',
  tabId,
  rowId,
  isModal = false,
  isAdvanced
) {
  return (dispatch) => {
    if (documentId.toLowerCase() === 'new') {
      documentId = 'NEW';
    }

    // this chain is really important,
    // to do not re-render widgets on init
    return dispatch(
      initWindow(windowType, documentId, tabId, rowId, isAdvanced)
    ).then((response) => {
      if (!response || !response.data) {
        return Promise.resolve(null);
      }

      const data = response.data[0];
      const tabs = data.includedTabsInfo;
      let docId = data.id;

      if (tabs) {
        Object.values(tabs).forEach((tab) => {
          const tabId = tab.tabId || tab.tabid;
          const tableId = getTableId({ windowId: windowType, docId, tabId });
          const tableData = {
            windowType,
            docId,
            tabId,
            ...tab,
          };

          dispatch(createTabTable(tableId, tableData));
        });
      }

      if (documentId === 'NEW' && !isModal) {
        // redirect immedietely
        return dispatch(replace(`/window/${windowType}/${docId}`));
      }

      let elem = 0;

      response.data.forEach((value, index) => {
        if (value.rowId === rowId) {
          elem = index;
        }
      });

      if (documentId === 'NEW') {
        dispatch(updateModal(null, docId));
      }

      // TODO: Is `elem` ever different than 0 ?
      docId = response.data[elem].id;
      dispatch(
        initDataSuccess({
          data: parseToDisplay(response.data[elem].fieldsByName),
          docId,
          saveStatus: data.saveStatus,
          scope: getScope(isModal),
          standardActions: data.standardActions,
          validStatus: data.validStatus,
          includedTabsInfo: data.includedTabsInfo,
          websocket: data.websocketEndpoint,
        })
      );

      if (isModal) {
        if (rowId === 'NEW') {
          dispatch(
            mapDataToState(response.data, false, 'NEW', docId, windowType)
          );
          dispatch(updateStatus(response.data));
          dispatch(updateModal(data.rowId));
        }
      } else {
        dispatch(getWindowBreadcrumb(windowType));
      }

      return getLayout('window', windowType, tabId, null, null, isAdvanced)
        .then(({ data }) => {
          const layoutTabs = data.tabs;

          if (layoutTabs) {
            Object.values(layoutTabs).forEach((tab) => {
              const { tabId } = tab;
              const tableId = getTableId({
                windowId: windowType,
                docId,
                tabId,
              });
              const tableData = {
                windowType,
                docId,
                tabId,
                ...tab,
              };
              dispatch(updateTabTable(tableId, tableData));
            });
          }

          dispatch(initLayoutSuccess(data, getScope(isModal)));
        })
        .catch((e) => Promise.reject(e));
    });
  };
}

const getChangelogUrl = function(windowId, docId, tabId, rowId) {
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

export function fetchTopActions(windowType, docId, tabId) {
  return (dispatch) => {
    dispatch({
      type: FETCH_TOP_ACTIONS,
    });

    return topActionsRequest(windowType, docId, tabId)
      .then((response) => {
        dispatch({
          type: FETCH_TOP_ACTIONS_SUCCESS,
          payload: response.data.actions,
        });

        return Promise.resolve(response.data.actions);
      })
      .catch((e) => {
        dispatch({
          type: FETCH_TOP_ACTIONS_FAILURE,
        });

        return Promise.reject(e);
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

/*
 *  Wrapper for patch request of widget elements
 *  when responses should merge store
 */
export function patch(
  entity,
  windowType,
  id = 'NEW',
  tabId,
  rowId,
  property,
  value,
  isModal,
  isAdvanced,
  viewId,
  isEdit
) {
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

    await dispatch(indicatorState('pending'));
    await dispatch({ type: PATCH_REQUEST, symbol, options });

    try {
      const response = await patchRequest(options);
      const data =
        response.data instanceof Array ? response.data : [response.data];
      const dataItem = data[0];

      await dispatch(
        mapDataToState(data, isModal, rowId, id, windowType, isAdvanced)
      );

      if (
        dataItem &&
        dataItem.validStatus &&
        !dataItem.validStatus.valid &&
        (property === dataItem.validStatus.fieldName ||
          dataItem.validStatus.fieldName === undefined)
      ) {
        await dispatch(indicatorState('error'));
        await dispatch({ type: PATCH_FAILURE, symbol });
        const errorMessage = dataItem.validStatus.reason;

        dispatch(
          addNotification(
            'Error: ' + errorMessage.split(' ', 4).join(' ') + '...',
            errorMessage,
            5000,
            'error',
            ''
          )
        );
      } else {
        await dispatch(indicatorState('saved'));
        await dispatch({ type: PATCH_SUCCESS, symbol });

        return data;
      }
    } catch (error) {
      await dispatch(indicatorState('error'));
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
        mapDataToState(
          response.data,
          isModal,
          rowId,
          id,
          windowType,
          isAdvanced
        )
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
  doNotFetchIncludedTabs,
}) {
  return (dispatch) => {
    getData({
      entity: 'window',
      docType: windowId,
      docId: documentId,
      tabId: tabId,
      rowId: rowId,
      fetchAdvancedFields: fetchAdvancedFields,
      doNotFetchIncludedTabs: doNotFetchIncludedTabs,
    }).then((response) => {
      dispatch(
        mapDataToState(
          response.data,
          isModal,
          rowId,
          documentId,
          windowId,
          fetchAdvancedFields
        )
      );
    });
  };
}

// TODO: Check if all cases are valid. Especially if `scope` is `master`, or
// `key` is `includedTabsInfo`.
function updateData(doc, scope) {
  return (dispatch) => {
    Object.keys(doc).map((key) => {
      if (key === 'fieldsByName') {
        Object.keys(doc.fieldsByName).map((fieldName) => {
          dispatch(
            updateDataFieldProperty(
              fieldName,
              doc.fieldsByName[fieldName],
              scope
            )
          );
        });
      } else if (key === 'includedTabsInfo') {
        dispatch(updateDataIncludedTabsInfo('master', doc[key]));
      } else {
        dispatch(updateDataProperty(key, doc[key], scope));
      }
    });
  };
}

function mapDataToState(data, isModal, rowId) {
  return (dispatch) => {
    const dataArray = typeof data.splice === 'function' ? data : [data];

    dataArray.map((item, index) => {
      const parsedItem = item.fieldsByName
        ? {
            ...item,
            fieldsByName: parseToDisplay(item.fieldsByName),
          }
        : item;

      if (!(index === 0 && rowId === 'NEW') && !item.rowId) {
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
 * @todo TODO: from my observations, this is triggered multiple times even
 * when just one field was changed - Kuba
 */
export function updatePropertyValue({
  property,
  value,
  tabId,
  rowId,
  isModal,
  entity,
  tableId,
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

// PROCESS ACTIONS

export function createProcess({
  ids,
  processType,
  rowId,
  tabId,
  type,
  viewId,
  selectedTab,
  childViewId,
  childViewSelectedIds,
  parentViewId,
  parentViewSelectedIds,
}) {
  let pid = null;

  return async (dispatch) => {
    await dispatch(setProcessPending());

    let response;

    try {
      response = await getProcessData({
        ids,
        processId: processType,
        rowId,
        tabId,
        type,
        viewId,
        selectedTab,
        childViewId,
        childViewSelectedIds,
        parentViewId,
        parentViewSelectedIds,
      });
    } catch (error) {
      // Close process modal in case when process start failed
      await dispatch(closeModal());
      await dispatch(setProcessSaved());

      throw error;
    }

    if (response.data) {
      const preparedData = parseToDisplay(response.data.fieldsByName);

      pid = response.data.pinstanceId;

      if (response.data.startProcessDirectly) {
        let response;

        try {
          response = await startProcess(processType, pid);

          await dispatch(handleProcessResponse(response, processType, pid));
        } catch (error) {
          await dispatch(closeModal());
          await dispatch(setProcessSaved());

          throw error;
        }
      } else {
        await dispatch(
          initDataSuccess({
            data: preparedData,
            scope: 'modal',
          })
        );

        let response;

        try {
          response = await getLayout(PROCESS_NAME, processType);

          await dispatch(setProcessSaved());

          const preparedLayout = {
            ...response.data,
            pinstanceId: pid,
          };

          await dispatch(initLayoutSuccess(preparedLayout, 'modal'));
        } catch (error) {
          await dispatch(setProcessSaved());

          throw error;
        }
      }
    }
  };
}

export function handleProcessResponse(response, type, id) {
  return async (dispatch) => {
    const { error, summary, action } = response.data;

    if (error) {
      await dispatch(addNotification('Process error', summary, 5000, 'error'));
      await dispatch(setProcessSaved());

      // Close process modal in case when process has failed
      await dispatch(closeModal());
    } else {
      let keepProcessModal = false;

      if (action) {
        switch (action.type) {
          case 'displayQRCode':
            dispatch(toggleOverlay({ type: 'qr', data: action.code }));
            break;
          case 'openView': {
            const { windowId, viewId } = action;

            await dispatch(closeModal());

            if (!action.modalOverlay) {
              window.open(`/window/${windowId}/?viewId=${viewId}`);

              return;
            }

            await dispatch(openRawModal(windowId, viewId, action.profileId));

            break;
          }
          case 'openReport':
            openFile(PROCESS_NAME, type, id, 'print', action.filename);

            break;
          case 'openDocument':
            await dispatch(closeModal());

            if (action.modal) {
              // Do not close process modal,
              // since it will be re-used with document view
              keepProcessModal = true;

              await dispatch(
                openModal(
                  '',
                  action.windowId,
                  'window',
                  null,
                  null,
                  action.advanced ? action.advanced : false,
                  '',
                  '',
                  action.documentId
                )
              );
            } else {
              await dispatch(
                push(`/window/${action.windowId}/${action.documentId}`)
              );
            }
            break;
          case 'openIncludedView':
            await dispatch(
              setListIncludedView({
                windowType: action.windowId,
                viewId: action.viewId,
                viewProfileId: action.profileId,
              })
            );

            break;
          case 'closeIncludedView':
            await dispatch(
              setListIncludedView({
                windowType: action.windowId,
                viewId: action.viewId,
              })
            );

            break;
          case 'selectViewRows': {
            // eslint-disable-next-line no-console
            console.info(
              '@TODO: `selectViewRows` - check if selection worked ok'
            );
            const { windowId, viewId, rowIds } = action;
            const tableId = getTableId({ windowId, viewId });

            dispatch(updateTableSelection(tableId, rowIds));

            break;
          }
        }
      }

      if (summary) {
        await dispatch(addNotification('Process', summary, 5000, 'primary'));
      }

      await dispatch(setProcessSaved());

      if (!keepProcessModal) {
        await dispatch(closeProcessModal());
      }
    }
  };
}
