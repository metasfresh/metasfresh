import { useEffect } from 'react';

const useEscapeKey = (onKeyPress) => {
  useEffect(() => {
    const handleKeyDown = (event) => {
      if (event.keyCode === 27) onKeyPress();
    };
    window.addEventListener('keydown', handleKeyDown);

    return () => {
      window.removeEventListener('keydown', handleKeyDown);
    };
  }, []);
};

export default useEscapeKey;
