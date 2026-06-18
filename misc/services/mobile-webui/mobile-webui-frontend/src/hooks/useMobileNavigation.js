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
      // and is also swallowed by the device-back trap (see useDeviceBackButton). Go Home instead.
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
        // Multi-step browser-history jumps don't map to this app's replace-based navigation
        // (and would be swallowed by the device-back trap). Go Home instead.
        console.warn('Multi-step browser history navigation is not supported; navigating Home.', { delta });
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
