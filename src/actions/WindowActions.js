import * as types from '../constants/ActionTypes'
import axios from 'axios';
import config from '../config';

export function createWindow(windowType){
    return (dispatch) => {
        axios.get(config.API_URL + '/window/layout?type=' + windowType)
            .then((response) => {
                dispatch(createWindowSuccess(response.data));
            });
    }
}

export function createWindowSuccess(layout) {
    return {
        type: types.CREATE_WINDOW_SUCCESS,
        layout: layout
    }
}
