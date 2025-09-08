import axios from 'axios';
import { apiBasePath } from '../../../constants';
import { unboxAxiosResponse } from '../../../utils';
import { toUrl } from '../../../utils/index';

export const getProducts = ({ posTerminalId, query }) => {
  return axios
    .get(toUrl(`${apiBasePath}/pos/products`, { posTerminalId, query }))
    .then((response) => unboxAxiosResponse(response));
};
