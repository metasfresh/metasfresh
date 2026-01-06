import { useApplicationInfo } from '../reducers/applications';
import { apiBasePath } from '../constants';
import axios from 'axios';
import { unboxAxiosResponse } from '../utils';
import { useQuery } from '../hooks/useQuery';
import { useState } from 'react';

const trolleyAPIBase = `${apiBasePath}/userWorkflows/trolley`;

export const useCurrentTrolley = ({ applicationId }) => {
  const { requiresTrolley: isTrolleyRequired } = useApplicationInfo({ applicationId });
  const [trolley, setTrolley] = useState();

  const { isPending: isTrolleyLoading } = useQuery({
    queryFn: getCurrentTrolleyInfo,
    onSuccess: (data) => {
      setTrolley(data?.trolley ?? null);
    },
  });

  return {
    isTrolleyRequired: !!isTrolleyRequired,
    isTrolleyLoading,
    trolley: isTrolleyLoading ? null : trolley,
    setTrolleyByScannedCode: (scannedCode) => {
      return postCurrentTrolley({ scannedCode }).then((data) => setTrolley(data?.trolley ?? null));
    },
    clearTrolley: () => {
      setTrolley(null);
    },
  };
};

export const getCurrentTrolleyInfo = () => {
  return axios.get(`${trolleyAPIBase}`).then(unboxAxiosResponse);
};

export const postCurrentTrolley = ({ scannedCode }) => {
  return axios.post(`${trolleyAPIBase}`, { scannedCode }).then(unboxAxiosResponse);
};
