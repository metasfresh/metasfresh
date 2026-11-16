import { useHistory, useLocation } from 'react-router-dom';
import { useBackLocationFromHeaders } from '../reducers/headers';
import { useMobileLocation } from './useMobileLocation';

export const useMobileNavigation = ({ backLocation: backLocationParam } = {}) => {
  const history = useHistory();
  const location = useLocation();
  const backLocationFromHeaders = useBackLocationFromHeaders();
  const backLocation = backLocationParam ?? backLocationFromHeaders;
  //console.log(`useMobileNavigation: back location ${backLocation}`, { backLocationParam, backLocationFromHeaders });

  const { applicationId, wfProcessId, activityId, lineId, stepId, altStepId } = useMobileLocation();

  const goTo = (location) => {
    const locationEff = parseLocation({
      location,
      applicationId,
      wfProcessId,
      activityId,
      lineId,
      stepId,
      altStepId,
    });
    // console.error(`*** useMobileNavigation: goTo ${locationEff}`);
    history.replace(locationEff);
  };

  const goBack = () => {
    // console.trace('useMobileNavigation: goBack', { backLocation });
    if (backLocation) {
      goTo(backLocation);
    } else {
      // No screen-declared back target. The app navigates with history.replace, so the browser
      // history stack does not reflect the logical screen flow — a history.go(-1) here is unreliable
      // (the browser stack is sentinel-managed by the device-back trap, see useDeviceBackButton).
      // Go Home instead.
      console.warn('No backLocation provided; navigating Home instead of browser back.');
      goHome();
    }
  };

  const goHome = () => goTo('/');

  const goToFromLocation = () => {
    const from = location?.state?.from?.pathname;
    if (from) {
      goTo(from);
    } else {
      console.warn('Going to home because from location is not available');
      goHome();
    }
  };

  return {
    push: goTo,
    replace: goTo,
    goTo,
    goBack,
    go: (delta) => {
      if (typeof delta === 'function' || typeof delta === 'string') {
        console.warn('Please use goTo instead of go', { delta });
        return goTo(delta);
      } else if (delta === -1) {
        goBack();
      } else {
        // Only single-step Back (delta === -1) maps to this app's replace-based navigation.
        // Forward (delta > 0) and multi-step Back don't (the browser stack is sentinel-managed by
        // the device-back trap and holds arrival order, not the logical flow). Go Home instead.
        console.warn('Only single-step back navigation is supported; navigating Home.', { delta });
        goHome();
      }
    },
    goHome,
    goToFromLocation,
  };
};

const parseLocation = ({ location, ...params }) => {
  if (typeof location === 'function') {
    return location({ ...params });
  } else if (typeof location === 'string') {
    return location;
  } else {
    console.warn('Unknown location provided. Returning null.', { location, ...params });
    return null;
  }
};
