import { patch, post } from 'axios';
import { createPatchRequestPayload, toSingleFieldPatchRequest } from '../utils';

/**
 * @summary Creates a new editing instance
 * @param {string} [attributeType] 'pattribute' or 'address'
 * @param {string|number} [templateId] original instance ID that we are editing; it might be null
 * @param {object} [source] source document/row/field that we are editing
 * @return attributes document, i.e. { id, layout, fieldsByName }
 */
export function createAttributesEditingInstance(
  attributeType,
  templateId,
  source
) {
  return post(`${config.API_URL}/${attributeType}`, {
    templateId: templateId,
    source: source,
  }).then((axiosResponse) => axiosResponse.data);
}

/**
 * @return {object} fieldsByName
 */
export function patchAttributes({
  attributeType,
  editingInstanceId,
  fieldName,
  value,
}) {
  const payload = createPatchRequestPayload(fieldName, value);

  return patch(
    `${config.API_URL}/${attributeType}/${editingInstanceId}`,
    payload
  ).then((axiosResponse) =>
    axiosResponse.data && axiosResponse.data.length
      ? axiosResponse.data[0].fieldsByName
      : {}
  );
}

/**
 * @summary Completes the attributes editing and returns the ID of the new immutable instance (i.e. the M_AttributeSetInstance_ID or C_Location_ID)
 * @param {string} [attributeType] - i.e. 'pattribute' or 'address'
 * @param {string} [editingInstanceId]
 * @param {object} [fieldsByName] - e.g. { 'FieldName': { value: 123 } }
 * @return {object} created lookup value, e.g. { key: 1234, caption: 'bla bla' }
 */
export function completeAttributesEditing(
  attributeType,
  editingInstanceId,
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
    `${config.API_URL}/${attributeType}/${editingInstanceId}/complete`,
    requestBody
  ).then((response) => response.data);
}
