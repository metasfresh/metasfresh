import { useRef, useEffect } from 'react';

/**
 * @function usePrevious
 * @module usePrevious
 * React hook for getting the previous props value
 */
function usePrevious(value) {
  const ref = useRef();
  useEffect(() => {
    ref.current = value;
  });
  return ref.current;
}

export default usePrevious;
