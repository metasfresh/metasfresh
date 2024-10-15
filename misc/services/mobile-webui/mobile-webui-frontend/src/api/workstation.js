import { apiBasePath } from '../constants';
import axios from 'axios';
import { unboxAxiosResponse } from '../utils';
import { useApplicationInfo } from '../reducers/applications';
import { useEffect, useState } from 'react';
import { toastError } from '../utils/toast';

const workstationAPIBase = `${apiBasePath}/workstation`;

export const getCurrentWorkstationInfo = () => {
  return axios.get(`${workstationAPIBase}`).then(unboxAxiosResponse);
};

export const useCurrentWorkstation = ({ applicationId }) => {
  const { requiresWorkstation: requiresWorkstationIfAvailable } = useApplicationInfo({ applicationId });
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
  }, []);

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

export const assignWorkstationById = (workstationId) => {
  return axios.post(`${workstationAPIBase}/assign`, { workstationId }).then(unboxAxiosResponse);
};

const assignWorkstationByQRCode = (workstationQRCode) => {
  return axios.post(`${workstationAPIBase}/assign`, { workstationQRCode }).then(unboxAxiosResponse);
};
