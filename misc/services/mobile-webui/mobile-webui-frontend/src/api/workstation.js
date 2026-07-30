import { apiBasePath } from '../constants';
import axios from 'axios';
import { unboxAxiosResponse } from '../utils';
import { useApplicationInfo } from '../reducers/applications';
import { useEffect, useState } from 'react';
import { toastError } from '../utils/toast';
import { useReentryKey } from '../hooks/useReentryKey';

const workstationAPIBase = `${apiBasePath}/workstation`;

export const getCurrentWorkstationInfo = () => {
  return axios.get(`${workstationAPIBase}`).then(unboxAxiosResponse);
};

export const useCurrentWorkstation = ({ applicationId }) => {
  const { requiresWorkstation: requiresWorkstationIfAvailable } = useApplicationInfo({ applicationId });
  const reentryKey = useReentryKey();
  const [isLoading, setIsLoading] = useState(requiresWorkstationIfAvailable);
  const [workstation, setWorkstation] = useState(null);

  useEffect(() => {
    if (requiresWorkstationIfAvailable) {
      setIsLoading(true);
      getCurrentWorkstationInfo()
        .then(({ assignedWorkstation }) => {
          setWorkstation(assignedWorkstation);
        })
        .catch((axiosError) => toastError({ axiosError }))
        .finally(() => setIsLoading(false));
    } else {
      setIsLoading(false);
    }
    // reentryKey: re-read when the operator returns to this screen — the assignment may have been
    // changed elsewhere (another app instance) while the screen stayed mounted. It also makes a read
    // that failed (offline blip) retry on the next re-entry instead of leaving the header blank.
    // requiresWorkstationIfAvailable: it arrives from the applications store, so an effect that ran
    // once while it was still falsy would never fetch at all.
  }, [reentryKey, requiresWorkstationIfAvailable]);

  const setWorkstationByQRCode = (qrCode) => {
    assignWorkstationByQRCode(qrCode)
      .then((workstation) => setWorkstation(workstation))
      .catch((axiosError) => toastError({ axiosError }));
  };

  return {
    isWorkstationRequired: requiresWorkstationIfAvailable,
    isWorkstationLoading: isLoading,
    workstation,
    setWorkstationByQRCode,
  };
};

export const getWorkstationByQRCode = (qrCode) => {
  return axios.post(`${workstationAPIBase}/byQRCode`, { qrCode }).then(unboxAxiosResponse);
};

export const assignWorkstationByQRCode = (workstationQRCode) => {
  return axios.post(`${workstationAPIBase}/assign`, { workstationQRCode }).then(unboxAxiosResponse);
};
