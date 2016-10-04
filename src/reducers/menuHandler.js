import * as types from '../constants/ActionTypes';
import update from 'react-addons-update';

const initialState = {
    breadcrumb: [],
    references: []
}

export default function menuHandler(state = initialState, action) {
    switch(action.type){
        case types.SET_BREADCRUMB:
            return Object.assign({}, state, {
                breadcrumb: action.breadcrumb
            })
        case types.SET_REFERENCES:
            return Object.assign({}, state, {
                references: action.references
            })
        default:
            return state
    }
}
