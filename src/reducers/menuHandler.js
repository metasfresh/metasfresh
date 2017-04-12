import * as types from '../constants/MenuTypes';

const initialState = {
    breadcrumb: []
}

export default function menuHandler(state = initialState, action) {
    switch(action.type){
        case types.SET_BREADCRUMB:
            return Object.assign({}, state, {
                breadcrumb: action.breadcrumb
            });
        case types.SET_GLOBAL_GRID_FILTER:
            return Object.assign({}, state, {
                globalGridFilter: action.filter
            });
        default:
            return state;
    }
}
