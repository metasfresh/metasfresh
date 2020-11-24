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
  doNotFetchIncludedTabs,
}) {
  let queryParams = getQueryString({
    advanced: fetchAdvancedFields,
    noTabs: doNotFetchIncludedTabs,
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

export async function quickActionsRequest(
  windowId,
  viewId,
  viewProfileId,
  selectedIds,
  childView,
  parentView
) {
  const requests = [];
  const query = getQueryString({
    viewProfileId,
    selectedIds,
    childViewId: childView.viewId,
    childViewSelectedIds: childView.viewSelectedIds,
    parentViewId: parentView.viewId,
    parentViewSelectedIds: parentView.viewSelectedIds,
  });

  if (parentView.viewId) {
    const parentQuery = getQueryString({
      viewProfileId,
      selectedIds: parentView.viewSelectedIds,
      childViewId: viewId,
      childViewSelectedIds: selectedIds,
    });

    const r1 = get(`
      ${config.API_URL}/documentView/${parentView.windowType}/${
      parentView.viewId
    }/quickActions${parentQuery ? `?${parentQuery}` : ''}`);
    requests.push(r1);
  } else if (childView.viewId) {
    const childQuery = getQueryString({
      viewProfileId,
      selectedIds: childView.selectedIds,
      parentViewId: viewId,
      parentViewSelectedIds: selectedIds,
    });

    const r2 = get(`
      ${config.API_URL}/documentView/${childView.windowType}/${
      childView.viewId
    }/quickActions${childQuery ? `?${childQuery}` : ''}`);
    requests.push(r2);
  }

  const r3 = get(`
    ${config.API_URL}/documentView/${windowId}/${viewId}/quickActions${
    query ? `?${query}` : ''
  }`);
  requests.push(r3);

  return await Promise.all(requests);
}
