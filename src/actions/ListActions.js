import * as types from '../constants/ActionTypes'
import axios from 'axios';

export function setFilter(filter, windowType){
    return {
        type: types.SET_LIST_FILTERS,
        filter: filter,
        windowType: windowType
    }
}

export function setSorting(prop, dir, windowType){
    return {
        type: types.SET_LIST_SORTING,
        prop: prop,
        dir: dir,
        windowType: windowType
    }
}

export function setPagination(page, windowType){
    return {
        type: types.SET_LIST_PAGINATION,
        page: page,
        windowType: windowType
    }
}

export function clearListProps(){
    return {
        type: types.CLEAR_LIST_PROPS
    }
}

export function clearListPagination(){
    return {
        type: types.CLEAR_LIST_PAGINATION
    }
}

export function updateFiltersParameters(filterId, property, value, valueTo){
	return {
		type: types.UPDATE_FILTERS_PARAMETERS,
		filterId: filterId,
		property: property,
		value: value,
        valueTo: valueTo
	}
}

export function deleteFiltersParameters(){
	return {
		type: types.DELETE_FILTERS_PARAMETERS
	}
}

export function initFiltersParameters(filterId, parameters){
	return {
		type: types.INIT_FILTERS_PARAMETERS,
		filterId: filterId,
		parameters: parameters
	}
}

export function initDocumentView(viewId) {
    return {
        type: types.INIT_DOC_VIEW,
        viewId: viewId
    }
}

export function quickActionsRequest(windowId, viewId) {
    return () => axios.get(
        config.API_URL + 'documentView/' + 
        windowId + viewId +
        '/quickActions'
    );
}
