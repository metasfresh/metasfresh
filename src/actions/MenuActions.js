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

export function nodePathsRequest(nodeId, limit) {
    return dispatch => axios.get(config.API_URL + '/menu/node?nodeId=' + nodeId + '&depth=2' + (limit ? '&childrenLimit=' + limit : ""));
}

export function elementPathRequest(pathType, elementId) {
    return dispatch => axios.get(config.API_URL + '/menu/elementPath?type=' + pathType + '&elementId=' + elementId);
}

export function queryPathsRequest(query, limit) {
    return dispatch => axios.get(config.API_URL + '/menu/queryPaths?nameQuery=' + query  + (limit ? '&childrenLimit=' + limit : ""));
}

export function rootRequest(limit) {
    return dispatch => axios.get(config.API_URL + '/menu/root?depth=3' + (limit ? '&childrenLimit=' + limit : ""));
}

export function getWindowBreadcrumb(id){
    return dispatch => {
        dispatch(elementPathRequest("window", id)).then(response => {
            let req = 0;
            let pathData = flatten(response.data);

            // promise to get all of the breadcrumb menu options
            let breadcrumbProcess = new Promise((resolve, reject) => {

                for(let i = 0; i < pathData.length; i++){
                    const node = pathData[i];

                    if(node.nodeId != "0"){
                        //not root menu
                        dispatch(nodePathsRequest(node.nodeId, 10)).then(item => {
                            node.children = item.data;
                            req += 1;

                            if(req === pathData.length){
                                resolve(pathData);
                            }
                        })
                    }else{
                        //root menu
                        dispatch(rootRequest(4)).then(root => {
                            node.children = root.data;
                            req += 1;

                            if(req === pathData.length){
                                resolve(pathData);
                            }
                        })
                    }
                }
            });

            return breadcrumbProcess;
        }).then((item) => {
            dispatch(setBreadcrumb(item.reverse()));
        });
    }
}

//END OF THUNK ACTIONS

// UTILITIES

export function flatten(node) {
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
