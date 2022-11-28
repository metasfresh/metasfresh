import {
  getLineByIdFromActivity,
  getQtyRejectedReasonsFromActivity,
  getScaleDeviceFromActivity,
  getStepByIdFromLine,
} from '../../../../../reducers/wfProcesses';

export const computeStepScanPropsFromActivity = ({ activity, lineId, stepId }) => {
  const line = getLineByIdFromActivity(activity, lineId);
  const step = getStepByIdFromLine(line, stepId);

  const lineQtyToIssue = line.qtyToIssue;
  const lineQtyToIssueMax = Math.max(line.qtyToIssueMax, lineQtyToIssue);
  const lineQtyIssued = line.qtyIssued;
  const lineQtyToIssueTolerance = line.qtyToIssueTolerance;
  const isWeightable = !!line.weightable;

  const uom = step.uom;
  const stepQtyToIssue = step.qtyToIssue;
  const qtyHUCapacity = step.qtyHUCapacity;

  const qtyToIssueMax = Math.max(lineQtyToIssueMax - lineQtyIssued, 0);
  //qtyToIssueMax = Math.min(qtyToIssueMax, qtyHUCapacity); // allow exceeding the HU capacity

  const lineQtyToIssueRemaining = Math.max(lineQtyToIssue - lineQtyIssued, 0);
  const qtyToIssueTarget = Math.min(stepQtyToIssue, lineQtyToIssueRemaining, qtyToIssueMax);

  const isIssueWholeHU = qtyToIssueTarget >= qtyHUCapacity;

  console.log('RawMaterialIssueStepScanScreen.getPropsFromState', {
    qtyToIssueTarget,
    qtyToIssueMax,
    qtyHUCapacity,
    isIssueWholeHU,
    //
    line,
    step,
    //
    lineQtyToIssueMax,
    lineQtyToIssueRemaining,
    lineQtyToIssue,
    stepQtyToIssue,
  });

  return {
    huQRCode: step.huQRCode,
    uom,
    qtyToIssueTarget,
    qtyToIssueMax,
    qtyHUCapacity,
    lineQtyToIssue,
    lineQtyToIssueTolerance,
    lineQtyToIssueRemaining,
    isWeightable,
    isIssueWholeHU,
    qtyRejectedReasons: isIssueWholeHU ? getQtyRejectedReasonsFromActivity(activity) : null,
    scaleDevice: isWeightable ? getScaleDeviceFromActivity(activity) : null,
  };
};
