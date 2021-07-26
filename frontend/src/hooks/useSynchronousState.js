import { useState } from 'react';

/**
 * @function useConstructor
 * @module useConstructor
 * React hook to be used instead of the built-in `useState`, when synchronous values
 * are needed.
 */
const useSynchronousState = (initialValue) => {
  const [value, updateValue] = useState(initialValue);
  let latestValue = value;

  const get = () => latestValue;

  const set = (newValue) => {
    latestValue = newValue;
    updateValue(newValue);
    return latestValue;
  };

  return [get, set];
};

export default useSynchronousState;
