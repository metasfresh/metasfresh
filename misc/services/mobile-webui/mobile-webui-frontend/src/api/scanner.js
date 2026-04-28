import axios from 'axios';
import { apiBasePath } from '../constants';
import { unboxAxiosResponse } from '../utils';

export const postScannedBarcode = ({ wfProcessId, activityId, scannedBarcode }) => {
  return axios
    .post(`${apiBasePath}/userWorkflows/wfProcess/${wfProcessId}/${activityId}/scannedBarcode`, {
      barcode: scannedBarcode,
    })
    .then((response) => unboxAxiosResponse(response));
};

export const getScannedBarcodeSuggestions = ({ wfProcessId, activityId }) => {
  return axios
    .get(`${apiBasePath}/userWorkflows/wfProcess/${wfProcessId}/${activityId}/scannedBarcode/suggestions`)
    .then((response) => unboxAxiosResponse(response));
};
