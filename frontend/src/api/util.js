export const getApiBaseUrl = () => {
  const API_URL = config.API_URL;
  const suffix = '/rest/api';

  return API_URL && API_URL.endsWith(suffix)
    ? API_URL.slice(0, -suffix.length)
    : API_URL;
};
