import { useDispatch } from 'react-redux';
import { useRouteMatch } from 'react-router-dom';
import { useEffect } from 'react';
import { pushHeaderEntry } from '../actions/HeaderActions';
import { trl } from '../utils/translations';
import { useMobileNavigation } from './useMobileNavigation';

export const useScreenDefinition = ({ captionKey, values, isHomeStop, back } = {}) => {
  const dispatch = useDispatch();
  const history = useMobileNavigation();
  const {
    url,
    params: { applicationId, workflowId: wfProcessId, activityId, lineId, stepId, altStepId },
  } = useRouteMatch();

  useEffect(() => {
    dispatch(
      pushHeaderEntry({
        location: url,
        caption: computeCaption({ captionKey }),
        values,
        isHomeStop,
        backLocation: computeBackLocation({ back, applicationId, wfProcessId, activityId, lineId, stepId, altStepId }),
      })
    );
  }, [url]);

  return {
    history,
    //
    url,
    applicationId,
    wfProcessId,
    activityId,
    lineId,
    stepId,
    altStepId,
  };
};

const computeCaption = ({ captionKey }) => {
  if (captionKey === undefined) {
    return undefined;
  } else {
    return captionKey ? trl(captionKey) : undefined;
  }
};
const computeBackLocation = ({ back, ...params }) => {
  if (back === undefined) {
    return undefined;
  } else if (back == null) {
    return null;
  } else if (typeof back === 'function') {
    return back?.({ ...params });
  } else if (typeof back === 'string') {
    return back;
  } else {
    console.warn('Unknown back value provided. Returning null.', { back, ...params });
    return null;
  }
};
