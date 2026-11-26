import { apiBasePath } from '../constants';
import axios from 'axios';
import { unboxAxiosResponse } from '../utils';
import { useApplicationInfo } from '../reducers/applications';
import { useCallback, useEffect, useRef, useState } from 'react';
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
  const [errorMessage, setErrorMessage] = useState(null);
  // What "Retry" has to re-run. Re-READING after a failed SCAN would drop the scan instead of
  // retrying it, putting the operator back in front of the scan prompt with no clue why, so the
  // operation that actually failed is remembered here.
  const retryFailedOperationRef = useRef(null);

  // A server response means the backend answered and retrying the same request won't help, so keep the
  // toast (existing automation asserts on it). No response at all is the routine warehouse-WiFi blip —
  // retrying may well succeed — so surface it inline with a retry instead.
  // Shape per module CLAUDE.md § "API Error Surfacing"; reference impl ConfirmActivity.jsx.
  const onRequestFailed = ({ axiosError, eventName, retry }) => {
    const isNetworkFailure = !axiosError?.response;
    const message = extractUserFriendlyErrorMessageFromAxiosError({ axiosError });
    uiTrace.trace({
      eventName,
      httpStatus: axiosError?.response?.status ?? null,
      axiosCode: axiosError?.code ?? null,
      isNetworkFailure,
      message,
    });
    if (isNetworkFailure) {
      retryFailedOperationRef.current = retry;
      setErrorMessage(message);
    } else {
      toastError({ axiosError });
    }
  };

  const reloadWorkstation = useCallback(() => {
    if (!requiresWorkstationIfAvailable) {
      setIsLoading(false);
      return;
    }

    setIsLoading(true);
    setErrorMessage(null);
    getCurrentWorkstationInfo()
      .then(({ assignedWorkstation }) => {
        setWorkstation(assignedWorkstation);
      })
      .catch((axiosError) =>
        // Leaving the workstation unset would ask the operator to re-scan a workstation they are
        // still assigned to.
        onRequestFailed({ axiosError, eventName: 'currentWorkstationLoadFailed', retry: reloadWorkstation })
      )
      .finally(() => setIsLoading(false));
  }, [requiresWorkstationIfAvailable]);

  useEffect(() => {
    reloadWorkstation();
  }, [reloadWorkstation]);

  const setWorkstationByQRCode = (qrCode) => {
    setErrorMessage(null);
    assignWorkstationByQRCode(qrCode)
      .then((workstation) => {
        setWorkstation(workstation);
        onWorkstationAssigned?.(workstation);
      })
      .catch((axiosError) =>
        onRequestFailed({
          axiosError,
          eventName: 'assignWorkstationFailed',
          retry: () => setWorkstationByQRCode(qrCode),
        })
      );
  };

  // Re-runs whatever failed last, then forgets it so a gloved double-tap on Retry cannot fire the same
  // assign twice; that second tap falls back to a plain re-read rather than crashing on a null op.
  const retryWorkstation = () => {
    const retry = retryFailedOperationRef.current ?? reloadWorkstation;
    retryFailedOperationRef.current = null;
    retry();
  };

  return {
    isWorkstationRequired: requiresWorkstationIfAvailable,
    isWorkstationLoading: isLoading,
    workstation,
    workstationErrorMessage: errorMessage,
    retryWorkstation,
    setWorkstationByQRCode,
  };
};

export const getWorkstationByQRCode = (qrCode) => {
  return axios.post(`${workstationAPIBase}/byQRCode`, { qrCode }).then(unboxAxiosResponse);
};

export const assignWorkstationByQRCode = (workstationQRCode) => {
  return axios.post(`${workstationAPIBase}/assign`, { workstationQRCode }).then(unboxAxiosResponse);
};
