import axios, { patch } from 'axios';

const LETTER_API_URL = `${config.API_URL}/letter`;

export const createLetter = (windowId, documentId) => {
  return axios.post(LETTER_API_URL, {
    documentPath: {
      documentId: documentId,
      windowId: windowId,
    },
  });
};

export const completeLetter = (letterId) => {
  return axios.post(`${LETTER_API_URL}/${letterId}/complete`);
};

export const getTemplates = () => {
  return axios.get(`${LETTER_API_URL}/templates`);
};

const patchLetter = (letterId, property, value) => {
  return patch(`${LETTER_API_URL}/${letterId}`, [
    {
      op: 'replace',
      path: property,
      value,
    },
  ]);
};

export const applyTemplate = (letterId, templateId) => {
  return (
    patchLetter(letterId, 'templateId', templateId)
      // unbox and return the current letter
      .then((axiosResponse) => axiosResponse.data)
  );
};

export const patchMessage = (letterId, message) => {
  return (
    patchLetter(letterId, 'message', message)
      // unbox and return the current letter
      .then((axiosResponse) => axiosResponse.data)
  );
};
