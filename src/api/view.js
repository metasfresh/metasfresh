import { post, get, delete as del } from 'axios';
import Moment from 'moment';
import { getQueryString } from '../utils';
import { DATE_FORMAT } from '../constants/Constants';

export function getViewLayout(windowId, viewType, viewProfileId = null) {
  return get(
    `${config.API_URL}/documentView/${windowId}/layout?viewType=${viewType}${
      viewProfileId ? `&profileId= + ${viewProfileId}` : ''
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

export function deleteView(windowId, viewId) {
  return del(`${config.API_URL}/documentView/${windowId}/${viewId}`);
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
  filters.map(filter => {
    filter.parameters &&
      filter.parameters.map((param, index) => {
        if (param.widgetType === 'Date' && param.value) {
          filter.parameters[index].value = Moment(param.value).format(
            DATE_FORMAT
          );
        }
      });
  });

  return post(`${config.API_URL}/documentView/${windowId}/${viewId}/filter`, {
    filters: filters,
  });
}

export function deleteStaticFilter(windowId, viewId, filterId) {
  return del(
    `${
      config.API_URL
    }/documentView/${windowId}/${viewId}/staticFilter/${filterId}`
  );
}

export function quickActionsRequest(
  windowId,
  viewId,
  selectedIds,
  childView,
  parentView
) {
  const query = getQueryString({
    selectedIds,
    childViewId: childView.viewId,
    childViewSelectedIds: childView.viewSelectedIds,
    parentViewId: parentView.viewId,
    parentViewSelectedIds: parentView.viewSelectedIds,
  });

  return get(
    config.API_URL +
      '/documentView/' +
      windowId +
      '/' +
      viewId +
      '/quickActions' +
      (query ? '?' + query : '')
  );
}
