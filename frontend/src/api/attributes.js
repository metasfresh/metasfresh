import { get, patch, post } from 'axios';
import { createPatchRequestPayload } from '../utils';

/**
 * @summary Creates a new editing instance
 * @param {string} [attributeType] 'pattribute' or 'address'
 * @param {string|number} [templateId] original instance ID that we are editing; it might be null
 * @param {object} [source] source document/row/field that we are editing
 */
export function createAttributesEditingInstance(
  attributeType,
  templateId,
  source
) {
  return post(`${config.API_URL}/${attributeType}`, {
    templateId: templateId,
    source: source,
  });
}

/**
 * @param {string} attributeType 'pattribute' or 'address'
 * @param {string} instanceId editing instance ID
 */
export function getAttributesLayout(attributeType, instanceId) {
  return get(`${config.API_URL}/${attributeType}/${instanceId}/layout`);
}

/**
 * @summary Completes the attributes editing and returns the ID of the new immutable instance (i.e. the M_AttributeSetInstance_ID or C_Location_ID)
 * @param {string} attributeType - i.e. 'pattribute' or 'address'
 * @param {string} instanceId editing instance ID
 * @return {object} key and caption
 */
export function completeAttributesEditing(attributeType, instanceId) {
  return post(`${config.API_URL}/${attributeType}/${instanceId}/complete`);
}

export function patchAttributes({
  attributeType,
  instanceId,
  fieldName,
  value,
}) {
  const payload = createPatchRequestPayload(fieldName, value);

  return patch(
    `${config.API_URL}/${attributeType}/${instanceId}`,
    payload
  ).then((rawResponse) => {
    // this is fixed on the FE because the BE is not consistent in sending the `documents` key with every PATCH request
    // this differs when patch is done within processes for example
    if (!rawResponse.data.documents) {
      rawResponse.data.documents = rawResponse.data;
    }

    return Promise.resolve(rawResponse);
  });
}
