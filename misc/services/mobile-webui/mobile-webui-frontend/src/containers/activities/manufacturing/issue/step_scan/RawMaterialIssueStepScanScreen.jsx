import React from 'react';
import RawMaterialIssueStepScanComponent from './RawMaterialIssueStepScanComponent';
import { useScreenDefinition } from '../../../../../hooks/useScreenDefinition';
import { manufacturingStepScreenLocation } from '../../../../../routes/manufacturing_issue';

const RawMaterialIssueStepScanScreen = () => {
  const { wfProcessId, activityId, lineId, stepId } = useScreenDefinition({
    screenId: 'RawMaterialIssueStepScanScreen',
    back: manufacturingStepScreenLocation,
  });

  return (
    <RawMaterialIssueStepScanComponent
      wfProcessId={wfProcessId}
      activityId={activityId}
      lineId={lineId}
      stepId={stepId}
    />
  );
};

export default RawMaterialIssueStepScanScreen;
