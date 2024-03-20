import axios from 'axios';
import { apiBasePath } from '../constants';
import { unboxAxiosResponse } from '../utils';

export const getMobileConfiguration = () => {
  return axios.get(`${apiBasePath}/public/mobile/config`).then((response) => unboxAxiosResponse(response));
};
