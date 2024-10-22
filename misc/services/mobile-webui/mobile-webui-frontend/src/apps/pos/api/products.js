import axios from 'axios';
import { apiBasePath } from '../../../constants';
import { unboxAxiosResponse } from '../../../utils';
import { toUrl } from '../../../utils/index';

export const getProducts = ({ posTerminalId, query }) => {
  return axios
    .get(toUrl(`${apiBasePath}/pos/products`, { posTerminalId, query }))
    .then((response) => unboxAxiosResponse(response));
};

export const getProductCategoryImage = ({ categoryId, maxWidth = 0, maxHeight = 0 }) => {
  return axios({
    url: toUrl(`${apiBasePath}/pos/images/productCategory`, { id: categoryId, maxWidth, maxHeight }),
    responseType: 'blob',
  })
    .then((response) => unboxAxiosResponse(response))
    .then(blob2base64);
};

const blob2base64 = (blob) => {
  return new Promise((resolve) => {
    let reader = new FileReader();
    reader.onload = () => resolve(reader.result);
    reader.readAsDataURL(blob);
  });
};
