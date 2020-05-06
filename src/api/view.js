import { post, get, delete as del } from 'axios';
import { getQueryString, cleanupFilter } from '../utils';

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

export function locationSearchRequest({ windowId, viewId }) {
  return get(
    `${config.API_URL}/documentView/${windowId}/${viewId}/geoLocations?limit=0`
  );
}

export function locationConfigRequest() {
  return get(`${config.API_URL}/geolocation/config`);
}

export function deleteView(windowId, viewId, action) {
  return del(
    `${config.API_URL}/documentView/${windowId}/${viewId}${
      action ? `?action=${action}` : ''
    }`
  );
}

export function createViewRequest({
  windowId,
  viewType,
  filters,
  refDocType = null,
  refDocId = null,
  refTabId = null,
  refRowIds = null,
}) {
  let referencing = null;

  if (refDocType && refDocId) {
    referencing = {
      documentType: refDocType,
      documentId: refDocId,
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
