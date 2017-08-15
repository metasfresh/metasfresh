import * as types from '../constants/ActionTypes'
import axios from 'axios';
import { push, replace } from 'react-router-redux';
import counterpart from 'counterpart';

import SockJs from 'sockjs-client';
import Stomp from 'stompjs/lib/stomp.min.js';

import {
    getWindowBreadcrumb
} from './MenuActions';

import {
    initLayout,
    getData,
    patchRequest,
    openFile
} from './GenericActions';

import {
    addNotification,
    setNotificationProgress,
    setProcessSaved,
    setProcessPending
} from './AppActions';

import {
    setListIncludedView
} from './ListActions';

export function setLatestNewDocument(id) {
    return {
        type: types.SET_LATEST_NEW_DOCUMENT,
        id: id
    }
}

export function openRawModal(windowType, viewId) {
    return {
        type: types.OPEN_RAW_MODAL,
        windowType: windowType,
        viewId: viewId
    }
}

export function closeRawModal() {
    return {
        type: types.CLOSE_RAW_MODAL
    }
}

export function activateTab(scope, tabId) {
    return {
        type: types.ACTIVATE_TAB,
        scope,
        tabId
    }
}

export function initLayoutSuccess(layout, scope) {
    return {
        type: types.INIT_LAYOUT_SUCCESS,
        layout: layout,
        scope: scope
    }
}

export function initDataSuccess(
    data, scope, docId, saveStatus, validStatus, includedTabsInfo, websocket
) {
    return {
        type: types.INIT_DATA_SUCCESS,
        data,
        scope,
        docId,
        saveStatus,
        validStatus,
        includedTabsInfo,
        websocket
    }
}

export function clearMasterData() {
    return {
        type: types.CLEAR_MASTER_DATA
    }
}

export function addRowData(data, scope) {
    return {
        type: types.ADD_ROW_DATA,
        data,
        scope
    }
}

export function sortTab(scope, tabId, field, asc) {
    return {
        type: types.SORT_TAB,
        scope, tabId, field, asc
    }
}

export function updateRowStatus(scope, tabid, rowid, saveStatus) {
    return {
        type: types.UPDATE_ROW_STATUS,
        scope,
        tabid,
        rowid,
        saveStatus
    }
}

export function updateDataProperty(property, value, scope) {
    return {
        type: types.UPDATE_DATA_PROPERTY,
        property,
        value,
        scope
    }
}

export function updateDataSaveStatus(scope, saveStatus) {
    return {
        type: types.UPDATE_DATA_SAVE_STATUS,
        scope,
        saveStatus
    }
}

export function updateDataValidStatus(scope, validStatus) {
    return {
        type: types.UPDATE_DATA_VALID_STATUS,
        scope,
        validStatus
    }
}

export function updateRowProperty(property, item, tabid, rowid, scope) {
    return {
        type: types.UPDATE_ROW_PROPERTY,
        property,
        item,
        tabid,
        rowid,
        scope
    }
}

export function updateDataIncludedTabsInfo(scope, includedTabsInfo) {
    return {
        type: types.UPDATE_DATA_INCLUDED_TABS_INFO,
        scope,
        includedTabsInfo
    }
}

export function addNewRow(item, tabid, rowid, scope) {
    return {
        type: types.ADD_NEW_ROW,
        item: item,
        tabid: tabid,
        rowid: rowid,
        scope: scope
    }
}

export function deleteRow(tabid, rowid, scope) {
    return {
        type: types.DELETE_ROW,
        tabid: tabid,
        rowid: rowid,
        scope: scope
    }
}

export function selectRow(selected) {
    return {
        type: types.SELECT_ROW,
        selected
    }
}

export function updateDataFieldProperty(property, item, scope) {
    return {
        type: types.UPDATE_DATA_FIELD_PROPERTY,
        property: property,
        item: item,
        scope: scope
    }
}

export function updateRowFieldProperty(property, item, tabid, rowid, scope) {
    return {
        type: types.UPDATE_ROW_FIELD_PROPERTY,
        property: property,
        item: item,
        tabid: tabid,
        rowid: rowid,
        scope: scope
    }
}

export function noConnection(status) {
    return {
        type: types.NO_CONNECTION,
        status: status
    }
}

export function openModal(
    title, windowType, type, tabId, rowId, isAdvanced, viewId, viewDocumentIds,
    dataId, triggerField
) {
    return {
        type: types.OPEN_MODAL,
        windowType: windowType,
        modalType: type,
        tabId: tabId,
        rowId: rowId,
        viewId: viewId,
        dataId: dataId,
        title: title,
        isAdvanced: isAdvanced,
        viewDocumentIds: viewDocumentIds,
        triggerField: triggerField
    }
}

export function closeModal() {
    return {
        type: types.CLOSE_MODAL
    }
}

export function updateModal(rowId, dataId) {
    return {
        type: types.UPDATE_MODAL,
        rowId,
        dataId
    }
}

// INDICATOR ACTIONS

export function indicatorState(state) {
    return {
        type: types.CHANGE_INDICATOR_STATE,
        state: state
    }
}

//SELECT ON TABLE

export function selectTableItems(ids, windowType) {
    return {
        type: types.SELECT_TABLE_ITEMS,
        ids,
        windowType
    }
}

// THUNK ACTIONS

/*
 * Main method to generate window
 */
export function createWindow(
    windowType, docId = 'NEW', tabId, rowId, isModal = false, isAdvanced
) {
    return (dispatch) => {
        if (docId == 'new') {
            docId = 'NEW';
        }

        // this chain is really important,
        // to do not re-render widgets on init
        return dispatch(initWindow(windowType, docId, tabId, rowId, isAdvanced))
            .then(response => {
                if(!response){
                    return;
                }
                if (docId == 'NEW' && !isModal) {
                    dispatch(setLatestNewDocument(response.data[0].id));
                    // redirect immedietely
                    return dispatch(replace(
                        '/window/' + windowType + '/' + response.data[0].id)
                    );
                }

                let elem = 0;

                response.data.forEach((value, index) => {
                    if (value.rowId === rowId) {
                        elem = index;
                    }
                });

                if(docId === 'NEW'){
                    dispatch(updateModal(null, response.data[0].id));
                }

                docId = response.data[elem].id;
                dispatch(initDataSuccess(
                    parseToDisplay(response.data[elem].fieldsByName),
                    getScope(isModal), docId,
                    response.data[0].saveStatus, response.data[0].validStatus,
                    response.data[0].includedTabsInfo,
                    response.data[0].websocketEndpoint
                ));

                if (isModal) {
                    if(rowId === 'NEW'){
                        dispatch(mapDataToState(
                            response.data, false, 'NEW', docId, windowType
                        ));
                        dispatch(updateStatus(response.data))
                        dispatch(updateModal(response.data[0].rowId));
                    }
                }else{
                    dispatch(getWindowBreadcrumb(windowType));
                }

                initLayout(
                    'window', windowType, tabId, null, null, isAdvanced
                ).then(response =>
                    dispatch(initLayoutSuccess(
                        response.data, getScope(isModal)
                    ))
                ).then(response => {
                    if(!isModal){
                        dispatch(initTabs(
                            response.layout.tabs, windowType, docId, isModal
                        ))
                    }
                })
        });
    }
}

function initTabs(layout, windowType, docId, isModal) {
    return dispatch => {
        let tabTmp = {};

        layout && layout.map((tab, index) => {
            tabTmp[tab.tabid] = {};

            if(index === 0 || !tab.queryOnActivate){
                getTab(tab.tabid, windowType, docId)
                    .then(res => {
                        tabTmp[tab.tabid] = res;
                        dispatch(
                            addRowData(tabTmp, getScope(isModal))
                        );
                    })
            }
        })
    }
}

export function getTab(tabId, windowType, docId, orderBy) {
    return getData(
        'window', windowType, docId, tabId, null, null, null, null, orderBy
    ).then(res => res.data && res.data.map(row =>
        Object.assign({}, row, {
            fieldsByName: parseToDisplay(row.fieldsByName)
        }
    )));
}

export function initWindow(windowType, docId, tabId, rowId = null, isAdvanced) {
    return (dispatch) => {
        if (docId === 'NEW') {
            //New master document
            return patchRequest('window', windowType, docId)
        } else {
            if (rowId === 'NEW') {
                //New row document
                return patchRequest(
                    'window', windowType, docId, tabId, 'NEW'
                )
            } else if (rowId) {
                //Existing row document
                return getData(
                    'window', windowType, docId, tabId, rowId, null, null,
                    isAdvanced
                )
            } else {
                //Existing master document
                return getData(
                    'window', windowType, docId, null, null, null, null,
                    isAdvanced
                ).catch(() => {
                    dispatch(initDataSuccess(
                        {}, 'master', 'notfound', {saved: true}, {}, {}
                    ));
                    dispatch(getWindowBreadcrumb(windowType));
                });
            }
        }
    }
}

/*
 *  Wrapper for patch request of widget elements
 *  when responses should merge store
 */
export function patch(
    entity, windowType, id = 'NEW', tabId, rowId, property, value, isModal,
    isAdvanced
) {
    return dispatch => {
        let responsed = false;

        dispatch(indicatorState('pending'));

        return patchRequest(
            entity, windowType, id, tabId, rowId, property, value, null, null,
            isAdvanced
        ).then(response => {
            responsed = true;

            dispatch(mapDataToState(
                response.data, isModal, rowId, id, windowType, isAdvanced
            ));

            dispatch(indicatorState('saved'));
        }).catch(() => {
            getData(
                entity, windowType, id, tabId, rowId, null, null, isAdvanced
            ).then(response => {
                dispatch(mapDataToState(
                    response.data, isModal, rowId, id, windowType, isAdvanced
                ));
            });
        });
    }
}

export function fireUpdateData(entity, windowType, id, tabId, rowId, isModal,
    isAdvanced){

        return dispatch => {
            getData(
                entity, windowType, id, tabId, rowId, null, null, isAdvanced
            ).then(response => {
                dispatch(mapDataToState(
                    response.data, isModal, rowId, id, windowType, isAdvanced
                ));
            });
        }
}

function updateData(doc, scope){
    return dispatch => {
        Object.keys(doc).map(key => {
            if(key === 'fieldsByName'){
                Object.keys(doc.fieldsByName).map(fieldName => {
                    dispatch(updateDataFieldProperty(
                        fieldName, doc.fieldsByName[fieldName], scope
                    ))
                })
            }else if(key === 'includedTabsInfo'){
                dispatch(updateDataIncludedTabsInfo(
                    'master', doc[key]
                ));
            }else{
                dispatch(updateDataProperty(key, doc[key], scope))
            }
        })
    }
}

function updateRow(row, scope){
    return dispatch => {
        Object.keys(row).map(key => {
            if(key === 'fieldsByName'){
                Object.keys(row.fieldsByName).map(fieldName => {
                    dispatch(updateRowFieldProperty(
                        fieldName, row.fieldsByName[fieldName], row.tabid,
                        row.rowId, scope
                    ))
                });
            }else{
                dispatch(updateRowProperty(
                    key, row[key], row.tabid, row.rowId, scope
                ));
            }
        })
    }
}

function mapDataToState(data, isModal, rowId, id, windowType, isAdvanced) {
    return (dispatch) => {
        data.map((item, index) => {
            const parsedItem = item.fieldsByName ? Object.assign({}, item, {
                fieldsByName: parseToDisplay(item.fieldsByName)
            }) : item;

            // First item in response is direct one for action that called it.
            if(index === 0 && rowId === 'NEW'){
                dispatch(addNewRow(
                    parsedItem, parsedItem.tabid, parsedItem.rowId, 'master'
                ))
            }else{
                if (item.rowId && !isModal) {
                    // Update directly to a row by the widget in cell
                    dispatch(updateRow(parsedItem, 'master'));
                } else {
                    // Update by a modal
                    item.rowId && dispatch(updateRow(parsedItem, 'master'));

                    // Advanced edit
                    isAdvanced && dispatch(updateData(parsedItem, 'master'));

                    dispatch(updateData(
                        parsedItem, getScope(isModal && index === 0)
                    ));
                }
            }
        })
    }
}

function updateStatus(responseData) {
    return dispatch => {
        const updateDispatch = (item) => {
            if(item.rowId){
                dispatch(updateRowStatus(
                    'master', item.tabid, item.rowId, item.saveStatus
                ));
            }else{

                item.validStatus &&
                    dispatch(updateDataValidStatus('master', item.validStatus));
                item.saveStatus &&
                    dispatch(updateDataSaveStatus('master', item.saveStatus));
                item.includedTabsInfo &&
                    dispatch(updateDataIncludedTabsInfo(
                        'master', item.includedTabsInfo
                    ));
            }
        }

        if(Array.isArray(responseData)){
            responseData.map(item => {
                updateDispatch(item)
            });
        }else{
            updateDispatch(responseData)
        }
    }
}

/*
 * It updates store for single field value modification, like handleChange
 * in MasterWidget
 */
export function updatePropertyValue(property, value, tabid, rowid, isModal) {
    return dispatch => {
        if (tabid && rowid) {
            dispatch(updateRowFieldProperty(
                property, {value}, tabid, rowid, 'master'
            ))
            if (isModal) {
                dispatch(updateDataFieldProperty(property, {value}, 'modal'))
            }
        } else {
            dispatch(updateDataFieldProperty(
                property, {value}, getScope(isModal)
            ))
            if (isModal) {
                //update the master field too if exist
                dispatch(updateDataFieldProperty(property, {value}, 'master'))
            }
        }
    }
}

function handleUploadProgress(dispatch, notificationTitle, progressEvent) {
    let percentLeft = Math.min(Math.floor((progressEvent.loaded * 100) / progressEvent.total), 98);

    dispatch(setNotificationProgress(notificationTitle, percentLeft));
}

export function attachFileAction(windowType, docId, data){
    return (dispatch) => {
        const notificationTitle = counterpart.translate('window.attachment.title');

        dispatch(addNotification(
            notificationTitle, counterpart.translate('window.attachment.uploading'), 0, 'primary'
        ));

        let requestConfig = {
            onUploadProgress: handleUploadProgress.bind(this, dispatch, notificationTitle)
        };

        return axios.post(
            `${config.API_URL}/window/${windowType}/${docId}/attachments`, data, requestConfig
        ).then(() => {
            dispatch(setNotificationProgress(notificationTitle, 100));

            dispatch(addNotification(
                notificationTitle, counterpart.translate('window.attachment.upload.success'), 5000, 'primary'
            ))
        })
        .catch(() => {
            dispatch(addNotification(
                notificationTitle, counterpart.translate('window.attachment.upload.error'), 5000, 'error'
            ))
        })
    }
}

//ZOOM INTO
export function getZoomIntoWindow(entity, windowId, docId, tabId, rowId, field){
   return axios.get(
        config.API_URL +
        '/' + entity +
        '/' + windowId +
        (docId ? '/' + docId : '') +
        (tabId ? '/' + tabId : '') +
        (rowId ? '/' + rowId : '') +
        '/field' +
        '/' + field +
        '/zoomInto?showError=true'
    );
}

// PROCESS ACTIONS

export function createProcess(processType, viewId, type, ids, tabId, rowId) {
    let pid = null;
    return (dispatch) => {
        dispatch(setProcessPending());

        return getProcessData(
            processType, viewId, type, ids, tabId, rowId
        ).then( (response) => {
            if (response.data) {
                const preparedData = parseToDisplay(response.data.fieldsByName);

                pid = response.data.pinstanceId;

                if (Object.keys(preparedData).length === 0) {
                    startProcess(processType, pid).then(response => {
                        dispatch(handleProcessResponse(response, processType, pid));
                    }).catch(err => {
                        dispatch(setProcessSaved());
                        throw err;
                    });
                }
                else {
                    dispatch(initDataSuccess(preparedData, 'modal'));
                    initLayout('process', processType).then(response => {
                        dispatch(setProcessSaved());

                        const preparedLayout = Object.assign({}, response.data, {
                            pinstanceId: pid
                        });

                        return dispatch(initLayoutSuccess(preparedLayout, 'modal'))
                    }).catch(err => {
                        dispatch(setProcessSaved());

                        throw err;
                    });
                }
            }
        }).catch(err => {
            // Close process modal in case when process start failed
            dispatch(closeModal());

            dispatch(setProcessSaved());

            throw err;
        });
    }
}

export function handleProcessResponse(response, type, id, successCallback) {
    return (dispatch) => {
        const {
            error, summary, action
        } = response.data;

        if(error){
            dispatch(addNotification('Process error', summary, 5000, 'error'));
            dispatch(setProcessSaved());

            // Close process modal in case when process has failed
            dispatch(closeModal());
        }
        else {
            let closeProcessModal = true;

            if(action){
                switch(action.type){
                    case 'openView':
                        dispatch(closeModal());

                        dispatch(openRawModal(action.windowId, action.viewId));

                        break;
                    case 'openReport':
                        openFile(
                            'process', type, id, 'print', action.filename
                        );
                        break;
                    case 'openDocument':
                        dispatch(closeModal());

                        if(action.modal) {
                            // Do not close process modal, since it will be re-used with document view
                            closeProcessModal = false;

                            dispatch(
                                openModal(
                                    '', action.windowId, 'window', null, null,
                                    action.advanced ? action.advanced : false,
                                    '', '', action.documentId
                                )
                            );
                        } else {
                            dispatch(push(
                                '/window/' + action.windowId +
                                '/' + action.documentId
                            ));
                        }
                        break;
                    case 'openIncludedView':
                        dispatch(setListIncludedView(
                            action.windowId, action.viewId
                        ));
                        break;
                    case 'closeIncludedView':
                        dispatch(setListIncludedView());
                        break;
                    case 'selectViewRows':
                        dispatch(selectTableItems(
                            action.rowIds, action.windowId
                        ));
                        break;
                }
            }

            if(summary){
                dispatch(addNotification('Process', summary, 5000, 'primary'))
            }

            dispatch(setProcessSaved());

            if (closeProcessModal) {
                dispatch(closeModal());
            }

            successCallback && successCallback();
        }
    }
}

function getProcessData(processId, viewId, type, ids, tabId, rowId) {
    return axios.post(
        config.API_URL +
        '/process/' + processId,
        viewId ? {
            processId: processId,
            viewId: viewId,
            viewDocumentIds: ids
        } : {
            processId: processId,
            documentId: Array.isArray(ids) ? ids[0] : ids,
            documentType: type,
            tabId: tabId,
            rowId: rowId
        }
    );
}

export function startProcess(processType, pinstanceId) {
    return axios.get(
        config.API_URL +
        '/process/' + processType +
        '/' + pinstanceId +
        '/start'
    );
}

export function deleteLocal(tabid, rowsid, scope, response) {
    return (dispatch) => {
        for (let rowid of rowsid) {
            dispatch(deleteRow(tabid, rowid, scope))
        }
        dispatch(updateStatus(response.data))
    }
}

// END PROCESS ACTIONS

// UTILITIES

function getScope(isModal) {
    return isModal ? 'modal' : 'master';
}

export function parseToDisplay(obj) {
    return parseDateToReadable(nullToEmptyStrings(obj));
}

function parseDateToReadable(obj) {
    const dateParse = ['Date', 'DateTime', 'Time'];

    return Object.keys(obj).reduce((acc, key) => {
        acc[key] =
            (dateParse.indexOf(obj[key].widgetType) > -1 && obj[key].value) ?
                Object.assign({}, obj[key], {
                    value: obj[key].value ? new Date(obj[key].value) : ''
                }) : obj[key];
        return acc;
    }, {});
}

function nullToEmptyStrings(obj) {
    return Object.keys(obj).reduce((acc, key) => {
        acc[key] = (obj[key].value === null) ?
            Object.assign({}, obj[key], { value: '' }) :
            obj[key];
        return acc;
    }, {})
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

    arr && arr.map((item) => {
        if (item[prop] === value) {
            ret.push(item);
        }
    });

    return ret;
}

export function mapIncluded(
    node, indent, isParentLastChild = false
) {
    let ind = indent ? indent : [];
    let result = [];

    const nodeCopy = Object.assign({}, node, {
        indent: ind
    });

    result = result.concat([nodeCopy]);

    if(isParentLastChild){
        ind[ind.length - 2] = false;
    }

    if(node.includedDocuments){
        for(let i = 0; i < node.includedDocuments.length; i++){
            let copy = node.includedDocuments[i];
            if(i === node.includedDocuments.length - 1){
                copy = Object.assign({}, copy, {
                    lastChild: true
                });
            }

            result = result.concat(
                mapIncluded(copy, ind.concat([true]), node.lastChild)
            )
        }
    }
    return result;
}

export function collapsedMap(
    node, isCollapsed, initialMap
) {
    let collapsedMap = [];
    if(initialMap){
        if(!isCollapsed) {
            initialMap.splice(
                initialMap.indexOf(node.includedDocuments[0]),
                node.includedDocuments.length
                );
            collapsedMap = initialMap;
        } else {
            initialMap.map(item => {
                collapsedMap.push(item);
                if(item.id === node.id) {
                    collapsedMap = collapsedMap.concat(node.includedDocuments);

                }
            });
        }

    } else {
        if(node.includedDocuments){
            collapsedMap.push(node);
        }
    }

    return collapsedMap;
}

export function connectWS(topic, cb) {
    (this.sockClient && this.sockClient.connected) &&
        this.sockClient.disconnect();

    this.sock = new SockJs(config.WS_URL);
    this.sockClient = Stomp.Stomp.over(this.sock);
    this.sockClient.debug = null;
    this.sockClient.connect({}, () => {
        this.sockClient.subscribe(topic, msg => {
            cb(JSON.parse(msg.body));
        });
    });
}

export function disconnectWS() {
    (this.sockClient && this.sockClient.connected) &&
        this.sockClient.disconnect();
}
