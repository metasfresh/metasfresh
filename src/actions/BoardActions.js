import axios from 'axios';

export function getView(entity, boardId, viewId) {
    return axios.get(
        config.API_URL +
        '/' + entity + '/' + boardId +
        '/newCardsView' +
        (viewId ? '/' + viewId : '')
    );
}
