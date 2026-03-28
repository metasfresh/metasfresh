import { apiBasePath } from '../constants';
import axios from 'axios';
import { toUrl, unboxAxiosResponse } from '../utils';

const warehouseAPIBase = `${apiBasePath}/material/warehouses`;

export const resolveLocatorScannedCode = ({ scannedBarcode }) => {
  return axios.get(toUrl(`${warehouseAPIBase}/resolveLocator`, { scannedBarcode })).then(unboxAxiosResponse);
};
