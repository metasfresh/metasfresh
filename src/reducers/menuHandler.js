import * as types from '../constants/MenuTypes';
import update from 'react-addons-update';

const initialState = {
    breadcrumb: [],
    references: [],
    attachments: [],
    actions: [],
    homemenu: {}
}

export default function menuHandler(state = initialState, action) {
    switch(action.type){
        case types.SET_BREADCRUMB:
            return Object.assign({}, state, {
                breadcrumb: action.breadcrumb
            })
        case types.SET_HOMEMENU:
            return Object.assign({}, state, {
                homemenu: action.homemenu
            })
        case types.SET_REFERENCES:
            return Object.assign({}, state, {
                references: action.references
            })
        case types.SET_ACTIONS:
            return Object.assign({}, state, {
                actions: action.actions
            })
        case types.SET_ATTACHMENTS:
            return Object.assign({}, state, {
                attachments: action.attachments
            })
        case types.SET_GLOBAL_GRID_FILTER:
            return Object.assign({}, state, {
                globalGridFilter: action.filter
            })
        default:
            return state
    }
}
