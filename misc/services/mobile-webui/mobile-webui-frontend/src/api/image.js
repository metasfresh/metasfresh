import axios from 'axios';
import { apiBasePath } from '../constants';

export const getImageData = ({ imageId, maxWidth = 0, maxHeight = 0 }) => {
  return axios({
    url: `${apiBasePath}/images/byId/${imageId}?maxWidth=${maxWidth}&maxHeight=${maxHeight}`,
    responseType: 'blob',
  }).then((response) => convertToBase64(response.data));
};

const convertToBase64 = (blob) => {
  return new Promise((resolve) => {
    const reader = new FileReader();
    reader.onload = () => resolve(reader.result);
    reader.readAsDataURL(blob);
  });
};
