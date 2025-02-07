import { useHistory, useLocation, useRouteMatch } from 'react-router-dom';
import { useBackLocationFromHeaders } from '../reducers/headers';

export const useMobileNavigation = ({ backLocation: backLocationParam } = {}) => {
  const history = useHistory();
  const location = useLocation();
  const backLocationFromHeaders = useBackLocationFromHeaders();
  const backLocation = backLocationParam ?? backLocationFromHeaders;
  //console.log(`useMobileNavigation: back location ${backLocation}`, { backLocationParam, backLocationFromHeaders });

  const {
    params: { applicationId, workflowId: wfProcessId, activityId, lineId, stepId, altStepId },
  } = useRouteMatch();

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
    // console.log(`useMobileNavigation: goTo ${locationEff}`);
    history.replace(locationEff);
  };

  const goBack = () => {
    // console.trace('useMobileNavigation: goBack', { backLocation });
    if (backLocation) {
      goTo(backLocation);
    } else {
      console.warn('Going back to previous location using history.go(-1) because backLocation is not provided.');
      history.go(-1);
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
        console.warn('Going back more than one step is not supported yet.');
        history.go(delta);
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
