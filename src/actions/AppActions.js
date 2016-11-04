import * as types from '../constants/ActionTypes'
import axios from 'axios';
import config from '../config';
import {replace} from 'react-router-redux';

export function loginSuccess() {
	return () => {
		/** global: localStorage */
		localStorage.setItem('isLogged', true);
	}
}
export function logoutSuccess() {
	return () => {
		/** global: localStorage */
		localStorage.removeItem('isLogged');
	}
}

export function autocompleteRequest(windowType, propertyName, query, id = "NEW", tabId, rowId) {
	return () => {
		query = encodeURIComponent(query);
		return axios.get(
            config.API_URL +
            '/window/typeahead?type=' + windowType +
            '&id='+id+'&field='+ propertyName +
            '&query=' + query +
    		(tabId ? "&tabid=" + tabId : "") +
    		(rowId ? "&rowId=" + rowId : "")
        );
	}
}

export function dropdownRequest(windowType, propertyName, id, tabId, rowId) {
	return () => axios.get(
		config.API_URL +
		'/window/dropdown?type=' + windowType +
		'&id=' + id +
		'&field=' + propertyName+
		(tabId ? "&tabid=" + tabId : "") +
		(rowId ? "&rowId=" + rowId : "")
	);
}

export function viewLayoutRequest(windowType, type){
	return () => axios.get(config.API_URL + '/documentView/layout?type=' + windowType + '&viewType=' + type);
}

export function browseViewRequest(viewId, page, pageLength, orderBy){
	return () => {
		const firstRow = pageLength * (page - 1);
		return axios.get(config.API_URL + '/documentView/' + viewId + '?firstRow=' + firstRow + '&pageLength=' + pageLength + (orderBy ? '&orderBy=' + orderBy : ''))
	}
}

export function createViewRequest(windowType, viewType, pageLength, filters){
	return () => axios.put(config.API_URL + '/documentView/?type=' + windowType + '&viewType=' + viewType, filters);
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

export function loginRequest(login, passwd){
	return () => axios.post(config.API_URL + '/login/authenticate?username=' + login + '&password=' + passwd);
}

export function localLoginRequest(){
	return () => axios.get(config.API_URL + '/login/isLoggedIn');
}

export function loginCompletionRequest(role){
	return () => axios.post(config.API_URL + '/login/loginComplete', role);
}

export function logoutRequest(){
	return () => axios.get(config.API_URL + '/login/logout');
}

export function filterDropdownRequest(type, filterId, parameterName) {
	return () => axios.get(config.API_URL + '/documentView/filters/parameter/dropdown?type=' + type + '&filterId=' + filterId + '&parameterName=' + parameterName);
}

export function filterAutocompleteRequest(type, filterId, parameterName, query) {
	return () => {
		query = encodeURIComponent(query);
		return axios.get(config.API_URL + '/documentView/filters/parameter/typeahead?type=' + type + '&filterId=' + filterId + '&parameterName=' + parameterName +'&query=' + query);
	}
}
