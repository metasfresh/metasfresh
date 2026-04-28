import { useEffect } from 'react';

export const useWindowEventListener = (type, listener) => {
  useEffect(() => {
    window.addEventListener(type, listener);
    return () => {
      window.removeEventListener(type, listener);
    };
  }, []);
};
