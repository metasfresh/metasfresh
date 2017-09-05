import * as types from '../constants/ListTypes'
import axios from 'axios';

export function quickActionsRequest(windowId, viewId, selectedIds) {
    return axios.get(
        config.API_URL + '/documentView/' +
        windowId + '/' + viewId +
        '/quickActions' +
        (selectedIds && selectedIds.length ? '?selectedIds=' + selectedIds : '')
    );
}

export function setListId(viewId, windowType) {
    return {
        type: types.SET_LIST_ID,
        viewId,
        windowType
    }
}

export function setFilter(filter, windowType){
    return {
        type: types.SET_LIST_FILTERS,
        filter,
        windowType
    }
}

export function setSorting(sort, windowType){
    return {
        type: types.SET_LIST_SORTING,
        sort,
        windowType
    }
}

export function setPagination(page, windowType){
    return {
        type: types.SET_LIST_PAGINATION,
        page,
        windowType
    }
}

export function setListIncludedView(windowType, viewId) {
    return {
        type: types.SET_LIST_INCLUDED_VIEW,
        windowType,
        viewId
    }
}

export function closeListIncludedView() {
    return {
        type: types.CLOSE_LIST_INCLUDED_VIEW
    }
}
