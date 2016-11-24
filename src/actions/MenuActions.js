import * as types from '../constants/ActionTypes'
import axios from 'axios';
import config from '../config';


export function setBreadcrumb(breadcrumb){
    return {
        type: types.SET_BREADCRUMB,
        breadcrumb: breadcrumb
    }
}

export function setReferences(references){
    return {
        type: types.SET_REFERENCES,
        references: references
    }
}

export function setActions(actions){
    return {
        type: types.SET_ACTIONS,
        actions: actions
    }
}

// THUNK ACTIONS
export function pathRequest(nodeId) {
    return () => axios.get(config.API_URL + '/menu/path?nodeId=' + nodeId + '&inclusive=true');
}

export function nodePathsRequest(nodeId, limit) {
    return () => axios.get(config.API_URL + '/menu/node?nodeId=' + nodeId + '&depth=2' + (limit ? '&childrenLimit=' + limit : ""));
}

export function elementPathRequest(pathType, elementId) {
    return () => axios.get(config.API_URL + '/menu/elementPath?type=' + pathType + '&elementId=' + elementId + '&inclusive=true');
}

export function queryPathsRequest(query, limit, child) {
    return () => axios.get(config.API_URL + '/menu/queryPaths?nameQuery=' + query  + (limit ? '&childrenLimit=' + limit : "") + (child ? '&childrenInclusive=true':''));
}

export function rootRequest(limit) {
    return () => axios.get(config.API_URL + '/menu/root?depth=10' + (limit ? '&childrenLimit=' + limit : ""));
}

export function getRootBreadcrumb() {
    return (dispatch) => {
        dispatch(rootRequest(6)).then(root => {
            dispatch(setBreadcrumb([{nodeId: "0", children: root.data}]));
        });
    }
}

export function getWindowBreadcrumb(id){
    return dispatch => {
        dispatch(elementPathRequest("window", id)).then(response => {
            let req = 0;
            let pathData = flatten(response.data);


            // promise to get all of the breadcrumb menu options
            let breadcrumbProcess = new Promise((resolve) => {

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
                        dispatch(rootRequest(6)).then(root => {
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
        }).catch(() => {
            dispatch(setBreadcrumb([]));
        });
    }
}

export function getRelatedDocuments(type, id){
    return () => axios.get(config.API_URL + '/window/documentReferences?type=' + type + '&id=' + id);
}

export function getDocumentActions(type, id){
    return () => axios.get(config.API_URL + '/window/documentActions?type=' + type + '&id=' + id);
}

export function getViewActions(viewId){
    return () => axios.get(config.API_URL + '/documentView/' + viewId + '/actions');
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
