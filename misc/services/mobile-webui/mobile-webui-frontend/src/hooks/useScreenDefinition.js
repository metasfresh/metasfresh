import { useDispatch } from 'react-redux';
import { useEffect } from 'react';
import { pushHeaderEntry } from '../actions/HeaderActions';
import { trl } from '../utils/translations';
import { useMobileNavigation } from './useMobileNavigation';
import { useMobileLocation } from './useMobileLocation';

export const useScreenDefinition = ({ screenId, captionKey, values, isHomeStop, back } = {}) => {
  const dispatch = useDispatch();
  const { url, applicationId, wfProcessId, activityId, lineId, stepId, altStepId, ...otherParams } =
    useMobileLocation();

  const backLocation = computeBackLocation({
    back,
    applicationId,
    wfProcessId,
    activityId,
    lineId,
    stepId,
    altStepId,
    ...otherParams,
  });

  const history = useMobileNavigation({ backLocation });

  useEffect(() => {
    dispatch(
      pushHeaderEntry({
        location: url,
        screenId,
        caption: computeCaption({ captionKey }),
        values,
        isHomeStop,
        backLocation,
      })
    );
  }, [url]);

  return {
    ...otherParams,
    url,
    applicationId,
    wfProcessId,
    activityId,
    lineId,
    stepId,
    altStepId,
    //
    history,
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
