import React from 'react';
import { useParams } from 'react-router-dom';

import PickStepScanScreen from './picking/PickStepScanScreen';
import DistributionStepScreen from './distribution/DistributionStepScreen';

const getStepComponent = (appId, locator) => {
  switch (appId) {
    case 'picking':
      return { Component: PickStepScanScreen };
    case 'distribution':
      return { Component: DistributionStepScreen, modifier: locator };
    default:
      return null;
  }
};

const StepScanScreen = (props) => {
  const { appId, locatorId } = useParams();
  const { Component, modifier } = getStepComponent(appId, locatorId);

  return (
    <>
      <Component {...props} modifier={!!modifier} />
    </>
  );
};

export default StepScanScreen;
