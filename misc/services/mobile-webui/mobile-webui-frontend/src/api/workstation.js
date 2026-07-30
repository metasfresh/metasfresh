import { apiBasePath } from '../constants';
import axios from 'axios';
import { unboxAxiosResponse } from '../utils';
import { useApplicationInfo } from '../reducers/applications';
import { useCallback, useEffect, useState } from 'react';
import { extractUserFriendlyErrorMessageFromAxiosError, toastError } from '../utils/toast';
import * as uiTrace from '../utils/ui_trace';

const workstationAPIBase = `${apiBasePath}/workstation`;

export const getCurrentWorkstationInfo = () => {
  return axios.get(`${workstationAPIBase}`).then(unboxAxiosResponse);
};

/**
 * @param applicationId the mobile application whose config decides whether a workstation is needed at all
 * @param onWorkstationAssigned called after a scan successfully assigned a workstation. Assigning a
 *        workstation ALSO re-assigns the operator's workplace server-side (to the workstation's linked
 *        workplace, see WorkstationRestController#assign) — state this hook does not own, so its owner
 *        has to be told to re-read it.
 */
export const useCurrentWorkstation = ({ applicationId, onWorkstationAssigned }) => {
  const { requiresWorkstation: requiresWorkstationIfAvailable } = useApplicationInfo({ applicationId });
  const [isLoading, setIsLoading] = useState(requiresWorkstationIfAvailable);
  const [workstation, setWorkstation] = useState(null);
  const [loadErrorMessage, setLoadErrorMessage] = useState(null);

  const reloadWorkstation = useCallback(() => {
    if (!requiresWorkstationIfAvailable) {
      setIsLoading(false);
      return;
    }

    setIsLoading(true);
    setLoadErrorMessage(null);
    getCurrentWorkstationInfo()
      .then(({ assignedWorkstation }) => {
        setWorkstation(assignedWorkstation);
      })
      .catch((axiosError) => {
        // A server response means the backend answered and retrying the same read won't help, so keep the
        // toast (existing automation asserts on it). No response at all is the routine warehouse-WiFi blip:
        // leaving the workstation unset would ask the operator to re-scan a workstation they are still
        // assigned to, so surface it for retry instead.
        const isNetworkFailure = !axiosError?.response;
        const message = extractUserFriendlyErrorMessageFromAxiosError({ axiosError });
        uiTrace.trace({
          eventName: 'currentWorkstationLoadFailed',
          httpStatus: axiosError?.response?.status ?? null,
          axiosCode: axiosError?.code ?? null,
          isNetworkFailure,
          message,
        });
        if (isNetworkFailure) {
          setLoadErrorMessage(message);
        } else {
          toastError({ axiosError });
        }
      })
      .finally(() => setIsLoading(false));
  }, [requiresWorkstationIfAvailable]);

  useEffect(() => {
    reloadWorkstation();
  }, [reloadWorkstation]);

  const setWorkstationByQRCode = (qrCode) => {
    assignWorkstationByQRCode(qrCode)
      .then((workstation) => {
        setWorkstation(workstation);
        onWorkstationAssigned?.(workstation);
      })
      .catch((axiosError) => toastError({ axiosError }));
  };

  return {
    isWorkstationRequired: requiresWorkstationIfAvailable,
    isWorkstationLoading: isLoading,
    workstation,
    workstationLoadErrorMessage: loadErrorMessage,
    reloadWorkstation,
    setWorkstationByQRCode,
  };
};

export const getWorkstationByQRCode = (qrCode) => {
  return axios.post(`${workstationAPIBase}/byQRCode`, { qrCode }).then(unboxAxiosResponse);
};

export const assignWorkstationByQRCode = (workstationQRCode) => {
  return axios.post(`${workstationAPIBase}/assign`, { workstationQRCode }).then(unboxAxiosResponse);
};
