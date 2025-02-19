import React from 'react';
import RawMaterialIssueStepScanComponent from './step_scan/RawMaterialIssueStepScanComponent';
import { useScreenDefinition } from '../../../../hooks/useScreenDefinition';
import { manufacturingLineScreenLocation } from '../../../../routes/manufacturing_issue';

const RawMaterialIssueLineScanScreen = () => {
  const { wfProcessId, activityId, lineId } = useScreenDefinition({ back: manufacturingLineScreenLocation });

  return <RawMaterialIssueStepScanComponent wfProcessId={wfProcessId} activityId={activityId} lineId={lineId} />;
};

export default RawMaterialIssueLineScanScreen;
