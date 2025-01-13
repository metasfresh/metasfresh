import { useLocationChange } from '../../hooks/useLocationChange';
import * as uiTrace from '../../utils/ui_trace';

export const useUITraceLocationChange = () => {
  useLocationChange(({ newLocation, prevLocation }) => {
    uiTrace.trace({
      eventName: 'locationChanged',
      newLocation,
      prevLocation,
    });
  });
};
