import * as types from '../constants/ActionTypes';
import update from 'react-addons-update';

const initialState = {
    selectedProducts: []
}

export default function appHandler(state = initialState, action) {
    switch(action.type){
        case types.SELECT_PRODUCT:
            return update(state, {
                selectedProducts: {$push: [action.product]}
            })
        case types.SELECT_ONE_PRODUCT:
            return update(state, {
                selectedProducts: {$set: [action.product]}
            })
        case types.SELECT_RANGE_PRODUCT:
            return update(state, {
                selectedProducts: {$set: action.ids}
            })
        case types.DESELECT_PRODUCT:
            return update(state, {
                selectedProducts: {$splice: [[action.product, 1]]}
            })
        case types.DESELECT_ALL_PRODUCTS:
            return update(state, {
                selectedProducts: {$set: []}
            })

        default:
            return state
    }
}
