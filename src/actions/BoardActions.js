import axios from 'axios';

export function getLayout(boardId) {
  return axios.get(
    config.API_URL + '/board/' + boardId + '/newCardsView/layout'
  );
}

export function createView(boardId) {
  return axios.post(config.API_URL + '/board/' + boardId + '/newCardsView');
}

export function getView(boardId, viewId, firstRow) {
  return axios.get(
    config.API_URL +
      '/board/' +
      boardId +
      '/newCardsView' +
      (viewId ? '/' + viewId : '') +
      (viewId ? '?firstRow=' + firstRow + '&pageLength=50' : '')
  );
}

export function addCard(boardId, laneId, cardId, index) {
  return axios.post(config.API_URL + '/board/' + boardId + '/card', {
    laneId: laneId,
    position: index,
    documentId: cardId,
  });
}

export function filterCards(boardId, viewId) {
  return axios.post(
    config.API_URL + '/board/' + boardId + '/newVardsView/' + viewId + '/filter'
  );
}

export function addDashboardWidget(entity, id, pos) {
  return axios.post(config.API_URL + '/dashboard/' + entity + '/new', {
    kpiId: id,
    position: pos,
  });
}

export function removeDashboardWidget(entity, id) {
  return axios.delete(config.API_URL + '/dashboard/' + entity + '/' + id);
}
