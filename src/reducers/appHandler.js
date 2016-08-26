import * as types from '../constants/ActionTypes';
import update from 'react-addons-update';

const initialState = {
    autocomplete: {
        query: "",
        results: [],
    },
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
        case types.AUTOCOMPLETE:
            return Object.assign({}, state, {
                autocomplete: Object.assign({}, state.autocomplete, {
                    query: action.query
                })
            })
        case types.AUTOCOMPLETE_SUCCESS:
            return Object.assign({}, state, {
                autocomplete: Object.assign({}, state.autocomplete, {
                    results: action.results
                })
            })
        case types.AUTOCOMPLETE_SELECT:
            return Object.assign({}, state, {
                autocomplete: Object.assign({}, state.autocomplete, {
                    selected: action.id
                })
            })

        default:
            return state
    }
}
