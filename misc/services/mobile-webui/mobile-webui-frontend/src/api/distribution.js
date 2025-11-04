import axios from 'axios';
import { apiBasePath } from '../constants';
import { unboxAxiosResponse } from '../utils';
import { toQRCodeString } from '../utils/qrCode/hu';

export function postDistributionPickFrom({ wfProcessId, activityId, lineId, stepId, pickFrom }) {
  return axios
    .post(`${apiBasePath}/distribution/event`, {
      wfProcessId,
      wfActivityId: activityId,
      lineId,
      distributionStepId: stepId,
      pickFrom: { ...pickFrom },
    })
    .then((response) => unboxAxiosResponse(response));
}

export function postDistributionDropTo({ wfProcessId, activityId, lineId, stepId, dropToLocatorQRCode }) {
  return axios
    .post(`${apiBasePath}/distribution/event`, {
      wfProcessId,
      wfActivityId: activityId,
      lineId,
      distributionStepId: stepId,
      dropTo: { qrCode: dropToLocatorQRCode ? toQRCodeString(dropToLocatorQRCode) : null },
    })
    .then((response) => unboxAxiosResponse(response));
}

export function postDistributionUnpickEvent({ wfProcessId, activityId, lineId, stepId, unpickToTargetQRCode }) {
  return axios
    .post(`${apiBasePath}/distribution/event`, {
      wfProcessId,
      wfActivityId: activityId,
      lineId,
      distributionStepId: stepId,
      unpick: { unpickToTargetQRCode },
    })
    .then((response) => unboxAxiosResponse(response));
}
