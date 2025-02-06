import { get, patch, post } from 'axios';
import { createPatchRequestPayload } from '../utils';

/**
 * @summary Start a new quick input session
 */
export const initQuickInput = (windowId, docId, tabId) => {
  return post(
    `${config.API_URL}/window/${windowId}/${docId}/${tabId}/quickInput`
  );
};

export const getQuickInputLayout = (windowId, docId, tabId) => {
  return get(
    `${config.API_URL}/window/${windowId}/${docId}/${tabId}/quickInput/layout`
  );
};

export const patchQuickInput = ({
  windowId,
  docId,
  tabId,
  quickInputId,
  fieldName,
  fieldValue,
}) => {
  const payload = createPatchRequestPayload(fieldName, fieldValue);

  return patch(
    `${config.API_URL}/window/${windowId}/${docId}/${tabId}/quickInput/${quickInputId}`,
    payload
  );
};

/**
 * @summary Save changes in attributes/quick input
 */
export const completeQuickInput = (windowId, docId, tabId, quickInputId) => {
  return post(
    `${config.API_URL}/window/${windowId}/${docId}/${tabId}/quickInput/${quickInputId}/complete`
  );
};
