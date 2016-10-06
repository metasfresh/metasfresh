import * as types from '../constants/ActionTypes'
import axios from 'axios';
import config from '../config';

export function setFilter(filter){
    return {
        type: types.SET_LIST_FILTERS,
        filter: filter
    }
}
export function setSorting(prop, dir){
    return {
        type: types.SET_LIST_SORTING,
        prop: prop,
        dir: dir
    }
}
export function setPagination(page){
    return {
        type: types.SET_LIST_PAGINATION,
        page: page
    }
}

export function clearListProps(){
    return {
        type: types.CLEAR_LIST_PROPS
    }
}
