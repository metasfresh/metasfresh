import { post, get } from 'axios';

export function initLayout(
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

export function getAttributesInstance(attrType, tmpId) {
  return post(`${config.API_URL}/${attrType}`, {
    templateId: tmpId,
  });
}

export function topActionsRequest(windowId, documentId, tabId) {
  return get(`
    ${config.API_URL}/window/${windowId}/${documentId}/${tabId}/topActions
  `);
}
