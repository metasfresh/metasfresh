import {
  getLineByIdFromActivity,
  getQtyRejectedReasonsForStep,
  getScaleDeviceFromActivity,
  getStepByIdFromLine,
} from '../../../../../reducers/wfProcesses';

export const computeStepScanPropsFromActivity = ({ activity, lineId, stepId, isProcessedQtyStillOnScale }) => {
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

  const qtyToIssueMax =
    isWeightable && isProcessedQtyStillOnScale
      ? Math.max(lineQtyToIssueMax, 0)
      : Math.max(lineQtyToIssueMax - lineQtyIssued, 0);

  const qtyAlreadyOnScale = isWeightable && isProcessedQtyStillOnScale ? Math.max(lineQtyIssued, 0) : undefined;
  //qtyToIssueMax = Math.min(qtyToIssueMax, qtyHUCapacity); // allow exceeding the HU capacity

  const lineQtyToIssueRemaining = Math.max(lineQtyToIssue - lineQtyIssued, 0);
  const qtyToIssueTarget = Math.min(stepQtyToIssue, lineQtyToIssueRemaining, qtyToIssueMax, qtyHUCapacity);

  console.log('RawMaterialIssueStepScanScreen.getPropsFromState', {
    qtyToIssueTarget,
    qtyToIssueMax,
    qtyHUCapacity,
    qtyAlreadyOnScale,
    //
    line,
    step,
    //
    lineQtyToIssueMax,
    lineQtyToIssueRemaining,
    lineQtyIssued,
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
    lineQtyIssued,
    isWeightable,
    qtyAlreadyOnScale,
    // Offered on EVERY step: an HU holding more than the step needs is exactly the case an operator
    // must be able to declare not-found / damaged / emptied on, so the list is never suppressed by
    // the step's target being smaller than the HU's content. A reason is still ASKED FOR only once
    // the entered qty falls short of the target (`qtyRejected > 0` in GetQuantityDialog.jsx).
    //
    // The step's own `allowEmptying` flag stays authoritative for the "empty (auto. inventory)" entry
    // — the activity-wide list may still carry it for a step whose source HU must refuse it (e.g. a
    // pallet's primary LU step). See JsonRawMaterialsIssueLineStep#isAllowEmptying (wire key
    // `allowEmptying`).
    qtyRejectedReasons: getQtyRejectedReasonsForStep(activity, step),
    scaleDevice: isWeightable ? getScaleDeviceFromActivity(activity) : null,
    scaleTolerance: isWeightable ? step.scaleTolerance : null,
  };
};
