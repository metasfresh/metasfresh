import * as types from '../constants/ActionTypes'
import axios from 'axios';
import config from '../config';

export function autocompleteRequest(windowType, propertyName, query, id = "NEW") {
    return (dispatch) => axios.get(config.API_URL + '/window/typeahead?type=' + windowType + '&id='+id+'&field='+ propertyName +'&query=' + query);
}
export function dropdownRequest(windowType, propertyName, id = "NEW", tabId, rowId) {
    return (dispatch) => axios.get(
        config.API_URL +
        '/window/dropdown?type=' + windowType +
        '&id=' + id +
        '&field=' + propertyName+
        (tabId ? "&tabid=" + tabId : "") +
        (rowId ? "&rowId=" + rowId : "")
    );
}
