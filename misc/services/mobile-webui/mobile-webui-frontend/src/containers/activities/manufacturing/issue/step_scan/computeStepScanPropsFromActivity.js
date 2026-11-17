import {
  getLineByIdFromActivity,
  getQtyRejectedReasonsForStep,
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

  // An ALTERNATIVE issue step is a non-FEFO HU of the line's product that the plan did not pre-assign any
  // demand to (the first/earliest-expiring HU already covers the line), so it carries a ZERO target — in
  // the plan it is only a placeholder. FEFO is the DEFAULT suggestion, not a lock: when the operator scans
  // such an HU and it is BOTH emptying-eligible (`allowEmptying` — a single-product TU/VHU, not an
  // aggregate/LU) AND a free-standing top-level HU (`issueFromTopLevelHU` — not a TU nested on a pallet/LU,
  // which is issued/emptied via its pallet and stays a locked placeholder on this line, even though such a
  // TU is itself emptying-eligible), they may still use it — issue from it and/or declare it empty. Target is
  // then the HU's own content; the amount that may actually go to the line stays capped at the line's
  // remaining demand (`qtyToIssueMax` below → the over-issue guard), so issuing from it simply reallocates
  // the demand away from the FEFO-preferred HU. A non-eligible alternative keeps its zero placeholder.
  const isEngageableAlternativeHU =
    !!step.alternativeIssue && !!step.allowEmptying && !!step.issueFromTopLevelHU && qtyHUCapacity > 0;
  const effStepQtyToIssue = isEngageableAlternativeHU ? qtyHUCapacity : stepQtyToIssue;
  const effStepQtyToIssueMax = isEngageableAlternativeHU ? null : stepQtyToIssueMax;

  const qtyToIssueMax =
    isWeightable && isProcessedQtyStillOnScale
      ? Math.max(lineQtyToIssueMax, 0)
      : Math.max(effStepQtyToIssueMax ?? lineQtyToIssueMax - lineQtyIssued, 0);

  const qtyAlreadyOnScale = isWeightable && isProcessedQtyStillOnScale ? Math.max(lineQtyIssued, 0) : undefined;
  //qtyToIssueMax = Math.min(qtyToIssueMax, qtyHUCapacity); // allow exceeding the HU capacity

  const lineQtyToIssueRemaining = Math.max(lineQtyToIssue - lineQtyIssued, 0);
  // For a weightable line the step UOM equals the line UOM (weight), so all three terms share a UOM.
  // Otherwise the target is capped purely in the stocking UOM: stepQtyToIssue and qtyToIssueMax are both
  // in the stocking UOM, while lineQtyToIssueRemaining is in the (different) BOM line UOM — mixing it in
  // would wrongly cap the target (e.g. undo the round-up to a whole stocking unit).
  const qtyToIssueTarget = isWeightable
    ? Math.min(effStepQtyToIssue, lineQtyToIssueRemaining, qtyToIssueMax, qtyHUCapacity)
    : Math.min(effStepQtyToIssue, qtyToIssueMax, qtyHUCapacity);

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
    lineUom,
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
