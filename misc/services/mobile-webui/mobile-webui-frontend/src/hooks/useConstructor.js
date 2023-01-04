import { useRef } from 'react';

/**
 * @function useConstructor
 * @module useConstructor
 * React hook replacing the class `constructor` in functional components. Called before
 * the component is rendered.
 */
const useConstructor = (callBack = () => {}) => {
  const hasBeenCalled = useRef(false);
  if (hasBeenCalled.current) return;
  callBack();
  hasBeenCalled.current = true;
};

export default useConstructor;
