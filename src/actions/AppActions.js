import * as types from '../constants/ActionTypes'
import axios from 'axios';
import config from '../config';
import {replace} from 'react-router-redux';

export function loginSuccess() {
	return dispatch => {
		/** global: localStorage */
		localStorage.setItem('isLogged', true);

        dispatch(getNotifications()).then(response => {
            dispatch(getNotificationsSuccess(response.data));
        });
	}
}

export function logoutSuccess() {
	return () => {
		/** global: localStorage */
		localStorage.removeItem('isLogged');
	}
}

export function getUserLang() {
    return () => axios.get(config.API_URL + '/userSession/language');
}

export function getAvailableLang() {
    return () => axios.get(config.API_URL + '/login/availableLanguages');
}

export function autocompleteRequest(windowType, propertyName, query, id = "NEW", tabId, rowId, entity) {
    if (entity){
        return () => axios.get(
    		config.API_URL +
    		'/process/instance/' + id +
            '/parameters/' + propertyName +
            '/typeahead' +
            '?query=' + query
    	);
    }else{
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
}

export function dropdownRequest(windowType, propertyName, id, tabId, rowId, entity) {
    if (entity){
        return () => axios.get(
    		config.API_URL +
    		'/process/instance/' + id +
            '/parameters/' + propertyName +
            '/dropdown'
    	);
    }else{
    	return () => axios.get(
    		config.API_URL +
    		'/window/dropdown?type=' + windowType +
    		'&id=' + id +
    		'&field=' + propertyName+
    		(tabId ? "&tabid=" + tabId : "") +
    		(rowId ? "&rowId=" + rowId : "")
    	);
    }
}

export function getUserDashboardWidgets() {
    return () => axios.get(config.API_URL + '/dashboard/kpis');
}

export function setUserDashboardWidgets(payload) {
    return () => axios.patch(config.API_URL + '/dashboard/kpis', payload);
}

export function getUserDashboardIndicators() {
    return () => axios.get(config.API_URL + '/dashboard/targetIndicators');
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

export function getNotifications() {
    return () => axios.get(config.API_URL + '/notifications/all?limit=20');
}

export function getNotificationsSuccess(notifications) {
    return {
        type: types.GET_NOTIFICATIONS_SUCCESS,
        payload: notifications
    }
}
