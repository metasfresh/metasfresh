import axios from "axios";
import Moment from "moment";
import { getQueryString } from "./GenericActions";
import { DATE_FORMAT } from "../constants/Constants";

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
  filters.map(filter => {
    filter.parameters &&
      filter.parameters.map((param, index) => {
        if (param.widgetType === "Date" && param.value) {
          filter.parameters[index].value = Moment(param.value).format(
            DATE_FORMAT
          );
        }
      });
  });
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

export function mergeColumnInfosIntoViewRows(columnInfosByFieldName, rows) {
  if (!columnInfosByFieldName) {
    return rows;
  }

  return rows.map(row =>
    mergeColumnInfosIntoViewRow(columnInfosByFieldName, row)
  );
}

function mergeColumnInfosIntoViewRow(columnInfosByFieldName, row) {
  const fieldsByName = Object.values(row.fieldsByName)
    .map(viewRowField =>
      mergeColumnInfoIntoViewRowField(
        columnInfosByFieldName[viewRowField.field],
        viewRowField
      )
    )
    .reduce((acc, viewRowField) => {
      acc[viewRowField.field] = viewRowField;
      return acc;
    }, {});

  return Object.assign({}, row, { fieldsByName });
}

function mergeColumnInfoIntoViewRowField(columnInfo, viewRowField) {
  if (!columnInfo) {
    return viewRowField;
  }

  if (columnInfo.widgetType) {
    viewRowField["widgetType"] = columnInfo.widgetType;
  }

  // NOTE: as discussed with @metas-mk, at the moment we cannot apply the maxPrecision per page,
  // because it would puzzle the user.
  // if (columnInfo.maxPrecision && columnInfo.maxPrecision > 0) {
  //   viewRowField["precision"] = columnInfo.maxPrecision;
  // }

  return viewRowField;
}

export function mergeRows({ toRows, fromRows, columnInfosByFieldName }) {
  if (!fromRows) {
    return toRows;
  }

  const fromRowsById = fromRows.reduce((acc, row) => {
    acc[row.id] = row;
    return acc;
  }, {});

  return toRows.map(
    row =>
      fromRowsById[row.id]
        ? mergeColumnInfosIntoViewRow(
            columnInfosByFieldName,
            fromRowsById[row.id]
          )
        : row
  );
}
