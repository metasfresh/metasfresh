import axios from 'axios';

// IMPORTANT GENERIC METHODS TO HANDLE LAYOUTS, DATA, COMMITS

export function initLayout(
    entity, docType, tabId, subentity = null, docId = null, isAdvanced, list, supportTree
) {
    return () => axios.get(
        config.API_URL +
        '/' + entity + '/' + docType +
        (docId ? "/" + docId : "") +
        (tabId ? "/" + tabId : "") +
        (subentity ? "/" + subentity : "") +
        '/layout' +
        (isAdvanced ? "?advanced=true" : "") +
        (list ? "?viewType=" + list : "") +
        (supportTree ? "&supportTree=true" : "")
    );
}

export function getData(
    entity, docType, docId, tabId, rowId, subentity, subentityId, isAdvanced
) {
    return () => axios.get(
        config.API_URL +
        '/' + entity + '/' +
        docType + '/' +
        (docId ? "/" + docId : "") +
        (tabId ? "/" + tabId : "") +
        (rowId ? "/" + rowId : "") +
        (subentity ? "/" + subentity : "") +
        (subentityId ? "/" + subentityId : "") +
        (isAdvanced ? "?advanced=true" : "")
    );
}

export function createInstance(entity, docType, docId, tabId, subentity) {
    return () => axios.post(
        config.API_URL +
        '/' + entity + '/' + docType + '/' + docId +
        (tabId ? "/" + tabId : "") +
        (subentity ? "/" + subentity : "")
    );
}

export function patchRequest(
    entity, docType, docId = "NEW", tabId, rowId, property, value, subentity,
    subentityId, isAdvanced
) {
    let payload = {};

    if (docId === "NEW") {
        payload = [];
    } else {
        if (property && value !== undefined) {
            payload = [{
                'op': 'replace',
                'path': property,
                'value': value
            }];
        } else {
            payload = [];
        }
    }

    return () => axios.patch(
        config.API_URL +
        '/' + entity +
        (docType ? "/" + docType : "") +
        (docId ? "/" + docId : "") +
        (tabId ? "/" + tabId : "") +
        (rowId ? "/" + rowId : "") +
        (subentity ? "/" + subentity : "") +
        (subentityId ? "/" + subentityId : "") +
        (isAdvanced ? "?advanced=true" : ""), payload);
}

export function completeRequest(
    entity, docType, docId, tabId, rowId, subentity, subentityId
) {
    return () => axios.post(
        config.API_URL +
        '/' + entity + '/' +
        (docType ? "/" + docType : "") +
        (docId ? "/" + docId : "") +
        (tabId ? "/" + tabId : "") +
        (rowId ? "/" + rowId : "") +
        (subentity ? "/" + subentity : "") +
        (subentityId ? "/" + subentityId : "") +
        '/complete'
    );
}

export function autocompleteRequest(docType, propertyName, query, docId, tabId, rowId, entity, subentity, subentityId) {
    return () => axios.get(
        config.API_URL +
        '/' + entity +
        (docType ? "/" + docType : "") +
        (docId ? "/" + docId : "") +
        (tabId ? "/" + tabId : "") +
        (rowId ? "/" + rowId : "") +
        (subentity ? "/" + subentity : "") +
        (subentityId ? "/" + subentityId : "") +
        '/attribute/' + propertyName +
        '/typeahead' + '?query=' + encodeURIComponent(query)
    );
}

export function dropdownRequest(docType, propertyName, docId, tabId, rowId, entity, subentity, subentityId) {
    return () => axios.get(
        config.API_URL +
        '/' + entity +
        (docType ? "/" + docType : "") +
        (docId ? "/" + docId : "") +
        (tabId ? "/" + tabId : "") +
        (rowId ? "/" + rowId : "") +
        (subentity ? "/" + subentity : "") +
        (subentityId ? "/" + subentityId : "") +
        '/attribute/' + propertyName +
        '/dropdown'
    );
}

export function deleteRequest(entity, docType, docId, tabId, ids) {
    return () => axios.delete(
        config.API_URL +
        '/' + entity +
        (docType ? "/" + docType : "") +
        (docId ? "/" + docId : "") +
        (tabId ? "/" + tabId : "") +
        (ids ? "?ids=" + ids : "")
    );
}

export function actionsRequest(entity, type, id, selected){
    let query = "";
    for (let item of selected) {
       query+=","+item;
    }
    query = query.substring(1);

    return () => axios.get(
        config.API_URL + '/' +
        entity + '/' +
        type + '/' +
        id +
        '/actions'+
        (selected.length > 0 && entity=="documentView" ? "?selectedIds="+ query :"")
    );
}

export function referencesRequest(entity, type, id){
    return () => axios.get(
        config.API_URL + '/' +
        entity + '/' +
        type + '/' +
        id +
        '/references'
    );
}

export function printRequest(entity, docType, docId, name) {
    return () => {
        const url =
            config.API_URL + '/' +
            entity + '/' +
            docType + '/' +
            docId +
            '/print/' + name;

        window.open(url, "_blank");
    }
}
