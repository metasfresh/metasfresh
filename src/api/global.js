import axios from 'axios';

import { createPatchRequestPayload } from '../utils';

export function getData(
  entity,
  docType,
  docId,
  tabId,
  rowId,
  subentity,
  subentityId,
  isAdvanced,
  orderBy,
  viewId
) {
  return axios.get(
    `${config.API_URL}/${entity}/${docType}${viewId ? `/${viewId}` : ''}${
      docId ? `/${docId}` : ''
    }${tabId ? `/${tabId}` : ''}${rowId ? `/${rowId}` : ''}${
      subentity ? `/${subentity}` : ''
    }${subentityId ? `/${subentityId}` : ''}/${
      isAdvanced ? `?advanced=true` : ''
    }${orderBy ? `?orderBy=${orderBy}` : ''}`
  );
}

export function patchRequest({
  // HOTFIX: before refactoring all calls explicity set docId to `null`
  // instead of `undefined` so default value 'NEW' was never used!
  docId,

  docType,
  entity,
  isAdvanced,
  property,
  rowId,
  subentity,
  subentityId,
  tabId,
  value,
  viewId,
  isEdit,
}) {
  let payload =
    docId !== 'NEW' ? createPatchRequestPayload(property, value) : [];

  return axios.patch(
    config.API_URL +
      '/' +
      entity +
      (docType ? '/' + docType : '') +
      (viewId ? '/' + viewId : '') +
      (docId ? '/' + docId : '') +
      (tabId ? '/' + tabId : '') +
      (rowId ? '/' + rowId : '') +
      (subentity ? '/' + subentity : '') +
      (subentityId ? '/' + subentityId : '') +
      (isAdvanced ? '?advanced=true' : '') +
      (isEdit ? '/edit' : ''),
    payload
  );
}
