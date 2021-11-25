import React from 'react';
import { useParams } from 'react-router-dom';

import PickStepScanScreen from '../picking/PickStepScanScreen';
import ManufacturingReceiptScanScreen from '../manufacturing/ManufacturingReceiptScanScreen';
import RawMaterialIssueScanScreen from '../manufacturing/RawMaterialIssueScanScreen';

// if locator param is defined, Scanner component will be initiated to scan locator instead of HU
const getStepComponent = (appId, locator, stepId) => {
  switch (appId) {
    case 'picking':
      return { Component: PickStepScanScreen };
    case 'mfg':
      if (stepId === 'receipt') {
        return { Component: ManufacturingReceiptScanScreen };
      }
      return { Component: RawMaterialIssueScanScreen };
    default:
      return null;
  }
};

const StepScanScreen = (props) => {
  const { appId, locatorId, stepId } = useParams();
  const { Component, modifier } = getStepComponent(appId, locatorId, stepId);

  return (
    <>
      <Component {...props} modifier={!!modifier} />
    </>
  );
};

export default StepScanScreen;
