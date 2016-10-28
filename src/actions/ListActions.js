import * as types from '../constants/ActionTypes'
import axios from 'axios';
import config from '../config';

export function setFilter(filter){
    return {
        type: types.SET_LIST_FILTERS,
        filter: filter
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


export function updateFiltersParameters(filterId, property, value){
	return {
		type: types.UPDATE_FILTERS_PARAMETERS,
		filterId: filterId,
		property: property,
		value: value
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
