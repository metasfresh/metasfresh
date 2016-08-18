import * as types from '../constants/ActionTypes'
import axios from 'axios';
import config from '../config';

export function createWindow(windowType){
    return (dispatch) => {
        if(windowType === "test"){
            dispatch(initTestLayout());
        }else{
            //
            // this chain is really important,
            // to do not re-render widgets on init
            //
            dispatch(patchRequest(windowType)).then((response)=>{
                const id = response.data;
                dispatch(initData(windowType, id)).then((response) => {
                    dispatch(initDataSuccess(response.data[0]));
                    dispatch(initLayout(windowType)).then((response) => {
                        dispatch(initLayoutSuccess(response.data))
                    })
                })
            });
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
    return dispatch => axios.get(config.API_URL + '/window/layout?type=' + windowType);
}

export function initData(windowType, id) {
    return dispatch => axios.get(config.API_URL + '/window/data?type=' + windowType + '&id=' + id);
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

export function patchRequest(windowType, id = "NEW") {
    return (dispatch) => axios.patch(config.API_URL + '/window/commit?type=' + windowType + '&id=' + id, {
        data: {
            events: []
        }
    });
}
