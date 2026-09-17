import {
  getLineByIdFromActivity,
  getQtyRejectedReasonsFromActivity,
  getScaleDeviceFromActivity,
  getStepByIdFromLine,
} from '../../../../../reducers/wfProcesses';

export const computeStepScanPropsFromActivity = ({ activity, lineId, stepId, isProcessedQtyStillOnScale }) => {
  const line = getLineByIdFromActivity(activity, lineId);
  const step = getStepByIdFromLine(line, stepId);

  const lineUom = line.uom;
  const lineQtyToIssue = line.qtyToIssue;
  const lineQtyToIssueMax = Math.max(line.qtyToIssueMax, lineQtyToIssue);
  const lineQtyIssued = line.qtyIssued;
  const lineQtyToIssueTolerance = line.qtyToIssueTolerance;
  const isWeightable = !!line.weightable;

  const uom = step.uom;
  const stepQtyToIssue = step.qtyToIssue;
  const qtyHUCapacity = step.qtyHUCapacity;
  // Backend-provided: the BOM line's remaining demand (incl. tolerance) converted to this step's
  // stocking UOM and rounded UP. The Qty input is entered in the stocking UOM, so its ceiling must be
  // in that UOM too — the line-level `lineQtyToIssueMax`/`lineQtyIssued` are in the BOM line UOM (e.g. kg)
  // and cannot cap a Stk entry (that let a manual override issue e.g. 30 Stk against a 34.5 kg demand).
  const stepQtyToIssueMax = step.qtyToIssueMax;

  const qtyToIssueMax =
    isWeightable && isProcessedQtyStillOnScale
      ? Math.max(lineQtyToIssueMax, 0)
      : Math.max(stepQtyToIssueMax ?? lineQtyToIssueMax - lineQtyIssued, 0);

  const qtyAlreadyOnScale = isWeightable && isProcessedQtyStillOnScale ? Math.max(lineQtyIssued, 0) : undefined;
  //qtyToIssueMax = Math.min(qtyToIssueMax, qtyHUCapacity); // allow exceeding the HU capacity

  const lineQtyToIssueRemaining = Math.max(lineQtyToIssue - lineQtyIssued, 0);
  // For a weightable line the step UOM equals the line UOM (weight), so all three terms share a UOM.
  // Otherwise the target is capped purely in the stocking UOM: stepQtyToIssue and qtyToIssueMax are both
  // in the stocking UOM, while lineQtyToIssueRemaining is in the (different) BOM line UOM — mixing it in
  // would wrongly cap the target (e.g. undo the round-up to a whole stocking unit).
  const qtyToIssueTarget = isWeightable
    ? Math.min(stepQtyToIssue, lineQtyToIssueRemaining, qtyToIssueMax, qtyHUCapacity)
    : Math.min(stepQtyToIssue, qtyToIssueMax, qtyHUCapacity);

  const isIssueWholeHU = qtyToIssueTarget >= qtyHUCapacity;

  console.log('RawMaterialIssueStepScanScreen.getPropsFromState', {
    qtyToIssueTarget,
    qtyToIssueMax,
    qtyHUCapacity,
    isIssueWholeHU,
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
    lineUom,
    qtyToIssueTarget,
    qtyToIssueMax,
    qtyHUCapacity,
    lineQtyToIssue,
    lineQtyToIssueTolerance,
    lineQtyToIssueRemaining,
    lineQtyIssued,
    isWeightable,
    isIssueWholeHU,
    qtyAlreadyOnScale,
    qtyRejectedReasons: isIssueWholeHU ? getQtyRejectedReasonsFromActivity(activity) : null,
    scaleDevice: isWeightable ? getScaleDeviceFromActivity(activity) : null,
    scaleTolerance: isWeightable ? step.scaleTolerance : null,
  };
};
