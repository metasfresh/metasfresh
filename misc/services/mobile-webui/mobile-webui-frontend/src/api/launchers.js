import axios from 'axios';
import { toQueryString, unboxAxiosResponse } from '../utils';
import { apiBasePath } from '../constants';
import { useEffect } from 'react';
import * as ws from '../utils/websocket';
import { toQRCodeString } from '../utils/qrCode/hu';

/**
 * @summary Get the list of available launchers
 */
export const getLaunchers = ({
  applicationId,
  filterByQRCodeString,
  filters,
  facetIds: facetIdsParam,
  facets: facetsParam,
  excludeAlreadyStarted,
  countOnly = false,
  limit,
}) => {
  let facetIds = null;
  if (facetIdsParam) {
    facetIds = facetIdsParam;
  } else if (facetsParam) {
    facetIds = facetsParam ? facetsParam.map((facet) => facet.facetId) : null;
  }

  return axios
    .post(`${apiBasePath}/userWorkflows/launchers/query`, {
      ...filters,
      applicationId,
      filterByQRCode: filterByQRCodeString,
      facetIds,
      excludeAlreadyStarted,
      countOnly,
      limit,
    })
    .then((response) => unboxAxiosResponse(response));
};

export const countLaunchers = ({ applicationId, facetIds, filters }) => {
  return getLaunchers({
    applicationId,
    facetIds,
    filters,
    countOnly: true,
  }).then((response) => response.count);
};

export const getFacets = ({ applicationId, activeFacetIds, filters }) => {
  return axios
    .post(`${apiBasePath}/userWorkflows/facets`, {
      applicationId,
      activeFacetIds,
      ...filters,
    })
    .then((response) => unboxAxiosResponse(response))
    .then((response) => response.groups);
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
  filters,
  facets,
  onWebsocketMessage,
}) => {
  const filterByQRCodeString = toQRCodeString(filterByQRCode);
  const facetIds = facets ? facets.map((facet) => facet.facetId).join(',') : null;

  useEffect(() => {
    let client;
    // ws.disconnectClient is async, so a frame can still be delivered after this effect's cleanup ran and
    // the screen unmounted -- in the captured failure the STOMP DISCONNECT was logged BEFORE the deleting
    // MESSAGE. An unmounted subscription must not write to the store at all.
    let isSubscriptionActive = true;
    if (enabled) {
      const topic = `/v2/userWorkflows/launchers/?${toQueryString({
        userToken,
        applicationId,
        qrCode: filterByQRCodeString,
        documentNo: filters?.filterByDocumentNo, // FIXME make it general
        filterByQtyAvailableAtPickFromLocator: filters?.filterByQtyAvailableAtPickFromLocator,
        facetIds,
      })}`;

      // console.debug(`WS connecting to ${topic}`, { applicationId, filterByQRCodeString, filters, facetIds });
      client = ws.connectAndSubscribe({
        topic,
        debug: !!window?.debug_ws,
        onWebsocketMessage: (message) => {
          if (!isSubscriptionActive) return;
          const applicationLaunchers = JSON.parse(message.body);
          onWebsocketMessage({ applicationId, applicationLaunchers });
        },
      });
    }

    return () => {
      isSubscriptionActive = false;
      if (client) {
        ws.disconnectClient(client);
        client = null;
        // console.debug('WS disconnected', { applicationId, filterByQRCode, filters });
      }
    };
  }, [enabled, userToken, applicationId, filterByQRCodeString, JSON.stringify(filters), facetIds]);
};
