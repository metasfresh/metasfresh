import { post, get, patch, delete as del } from 'axios';

import { getQueryString, createPatchRequestPayload } from '../utils';
import { prepareFilterForBackend } from '../utils/filterHelpers';

export function getData({
  entity,
  docType,
  docId,
  tabId,
  rowId,
  subentity,
  subentityId,
  orderBy,
  viewId,
  fetchAdvancedFields,
}) {
  let queryParams = getQueryString({
    advanced: fetchAdvancedFields,
    orderBy: orderBy,
  });

  return get(
    `${config.API_URL}/${entity}/${docType}${viewId ? `/${viewId}` : ''}${
      docId ? `/${docId}` : ''
    }${tabId ? `/${tabId}` : ''}${rowId ? `/${rowId}` : ''}${
      subentity ? `/${subentity}` : ''
    }${subentityId ? `/${subentityId}` : ''}/${
      queryParams ? `?${queryParams}` : ''
    }`
  );
}

export function getRowsData({ entity, docType, docId, tabId, rows }) {
  rows = rows || [];
  const ids = rows.join(',');

  return get(
    `${config.API_URL}/${entity}/${docType}/${docId}/${tabId}?ids=${ids}`
  );
}

export function getLayout(
  entity,
  docType,
  tabId,
  subentity = null,
  docId = null,
  isAdvanced,
  list,
  supportTree
) {
  return get(`${config.API_URL}/${entity}/${docType}${
    docId ? `/${docId}` : ''
  }${tabId ? `/${tabId}` : ''}${subentity ? `/${subentity}` : ''}/layout${
    isAdvanced ? '?advanced=true' : ''
  }${list ? `?viewType=${list}` : ''}${supportTree ? '&supportTree=true' : ''}
  `);
}

export function getViewLayout(windowId, viewType, viewProfileId = null) {
  return get(
    `${config.API_URL}/documentView/${windowId}/layout?viewType=${viewType}${
      viewProfileId ? `&profileId=${viewProfileId}` : ''
    }`
  );
}

export function getViewRowsByIds(windowId, viewId, docIds) {
  return get(
    `${config.API_URL}/documentView/${windowId}/${viewId}/byIds?ids=${docIds}`
  );
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

  return patch(
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
  ).then((rawResponse) => {
    // this is fixed on the FE because the BE is not consistent in sending the `documents` key with every PATCH request
    // this differs when patch is done within processes for example
    if (!rawResponse.data.documents) {
      rawResponse.data.documents = rawResponse.data;
    }

    return Promise.resolve(rawResponse);
  });
}

export function browseViewRequest({
  windowId,
  viewId,
  page,
  pageLength,
  orderBy,
}) {
  return get(
    `${config.API_URL}/documentView/${windowId}/${viewId}?firstRow=${
      pageLength * (page - 1)
    }&pageLength=${pageLength}${orderBy ? `&orderBy=${orderBy}` : ''}`
  );
}

export function createViewRequest({
  windowId,
  viewType,
  filters,
  referenceId = null,
  refDocType = null,
  refDocumentId = null,
  refTabId = null,
  refRowIds = null,
}) {
  let referencing = null;

  if (refDocType && refDocumentId) {
    referencing = {
      documentType: refDocType,
      documentId: refDocumentId,
      referenceId: referenceId,
    };

    if (refTabId && refRowIds) {
      referencing.tabId = refTabId;
      referencing.rowIds = refRowIds;
    }
  }

  return post(`${config.API_URL}/documentView/${windowId}`, {
    documentType: windowId,
    viewType,
    referencing,
    filters,
  });
}

export function filterViewRequest(windowId, viewId, filters) {
  filters.map((filter, idx) => {
    filter = prepareFilterForBackend(filter);
    filters[idx] = filter;
  });

  return post(`${config.API_URL}/documentView/${windowId}/${viewId}/filter`, {
    filters,
  });
}

export function locationSearchRequest({ windowId, viewId }) {
  return get(
    `${config.API_URL}/documentView/${windowId}/${viewId}/geoLocations?limit=0`
  );
}

export function locationConfigRequest() {
  return get(`${config.API_URL}/geolocation/config`);
}

export function headerPropertiesRequest({ windowId, viewId }) {
  return get(`
    ${config.API_URL}/documentView/${windowId}/${viewId}/headerProperties`);
}

export function deleteViewRequest(windowId, viewId, action) {
  return del(
    `${config.API_URL}/documentView/${windowId}/${viewId}${
      action ? `?action=${action}` : ''
    }`
  );
}

export function deleteStaticFilter(windowId, viewId, filterId) {
  return del(
    `${config.API_URL}/documentView/${windowId}/${viewId}/staticFilter/${filterId}`
  );
}

/*
 * @method quickActionsRequest
 * @summary Do a request for quick actions
 *
 * @param {string} viewId
 * @param {string} viewProfileId
 * @param {array} selectedIds
 * @param {object} childView
 * @param {object} parentView
 */
export async function quickActionsRequest({
  windowId,
  viewId,
  viewProfileId,
  selectedIds,
  childView,
  parentView,
}) {
  let query = null;

  if (childView && childView.viewId) {
    query = getQueryString({
      viewProfileId,
      selectedIds,
      childViewId: childView.viewId,
      childViewSelectedIds: childView.selected,
    });
  } else if (parentView && parentView.viewId) {
    query = getQueryString({
      viewProfileId,
      selectedIds,
      parentViewId: parentView.viewId,
      parentViewSelectedIds: parentView.selected,
    });
  } else {
    query = getQueryString({
      viewProfileId,
      selectedIds,
    });
  }

  return get(`
    ${config.API_URL}/documentView/${windowId}/${viewId}/quickActions${
    query ? `?${query}` : ''
  }`);
}

/*
 * @method advSearchRequest
 * @summary Does a POST to communicate to the BE what was selected
 *
 * @param {string} windowId
 * @param {string} documentId
 * @param {string} fieldName
 * @param {string} advSearchWindowId
 * @param {string} selectedId
 */
export function advSearchRequest({
  windowId,
  documentId,
  fieldName,
  advSearchWindowId,
  selectedId,
}) {
  return post(
    `${config.API_URL}/window/${windowId}/${documentId}/field/${fieldName}/advSearchResult`,
    {
      advSearchWindowId,
      selectedId,
    }
  );
}

/**
 * @method getViewAttributesLayoutRequest
 * @summary gets layout for the selection attributes view
 *
 * @param {number} windowId
 * @param {string} viewId
 * @param {string} rowId
 */
export function getViewAttributesLayoutRequest(windowId, viewId, rowId) {
  return get(
    `${config.API_URL}/documentView/${windowId}/${viewId}/${rowId}/attributes/layout`
  );
}

/**
 * @method getViewAttributesRequest
 * @summary gets field data for the selection attributes view
 *
 * @param {number} windowId
 * @param {string} viewId
 * @param {string} rowId
 */
export function getViewAttributesRequest(windowId, viewId, rowId) {
  return get(
    `${config.API_URL}/documentView/${windowId}/${viewId}/${rowId}/attributes`
  );
}

/**
 * @method patchViewAttributesRequest
 * @summary patches selection attributes field
 *
 * @param {number} windowId
 * @param {string} viewId
 * @param {string} rowId
 * @param {string} property - field name
 * @param {any} value - field value
 */
export function patchViewAttributesRequest(
  windowId,
  viewId,
  rowId,
  property,
  value
) {
  const payload = createPatchRequestPayload(property, value);

  return patch(
    `${config.API_URL}/documentView/${windowId}/${viewId}/${rowId}/attributes`,
    payload
  );
}

/**
 * @method getViewAttributeDropdown
 * @summary get data for a dropdown field in selection attributes
 *
 * @param {number} windowId
 * @param {string} viewId
 * @param {string} rowId
 * @param {attribute} - field name
 */
export function getViewAttributeDropdown(windowId, viewId, rowId, attribute) {
  return get(
    `${config.API_URL}/documentView/${windowId}/${viewId}/${rowId}/attributes/attribute/${attribute}/dropdown`
  );
}

/**
 * @method getViewAttributeTypeahead
 * @summary
 *
 * @param {number} windowId
 * @param {string} viewId
 * @param {string} rowId
 * @param {string} attribute - field name
 * @param {string} query - search phrase
 */
export function getViewAttributeTypeahead(
  windowId,
  viewId,
  rowId,
  attribute,
  query
) {
  return get(
    `${config.API_URL}/documentView/
      ${windowId}/
      ${viewId}/
      ${rowId}/attributes/attribute/
      ${attribute}/typeahead?query=
      ${encodeURIComponent(query)}`
  );
}
