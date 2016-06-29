import * as types from '../constants/ActionTypes';
import update from 'react-addons-update';


const initialState = {
    products: {
        products: [{id: 0},{id: 1},{id: 2}],
        containers: [{id: 0}]
    }
}

export default function salesOrderStateHandler(state = initialState, action) {
    switch(action.type){

        default:
            return state
    }
}
