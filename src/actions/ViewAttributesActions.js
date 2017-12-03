import axios from 'axios';

//
// Handles view attributes (the panel which is displayed on the right side of a view, if view supports attributes)
// Endpoints: /rest/api/documentView/{windowId}/{viewId}/{rowId}/attributes/**
//

export function getViewAttributesLayout(windowId, viewId, rowId) {
    return axios.get(
        config.API_URL +
        '/documentView'+
        '/' + windowId +
        '/' + viewId +
        '/' + rowId +
        '/attributes/layout'
    );
}

export function getViewAttributeDropdown(windowId, viewId, rowId, attribute) {
    return axios.get(
        config.API_URL +
        '/documentView'+
        '/' + windowId +
        '/' + viewId +
        '/' + rowId +
        '/attributes/attribute/' + attribute +
        '/dropdown'
    );
}

/**
 *
 * @param {*} windowId
 * @param {*} viewId
 * @param {*} rowId
 */
export function getViewAttributes (
    windowId, viewId, rowId
) {
    return axios.get(
        config.API_URL +
        '/documentView'+
        '/' + windowId +
        '/' + viewId +
        '/' + rowId +
        '/attributes'
    );
}

function createPatchRequestPayload(property, value) {
    let payload = [];
    if (Array.isArray(property) && value !== undefined) {
        payload = property.map(item => ({
            op: 'replace',
            path: item.field,
            value
        }));
    } else if (property && value !== undefined) {
        payload = [{
            op: 'replace',
            path: property,
            value
        }];
    }

    return payload;
}

export function patchViewAttributes(
    windowId,
    viewId,
    rowId,
    property,
    value
) {
    const payload = createPatchRequestPayload(property, value);

    return axios.patch(
        config.API_URL +
        '/documentView'+
        '/' + windowId +
        '/' + viewId +
        '/' + rowId +
        '/attributes', payload);
}
