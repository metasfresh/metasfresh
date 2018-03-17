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

export function getAttributesInstance(
  attrType,
  tmpId,
  docType,
  docId,
  tabId,
  rowId,
  fieldName,
  entity
) {
  const type = entity === 'process' ? 'processId' : 'windowId';

  return post(`${config.API_URL}/${attrType}`, {
    templateId: tmpId,
    source: {
      [type]: docType,
      documentId: docId,
      tabid: tabId,
      rowId: rowId,
      fieldName: fieldName,
    },
  });
}
