import * as types from '../constants/ActionTypes'
import axios from 'axios';
import config from '../config';


export function saveProductProperty(id, property, value) {
    return {
        type: 'SAVE_PRODUCT_PROPERTY',
        id: id,
        property: property,
        value: value
    }
}
export function changeProductProperty(value) {
    return {
        type: 'CHANGE_PRODUCT_PROPERTY',
        value: value
    }
}
export function editProductProperty(property) {
    return {
        type: 'EDIT_PRODUCT_PROPERTY',
        property: property
    }
}


export function selectProduct(id) {
    return {
        type: 'SELECT_PRODUCT',
        product: id
    }
}
export function selectOneProduct(id) {
    return {
        type: 'SELECT_ONE_PRODUCT',
        product: id
    }
}
export function selectRangeProduct(ids) {
    return {
        type: 'SELECT_RANGE_PRODUCT',
        ids: ids
    }
}
export function deselectProduct(id) {
    return {
        type: 'DESELECT_PRODUCT',
        product: id
    }
}
export function deselectAllProducts() {
    return {
        type: 'DESELECT_ALL_PRODUCTS'
    }
}

export function deleteSelectedProducts(ids) {
    return {
        type: 'DELETE_SELECTED_PRODUCTS',
        ids: ids
    }
}

export function autocompleteRequest(windowType, propertyName, query, id = "NEW") {
    return (dispatch) => axios.get(config.API_URL + '/window/typeahead?type=' + windowType + '&id='+id+'&field='+ propertyName +'&query=' + query);
}
export function dropdownRequest(windowType, propertyName, id = "NEW") {
    return (dispatch) => axios.get(config.API_URL + '/window/dropdown?type=' + windowType + '&id='+id+'&field='+ propertyName);
}

export function changeOrderStatus(id){
    return {
        type: 'CHANGE_ORDER_STATUS',
        value: id
    }
}
