/**
 * Maps what the operator entered on the scan/qty screen to the manufacturing-issue request payload.
 *
 * Pure on purpose: the mapping carries the two predicates below, and both are easier to get wrong than
 * to read, so they are unit-tested instead of being buried in the component's `onResult`.
 *
 * @param qty quantity the operator entered (already validated by ScanHUAndGetQtyComponent)
 * @param qtyRejected quantity the operator declared as not issued (leftover/damaged/...)
 * @param reason the qtyRejected reason code, or null
 * @param resolvedBarcodeData props computed by computeStepScanPropsFromActivity for the scanned step
 */
export const computeIssueRequest = ({ qty = 0, qtyRejected = 0, reason = null, resolvedBarcodeData }) => {
  const isWeightable = !!resolvedBarcodeData.isWeightable;

  // The whole-HU decision must be the one made when the step was offered (target vs the HU's own
  // capacity - computeStepScanPropsFromActivity.js), not recomputed from the qty the operator
  // actually typed: typing less than capacity is exactly the "issue a partial amount with a
  // reason" case (not found / damaged / empty), and recomputing from the entered qty always
  // evaluates false there, silently dropping qtyRejected/qtyRejectedReasonCode before they reach
  // the backend.
  const isIssueWholeHU = !!resolvedBarcodeData.isIssueWholeHU;

  // ...but the WEIGHT field answers a different question: "did the operator physically weigh the whole
  // container?" That is a fact about what was actually measured, so it is decided by the TYPED qty, not
  // by the offer-time flag above. Entering less than the HU's capacity is a short entry, not a weighing
  // of the whole container: sending it as the gross weight makes the backend re-weigh the HU down to
  // that qty BEFORE the issue, the issue then consumes everything, and the leftover write-off that the
  // rejected-qty reason asked for never happens.
  // Equivalent to the predicate of https://github.com/metasfresh/metasfresh/pull/25716 (merged to
  // new_dawn_uat as d58d99fa1c0), `isWeightable && isIssueWholeHU && uom === 'kg'`, whose
  // `isIssueWholeHU` is the submit-time `qty >= qtyHUCapacity` one; that commit is not an ancestor of
  // task_force_hotfix, so keeping the predicates equivalent is what stops a third divergent variant of
  // these lines colliding at the next merge-up.
  const isQtyEnteredAsWeight = resolvedBarcodeData.uom === 'kg';
  // qtyHUCapacity is `@NonNull` on the wire (JsonRawMaterialsIssueLineStep), so no null guard here.
  const isWeighedFullHU = isQtyEnteredAsWeight && qty >= resolvedBarcodeData.qtyHUCapacity;

  return {
    stepId: resolvedBarcodeData.stepId,
    huWeightGrossBeforeIssue: isWeightable && isWeighedFullHU ? qty : null,
    qtyIssued: qty,
    qtyRejected: isIssueWholeHU ? qtyRejected : 0,
    qtyRejectedReasonCode: isIssueWholeHU ? reason : null,
  };
};
