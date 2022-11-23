import axios from 'axios';
import { unboxAxiosResponse } from '../utils';
import { apiBasePath } from '../constants';
import { useEffect } from 'react';
import * as ws from '../utils/websocket';
import { base64Encode } from '../utils/base64';
import { toQRCodeString } from '../utils/huQRCodes';

/**
 * @summary Get the list of available launchers
 */
export const getLaunchers = (applicationId, filterByQRCode) => {
  return axios
    .get(`${apiBasePath}/userWorkflows/launchers`, {
      params: {
        applicationId,
        filterByQRCode: toQRCodeString(filterByQRCode),
      },
    })
    .then((response) => unboxAxiosResponse(response));
};

/**
 * @method startWorkflowRequest
 * @summary Start a workflow from the launchers list
 * @returns wfProcess
 */
export const startWorkflowRequest = ({ wfParameters }) => {
  return axios
    .post(`${apiBasePath}/userWorkflows/wfProcess/start`, { wfParameters })
    .then((response) => unboxAxiosResponse(response));
};

export const continueWorkflowRequest = (wfProcessId) => {
  return axios
    .post(`${apiBasePath}/userWorkflows/wfProcess/${wfProcessId}/continue`)
    .then((response) => unboxAxiosResponse(response));
};

/**
 * @method startWorkflow
 * @summary Continue a workflow from the launchers list
 * @returns wfProcess
 */
export const getWorkflowRequest = (wfProcessId) => {
  return axios
    .get(`${apiBasePath}/userWorkflows/wfProcess/${wfProcessId}`)
    .then((response) => unboxAxiosResponse(response));
};

/**
 * @method abortWorkflow
 * @summary Abort a workflow
 * @returns wfProcess
 */
export const abortWorkflowRequest = (wfProcessId) => {
  return axios
    .post(`${apiBasePath}/userWorkflows/wfProcess/${wfProcessId}/abort`)
    .then((response) => unboxAxiosResponse(response));
};

export const useLaunchersWebsocket = ({ enabled, userToken, applicationId, filterByQRCode, onWebsocketMessage }) => {
  const filterByQRCodeString = toQRCodeString(filterByQRCode);
  useEffect(() => {
    let client;
    if (enabled) {
      let topic = `/v2/userWorkflows/launchers/${userToken}/${applicationId}`;
      if (filterByQRCodeString) {
        topic += `/${base64Encode(filterByQRCodeString)}`;
      }

      console.debug(`WS connecting to ${topic}`, { applicationId, filterByQRCode });
      client = ws.connectAndSubscribe({
        topic,
        debug: !!window?.debug_ws,
        onWebsocketMessage: (message) => {
          const applicationLaunchers = JSON.parse(message.body);
          onWebsocketMessage({ applicationId, applicationLaunchers });
        },
      });
    }

    return () => {
      if (client) {
        ws.disconnectClient(client);
        client = null;
        console.debug('WS disconnected', { applicationId, filterByQRCode });
      }
    };
  }, [enabled, userToken, applicationId, filterByQRCodeString]);
};
