import axios from 'axios';
import { useEffect, useState } from 'react';
import { apiBasePath } from '../../../constants';
import { unboxAxiosResponse } from '../../../utils';

export const usePOSTerminal = () => {
  const [isLoading, setLoading] = useState(false);
  const [posTerminal, setPOSTerminal] = useState([]);

  useEffect(() => {
    setLoading(true);
    getPOSTerminal()
      .then(setPOSTerminal)
      .finally(() => setLoading(false));
  }, []);

  return isLoading ? { isLoading: true } : posTerminal;
};

const getPOSTerminal = () => {
  return axios.get(`${apiBasePath}/pos/terminal`).then((response) => unboxAxiosResponse(response));
};
