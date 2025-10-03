import axios from 'axios';
import { toUrl, unboxAxiosResponse } from '../../utils';
import { apiBasePath } from '../../constants';

const inventoryBasePath = `${apiBasePath}/mobile/inventory`;

export const resolveHU = ({ scannedCode, wfProcessId, lineId }) => {
  return axios
    .post(toUrl(`${inventoryBasePath}/resolveHU`), { scannedCode, wfProcessId, lineId })
    .then((response) => unboxAxiosResponse(response));
};
