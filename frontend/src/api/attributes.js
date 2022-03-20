import { patch, post } from 'axios';
import { createPatchRequestPayload, toSingleFieldPatchRequest } from '../utils';

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

/**
 * @summary Completes the attributes editing and returns the ID of the new immutable instance (i.e. the M_AttributeSetInstance_ID or C_Location_ID)
 * @param {string} [attributeType] - i.e. 'pattribute' or 'address'
 * @param {string} [instanceId] editing instance ID
 * @param {object} [fieldsByName] - e.g. { 'FieldName': { value: 123 } }
 * @return {object} key and caption
 */
export function completeAttributesEditing(
  attributeType,
  instanceId,
  fieldsByName = null
) {
  const requestBody = { events: [] };
  if (fieldsByName) {
    Object.keys(fieldsByName).forEach((fieldName) => {
      const value = fieldsByName[fieldName].value;
      requestBody.events.push(toSingleFieldPatchRequest(fieldName, value));
    });
  }

  return post(
    `${config.API_URL}/${attributeType}/${instanceId}/complete`,
    requestBody
  );
}
