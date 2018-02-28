import axios from 'axios';

import * as types from '../constants/MenuTypes';

// REQUESTS

let breadcrumbsRequested = false;
let breadcrumbsId = null;

export function pathRequest(nodeId) {
  return axios.get(
    config.API_URL + '/menu/' + nodeId + '/path/' + '&inclusive=true'
  );
}

export function nodePathsRequest(nodeId, limit) {
  return axios.get(
    config.API_URL +
      '/menu/node/' +
      nodeId +
      '?depth=2' +
      (limit ? '&childrenLimit=' + limit : '')
  );
}

export function elementPathRequest(pathType, elementId) {
  return axios.get(
    config.API_URL +
      '/menu/elementPath?type=' +
      pathType +
      '&elementId=' +
      elementId +
      '&inclusive=true'
  );
}

export function queryPathsRequest(query, limit, child) {
  return axios.get(
    config.API_URL +
      '/menu/queryPaths?nameQuery=' +
      query +
      (limit ? '&childrenLimit=' + limit : '') +
      (child ? '&childrenInclusive=true' : '')
  );
}

export function rootRequest(limit, depth = 0, onlyFavorites) {
  return axios.get(
    config.API_URL +
      '/menu/root?depth=' +
      depth +
      (limit ? '&childrenLimit=' + limit : '') +
      (onlyFavorites ? '&favorites=true' : '')
  );
}
export function breadcrumbRequest(nodeId) {
  return axios.get(config.API_URL + '/menu/node/' + nodeId + '/breadcrumbMenu');
}

// END OF REQUESTS

export function setBreadcrumb(breadcrumb) {
  return {
    type: types.SET_BREADCRUMB,
    breadcrumb,
  };
}

export function updateBreadcrumb(node) {
  return {
    type: types.UPDATE_BREADCRUMB,
    node,
  };
}

export function getRootBreadcrumb() {
  return rootRequest(6, 10, true).then(root => ({
    nodeId: '0',
    children: root.data.children,
  }));
}

export function getWindowBreadcrumb(id) {
  return dispatch => {
    if (!breadcrumbsRequested && breadcrumbsId !== id) {
      breadcrumbsRequested = true;

      elementPathRequest('window', id)
        .then(response => {
          let pathData = flattenOneLine(response.data);
          return pathData;
        })
        .then(item => {
          dispatch(setBreadcrumb(item.reverse()));

          breadcrumbsId = id;
          breadcrumbsRequested = false;
        })
        .catch(() => {
          dispatch(setBreadcrumb([]));

          breadcrumbsId = null;
          breadcrumbsRequested = false;
        });
    }
  };
}

export function getElementBreadcrumb(entity, id) {
  return dispatch => {
    if (!breadcrumbsRequested && breadcrumbsId !== id) {
      breadcrumbsRequested = true;

      elementPathRequest(entity, id)
        .then(response => {
          let pathData = flattenOneLine(response.data);
          return pathData;
        })
        .then(item => {
          dispatch(setBreadcrumb(item.reverse()));

          breadcrumbsId = id;
          breadcrumbsRequested = false;
        })
        .catch(() => {
          dispatch(setBreadcrumb([]));

          breadcrumbsId = null;
          breadcrumbsRequested = false;
        });
    }
  };
}

//END OF THUNK ACTIONS

// UTILITIES

export function flattenLastElem(node, prop = 'children') {
  let result = [];

  if (node[prop]) {
    node[prop].map(child => {
      const flat = flattenLastElem(child);

      if (typeof flat === 'object') {
        result = result.concat(flat);
      } else {
        result.push(flattenLastElem(child));
      }
    });
    return result;
  } else {
    return [node];
  }
}

export function flattenOneLine(node) {
  let result = [];
  if (node.children) {
    flattenOneLine(node.children[0]).map(item => {
      result.push(item);
    });
  }
  result.push({
    nodeId: node.nodeId,
    caption: node.caption,
    type: node.type,
  });
  return result;
}
