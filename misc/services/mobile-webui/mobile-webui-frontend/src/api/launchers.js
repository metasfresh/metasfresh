import axios from 'axios';
import { toQueryString, unboxAxiosResponse } from '../utils';
import { apiBasePath } from '../constants';
import { useEffect } from 'react';
import * as ws from '../utils/websocket';
import { toQRCodeString } from '../utils/huQRCodes';

/**
 * @summary Get the list of available launchers
 */
export const getLaunchers = ({ applicationId, filterByQRCode, facets }) => {
  return axios
    .post(`${apiBasePath}/userWorkflows/launchers/query`, {
      applicationId,
      filterByQRCode: toQRCodeString(filterByQRCode),
      facets,
    })
    .then((response) => unboxAxiosResponse(response));
};

export const countLaunchers = ({ applicationId, facets }) => {
  return axios
    .post(`${apiBasePath}/userWorkflows/launchers/query`, {
      applicationId,
      facets,
      countOnly: true,
    })
    .then((response) => unboxAxiosResponse(response))
    .then((response) => response.count);
};

export const getFacets = (applicationId) => {
  return axios
    .get(`${apiBasePath}/userWorkflows/facets`, {
      params: {
        applicationId,
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
 * @method abortWorkflow
 * @summary Abort a workflow
 * @returns wfProcess
 */
export const abortWorkflowRequest = (wfProcessId) => {
  return axios
    .post(`${apiBasePath}/userWorkflows/wfProcess/${wfProcessId}/abort`)
    .then((response) => unboxAxiosResponse(response));
};

export const useLaunchersWebsocket = ({
  enabled,
  userToken,
  applicationId,
  filterByQRCode,
  facets,
  onWebsocketMessage,
}) => {
  const filterByQRCodeString = toQRCodeString(filterByQRCode);
  const facetIds = facets ? facets.map((facet) => facet.facetId).join(',') : null;

  useEffect(() => {
    let client;
    if (enabled) {
      const topic = `/v2/userWorkflows/launchers/?${toQueryString({
        userToken,
        applicationId,
        qrCode: filterByQRCodeString,
        facetIds,
      })}`;

      console.debug(`WS connecting to ${topic}`, { applicationId, filterByQRCode, facetIds });
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
  }, [enabled, userToken, applicationId, filterByQRCodeString, facetIds]);
};
