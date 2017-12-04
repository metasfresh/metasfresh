import axios from 'axios';

export function getViewLayout(windowId, viewType) {
    console.log(new Error().stack);
    return axios.get(
        config.API_URL +
        '/documentView/' + windowId +
        '/layout' +
        '?viewType=' + viewType
    );
}

export function getViewRowsByIds(windowId, viewId, docIds) {
    return axios.get(
        config.API_URL +
        '/documentView/' + windowId +
        '/' + viewId +
        '/byIds' +
        '?ids='+ docIds
    );
}

export function browseViewRequest(
    viewId, page, pageLength, orderBy, windowType
){
    return axios.get(
        config.API_URL +
        '/documentView/' +
        windowType + '/' +
        viewId +
        '?firstRow=' + pageLength * (page - 1) +
        '&pageLength=' + pageLength +
        (orderBy ? '&orderBy=' + orderBy : '')
    );
}

export function deleteView(
    windowId, viewId
){
    return axios.delete(
        config.API_URL +
        '/documentView/' +
        windowId + '/' +
        viewId
    );
}

export function createViewRequest(
    windowType, viewType, pageLength, filters, refDocType = null,
    refDocId = null, refTabId = null, refRowIds = null
){
    let referencing = null;

    if (refDocType && refDocId) {
        referencing = {
            'documentType': refDocType,
            'documentId': refDocId
        };

        if (refTabId && refRowIds) {
            referencing.tabId = refTabId;
            referencing.rowIds = refRowIds;
        }
    }

    return axios.post(config.API_URL + '/documentView/' + windowType, {
        'documentType': windowType,
        'viewType': viewType,
        'referencing': referencing,
        'filters': filters
    });
}

export function filterViewRequest(windowType, viewId, filters){
    return axios.post(config.API_URL + '/documentView/' + windowType +
    '/'+viewId+'/filter', {
        'filters': filters
    });
}

export function deleteStaticFilter(windowId, viewId, filterId) {
    return axios.delete(
        config.API_URL +
        '/documentView/' + windowId +
        '/' + viewId +
        '/staticFilter/' + filterId
    );
}

export function quickActionsRequest(windowId, viewId, selectedIds) {
    return axios.get(
        config.API_URL + '/documentView/' +
        windowId + '/' + viewId +
        '/quickActions' +
        (selectedIds && selectedIds.length ? '?selectedIds=' + selectedIds : '')
    );
}
