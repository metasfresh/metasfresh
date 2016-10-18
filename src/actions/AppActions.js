import * as types from '../constants/ActionTypes'
import axios from 'axios';
import config from '../config';
import {push,replace} from 'react-router-redux';

export function autocompleteRequest(windowType, propertyName, query, id = "NEW") {
    query = encodeURIComponent(query);
    return (dispatch) => axios.get(config.API_URL + '/window/typeahead?type=' + windowType + '&id='+id+'&field='+ propertyName +'&query=' + query);
}

export function dropdownRequest(windowType, propertyName, id, tabId, rowId) {
    return (dispatch) => axios.get(
        config.API_URL +
        '/window/dropdown?type=' + windowType +
        '&id=' + id +
        '&field=' + propertyName+
        (tabId ? "&tabid=" + tabId : "") +
        (rowId ? "&rowId=" + rowId : "")
    );
}

export function viewLayoutRequest(windowType, type){
    return (dispatch) => axios.get(config.API_URL + '/documentView/layout?type=' + windowType + '&viewType=' + type);
}

export function browseViewRequest(viewId, page, pageLength, orderBy){
    return (dispatch) => {
        const firstRow = pageLength * (page - 1);
        return axios.get(config.API_URL + '/documentView/' + viewId + '?firstRow=' + firstRow + '&pageLength=' + pageLength + (orderBy ? '&orderBy=' + orderBy : ''))
    }
}

export function createViewRequest(windowType, viewType, pageLength, filters){
	// FIXME: ugly workaround to fix the case when for some reason "filters" is not an array!
	if (filters != null && !(filters instanceof Array)) filters = [ filters ];
	//console.log("filters="+JSON.stringify(filters));
	//console.log(new Error().stack);
	
    return (dispatch) => axios.put(config.API_URL + '/documentView/?type=' + windowType + '&viewType=' + viewType, filters);
}

export function addNotification(title, msg, time, notifType){
    return {
        type: types.ADD_NOTIFICATION,
        title: title,
        msg: msg,
        time: time,
        notifType: notifType
    }
}

export function deleteNotification(id){
    return {
        type: types.DELETE_NOTIFICATION,
        id: id
    }
}

export function updateUri(pathname, query, prop, value) {
    return (dispatch) => {
        let url = pathname;
        url += "?";

        // add new prop
        // or overwrite existing
        query[prop] = value;

        const queryKeys = Object.keys(query);

        for(let i = 0; i < queryKeys.length; i++){
            url += queryKeys[i] + "=" + query[queryKeys[i]] + (queryKeys.length - 1 !== i  ? "&": "");
        }

        dispatch(replace(url));
    }
}

export function filterDropdownRequest(type, filterId, parameterName) {
    return dispatch => axios.get(config.API_URL + '/documentView/filters/parameter/dropdown?type=' + type + '&filterId=' + filterId + '&parameterName=' + parameterName);
}

export function filterAutocompleteRequest(type, filterId, parameterName, query) {
    query = encodeURIComponent(query);
    return (dispatch) => axios.get(config.API_URL + '/documentView/filters/parameter/typeahead?type=' + type + '&filterId=' + filterId + '&parameterName=' + parameterName +'&query=' + query);
}
