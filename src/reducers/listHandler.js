import * as types from '../constants/ActionTypes';
import update from 'react-addons-update';

const initialState = {
    filters: null,
    sorting: {
        prop: null,
        dir: null
    },
    page: 1
}

export default function listHandler(state = initialState, action) {
    switch(action.type){
        case types.SET_LIST_FILTERS:
            return Object.assign({}, state, {
                filters: action.filter
            })
        case types.SET_LIST_PAGINATION:
            return Object.assign({}, state, {
                page: action.page
            })
        case types.SET_LIST_SORTING:
            return Object.assign({}, state, {
                sorting: Object.assign({}, state.sorting, {
                    prop: action.prop,
                    dir: action.dir
                })
            })
        default:
            return state
    }
}
