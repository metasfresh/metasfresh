import { useAuth } from './useAuth';

export const useIsLoggedIn = () => {
  const auth = useAuth();
  return !!auth?.isLoggedIn;
};
