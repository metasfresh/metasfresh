import { useEffect, useRef } from 'react';

/**
 * @function useWhyDidYouUpdate
 * @module useWhyDidYouUpdate
 * React hook for checking render reasons in functional components
 */
function useWhyDidYouUpdate(name, props) {
  // Get a mutable ref object where we can store props ...
  // ... for comparison next time this hook runs.
  const previousProps = useRef();
  useEffect(() => {
    if (previousProps.current) {
      logWhyDidYouUpdate(name, props, previousProps.current);
    }
    // Finally update previousProps with current props for next hook call
    previousProps.current = props;
  });
}

export const logWhyDidYouUpdate = (name, props, previousProps) => {
  const changedProps = computeChangedProps(props, previousProps);
  const changedPropNames = Object.keys(changedProps);
  if (!changedPropNames.length) {
    return;
  }

  const changedPropNamesStr = changedPropNames.join(', ');
  console.log('[why-did-you-update]', name, changedPropNamesStr, changedProps);
};

const computeChangedProps = (props, previousProps) => {
  // Get all keys from previous and current props
  const allKeys = Object.keys({ ...previousProps, ...props });
  // Use this object to keep track of changed props
  const changedProps = {};
  // Iterate through keys
  allKeys.forEach((key) => {
    // If previous is different from current
    if (previousProps[key] !== props[key]) {
      // Add to changesObj
      changedProps[key] = {
        from: previousProps[key],
        to: props[key],
      };
    }
  });

  return changedProps;
};

// noinspection JSUnusedGlobalSymbols
export default useWhyDidYouUpdate;
