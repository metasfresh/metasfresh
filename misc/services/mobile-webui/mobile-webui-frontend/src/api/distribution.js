import axios from 'axios';
import { apiBasePath } from '../constants';
import { toUrl, unboxAxiosResponse } from '../utils';
import { toQRCodeString } from '../utils/qrCode/hu';

export const getDistributionScannedHUQRCodeInfo = ({ qrCode }) => {
  return axios
    .get(toUrl(`${apiBasePath}/distribution/hu/byScannedCode`, { scannedCode: qrCode }))
    .then((response) => unboxAxiosResponse(response));
};

export const postDistributionPickFrom = ({ wfProcessId, activityId, lineId, stepId, pickFrom }) => {
  return axios
    .post(`${apiBasePath}/distribution/event`, {
      wfProcessId,
      wfActivityId: activityId,
      lineId,
      distributionStepId: stepId,
      pickFrom: { ...pickFrom },
    })
    .then((response) => unboxAxiosResponse(response));
};

export const postDistributionDropTo = ({ wfProcessId, activityId, lineId, stepId, dropToLocatorQRCode }) => {
  return axios
    .post(`${apiBasePath}/distribution/event`, {
      wfProcessId,
      wfActivityId: activityId,
      lineId,
      distributionStepId: stepId,
      dropTo: { qrCode: dropToLocatorQRCode ? toQRCodeString(dropToLocatorQRCode) : null },
    })
    .then((response) => unboxAxiosResponse(response));
};

export const postDistributionUnpickEvent = ({ wfProcessId, activityId, lineId, stepId, unpickToTargetQRCode }) => {
  return axios
    .post(`${apiBasePath}/distribution/event`, {
      wfProcessId,
      wfActivityId: activityId,
      lineId,
      distributionStepId: stepId,
      unpick: { unpickToTargetQRCode },
    })
    .then((response) => unboxAxiosResponse(response));
};

export const postDropAll = ({ dropToQRCode }) => {
  return axios
    .post(`${apiBasePath}/distribution/dropAll`, {
      dropToQRCode,
    })
    .then((response) => unboxAxiosResponse(response));
};

export const completeDistributionJob = ({ wfProcessId }) => {
  return axios
    .post(`${apiBasePath}/distribution/job/${wfProcessId}/complete`)
    .then((response) => unboxAxiosResponse(response));
};
