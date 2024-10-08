import axios from 'axios';
import { apiBasePath } from '../../../constants';
import { toUrl, unboxAxiosResponse } from '../../../utils';

export const getPOSTerminalById = (posTerminalId) => {
  return axios
    .get(toUrl(`${apiBasePath}/pos/terminal`, { id: posTerminalId }))
    .then((response) => unboxAxiosResponse(response));
};

export const getPOSTerminalsArray = () => {
  return axios.get(`${apiBasePath}/pos/terminal/list`).then((response) => unboxAxiosResponse(response));
};
