import axios from 'axios';

export function createLetter(windowId, documentId) {
  return axios.post(config.API_URL + '/letter', {
    documentPath: {
      documentId: documentId,
      windowId: windowId,
    },
  });
}

export function completeLetter(letterId) {
  return axios.post(config.API_URL + '/letter/' + letterId + '/complete');
}

export function getTemplates() {
  return axios.get(config.API_URL + '/letter/templates');
}
