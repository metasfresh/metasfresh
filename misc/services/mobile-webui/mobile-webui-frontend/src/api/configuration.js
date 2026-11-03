import axios from 'axios';
import { apiBasePath } from '../constants';
import { toUrl, unboxAxiosResponse } from '../utils';
import { useQuery } from '../hooks/useQuery';

export const getMobileConfiguration = () => {
  return axios.get(`${apiBasePath}/public/mobile/config`).then((response) => unboxAxiosResponse(response));
};

export const useMobileConfiguration = ({ onSuccess } = {}) => {
  const { isPending, data } = useQuery({
    queryFn: () => getMobileConfiguration(),
    onSuccess,
  });

  return { isConfigLoading: isPending, config: data };
};

export const getMessages = ({ lang } = {}) => {
  return axios
    .get(toUrl(`${apiBasePath}/public/mobile/messages`, { lang }))
    .then((response) => unboxAxiosResponse(response));
};
