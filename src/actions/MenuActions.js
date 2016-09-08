import * as types from '../constants/ActionTypes'
import axios from 'axios';
import config from '../config';

export function getWindowBreadcrumb(id){
    return dispatch => {
        dispatch(elementPathRequest("window", id)).then(response => {
            dispatch(setBreadcrumb(flatten(response.data).reverse()));
        })
    }
}

export function elementPathRequest(pathType, elementId) {
    return (dispatch) => axios.get(config.API_URL + '/menu/elementPath?type=' + pathType + '&elementId=' + elementId);
}

export function setBreadcrumb(breadcrumb){
    return {
        type: types.SET_BREADCRUMB,
        breadcrumb: breadcrumb
    }
}


// UTILITIES

function flatten(node) {
    let result = [];

    if(!!node.children){
        flatten(node.children[0]).map(item => {
            result.push(
                item
            );
        })
    }

    result.push(node.caption);

    return result;
}
