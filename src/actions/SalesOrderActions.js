import * as types from '../constants/ActionTypes'
import axios from 'axios';
import config from '../config';

export function unloadingChanged(value) {
    return {
        type: 'UNLOADING_CHANGED',
        value: value
    }
}
export function invoiceChanged(value) {
    return {
        type: 'INVOICE_CHANGED',
        value: value
    }
}


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


export function showSubHeader() {
    return {
        type: 'SHOW_SUBHEADER'
    }
}
export function hideSubHeader() {
    return {
        type: 'HIDE_SUBHEADER'
    }
}

export function toggleOrderList(value) {
    return {
        type: 'TOGGLE_ORDER_LIST',
        value: !!value
    }
}

export function autocomplete(query) {
    return {
        type: 'AUTOCOMPLETE',
        query: query
    }
}

export function createWindow(){
    return (dispatch) => {
        axios.get(config.API_URL + '/windows/openWindow/143')
        .then((response) => {
            return axios.get(config.API_URL + '/windows/getViewRootPropertyDescriptor/' + response.data);
        }).then((response) => {
            console.log(response.data);
        });
    }
}
