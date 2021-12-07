import React from 'react';
import { useParams } from 'react-router-dom';

import PickStepScanScreen from '../picking/PickStepScanScreen';
import ManufacturingReceiptScanScreen from '../manufacturing/ManufacturingReceiptScanScreen';
import RawMaterialIssueScanScreen from '../manufacturing/RawMaterialIssueScanScreen';

const getStepComponent = (appId, stepId) => {
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
  const { appId, stepId } = useParams();
  const { Component, modifier } = getStepComponent(appId, stepId);

  return (
    <>
      <Component {...props} modifier={!!modifier} />
    </>
  );
};

export default StepScanScreen;
