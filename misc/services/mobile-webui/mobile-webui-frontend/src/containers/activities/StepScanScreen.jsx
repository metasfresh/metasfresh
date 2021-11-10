import React from 'react';
import { useParams } from 'react-router-dom';

import PickStepScanScreen from './picking/PickStepScanScreen';
import DistributionStepScanScreen from './distribution/DistributionStepScanScreen';

const getStepComponent = (appId, locator) => {
  switch (appId) {
    case 'picking':
      return { Component: PickStepScanScreen };
    case 'distribution':
      return { Component: DistributionStepScanScreen, modifier: locator };
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
