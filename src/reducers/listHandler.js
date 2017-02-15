import * as types from '../constants/ActionTypes';
import update from 'react-addons-update';

const initialState = {
    sorting: {
        prop: null,
        dir: null,
        windowType: null
    },
    pagination: {
        page: 1,
        windowType: null
    },
    viewId: ''
}

export default function listHandler(state = initialState, action) {
    switch(action.type){
        case types.SET_LIST_FILTERS:
            return Object.assign({}, state, {
                filters: action.filter != null ? [action.filter] : [],
                filtersWindowType: action.windowType
            })

        case types.SET_LIST_PAGINATION:
            return Object.assign({}, state, {
                pagination: Object.assign({}, state.pagination, {
                    page: action.page,
                    windowType: action.windowType
                })
            })

        case types.SET_LIST_SORTING:
            return Object.assign({}, state, {
                sorting: Object.assign({}, state.sorting, {
                    prop: action.prop,
                    dir: action.dir,
                    windowType: action.windowType
                })
            })

        case types.CLEAR_LIST_PROPS:
            return Object.assign({}, state, {
                filters: [],
                sorting: {},
                windowType: null,
                pagination: null
            })

        default:
            return state
    }
}
