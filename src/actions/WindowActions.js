import axios from 'axios';
import counterpart from 'counterpart';
import { push, replace } from 'react-router-redux';
import SockJs from 'sockjs-client';
import currentDevice from 'current-device';
import Stomp from 'stompjs/lib/stomp.min.js';
import Moment from 'moment';
import { Set } from 'immutable';

import {
  ACTIVATE_TAB,
  ADD_NEW_ROW,
  ADD_ROW_DATA,
  ALLOW_SHORTCUT,
  CHANGE_INDICATOR_STATE,
  CLEAR_MASTER_DATA,
  CLOSE_MODAL,
  CLOSE_PROCESS_MODAL,
  CLOSE_RAW_MODAL,
  DELETE_ROW,
  DISABLE_SHORTCUT,
  INIT_DATA_SUCCESS,
  INIT_LAYOUT_SUCCESS,
  NO_CONNECTION,
  OPEN_MODAL,
  OPEN_RAW_MODAL,
  PATCH_FAILURE,
  PATCH_REQUEST,
  PATCH_SUCCESS,
  REMOVE_TABLE_ITEMS_SELECTION,
  SELECT_TABLE_ITEMS,
  SET_LATEST_NEW_DOCUMENT,
  SORT_TAB,
  UNSELECT_TAB,
  UPDATE_DATA_FIELD_PROPERTY,
  UPDATE_DATA_INCLUDED_TABS_INFO,
  UPDATE_DATA_PROPERTY,
  UPDATE_DATA_SAVE_STATUS,
  UPDATE_DATA_VALID_STATUS,
  UPDATE_MODAL,
  UPDATE_ROW_FIELD_PROPERTY,
  UPDATE_ROW_PROPERTY,
  UPDATE_ROW_STATUS,
  OPEN_FILTER_BOX,
  CLOSE_FILTER_BOX,
  ALLOW_OUTSIDE_CLICK,
  DISABLE_OUTSIDE_CLICK,
} from '../constants/ActionTypes';

import {
  addNotification,
  setNotificationProgress,
  setProcessPending,
  setProcessSaved,
  deleteNotification,
} from './AppActions';
import { getData, openFile, patchRequest } from './GenericActions';
import { initLayout } from '../api';
import { setListIncludedView } from './ListActions';
import { getWindowBreadcrumb } from './MenuActions';

export function setLatestNewDocument(id) {
  return {
    type: SET_LATEST_NEW_DOCUMENT,
    id: id,
  };
}

export function openRawModal(windowType, viewId) {
  return {
    type: OPEN_RAW_MODAL,
    windowType: windowType,
    viewId: viewId,
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

export function clearMasterData() {
  return {
    type: CLEAR_MASTER_DATA,
  };
}

export function addRowData(data, scope) {
  return {
    type: ADD_ROW_DATA,
    data,
    scope,
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

export function updateRowStatus(scope, tabid, rowid, saveStatus) {
  return {
    type: UPDATE_ROW_STATUS,
    scope,
    tabid,
    rowid,
    saveStatus,
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

export function updateRowProperty(property, item, tabid, rowid, scope) {
  return {
    type: UPDATE_ROW_PROPERTY,
    property,
    item,
    tabid,
    rowid,
    scope,
  };
}

export function updateDataIncludedTabsInfo(scope, includedTabsInfo) {
  return {
    type: UPDATE_DATA_INCLUDED_TABS_INFO,
    scope,
    includedTabsInfo,
  };
}

export function addNewRow(item, tabid, rowid, scope) {
  return {
    type: ADD_NEW_ROW,
    item: item,
    tabid: tabid,
    rowid: rowid,
    scope: scope,
  };
}

export function deleteRow(tabid, rowid, scope) {
  return {
    type: DELETE_ROW,
    tabid: tabid,
    rowid: rowid,
    scope: scope,
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

export function updateRowFieldProperty(property, item, tabid, rowid, scope) {
  return {
    type: UPDATE_ROW_FIELD_PROPERTY,
    property: property,
    item: item,
    tabid: tabid,
    rowid: rowid,
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
  windowType,
  type,
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
  childViewSelectedIds
) {
  const isMobile =
    currentDevice.type === 'mobile' || currentDevice.type === 'tablet';

  if (isMobile) {
    toggleFullScreen();
  }

  return {
    type: OPEN_MODAL,
    windowType: windowType,
    modalType: type,
    tabId: tabId,
    rowId: rowId,
    viewId: viewId,
    dataId: dataId,
    title: title,
    isAdvanced: isAdvanced,
    viewDocumentIds: viewDocumentIds,
    triggerField: triggerField,
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

//SELECT ON TABLE

export function removeSelectedTableItems({ windowType, viewId }) {
  return {
    type: REMOVE_TABLE_ITEMS_SELECTION,
    payload: { windowType, viewId },
  };
}

export function selectTableItems({ ids, windowType, viewId }) {
  return {
    type: SELECT_TABLE_ITEMS,
    payload: { ids, windowType, viewId },
  };
}

// THUNK ACTIONS

/*
 * Main method to generate window
 */
export function createWindow(
  windowType,
  docId = 'NEW',
  tabId,
  rowId,
  isModal = false,
  isAdvanced
) {
  return dispatch => {
    if (docId == 'new') {
      docId = 'NEW';
    }

    // this chain is really important,
    // to do not re-render widgets on init
    return dispatch(
      initWindow(windowType, docId, tabId, rowId, isAdvanced)
    ).then(response => {
      if (!response) {
        return;
      }
      if (docId == 'NEW' && !isModal) {
        dispatch(setLatestNewDocument(response.data[0].id));
        // redirect immedietely
        return dispatch(
          replace(`/window/${windowType}/${response.data[0].id}`)
        );
      }

      let elem = 0;

      response.data.forEach((value, index) => {
        if (value.rowId === rowId) {
          elem = index;
        }
      });

      if (docId === 'NEW') {
        dispatch(updateModal(null, response.data[0].id));
      }

      docId = response.data[elem].id;
      dispatch(
        initDataSuccess({
          data: parseToDisplay(response.data[elem].fieldsByName),
          docId,
          saveStatus: response.data[0].saveStatus,
          scope: getScope(isModal),
          standardActions: response.data[0].standardActions,
          validStatus: response.data[0].validStatus,
          includedTabsInfo: response.data[0].includedTabsInfo,
          websocket: response.data[0].websocketEndpoint,
        })
      );

      if (isModal) {
        if (rowId === 'NEW') {
          dispatch(
            mapDataToState(response.data, false, 'NEW', docId, windowType)
          );
          dispatch(updateStatus(response.data));
          dispatch(updateModal(response.data[0].rowId));
        }
      } else {
        dispatch(getWindowBreadcrumb(windowType));
      }

      initLayout('window', windowType, tabId, null, null, isAdvanced)
        .then(response =>
          dispatch(initLayoutSuccess(response.data, getScope(isModal)))
        )
        .then(response => {
          if (!isModal) {
            dispatch(
              initTabs(response.layout.tabs, windowType, docId, isModal)
            );
          }
        });
    });
  };
}

function initTabs(layout, windowType, docId, isModal) {
  return dispatch => {
    let tabTmp = {};

    layout &&
      layout.map((tab, index) => {
        tabTmp[tab.tabid] = {};

        if (index === 0 || !tab.queryOnActivate) {
          getTab(tab.tabid, windowType, docId).then(res => {
            tabTmp[tab.tabid] = res;
            dispatch(addRowData(tabTmp, getScope(isModal)));
          });
        }
      });
  };
}

export function initWindow(windowType, docId, tabId, rowId = null, isAdvanced) {
  return dispatch => {
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
        return getData(
          'window',
          windowType,
          docId,
          tabId,
          rowId,
          null,
          null,
          isAdvanced
        );
      } else {
        //Existing master document
        return getData(
          'window',
          windowType,
          docId,
          null,
          null,
          null,
          null,
          isAdvanced
        ).catch(() => {
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
          dispatch(getWindowBreadcrumb(windowType));
        });
      }
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
  return async dispatch => {
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
        property === dataItem.validStatus.fieldName
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

      const response = await getData(
        entity,
        windowType,
        id,
        tabId,
        rowId,
        null,
        null,
        isAdvanced,
        null,
        viewId
      );

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

export function fireUpdateData(
  entity,
  windowType,
  id,
  tabId,
  rowId,
  isModal,
  isAdvanced
) {
  return dispatch => {
    getData(entity, windowType, id, tabId, rowId, null, null, isAdvanced).then(
      response => {
        dispatch(
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
    );
  };
}

function updateData(doc, scope) {
  return dispatch => {
    Object.keys(doc).map(key => {
      if (key === 'fieldsByName') {
        Object.keys(doc.fieldsByName).map(fieldName => {
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

function updateRow(row, scope) {
  return dispatch => {
    Object.keys(row).map(key => {
      if (key === 'fieldsByName') {
        Object.keys(row.fieldsByName).map(fieldName => {
          dispatch(
            updateRowFieldProperty(
              fieldName,
              row.fieldsByName[fieldName],
              row.tabid,
              row.rowId,
              scope
            )
          );
        });
      } else {
        dispatch(updateRowProperty(key, row[key], row.tabid, row.rowId, scope));
      }
    });
  };
}

function mapDataToState(data, isModal, rowId, id, windowType, isAdvanced) {
  return dispatch => {
    const dataArray = typeof data.splice === 'function' ? data : [data];

    dataArray.map((item, index) => {
      const parsedItem = item.fieldsByName
        ? {
            ...item,
            fieldsByName: parseToDisplay(item.fieldsByName),
          }
        : item;

      // First item in response is direct one for action that called it.
      if (index === 0 && rowId === 'NEW') {
        dispatch(
          addNewRow(parsedItem, parsedItem.tabid, parsedItem.rowId, 'master')
        );
      } else {
        if (item.rowId && !isModal) {
          // Update directly to a row by the widget in cell
          dispatch(updateRow(parsedItem, 'master'));
        } else {
          // Update by a modal
          item.rowId && dispatch(updateRow(parsedItem, 'master'));

          // Advanced edit
          isAdvanced && dispatch(updateData(parsedItem, 'master'));

          dispatch(updateData(parsedItem, getScope(isModal && index === 0)));
        }
      }
    });
  };
}

function updateStatus(responseData) {
  return dispatch => {
    const updateDispatch = item => {
      if (item.rowId) {
        dispatch(
          updateRowStatus('master', item.tabid, item.rowId, item.saveStatus)
        );
      } else {
        item.validStatus &&
          dispatch(updateDataValidStatus('master', item.validStatus));
        item.saveStatus &&
          dispatch(updateDataSaveStatus('master', item.saveStatus));
        item.includedTabsInfo &&
          dispatch(updateDataIncludedTabsInfo('master', item.includedTabsInfo));
      }
    };

    if (Array.isArray(responseData)) {
      responseData.map(item => {
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
export function updatePropertyValue(
  property,
  value,
  tabid,
  rowid,
  isModal,
  entity
) {
  return dispatch => {
    if (tabid && rowid) {
      dispatch(
        updateRowFieldProperty(property, { value }, tabid, rowid, 'master')
      );
      if (isModal && entity !== 'process') {
        dispatch(updateDataFieldProperty(property, { value }, 'modal'));
      }
    } else {
      dispatch(updateDataFieldProperty(property, { value }, getScope(isModal)));
      if (isModal && entity !== 'process') {
        //update the master field too if exist
        dispatch(updateDataFieldProperty(property, { value }, 'master'));
      }
    }
  };
}

function handleUploadProgress(dispatch, notificationTitle, progressEvent) {
  let percentLeft = Math.min(
    Math.floor(progressEvent.loaded * 100 / progressEvent.total),
    98
  );

  dispatch(setNotificationProgress(notificationTitle, percentLeft));
}

export function attachFileAction(windowType, docId, data) {
  return dispatch => {
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
      .catch(thrown => {
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

  return async dispatch => {
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

      if (Object.keys(preparedData).length === 0) {
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
          response = await initLayout('process', processType);

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
  return async dispatch => {
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
          case 'openView':
            await dispatch(closeModal());

            await dispatch(openRawModal(action.windowId, action.viewId));

            break;
          case 'openReport':
            openFile('process', type, id, 'print', action.filename);

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
                push('/window/' + action.windowId + '/' + action.documentId)
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
          case 'selectViewRows':
            await dispatch(selectTableItems(action.rowIds, action.windowId));

            break;
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

export function deleteLocal(tabid, rowsid, scope, response) {
  return dispatch => {
    for (let rowid of rowsid) {
      dispatch(deleteRow(tabid, rowid, scope));
    }
    dispatch(updateStatus(response.data));
  };
}
// END PROCESS ACTIONS

// API CALLS

//ZOOM INTO
export function getZoomIntoWindow(
  entity,
  windowId,
  docId,
  tabId,
  rowId,
  field
) {
  return axios.get(
    config.API_URL +
      '/' +
      entity +
      '/' +
      windowId +
      (docId ? '/' + docId : '') +
      (tabId ? '/' + tabId : '') +
      (rowId ? '/' + rowId : '') +
      '/field' +
      '/' +
      field +
      '/zoomInto?showError=true'
  );
}

export function discardNewRow({ windowType, documentId, tabId, rowId } = {}) {
  return axios.post(
    config.API_URL +
      '/window/' +
      windowType +
      '/' +
      documentId +
      '/' +
      tabId +
      '/' +
      rowId +
      '/discardChanges'
  );
}

export function discardNewDocument({ windowType, documentId } = {}) {
  return axios.post(
    config.API_URL +
      '/window/' +
      windowType +
      '/' +
      documentId +
      '/discardChanges'
  );
}

export function getTab(tabId, windowType, docId, orderBy) {
  return getData(
    'window',
    windowType,
    docId,
    tabId,
    null,
    null,
    null,
    null,
    orderBy
  ).then(
    res =>
      res.data &&
      res.data.map(row => ({
        ...row,
        fieldsByName: parseToDisplay(row.fieldsByName),
      }))
  );
}

function getProcessData({
  processId,
  viewId,
  type,
  ids,
  tabId,
  rowId,
  selectedTab,
  childViewId,
  childViewSelectedIds,
  parentViewId,
  parentViewSelectedIds,
}) {
  const payload = {
    processId: processId,
  };

  if (viewId) {
    payload.viewId = viewId;
    payload.viewDocumentIds = ids;

    if (childViewId) {
      payload.childViewId = childViewId;
      payload.childViewSelectedIds = childViewSelectedIds;
    }

    if (parentViewId) {
      payload.parentViewId = parentViewId;
      payload.parentViewSelectedIds =
        parentViewSelectedIds instanceof Array
          ? parentViewSelectedIds
          : [parentViewSelectedIds];
    }
  } else {
    payload.documentId = Array.isArray(ids) ? ids[0] : ids;
    payload.documentType = type;
    payload.tabId = tabId;
    payload.rowId = rowId;
  }

  if (selectedTab) {
    const { tabId, rowIds } = selectedTab;

    if (tabId && rowIds) {
      payload.selectedTab = {
        tabId,
        rowIds,
      };
    }
  }

  return axios.post(config.API_URL + '/process/' + processId, payload);
}

export function startProcess(processType, pinstanceId) {
  return axios.get(
    config.API_URL + '/process/' + processType + '/' + pinstanceId + '/start'
  );
}

// UTILITIES
function toggleFullScreen() {
  const doc = window.document;
  const docEl = doc.documentElement;

  const requestFullScreen =
    docEl.requestFullscreen ||
    docEl.mozRequestFullScreen ||
    docEl.webkitRequestFullScreen ||
    docEl.msRequestFullscreen;
  const cancelFullScreen =
    doc.exitFullscreen ||
    doc.mozCancelFullScreen ||
    doc.webkitExitFullscreen ||
    doc.msExitFullscreen;

  if (
    !doc.fullscreenElement &&
    !doc.mozFullScreenElement &&
    !doc.webkitFullscreenElement &&
    !doc.msFullscreenElement
  ) {
    requestFullScreen.call(docEl);
  } else {
    cancelFullScreen.call(doc);
  }
}

function getScope(isModal) {
  return isModal ? 'modal' : 'master';
}

export function parseToDisplay(fieldsByName) {
  return parseDateToReadable(nullToEmptyStrings(fieldsByName));
}

// i.e 2018-01-27T17:00:00.000-06:00
export function parseDateWithCurrenTimezone(value) {
  if (value) {
    if (value instanceof Date) {
      return value;
    } else if (Moment.isMoment(value)) {
      return new Date(value);
    } else {
      const TIMEZONE_STRING_LENGTH = 7;
      const newValue = value.substring(
        0,
        value.length - TIMEZONE_STRING_LENGTH
      );
      return new Date(newValue);
    }
  }
  return '';
}

function parseDateToReadable(fieldsByName) {
  const dateParse = ['Date', 'DateTime', 'Time'];

  return Object.keys(fieldsByName).reduce((acc, fieldName) => {
    const field = fieldsByName[fieldName];
    const isDateField = dateParse.indexOf(field.widgetType) > -1;
    acc[fieldName] =
      isDateField && field.value
        ? {
            ...field,
            value: parseDateWithCurrenTimezone(field.value),
          }
        : field;
    return acc;
  }, {});
}

function nullToEmptyStrings(fieldsByName) {
  return Object.keys(fieldsByName).reduce((acc, fieldName) => {
    acc[fieldName] =
      fieldsByName[fieldName].value === null
        ? { ...fieldsByName[fieldName], value: '' }
        : fieldsByName[fieldName];
    return acc;
  }, {});
}

export function findRowByPropName(arr, name) {
  let ret = -1;

  if (!arr) {
    return ret;
  }

  for (let i = 0; i < arr.length; i++) {
    if (arr[i].field === name) {
      ret = arr[i];
      break;
    }
  }

  return ret;
}

export function getItemsByProperty(arr, prop, value) {
  let ret = [];

  arr &&
    arr.map(item => {
      if (item[prop] === value) {
        ret.push(item);
      }
    });

  return ret;
}
/**
 * flatten array with 1 level deep max(with fieldByName)
 * from includedDocuments data
 */
export function getRowsData(rowData) {
  let data = [];
  rowData &&
    rowData.map(item => {
      data = data.concat(mapIncluded(item));
    });

  return data;
}

export function mapIncluded(node, indent, isParentLastChild = false) {
  let ind = indent ? indent : [];
  let result = [];

  const nodeCopy = {
    ...node,
    indent: ind,
  };

  result = result.concat([nodeCopy]);

  if (isParentLastChild) {
    ind[ind.length - 2] = false;
  }

  if (node.includedDocuments) {
    for (let i = 0; i < node.includedDocuments.length; i++) {
      let copy = node.includedDocuments[i];
      copy.fieldsByName = parseToDisplay(copy.fieldsByName);
      if (i === node.includedDocuments.length - 1) {
        copy = {
          ...copy,
          lastChild: true,
        };
      }

      result = result.concat(
        mapIncluded(copy, ind.concat([true]), node.lastChild)
      );
    }
  }
  return result;
}

export function collapsedMap(node, isCollapsed, initialMap) {
  let collapsedMap = [];
  if (initialMap) {
    if (!isCollapsed) {
      initialMap.splice(
        initialMap.indexOf(node.includedDocuments[0]),
        node.includedDocuments.length
      );
      collapsedMap = initialMap;
    } else {
      initialMap.map(item => {
        collapsedMap.push(item);
        if (item.id === node.id) {
          collapsedMap = collapsedMap.concat(node.includedDocuments);
        }
      });
    }
  } else {
    if (node.includedDocuments) {
      collapsedMap.push(node);
    }
  }

  return collapsedMap;
}

export function connectWS(topic, onMessageCallback) {
  // Avoid disconnecting and reconnecting to same topic.
  // IMPORTANT: we assume the "onMessageCallback" is same
  if (this.sockTopic === topic) {
    // console.log("WS: Skip subscribing because already subscrinbed to %s", this.sockTopic);
    return;
  }

  const subscribe = ({ tries = 3 } = {}) => {
    if (this.sockClient.connected || tries <= 0) {
      this.sockSubscription = this.sockClient.subscribe(topic, msg => {
        // console.log("WS: Got event on %s: %s", topic, msg.body);
        if (topic === this.sockTopic) {
          onMessageCallback(JSON.parse(msg.body));
        } else {
          // console.warn(
          //   "Discard event because the WS topic changed. Current WS topic is %s",
          //   this.sockTopic
          // );
        }
      });

      this.sockTopic = topic;
      // console.log("WS: Subscribed to %s (tries=%s)", this.sockTopic, tries);
    } else {
      // not ready yet
      setTimeout(() => {
        subscribe({ tries: tries - 1 });
      }, 200);
    }
  };

  const connect = () => {
    this.sock = new SockJs(config.WS_URL);
    this.sockClient = Stomp.Stomp.over(this.sock);
    this.sockClient.debug = null;
    this.sockClient.connect({}, subscribe);
  };

  const wasConnected = disconnectWS.call(this, connect);
  if (!wasConnected) {
    connect();
  }
}

export function disconnectWS(onDisconnectCallback) {
  const connected =
    this.sockClient && this.sockClient.connected && this.sockSubscription;

  if (connected) {
    // console.log("WS: Unsubscribing from %s", this.sockTopic);
    this.sockSubscription.unsubscribe();
    this.sockClient.disconnect(onDisconnectCallback);
    this.sockTopic = null;
  }

  return connected;
}
