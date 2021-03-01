import { post, get, patch, delete as del } from 'axios';
import {
  getQueryString,
  cleanupFilter,
  createPatchRequestPayload,
} from '../utils';

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
  );
}

export function browseViewRequest({
  windowId,
  viewId,
  page,
  pageLength,
  orderBy,
}) {
  return get(
    `${
      config.API_URL
    }/documentView/${windowId}/${viewId}?firstRow=${pageLength *
      (page - 1)}&pageLength=${pageLength}${
      orderBy ? `&orderBy=${orderBy}` : ''
    }`
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
    filter = cleanupFilter(filter);
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
    `${
      config.API_URL
    }/documentView/${windowId}/${viewId}/staticFilter/${filterId}`
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
