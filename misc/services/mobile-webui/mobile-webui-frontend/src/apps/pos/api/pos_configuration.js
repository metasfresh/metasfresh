import axios from 'axios';
import { useEffect, useState } from 'react';
import { apiBasePath } from '../../../constants';
import { unboxAxiosResponse } from '../../../utils';

export const usePOSConfiguration = () => {
  const [isLoading, setLoading] = useState(false);
  const [config, setConfig] = useState([]);

  useEffect(() => {
    setLoading(true);
    getConfig()
      .then(setConfig)
      .finally(() => setLoading(false));
  }, []);

  return isLoading ? { isLoading: true } : config;
};

const getConfig = () => {
  return axios.get(`${apiBasePath}/pos/config`).then((response) => unboxAxiosResponse(response));
};
