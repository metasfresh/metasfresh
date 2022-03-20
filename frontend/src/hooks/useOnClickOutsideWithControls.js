import { useRef } from 'react';
import useOnClickOutside from 'use-onclickoutside';

export const useOnClickOutsideWithControls = (onClickOutside) => {
  const clickOutsideComponentRef = useRef(null);
  const clickOutsideEnabledRef = useRef(true);

  useOnClickOutside(clickOutsideComponentRef, () => {
    if (clickOutsideEnabledRef.current) {
      onClickOutside();
    }
  });

  return {
    clickOutsideComponentRef,
    enableOnClickOutside: () => {
      clickOutsideEnabledRef.current = true;
    },
    disableOnClickOutside: () => {
      clickOutsideEnabledRef.current = false;
    },
  };
};
