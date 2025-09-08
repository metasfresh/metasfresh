import { useMemo } from 'react';
import { debounce } from 'lodash';

export const useDebounce = (func, waitMillis, deps = []) => {
  return useMemo(() => debounce(func, waitMillis), [...deps, waitMillis]);
};
