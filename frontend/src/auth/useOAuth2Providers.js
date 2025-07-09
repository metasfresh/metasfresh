import { useEffect, useState } from 'react';
import { getOAuth2Providers } from '../api/login';

export const useOAuth2Providers = () => {
  const [providers, setProviders] = useState([]);

  useEffect(() => {
    getOAuth2Providers().then((providers) => {
      setProviders(providers?.list ?? []);
    });
  }, []);

  return { providers };
};
