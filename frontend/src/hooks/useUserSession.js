import { useSelector } from 'react-redux';

export const useUserSession = () => {
  return useSelector((state) => state?.appHandler?.me ?? {});
};
