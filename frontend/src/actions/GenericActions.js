import axios from 'axios';
import { getQueryString } from '../utils';

// IMPORTANT GENERIC METHODS TO HANDLE LAYOUTS, DATA, COMMITS
// @TODO: Everything should be moved to api

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

// TODO: This should be moved to the api
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
    `${config.API_URL}/${entity}${docType ? `/${docType}` : ''}${
      docId ? `/${docId}` : ''
    }${tabId ? `/${tabId}` : ''}${rowId ? `/${rowId}` : ''}${
      subentity ? `/${subentity}` : ''
    }${subentityId ? `/${subentityId}` : ''}/complete`
  );
}

// TODO: This should be moved to the api
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

// TODO: This should be moved to the api
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

// TODO: This should be moved to the api
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

// TODO: This should be moved to the api
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

  if (!entity) {
    return Promise.resolve({ data: { actions: [] } });
  }

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
