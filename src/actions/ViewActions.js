import axios from "axios";
import queryString from "query-string";

const getQueryString = query =>
  queryString.stringify(
    Object.keys(query).reduce((parameters, key) => {
      const value = query[key];

      if (Array.isArray(value) && value.length > 0) {
        parameters[key] = value.join(",");
      } else {
        parameters[key] = value;
      }

      return parameters;
    }, {})
  );

export function getViewLayout(windowId, viewType, viewProfileId = null) {
  return axios.get(
    config.API_URL +
      "/documentView/" +
      windowId +
      "/layout" +
      "?viewType=" +
      viewType +
      (viewProfileId ? "&profileId=" + viewProfileId : "")
  );
}

export function getViewRowsByIds(windowId, viewId, docIds) {
  return axios.get(
    config.API_URL +
      "/documentView/" +
      windowId +
      "/" +
      viewId +
      "/byIds" +
      "?ids=" +
      docIds
  );
}

export function browseViewRequest({
  windowId,
  viewId,
  page,
  pageLength,
  orderBy
}) {
  return axios.get(
    config.API_URL +
      "/documentView/" +
      windowId +
      "/" +
      viewId +
      "?firstRow=" +
      pageLength * (page - 1) +
      "&pageLength=" +
      pageLength +
      (orderBy ? "&orderBy=" + orderBy : "")
  );
}

export function deleteView(windowId, viewId) {
  return axios.delete(
    config.API_URL + "/documentView/" + windowId + "/" + viewId
  );
}

export function createViewRequest({
  windowId,
  viewType,
  filters,
  refDocType = null,
  refDocId = null,
  refTabId = null,
  refRowIds = null
}) {
  let referencing = null;

  if (refDocType && refDocId) {
    referencing = {
      documentType: refDocType,
      documentId: refDocId
    };

    if (refTabId && refRowIds) {
      referencing.tabId = refTabId;
      referencing.rowIds = refRowIds;
    }
  }

  return axios.post(config.API_URL + "/documentView/" + windowId, {
    documentType: windowId,
    viewType: viewType,
    referencing: referencing,
    filters: filters
  });
}

export function filterViewRequest(windowId, viewId, filters) {
  return axios.post(
    config.API_URL + "/documentView/" + windowId + "/" + viewId + "/filter",
    {
      filters: filters
    }
  );
}

export function deleteStaticFilter(windowId, viewId, filterId) {
  return axios.delete(
    config.API_URL +
      "/documentView/" +
      windowId +
      "/" +
      viewId +
      "/staticFilter/" +
      filterId
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
    parentViewSelectedIds: parentView.viewSelectedIds
  });

  return axios.get(
    config.API_URL +
      "/documentView/" +
      windowId +
      "/" +
      viewId +
      "/quickActions" +
      (query ? "?" + query : "")
  );
}
