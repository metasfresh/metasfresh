import * as types from '../constants/ActionTypes';
import update from 'react-addons-update';

const initialState = {
    layout: {}
}

export default function windowHandler(state = initialState, action) {
    switch(action.type){
        case types.CREATE_WINDOW_SUCCESS:
            return Object.assign({}, state, {
                layout: action.layout
            })
        default:
            return state
    }
}
