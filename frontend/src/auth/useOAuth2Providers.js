import { useEffect, useState } from 'react';
import { getOAuth2Providers, getOAuth2ProviderUrl } from '../api/login';

export const useOAuth2Providers = () => {
  const [providers, setProviders] = useState([]);

  useEffect(() => {
    getOAuth2Providers().then((providers) => {
      setProviders(providers?.list ?? []);
    });
  }, []);

  return {
    providers,
    onOAuthLoginRequest: ({ providerCode }) => {
      if (!providerCode) {
        console.warn(`Invalid provider: ${providerCode}`);
        return;
      }

      window.location.href = getOAuth2ProviderUrl({ providerCode });
    },
  };
};
