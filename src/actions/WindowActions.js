import * as types from '../constants/ActionTypes'
import axios from 'axios';
import { push, replace } from 'react-router-redux';

import {
    getWindowBreadcrumb
} from './MenuActions';

import {
    clearListProps
} from './ListActions';

import {
    initLayout,
    getData,
    patchRequest,
    printRequest
} from './GenericActions';

import {
    addNotification
} from './AppActions'


export function initLayoutSuccess(layout, scope) {
    return {
        type: types.INIT_LAYOUT_SUCCESS,
        layout: layout,
        scope: scope
    }
}

export function initDataSuccess(data, scope) {
    return {
        type: types.INIT_DATA_SUCCESS,
        data: data,
        scope: scope
    }
}

export function addRowData(data, scope) {
    return {
        type: types.ADD_ROW_DATA,
        data: data,
        scope: scope
    }
}

export function updateDataSuccess(item, scope) {
    return {
        type: types.UPDATE_DATA_SUCCESS,
        item: item,
        scope: scope
    }
}

export function updateRowSuccess(item, tabid, rowid, scope) {
    return {
        type: types.UPDATE_ROW_SUCCESS,
        item: item,
        tabid: tabid,
        rowid: rowid,
        scope: scope
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

export function updateDataProperty(property, value, scope) {
    return {
        type: types.UPDATE_DATA_PROPERTY,
        property: property,
        value: value,
        scope: scope
    }
}

export function updateRowProperty(property, value, tabid, rowid, scope) {
    return {
        type: types.UPDATE_ROW_PROPERTY,
        property: property,
        value: value,
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

export function openModal(title, windowType, type, tabId, rowId, isAdvanced) {
    return {
        type: types.OPEN_MODAL,
        windowType: windowType,
        modalType: type,
        tabId: tabId,
        rowId: rowId,
        title: title,
        isAdvanced: isAdvanced
    }
}

export function closeModal() {
    return {
        type: types.CLOSE_MODAL
    }
}

export function updateModal(rowId) {
    return {
        type: types.UPDATE_MODAL,
        rowId: rowId
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

export function selectTableItems(ids) {
    return {
        type: types.SELECT_TABLE_ITEMS,
        ids: ids
    }
}

// THUNK ACTIONS

/*
 * Main method to generate window
 */
export function createWindow(windowType, docId = "NEW", tabId, rowId, isModal = false, isAdvanced) {
    return (dispatch) => {
        if (docId == "new") {
            docId = "NEW";
        }

        // this chain is really important,
        // to do not re-render widgets on init
        return dispatch(initWindow(windowType, docId, tabId, rowId, isAdvanced))
            .then(response => {

                let elem = 0;

                response.data.forEach((value, index) => {
                    if (value.rowId === rowId) {
                        elem = index;
                    }
                });

                if (docId == "NEW" && !isModal) {
                    dispatch(clearListProps());
                    dispatch(replace("/window/" + windowType + "/" + response.data[0].id));
                }

                docId = response.data[elem].id;
                const preparedData = parseToDisplay(response.data[elem].fields);

                dispatch(initDataSuccess(preparedData, getScope(isModal)));

                if (isModal && rowId === "NEW") {
                    dispatch(mapDataToState([response.data[0]], false, "NEW", docId, windowType))
                    dispatch(updateModal(response.data[0].rowId));
                }

                if (!isModal) {
                    dispatch(getWindowBreadcrumb(windowType));
                }
            }).then(() =>
                dispatch(initLayout('window', windowType, tabId, null, null, isAdvanced))
            ).then(response =>
                dispatch(initLayoutSuccess(response.data, getScope(isModal)))
            ).then(response => {
                let tabTmp = {};

                response.layout.tabs && response.layout.tabs.map(tab => {
                    tabTmp[tab.tabid] = {};
                    dispatch(getTab(tab.tabid, windowType, docId)).then(res => {
                        tabTmp[tab.tabid] = res;
                        dispatch(addRowData(tabTmp, getScope(isModal)));
                    })
                })
            });
    }
}

function getTab(tabId, windowType, docId) {
    return dispatch =>
        dispatch(getData('window', windowType, docId, tabId))
            .then(res => {
                if(res.data){
                    let tab = {};
                    res.data.map(row => {
                        row.fields = parseToDisplay(row.fields);
                        tab[row.rowId] = row;
                    });
                    return tab;
                }
            })
}

export function initWindow(windowType, docId, tabId, rowId = null, isAdvanced) {
    return (dispatch) => {
        if (docId === "NEW") {
            //New master document
            return dispatch(patchRequest('window', windowType, docId))
        } else {
            if (rowId === "NEW") {
                //New row document
                return dispatch(patchRequest('window', windowType, docId, tabId, "NEW"))
            } else if (rowId) {
                //Existing row document
                return dispatch(getData('window', windowType, docId, tabId, rowId, null, null, isAdvanced))
            } else {
                //Existing master document
                return dispatch(getData('window', windowType, docId, null, null, null, null, isAdvanced))
            }
        }
    }
}

/*
 *  Wrapper for patch request of widget elements
 *  when responses should merge store
 */
export function patch(entity, windowType, id = "NEW", tabId, rowId, property, value, isModal, isAdvanced) {
    return dispatch => {
        let responsed = false;

        dispatch(indicatorState('pending'));
        let time = 0
        let timeoutLoop = () => {
            setTimeout(function() {
                time = 999;
                if (responsed) {
                    dispatch(indicatorState('saved'));
                } else {
                    timeoutLoop();
                }
            }, time);
        }
        timeoutLoop();

        return dispatch(patchRequest(
            entity, windowType, id, tabId, rowId, property, value, null, null,
            isAdvanced)
        ).then(response => {
            responsed = true;
            dispatch(mapDataToState(response.data, isModal, rowId, id, windowType));
        }).catch((err) => {
            dispatch(getData('window', windowType, id, tabId, rowId, null, null, isAdvanced))
                .then(response => {
                    dispatch(mapDataToState(response.data, isModal, rowId, id, windowType));
                });
        });
    }
}

function mapDataToState(data, isModal, rowId, id, windowType) {
    return (dispatch) => {
        let staleTabIds = [];
        data.map(item => {
            // Merging staleTabIds
            item.staleTabIds && item.staleTabIds.map(item => {
                if(staleTabIds.indexOf(item) === -1){
                    staleTabIds.push(item);
                }
            })
            // Mapping fields property
            item.fields = parseToDisplay(item.fields);
            if (rowId === "NEW") {
                dispatch(addNewRow(item, item.tabid, item.rowId, "master"))
            } else {
                item.fields.map(field => {
                    if (rowId && !isModal) {
                        dispatch(updateRowSuccess(field, item.tabid, item.rowId, getScope(isModal)));
                    } else {
                        if (rowId) {
                            dispatch(updateRowSuccess(field, item.tabid, item.rowId, getScope(false)));
                        }
                        
                        dispatch(updateDataSuccess(field, getScope(isModal)));
                    }
                });
            }
        })

        //Handling staleTabIds
        staleTabIds.map(staleTabId => {
            dispatch(getTab(staleTabId, windowType, id)).then(tab => {
                const keys = Object.keys(tab);

                keys.map(key => {
                    dispatch(addNewRow(tab[key], staleTabId, key, "master"))
                })
            })
        })
    }
}

export function updateProperty(property, value, tabid, rowid, isModal) {
    return dispatch => {
        if (tabid && rowid) {
            dispatch(updateRowProperty(property, value, tabid, rowid, "master"))
            if (isModal) {
                dispatch(updateDataProperty(property, value, "modal"))
            }
        } else {
            dispatch(updateDataProperty(property, value, getScope(isModal)))
            if (isModal) {
                //update the master field too if exist
                dispatch(updateDataProperty(property, value, "master"))
            }
        }
    }
}

// PROCESS ACTIONS

export function createProcess(processType, viewId, type, ids) {
    let pid = null;
    return (dispatch) =>
        dispatch(getProcessData(processType, viewId, type, ids)).then(response => {
            const preparedData = parseToDisplay(response.data.parameters);
            pid = response.data.pinstanceId;
            if (preparedData.length === 0) {
                dispatch(startProcess(processType, pid)).then(response => {
                    dispatch(handleProcessResponse(response, processType, pid));
                });
                throw new Error("close_modal");
            }else{
                dispatch(initDataSuccess(preparedData, "modal"));

                dispatch(initLayout('process', processType)).then(response => {
                    const preparedLayout = Object.assign({}, response.data, {
                        pinstanceId: pid
                    })
                    return dispatch(initLayoutSuccess(preparedLayout, "modal"))
                });

            }
        })
}

export function handleProcessResponse(response, type, id, successCallback) {
    return (dispatch) => {
        const {error, summary, viewId, viewWindowId, reportFilename} = response.data;

        if(error){
            dispatch(addNotification("Process error", summary, 5000, "error"));
        }else{
            if(viewId && viewWindowId){
                dispatch(push('/window/' + viewWindowId + '?viewId=' + viewId));
            }else if(reportFilename){
                dispatch(printRequest('process', type, id, reportFilename))
            }

            if(summary){
                dispatch(addNotification('Process', summary, 5000, 'primary'))
            }

            successCallback && successCallback();
        }
    }
}

function getProcessData(processId, viewId, type, ids) {
    return () => axios.post(
        config.API_URL +
        '/process/' + processId,
        viewId ? {
            processId: processId,
            viewId: viewId,
            viewDocumentIds: ids
        } : {
            processId: processId,
            documentId: ids,
            documentType: type
        }
    );
}

export function startProcess(processType, pinstanceId) {
    return () => axios.get(
        config.API_URL +
        '/process/' + processType +
        '/' + pinstanceId +
        '/start'
    );
}

export function updateProcess(processId, pinstanceId, data){
    return axios.patch(`${config.API_URL}/process/${processId}/${pinstanceId}`, data)
}

export function deleteLocal(tabid, rowsid, scope) {
    return (dispatch) => {
        for (let rowid of rowsid) {
            dispatch(deleteRow(tabid, rowid, scope))
        }
    }
}

// END PROCESS ACTIONS



// UTILITIES

function getScope(isModal) {
    return isModal ? "modal" : "master";
}

export function parseToDisplay(arr) {
    return parseDateToReadable(nullToEmptyStrings(arr));
}

function parseDateToReadable(arr) {
    const dateParse = ['Date', 'DateTime', 'Time'];
    return arr.map(item =>
        (dateParse.indexOf(item.widgetType) > -1 && item.value) ?
        Object.assign({}, item, { value: item.value ? new Date(item.value) : "" }) :
        item
    )
}

function nullToEmptyStrings(arr) {
    return arr.map(item =>
        (item.value === null) ?
        Object.assign({}, item, { value: "" }) :
        item
    )
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

    arr.map((item) => {
        if (item[prop] === value) {
            ret.push(item);
        }
    });

    return ret;
}
