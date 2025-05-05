export const useSearchParams = () => {
  const urlParams = new URLSearchParams(window.location.search);

  // NOTE: keep return similar to https://reactrouter.com/en/6.22.3/hooks/use-search-params
  return [urlParams];
};
