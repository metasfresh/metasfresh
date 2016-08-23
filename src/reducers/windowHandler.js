import * as types from '../constants/ActionTypes';
import update from 'react-addons-update';

const initialState = {
    layout: {},
    data: [{
        value: 0
    }]
}

export default function windowHandler(state = initialState, action) {
    switch(action.type){
        case types.INIT_LAYOUT_SUCCESS:
            return Object.assign({}, state, {
                layout: action.layout
        })
        case types.INIT_DATA_SUCCESS:
            return Object.assign({}, state, {
                data: action.data
        })
        case types.UPDATE_DATA_SUCCESS:
            return Object.assign({}, state, {
                data: state.data.map(item =>
                    item.field === action.item.field ?
                    Object.assign({}, item, action.item) :
                    item
                )
        })
        case types.UPDATE_DATA_PROPERTY:
            return Object.assign({}, state, {
                data: state.data.map(item =>
                    item.field === action.property ?
                    Object.assign({}, item, { value: action.value }) :
                    item
                )
        })
        default:
        return state
    }
}
