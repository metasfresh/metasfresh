import axios from 'axios';

const MAIL_API_URL = `${config.API_URL}/mail`;

/**
 * Create a new draft email ready for editing.
 *
 * @param {string} windowId
 * @param {string} documentId
 * @return {Promise<object>} email data (subject, to etc)
 */
export const createEmail = (windowId, documentId) => {
  return axios
    .post(MAIL_API_URL, {
      documentPath: { documentId: documentId, windowId: windowId },
    })
    .then((axiosResponse) => axiosResponse.data);
};

export const sendEmail = (emailId) => {
  return axios.post(`${MAIL_API_URL}/${emailId}/send`);
};

/**
 * @return {Promise<object>} email data (subject, to etc)
 */
export const addAttachment = (emailId, file) => {
  const data = new FormData();
  data.append('file', file);

  return axios
    .post(`${MAIL_API_URL}/${emailId}/field/attachments`, data)
    .then((axiosResponse) => axiosResponse.data);
};

/**
 * @return {Promise<object>} email data (subject, to etc)
 */
export const getEmail = (emailId) => {
  return axios
    .get(`${MAIL_API_URL}/${emailId}`)
    .then((axiosResponse) => axiosResponse.data);
};

export const getAvailableTemplatesArray = () => {
  return axios
    .get(`${MAIL_API_URL}/templates`)
    .then((axiosResponse) => axiosResponse.data.values);
};

export const patchEmail = (emailId, property, value) => {
  return axios
    .patch(`${MAIL_API_URL}/${emailId}`, [
      { op: 'replace', path: `${property}`, value },
    ])
    .then((axiosResponse) => axiosResponse.data);
};

/**
 * @param {string} emailId
 * @param {object} [template] template lookup value
 * @return {Promise<object>} email data (subject, to etc)
 */
export const applyTemplate = (emailId, template) => {
  return patchEmail(emailId, 'templateId', template);
};
/**
 * @param {string} emailId
 * @param {string} query
 * @return {Promise<array>} array of lookup values
 */
export const getToTypeahead = (emailId, query) => {
  return axios
    .get(
      `${MAIL_API_URL}/${emailId}/field/to/typeahead?query=${encodeURIComponent(
        query
      )}`
    )
    .then((axiosResponse) => axiosResponse.data.values);
};
