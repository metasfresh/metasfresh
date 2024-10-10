import { useEffect } from 'react';

const useKeyDown = (onKeyDown) => {
  useEffect(() => {
    window.addEventListener('keydown', onKeyDown);

    return () => {
      window.removeEventListener('keydown', onKeyDown);
    };
  }, []);
};

export default useKeyDown;
