import { post, get } from 'axios';

import { getData } from './view';
import { parseToDisplay } from '../utils/documentListHelper';

/**
 * @param attributeType 'pattribute' or 'address'
 */
export function getAttributesInstance(attributeType, templateId, source) {
  return post(`${config.API_URL}/${attributeType}`, {
    templateId: templateId,
    source: source,
  });
}

export function topActionsRequest(windowId, documentId, tabId) {
  return get(`
    ${config.API_URL}/window/${windowId}/${documentId}/${tabId}/topActions
  `);
}

export function getZoomIntoWindow(
  entity,
  windowId,
  docId,
  tabId,
  rowId,
  field
) {
  return get(
    config.API_URL +
      '/' +
      entity +
      '/' +
      windowId +
      (docId ? '/' + docId : '') +
      (tabId ? '/' + tabId : '') +
      (rowId ? '/' + rowId : '') +
      '/field' +
      '/' +
      field +
      '/zoomInto?showError=true'
  );
}

export function discardNewRow({ windowType, documentId, tabId, rowId } = {}) {
  return post(
    config.API_URL +
      '/window/' +
      windowType +
      '/' +
      documentId +
      '/' +
      tabId +
      '/' +
      rowId +
      '/discardChanges'
  );
}

export function discardNewDocument({ windowType, documentId } = {}) {
  return post(
    config.API_URL +
      '/window/' +
      windowType +
      '/' +
      documentId +
      '/discardChanges'
  );
}

export function getTab(tabId, windowType, docId, orderBy) {
  return getData({
    entity: 'window',
    docType: windowType,
    docId: docId,
    tabId: tabId,
    rowId: null, // all rows
    orderBy: orderBy,
  }).then(
    (res) =>
      res.data &&
      res.data.map((row) => ({
        ...row,
        fieldsByName: parseToDisplay(row.fieldsByName),
      }))
  );
}

export function startProcess(processType, pinstanceId) {
  return get(`${config.API_URL}/process/${processType}/${pinstanceId}/start`);
}

export function getProcessData({
  processId,
  viewId,
  type,
  ids,
  tabId,
  rowId,
  selectedTab,
  childViewId,
  childViewSelectedIds,
  parentViewId,
  parentViewSelectedIds,
}) {
  const payload = {
    processId: processId,
  };

  if (viewId) {
    payload.viewId = viewId;
    payload.viewDocumentIds = ids;

    if (childViewId) {
      payload.childViewId = childViewId;
      payload.childViewSelectedIds = childViewSelectedIds;
    }

    if (parentViewId) {
      payload.parentViewId = parentViewId;
      payload.parentViewSelectedIds =
        parentViewSelectedIds instanceof Array
          ? parentViewSelectedIds
          : [parentViewSelectedIds];
    }
  } else {
    payload.documentId = Array.isArray(ids) ? ids[0] : ids;
    payload.documentType = type;
    payload.tabId = tabId;
    payload.rowId = rowId;
  }

  if (selectedTab) {
    const { tabId, rowIds } = selectedTab;

    if (tabId && rowIds) {
      payload.selectedTab = {
        tabId,
        rowIds,
      };
    }
  }

  return post(`${config.API_URL}/process/${processId}`, payload);
}
