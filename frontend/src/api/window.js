import { post, get, delete as del } from 'axios';

import { getData } from './view';
import { parseToDisplay } from '../utils/documentListHelper';
import { formatSortingQuery } from '../utils';

export function topActionsRequest(windowId, documentId, tabId) {
  return get(`
    ${config.API_URL}/window/${windowId}/${documentId}/${tabId}/topActions
  `);
}

export function deleteRequest(
  entity,
  docType,
  docId,
  tabId,
  ids,
  subentity,
  subentityId
) {
  return del(
    `${config.API_URL}/${entity}${docType ? `/${docType}` : ''}${
      docId ? `/${docId}` : ''
    }${tabId ? `/${tabId}` : ''}${subentity ? `/${subentity}` : ''}${
      subentityId ? `/${subentityId}` : ''
    }${ids ? `?ids=${ids}` : ''}`
  );
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

export function discardNewRequest({ windowId, documentId, tabId, rowId } = {}) {
  return post(
    `${config.API_URL}/window/${windowId}/${documentId}${
      tabId && rowId ? `/${tabId}/${rowId}` : ''
    }/discardChanges`
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

export function getTabRequest(tabId, windowType, docId, orderBy) {
  return getData({
    entity: 'window',
    docType: windowType,
    docId: docId,
    tabId: tabId,
    rowId: null, // all rows
    orderBy: formatSortingQuery(orderBy),
  })
    .then(
      (res) =>
        res.data &&
        res.data.result &&
        res.data.result.map((row) => ({
          ...row,
          fieldsByName: parseToDisplay(row.fieldsByName),
        }))
    )
    .catch((error) => {
      // eslint-disable-next-line no-console
      console.error('getTabRequest error: ', error);
    });
}

export function getTabLayoutRequest(windowId, tabId) {
  return get(`${config.API_URL}/window/${windowId}/${tabId}/layout`);
}

/**
 * getAPIUrl function
 */
const getAPIUrl = function ({ windowId, docId, tabId, rowId, path }) {
  let documentId = docId;

  if (!docId && rowId) {
    documentId = rowId[0];
  }

  return `${config.API_URL}/window/${windowId}${
    documentId ? `/${documentId}` : ''
  }${rowId && tabId ? `/${tabId}/${rowId}` : ''}/${path}`;
};

/**
 * Formats the url for the api call
 */
export function formatParentUrl({ windowId, docId, rowId, target }) {
  let parentUrl;
  switch (target) {
    case 'comments':
      parentUrl = getAPIUrl({
        windowId,
        docId,
        tabId: null,
        rowId,
        path: target,
      });
      break;
    default:
      parentUrl = null;
      break;
  }
  return parentUrl;
}

export function startProcess(processType, pinstanceId) {
  return get(`${config.API_URL}/process/${processType}/${pinstanceId}/start`);
}

export function getProcessData({
  processId,
  viewId,
  documentType,
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
    payload.documentType = documentType;
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

/**
 * @method getPrintingOptions
 * @summary Get the printing options for a specified entity
 * @param {string} entity - for example 'window'
 * @param {string} windowId
 * @param {string} docId
 * @param {string} tabId
 * @param {string} rowId
 */
export function getPrintingOptions({ entity, windowId, docId, tabId, rowId }) {
  return get(
    config.API_URL +
      '/' +
      entity +
      '/' +
      windowId +
      (docId ? '/' + docId : '') +
      (tabId ? '/' + tabId : '') +
      (rowId ? '/' + rowId : '') +
      '/printingOptions'
  );
}

/**
 * @method initQuickInput
 * @summary Fetch data for table quick input
 * @param {string} entity - for example 'window'
 * @param {string} windowId
 * @param {string} docId
 * @param {string} tabId
 * @param {string} subentity - for example `quickInput`
 */
export function initQuickInput(entity, windowId, docId, tabId, subentity) {
  tabId = tabId ? `/${tabId}` : '';
  subentity = subentity ? `/${subentity}` : '';

  return post(
    `${config.API_URL}/${entity}/${windowId}/${docId}${tabId}${subentity}`
  );
}

/**
 * @method completeRequest
 * @summary Save changes in attributes/quick input
 * @param {string} entity - for example 'window'
 * @param {string} windowId
 * @param {string} docId
 * @param {string} tabId
 * @param {string} subentity - for example `quickInput`
 * @param {string} subentityId
 */
export function completeRequest(
  entity,
  docType,
  docId,
  tabId,
  rowId,
  subentity,
  subentityId
) {
  docType = docType ? `/${docType}` : '';
  docId = docId ? `/${docId}` : '';
  tabId = tabId ? `/${tabId}` : '';
  rowId = rowId ? `/${rowId}` : '';
  subentity = subentity ? `/${subentity}` : '';
  subentityId = subentityId ? `/${subentityId}` : '';

  return post(
    `${config.API_URL}/${entity}${docType}${docId}${tabId}${rowId}${subentity}${subentityId}/complete`
  );
}
