import { delete as del, get, post } from 'axios';

import { getData } from './view';
import { parseToDisplay } from '../utils/documentListHelper';
import { formatSortingQuery, getQueryString } from '../utils';

export function topActionsRequest(windowId, documentId, tabId = null) {
  const url =
    tabId == null
      ? `${config.API_URL}/window/${windowId}/${documentId}/topActions`
      : `${config.API_URL}/window/${windowId}/${documentId}/${tabId}/topActions`;

  return get(url);
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

export function getTabRequest(tabId, windowType, docId, orderBy) {
  return getData({
    entity: 'window',
    docType: windowType,
    docId: docId,
    tabId: tabId,
    rowId: null, // all rows
    orderBy,
  })
    .then((res) => {
      const rows =
        res?.data?.result?.map((row) => ({
          ...row,
          fieldsByName: parseToDisplay(row.fieldsByName),
        })) ?? [];

      const orderBys = res?.data?.orderBys ?? [];

      return { rows, orderBys };
    })
    .catch((error) => {
      // eslint-disable-next-line no-console
      console.error('getTabRequest error: ', error);
    });
}

export function getTabLayoutRequest(windowId, tabId, isAdvanced = false) {
  const queryParams = {};
  if (isAdvanced) {
    queryParams.advanced = true;
  }
  const queryParamsString = getQueryString(queryParams);

  return get(
    `${config.API_URL}/window/${windowId}${tabId ? `/${tabId}` : ''}/layout${
      queryParamsString ? `?${queryParamsString}` : ''
    }`
  ).then(({ data }) => data); // unbox
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

export function getPrintUrl({ windowId, documentId, filename, options }) {
  let filenameNorm = filename.replace(/[/\\?%*:|"<>]/g, '-');
  filenameNorm = encodeURIComponent(filenameNorm);

  let url = `${config.API_URL}/window/${windowId}/${documentId}/print/${filenameNorm}`;
  if (options) {
    const optionsStr = getQueryString(options);
    url += '?' + optionsStr;
  }
  return url;
}

export function getPrintFile({ windowId, documentId, filename, options }) {
  return get(getPrintUrl({ windowId, documentId, filename, options }));
}

export function getAttachments({ windowId, documentId }) {
  return get(`${config.API_URL}/window/${windowId}/${documentId}/attachments`);
}

export function getAttachmentUrl({ windowId, documentId, attachmentEntryId }) {
  return `${config.API_URL}/window/${windowId}/${documentId}/attachments/${attachmentEntryId}`;
}

export function deleteAttachment({ windowId, documentId, attachmentEntryId }) {
  return del(getAttachmentUrl({ windowId, documentId, attachmentEntryId }));
}
