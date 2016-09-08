import * as types from '../constants/ActionTypes'
import axios from 'axios';
import config from '../config';



export function setBreadcrumb(breadcrumb){
    return {
        type: types.SET_BREADCRUMB,
        breadcrumb: breadcrumb
    }
}

// THUNK ACTIONS

export function nodePathsRequest(nodeId) {
    return dispatch => axios.get(config.API_URL + '/menu/node?nodeId=' + query + '&depth=1');
}

export function elementPathRequest(pathType, elementId) {
    return dispatch => axios.get(config.API_URL + '/menu/elementPath?type=' + pathType + '&elementId=' + elementId);
}

export function queryPathsRequest(query) {
    return dispatch => axios.get(config.API_URL + '/menu/queryPaths?nameQuery=' + query);
}



export function getWindowBreadcrumb(id){
    return dispatch => {
        dispatch(elementPathRequest("window", id)).then(response => {
            dispatch(setBreadcrumb(flatten(response.data).reverse()));
        })
    }
}

//END OF THUNK ACTIONS

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

    result.push({
        nodeId: node.nodeId,
        caption: node.caption
    });

    return result;
}
