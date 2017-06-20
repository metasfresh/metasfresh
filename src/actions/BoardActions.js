import axios from 'axios';

export function getView(boardId, viewId, firstRow) {
    return axios.get(
        config.API_URL +
        '/board/' + boardId +
        '/newCardsView' +
        (viewId ? '/' + viewId : '') +
        (viewId ? '?firstRow=' + firstRow + '&pageLength=20' : '')
    );
}

export function addCard(boardId, laneId, cardId, index) {
    return axios.post(
        config.API_URL +
        '/board/' + boardId +
        '/card', {
            "laneId": laneId,
            "position": index,
            "documentId": cardId
        }
    );
}
