import axios from 'axios';

export function createEmail(windowId, documentId) {
  return axios.post(config.API_URL + '/mail', {
    documentPath: {
      documentId: documentId,
      windowId: windowId,
    },
  });
}

export function sendEmail(emailId) {
  return axios.post(config.API_URL + '/mail/' + emailId + '/send');
}

export function addAttachment(emailId, file) {
  let data = new FormData();

  data.append('file', file);

  return axios.post(
    config.API_URL + '/mail/' + emailId + '/field/attachments',
    data
  );
}

export function getEmail(emailId) {
  return axios.get(config.API_URL + '/mail/' + emailId);
}

export function getTemplates() {
  return axios.get(config.API_URL + '/mail/templates');
}
