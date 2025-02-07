import React from 'react';
import { useSelector } from 'react-redux';

import { trl } from '../../../../utils/translations';
import {
  getActivityById,
  getLineByIdFromActivity,
  getQtyRejectedReasonsFromActivity,
  getScaleDeviceFromActivity,
} from '../../../../reducers/wfProcesses';

import ScanHUAndGetQtyComponent from '../../../../components/ScanHUAndGetQtyComponent';
import { useScreenDefinition } from '../../../../hooks/useScreenDefinition';
import { issueAdjustmentLineScreenLocation } from '../../../../routes/manufacturing_issue_adjustment';

const IssueAdjustmentScanScreen = () => {
  const { history, wfProcessId, activityId, lineId, stepId } = useScreenDefinition({
    back: issueAdjustmentLineScreenLocation,
  });

  const { uom, qtyRejectedReasons, scaleDevice } = useSelector((state) =>
    getPropsFromState({ state, wfProcessId, activityId, lineId, stepId })
  );

  const onResult = ({ qty = 0, reason = null }) => {
    console.log('onResult', { qty, reason });
    history.goBack();
  };

  return (
    <ScanHUAndGetQtyComponent
      qtyTargetCaption={trl('general.QtyToPick')}
      uom={uom}
      qtyRejectedReasons={qtyRejectedReasons}
      scaleDevice={scaleDevice}
      // Callbacks:
      onResult={onResult}
    />
  );
};

const getPropsFromState = ({ state, wfProcessId, activityId, lineId }) => {
  const activity = getActivityById(state, wfProcessId, activityId);
  const rawMaterialsIssueActivityId = activity.dataStored.rawMaterialsIssueActivityId;
  const rawMaterialsIssueActivity = getActivityById(state, wfProcessId, rawMaterialsIssueActivityId);
  const line = getLineByIdFromActivity(rawMaterialsIssueActivity, lineId);

  return {
    uom: line.uom,
    qtyRejectedReasons: getQtyRejectedReasonsFromActivity(activity),
    scaleDevice: getScaleDeviceFromActivity(activity),
  };
};

export default IssueAdjustmentScanScreen;
