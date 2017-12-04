import axios from 'axios';

export function getViewLayout(windowId, viewType, viewProfileId = null) {
    return axios.get(
        config.API_URL +
        '/documentView/' + windowId +
        '/layout' +
        '?viewType=' + viewType +
        (viewProfileId ? '&profileId=' + viewProfileId : '')
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

export function browseViewRequest({
    windowId,
    viewId,
    page,
    pageLength,
    orderBy
}){
    return axios.get(
        config.API_URL +
        '/documentView/' +
        windowId + '/' +
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

export function createViewRequest({
    windowId, viewType,
    pageLength,
    filters,
    refDocType = null, refDocId = null, refTabId = null, refRowIds = null
}){
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

    return axios.post(config.API_URL + '/documentView/' + windowId, {
        'documentType': windowId,
        'viewType': viewType,
        'referencing': referencing,
        'filters': filters
    });
}

export function filterViewRequest(windowId, viewId, filters){
    return axios.post(config.API_URL +
      '/documentView/' + windowId +
      '/' + viewId +
      '/filter', {
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
