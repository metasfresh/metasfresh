import * as types from '../constants/ActionTypes';
import update from 'react-addons-update';

const initialState = {
    breadcrumb: []
}

export default function menuHandler(state = initialState, action) {
    switch(action.type){
        case types.SET_BREADCRUMB:
            return Object.assign({}, state, {
                breadcrumb: action.breadcrumb
            })
        default:
            return state
    }
}
