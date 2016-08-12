import * as types from '../constants/ActionTypes'
import axios from 'axios';
import config from '../config';

export function createWindow(windowType){
    return (dispatch) => {
        if(windowType === "test"){
            dispatch(initTestLayout());
        }else{
            // TODO : fix for chaining dispatched actions ( occurs double rendering )

            dispatch(initLayout(windowType));
            dispatch(initData(windowType));
        }
    }
}

export function initTestLayout() {
    return (dispatch) => {
        axios.get('http://private-anon-68a0fd5cf8-metasfresh.apiary-mock.com/api/layout?type=143')
            .then((response) => {
                dispatch(initLayoutSuccess(response.data));
            });
    }
}

export function initLayout(windowType){
    return (dispatch) => {
        axios.get(config.API_URL + '/window/layout?type=' + windowType)
            .then((response) => {
                dispatch(initLayoutSuccess(response.data));
            });
    }
}

export function initData(windowType, id = 'NEW') {
    return (dispatch) => {
        axios.get(config.API_URL + '/window/data?type=' + windowType + '&id=' + id)
            .then((response) => {
                dispatch(initDataSuccess(response.data[0]));
            });
    }
}

export function initLayoutSuccess(layout) {
    return {
        type: types.INIT_LAYOUT_SUCCESS,
        layout: layout
    }
}
export function initDataSuccess(data) {
    return {
        type: types.INIT_DATA_SUCCESS,
        data: data
    }
}
