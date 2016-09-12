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
    return dispatch => axios.get(config.API_URL + '/menu/node?nodeId=' + nodeId + '&depth=2');
}

export function elementPathRequest(pathType, elementId) {
    return dispatch => axios.get(config.API_URL + '/menu/elementPath?type=' + pathType + '&elementId=' + elementId);
}

export function queryPathsRequest(query) {
    return dispatch => axios.get(config.API_URL + '/menu/queryPaths?nameQuery=' + query);
}

export function rootRequest() {
    return dispatch => axios.get(config.API_URL + '/menu/root?depth=3');
}

export function getWindowBreadcrumb(id){
    return dispatch => {
        dispatch(elementPathRequest("window", id)).then(response => {
            let pathData = flatten(response.data);

            // promise to get all of the breadcrumb menu options
            let breadcrumb = new Promise((resolve, reject) => {
                let req = 0;

                pathData.map(node => {

                    if(node.nodeId != 0){
                        //not root menu

                        dispatch(nodePathsRequest(node.nodeId)).then(item => {
                            item.data.children.length > 10 ? item.data.children.length = 10 : null;

                            node.children = item.data;
                            req++;
                        })
                    }else{
                        //root menu

                        dispatch(rootRequest()).then(root => {
                            root.data.children.length > 4 ? root.data.children.length = 4 : null;
                            root.data.children.map(item => {
                                item.children.length > 4 ? item.children.length = 4 : null;
                            })
                            node.children = root.data;
                            req++;
                        })
                    }
                    if(req === pathData.length){
                        resolve();
                    }
                })
                dispatch(setBreadcrumb(pathData.reverse()));
            })
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
        nodeId: node.nodeId
    });

    return result;
}
