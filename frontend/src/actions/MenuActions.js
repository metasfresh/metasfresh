import { HOME_MENU_USER_MAX_ITEMS } from '../constants/Constants';

import * as types from '../constants/MenuTypes';
import { elementPathRequest, rootRequest } from '../api';

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
  return rootRequest(HOME_MENU_USER_MAX_ITEMS, 10, true).then((root) => ({
    nodeId: '0',
    children: root.data.children,
  }));
}

export function setBreadcrumbByWindowId(id) {
  return (dispatch) => {
    elementPathRequest('window', id)
      .then((response) => flattenOneLine(response.data))
      .then((item) => dispatch(setBreadcrumb(item.reverse())))
      .catch(() => dispatch(setBreadcrumb([])));
  };
}

//END OF THUNK ACTIONS

// UTILITIES

export function flattenLastElem(node, prop = 'children') {
  let result = [];

  if (node[prop]) {
    node[prop].map((child) => {
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
    flattenOneLine(node.children[0]).map((item) => {
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
