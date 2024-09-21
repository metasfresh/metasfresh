import axios from 'axios';
import { apiBasePath } from '../../../constants';
import { unboxAxiosResponse } from '../../../utils';

export const getPOSTerminal = () => {
  return axios.get(`${apiBasePath}/pos/terminal`).then((response) => unboxAxiosResponse(response));
};
