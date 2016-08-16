import * as types from '../constants/ActionTypes'
import axios from 'axios';
import config from '../config';

export function unloadingChanged(value) {
    return {
        type: 'UNLOADING_CHANGED',
        value: value
    }
}
export function purchaserChanged(purchaser) {
    return {
        type: 'PURCHASER_CHANGED',
        purchaser: purchaser
    }
}
export function purchaserPropertyChanged(props) {
    return {
        type: 'PURCHASER_PROP_CHANGED',
        props: props
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

export function toggleProductSummary(value) {
    return {
        type: 'TOGGLE_PRODUCT_ORDER_SUMMARY',
        value: !!value
    }
}

export function autocomplete(query) {
    return {
        type: 'AUTOCOMPLETE',
        query: query
    }
}
export function autocompleteSuccess(results) {
    return {
        type: 'AUTOCOMPLETE_SUCCESS',
        results: results
    }
}
export function autocompleteRequest(windowType, propertyName, query, id = "NEW") {
    return (dispatch) => {
        axios.get(config.API_URL + '/window/typeahead?type=' + windowType + '&id='+id+'&field='+ propertyName +'&query=' + query)
            .then((response) => {
                dispatch(autocompleteSuccess(response.data));
            });
    }
}
export function dropdownRequest(windowType, propertyName, id = "NEW") {
    return (dispatch) => axios.get(config.API_URL + '/window/dropdown?type=' + windowType + '&id='+id+'&field='+ propertyName);
}

export function newSalesOrderSuccess(response) {
    return {
        type: 'NEW_SALES_ORDER_CREATED',
        response: response
    }
}

export function changeOrderStatus(id){
    return {
        type: 'CHANGE_ORDER_STATUS',
        value: id
    }
}
