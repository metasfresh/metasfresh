import { useState } from 'react';

// simplified version of https://www.npmjs.com/package/@toolz/use-synchronous-state
// because it requires react 17 and webpack 5
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
