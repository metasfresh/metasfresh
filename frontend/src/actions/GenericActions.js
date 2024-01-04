import axios from 'axios';
import { getQueryString } from '../utils';
import {
  allActionsRequest,
  getViewFieldDropdown,
  getViewFilterParameterDropdown,
  getViewFilterParameterTypeahead,
} from '../api/view';

// IMPORTANT GENERIC METHODS TO HANDLE LAYOUTS, DATA, COMMITS
// @TODO: Everything should be moved to api

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
  // console.log('autocompleteRequest', {
  //   attribute,
  //   docId,
  //   docType,
  //   entity,
  //   propertyName,
  //   query,
  //   rowId,
  //   subentity,
  //   subentityId,
  //   tabId,
  //   viewId,
  // });

  // NOTE: following cases are already handled elsewhere:
  // * view attributes

  if (entity === 'documentView' && subentity === 'filter') {
    return getViewFilterParameterTypeahead({
      windowId: docType,
      viewId: viewId ?? docId, // NOTE in case of Labels widget, we really get the viewId. In the other cases we get the viewId as "docId".
      filterId: subentityId,
      parameterName: propertyName,
      query,
    });
  } else {
    return axios.get(`${config.API_URL}/${entity}${
      docType ? `/${docType}` : ''
    }${viewId ? `/${viewId}` : ''}${docId ? `/${docId}` : ''}${
      tabId ? `/${tabId}` : ''
    }${rowId ? `/${rowId}` : ''}${subentity ? `/${subentity}` : ''}${
      subentityId ? `/${subentityId}` : ''
    }${
      attribute ? '/attribute/' : '/field/'
    }${propertyName}/typeahead?query=${encodeURIComponent(query)}
  `);
  }
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
  if (entity === 'documentView') {
    if (subentity === 'filter') {
      return getViewFilterParameterDropdown({
        windowId: docType,
        viewId,
        filterId: subentityId,
        parameterName: propertyName,
      });
    } else {
      return getViewFieldDropdown({
        windowId: docType,
        viewId,
        rowId,
        fieldName: propertyName,
      });
    }
  } else {
    return axios.get(`
    ${config.API_URL}/${entity}${docType ? `/${docType}` : ''}${
      viewId ? `/${viewId}` : ''
    }${docId ? `/${docId}` : ''}${tabId ? `/${tabId}` : ''}${
      rowId ? `/${rowId}` : ''
    }${subentity ? `/${subentity}` : ''}${
      subentityId ? `/${subentityId}` : ''
    }${attribute ? '/attribute/' : '/field/'}${propertyName}/dropdown`);
  }
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
    ${config.API_URL}/${entity}/${windowId}/${viewId}/${rowId}/edit/${fieldName}/dropdown`);
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

/** Fetches actions to be displayed in top "burger" menu. */
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
  //
  // Dashboard actions
  if (!entity) {
    // no actions
    return Promise.resolve({ data: { actions: [] } });
  }
  //
  // View Actions:
  else if (entity === 'documentView') {
    return allActionsRequest({
      windowId: type,
      viewId: id,
      selectedIds,
      childViewId,
      childViewSelectedIds,
    });
  }
  //
  // Other actions fetching cases:
  else {
    const query = getQueryString({
      disabled: true,
      selectedIds,
      selectedTabId,
      selectedRowIds,
      childViewId,
      childViewSelectedIds,
    });

    return axios.get(
      `${config.API_URL}/${entity}/${type}/${id}/actions${
        query ? '?' + query : ''
      }`
    );
  }
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

export function processNewRecord(entity, docType, docId) {
  return axios.get(
    `${config.API_URL}/${entity}/${docType}/${docId}/processNewRecord`
  );
}

export function getRequest() {
  const url = config.API_URL + '/' + Array.from(arguments).join('/');

  return axios.get(url);
}
