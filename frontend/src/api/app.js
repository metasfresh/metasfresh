import axios from 'axios';

export function getNotificationsRequest() {
  return axios.get(`${config.API_URL}/notifications/all?limit=20`);
}

export function getNotificationsEndpointRequest() {
  return axios.get(`${config.API_URL}/notifications/websocketEndpoint`);
}

export function pathRequest(nodeId) {
  return axios.get(`${config.API_URL}/menu/${nodeId}/path/&inclusive=true`);
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
  return axios.get(`${config.API_URL}/menu/node/${nodeId}/breadcrumbMenu`);
}
