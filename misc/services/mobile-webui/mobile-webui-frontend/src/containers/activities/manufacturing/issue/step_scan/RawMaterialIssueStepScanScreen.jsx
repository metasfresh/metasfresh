import React from 'react';
import { useRouteMatch } from 'react-router-dom';
import RawMaterialIssueStepScanComponent from './RawMaterialIssueStepScanComponent';

const RawMaterialIssueStepScanScreen = () => {
  const {
    params: { workflowId: wfProcessId, activityId, lineId, stepId },
  } = useRouteMatch();

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
