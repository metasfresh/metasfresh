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
