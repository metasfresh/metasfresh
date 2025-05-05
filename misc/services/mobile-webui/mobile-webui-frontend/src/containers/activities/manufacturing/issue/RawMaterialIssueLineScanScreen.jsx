import React from 'react';
import { useRouteMatch } from 'react-router-dom';
import RawMaterialIssueStepScanComponent from './step_scan/RawMaterialIssueStepScanComponent';

const RawMaterialIssueLineScanScreen = () => {
  const {
    params: { workflowId: wfProcessId, activityId, lineId },
  } = useRouteMatch();

  return <RawMaterialIssueStepScanComponent wfProcessId={wfProcessId} activityId={activityId} lineId={lineId} />;
};

export default RawMaterialIssueLineScanScreen;
