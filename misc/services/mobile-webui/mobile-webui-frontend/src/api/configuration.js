import axios from 'axios';
import { apiBasePath } from '../constants';
import { unboxAxiosResponse } from '../utils';
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
