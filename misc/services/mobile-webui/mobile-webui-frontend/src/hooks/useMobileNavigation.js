import { useHistory, useRouteMatch } from 'react-router-dom';
import { useBackLocationFromHeaders } from '../reducers/headers';

export const useMobileNavigation = () => {
  const history = useHistory();
  const backLocation = useBackLocationFromHeaders();

  const {
    params: { applicationId, workflowId: wfProcessId, activityId, lineId, stepId, altStepId },
  } = useRouteMatch();

  // console.log('useMobileNavigation: backLocation', backLocation);

  const goTo = (location) => {
    const locationEff = parseLocation({ location, applicationId, wfProcessId, activityId, lineId, stepId, altStepId });
    // console.log(`useMobileNavigation: goTo ${locationEff}`);
    history.replace(locationEff);
  };

  const goBack = () => {
    // console.log('useMobileNavigation: goBack', { backLocation });
    if (backLocation) {
      goTo(backLocation);
    } else {
      console.warn('Going back to previous location using history.go(-1) because backLocation is not provided.');
      history.go(-1);
    }
  };

  return {
    push: (location) => {
      // console.log('useMobileNavigation.history.push', location);
      goTo(location);
    },
    replace: (location) => {
      // console.log('useMobileNavigation.history.replace', location);
      goTo(location);
    },
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
    goHome: () => goTo('/'),
  };
};

const parseLocation = ({ location, ...params }) => {
  if (typeof back === 'function') {
    return location({ ...params });
  } else if (typeof location === 'string') {
    return location;
  } else {
    console.warn('Unknown location provided. Returning null.', { location, ...params });
    return null;
  }
};
