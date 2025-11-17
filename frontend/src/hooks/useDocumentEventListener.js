import { useEffect } from 'react';

export const useDocumentEventListener = (type, listener) => {
  useEffect(() => {
    document.addEventListener(type, listener);
    return () => {
      document.removeEventListener(type, listener);
    };
  }, []);
};
