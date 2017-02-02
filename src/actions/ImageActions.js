import * as types from '../constants/ActionTypes'
import axios from 'axios';

export const getImageAction = (id) => axios({
    url: `${config.API_URL}/image/${id}`,
    responseType: 'blob'
})
    .then(response => response.data);

export const postImageAction = (data) => axios.post(`${config.API_URL}/image`, data)
    .then(response => response.data);
