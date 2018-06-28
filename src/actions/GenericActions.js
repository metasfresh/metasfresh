import axios from 'axios';
import { getQueryString } from '../utils';

// IMPORTANT GENERIC METHODS TO HANDLE LAYOUTS, DATA, COMMITS

export function getData(
  entity,
  docType,
  docId,
  tabId,
  rowId,
  subentity,
  subentityId,
  isAdvanced,
  orderBy,
  viewId
) {
  return axios.get(
    config.API_URL +
      '/' +
      entity +
      '/' +
      docType +
      (viewId ? '/' + viewId : '') +
      (docId ? '/' + docId : '') +
      (tabId ? '/' + tabId : '') +
      (rowId ? '/' + rowId : '') +
      (subentity ? '/' + subentity : '') +
      (subentityId ? '/' + subentityId : '') +
      (isAdvanced ? '?advanced=true' : '') +
      (orderBy ? '?orderBy=' + orderBy : '')
  );
}

export function createInstance(entity, docType, docId, tabId, subentity) {
  return axios.post(
    config.API_URL +
      '/' +
      entity +
      '/' +
      docType +
      '/' +
      docId +
      (tabId ? '/' + tabId : '') +
      (subentity ? '/' + subentity : '')
  );
}

export function createPatchRequestPayload(property, value) {
  if (Array.isArray(property) && Array.isArray(value)) {
    return property.map((item, index) => ({
      op: 'replace',
      path: item,
      value: value[index],
    }));
  } else if (Array.isArray(property) && value !== undefined) {
    return property.map(item => ({
      op: 'replace',
      path: item.field,
      value,
    }));
  } else if (property && value !== undefined) {
    return [
      {
        op: 'replace',
        path: property,
        value,
      },
    ];
  } else {
    // never return undefined; backend does not support it
    return [];
  }
}

export function patchRequest({
  // HOTFIX: before refactoring all calls explicity set docId to `null`
  // instead of `undefined` so default value 'NEW' was never used!
  docId,

  docType,
  entity,
  isAdvanced,
  property,
  rowId,
  subentity,
  subentityId,
  tabId,
  value,
  viewId,
  isEdit,
}) {
  let payload =
    docId !== 'NEW' ? createPatchRequestPayload(property, value) : [];

  return axios.patch(
    config.API_URL +
      '/' +
      entity +
      (docType ? '/' + docType : '') +
      (viewId ? '/' + viewId : '') +
      (docId ? '/' + docId : '') +
      (tabId ? '/' + tabId : '') +
      (rowId ? '/' + rowId : '') +
      (subentity ? '/' + subentity : '') +
      (subentityId ? '/' + subentityId : '') +
      (isAdvanced ? '?advanced=true' : '') +
      (isEdit ? '/edit' : ''),
    payload
  );
}

export function getDataByIds(entity, docType, viewId, docIds) {
  return axios.get(
    config.API_URL +
      '/' +
      entity +
      (docType ? '/' + docType : '') +
      (viewId ? '/' + viewId : '') +
      '/byIds' +
      '?ids=' +
      docIds
  );
}

export function completeRequest(
  entity,
  docType,
  docId,
  tabId,
  rowId,
  subentity,
  subentityId
) {
  return axios.post(
    config.API_URL +
      '/' +
      entity +
      '/' +
      (docType ? '/' + docType : '') +
      (docId ? '/' + docId : '') +
      (tabId ? '/' + tabId : '') +
      (rowId ? '/' + rowId : '') +
      (subentity ? '/' + subentity : '') +
      (subentityId ? '/' + subentityId : '') +
      '/complete'
  );
}

export function autocompleteRequest({
  attribute,
  docId,
  docType,
  entity,
  propertyName,
  query,
  rowId,
  subentity,
  subentityId,
  tabId,
  viewId,
}) {
  return axios.get(`${config.API_URL}/${entity}${docType ? `/${docType}` : ''}${
    viewId ? `/${viewId}` : ''
  }${docId ? `/${docId}` : ''}${tabId ? `/${tabId}` : ''}${
    rowId ? `/${rowId}` : ''
  }${subentity ? `/${subentity}` : ''}${subentityId ? `/${subentityId}` : ''}${
    attribute ? '/attribute/' : '/field/'
  }${propertyName}/typeahead?query=${encodeURIComponent(query)}
  `);
}

export function autocompleteModalRequest({
  docId,
  docType,
  entity,
  propertyName,
  query,
  rowId,
  tabId,
  viewId,
}) {
  return axios.get(`${config.API_URL}/${entity}${docType ? `/${docType}` : ''}${
    viewId ? `/${viewId}` : ''
  }${docId ? `/${docId}` : ''}${tabId ? `/${tabId}` : ''}${
    rowId ? `/${rowId}` : ''
  }/edit/${propertyName}/typeahead?query=${encodeURIComponent(query)}
  `);
}

export function dropdownRequest({
  attribute,
  docId,
  docType, // windowId
  entity,
  propertyName,
  rowId,
  subentity,
  subentityId,
  tabId,
  viewId,
}) {
  return axios.get(`
    ${config.API_URL}/${entity}${docType ? `/${docType}` : ''}${
    viewId ? `/${viewId}` : ''
  }${docId ? `/${docId}` : ''}${tabId ? `/${tabId}` : ''}${
    rowId ? `/${rowId}` : ''
  }${subentity ? `/${subentity}` : ''}${subentityId ? `/${subentityId}` : ''}${
    attribute ? '/attribute/' : '/field/'
  }${propertyName}/dropdown`);
}

export function dropdownModalRequest({
  windowId,
  entity,
  fieldName,
  rowId,
  viewId,
}) {
  return axios.get(`
    ${
      config.API_URL
    }/${entity}/${windowId}/${viewId}/${rowId}/edit/${fieldName}/dropdown`);
}

export function deleteRequest(
  entity,
  docType,
  docId,
  tabId,
  ids,
  subentity,
  subentityId
) {
  return axios.delete(
    config.API_URL +
      '/' +
      entity +
      (docType ? '/' + docType : '') +
      (docId ? '/' + docId : '') +
      (tabId ? '/' + tabId : '') +
      (subentity ? '/' + subentity : '') +
      (subentityId ? '/' + subentityId : '') +
      (ids ? '?ids=' + ids : '')
  );
}

export function duplicateRequest(entity, docType, docId) {
  return axios.post(
    config.API_URL +
      '/' +
      entity +
      (docType ? '/' + docType : '') +
      (docId ? '/' + docId : '') +
      '/duplicate'
  );
}

export function actionsRequest({
  entity,
  type,
  id,
  selectedIds,
  selectedTabId,
  selectedRowIds,
  childViewId,
  childViewSelectedIds,
}) {
  const query = getQueryString({
    disabled: true,
    selectedIds,
    selectedTabId,
    selectedRowIds,
    childViewId,
    childViewSelectedIds,
  });

  return axios.get(
    config.API_URL +
      '/' +
      entity +
      '/' +
      type +
      '/' +
      id +
      '/actions' +
      (query ? '?' + query : '')
  );
}

export function rowActionsRequest({ windowId, documentId, tabId, rowId }) {
  return axios.get(
    config.API_URL +
      '/window/' +
      windowId +
      '/' +
      documentId +
      '/' +
      tabId +
      '/' +
      rowId +
      '/actions'
  );
}

export function referencesRequest(entity, type, docId, tabId, rowId) {
  return axios.get(
    config.API_URL +
      '/' +
      entity +
      '/' +
      type +
      '/' +
      docId +
      (tabId ? '/' + tabId : '') +
      (rowId ? '/' + rowId : '') +
      '/references'
  );
}

export function attachmentsRequest(entity, docType, docId) {
  return axios.get(
    `${config.API_URL}/${entity}/${docType}/${docId}/attachments`
  );
}

export function processNewRecord(entity, docType, docId) {
  return axios.get(
    `${config.API_URL}/${entity}/${docType}/${docId}/processNewRecord`
  );
}

export function openFile(entity, docType, docId, fileType, fileId) {
  const url = `${
    config.API_URL
  }/${entity}/${docType}/${docId}/${fileType}/${fileId}`;

  window.open(url, '_blank');
}

export function getRequest() {
  const url = config.API_URL + '/' + Array.from(arguments).join('/');

  return axios.get(url);
}
