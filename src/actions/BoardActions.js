import axios from 'axios';

export function getView(entity, boardId, viewId, firstRow) {
    return axios.get(
        config.API_URL +
        '/' + entity + '/' + boardId +
        '/newCardsView' +
        (viewId ? '/' + viewId : '') +
        (viewId ? '?firstRow=' + firstRow + '&pageLength=20' : '')
    );
}
