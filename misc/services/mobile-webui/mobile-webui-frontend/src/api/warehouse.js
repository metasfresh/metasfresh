import { apiBasePath } from '../constants';
import axios from 'axios';
import { unboxAxiosResponse } from '../utils';

const warehouseAPIBase = `${apiBasePath}/material/warehouses`;

export const resolveLocatorScannedCode = ({ scannedBarcode }) => {
  return axios.post(`${warehouseAPIBase}/resolveLocator`, { scannedBarcode }).then(unboxAxiosResponse);
};
