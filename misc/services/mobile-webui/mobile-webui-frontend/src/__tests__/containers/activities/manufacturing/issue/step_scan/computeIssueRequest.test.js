import { computeIssueRequest } from '../../../../../../containers/activities/manufacturing/issue/step_scan/computeIssueRequest';

const REASON_EMPTIED = 'E';

const call_computeIssueRequest = ({ qty, qtyRejected = 0, reason = null, resolvedBarcodeData }) => {
  return computeIssueRequest({
    qty,
    qtyRejected,
    reason,
    resolvedBarcodeData: {
      stepId: 'S1',
      isWeightable: true,
      // the step was offered as a whole-HU step (qtyToIssueTarget >= qtyHUCapacity)
      isIssueWholeHU: true,
      uom: 'kg',
      qtyHUCapacity: 0.5,
      ...resolvedBarcodeData,
    },
  });
};

describe('computeIssueRequest', () => {
  it('sends the entered qty as the HU gross weight when the operator weighed the whole HU', () => {
    const result = call_computeIssueRequest({ qty: 0.5 });

    expect(result.huWeightGrossBeforeIssue).toEqual(0.5);
    expect(result.qtyIssued).toEqual(0.5);
    expect(result.stepId).toEqual('S1');
  });

  it('sends NO weight when the operator entered less than the HU capacity and declared the rest emptied', () => {
    const result = call_computeIssueRequest({ qty: 0.498, qtyRejected: 0.002, reason: REASON_EMPTIED });

    // A short entry is not a weighing of the whole container: sending it as the gross weight would make
    // the backend re-weigh the HU down to 0.498 BEFORE the issue, the issue would then consume
    // everything and the leftover write-off the reason asked for would never happen.
    expect(result.huWeightGrossBeforeIssue).toBeNull();
    expect(result.qtyIssued).toEqual(0.498);
    // ...while the reason still rides on the offer-time whole-HU flag and reaches the backend:
    expect(result.qtyRejected).toEqual(0.002);
    expect(result.qtyRejectedReasonCode).toEqual(REASON_EMPTIED);
  });

  it('sends NO weight when the step is not denominated in kg, even for a full HU', () => {
    const result = call_computeIssueRequest({
      qty: 5,
      resolvedBarcodeData: { uom: 'PCE', qtyHUCapacity: 5 },
    });

    expect(result.huWeightGrossBeforeIssue).toBeNull();
    expect(result.qtyIssued).toEqual(5);
  });

  it('sends NO weight for a non-weightable line', () => {
    const result = call_computeIssueRequest({ qty: 0.5, resolvedBarcodeData: { isWeightable: false } });

    expect(result.huWeightGrossBeforeIssue).toBeNull();
  });

  it('drops qtyRejected and its reason when the step was not offered as a whole-HU step', () => {
    const result = call_computeIssueRequest({
      qty: 0.2,
      qtyRejected: 0.3,
      reason: REASON_EMPTIED,
      resolvedBarcodeData: { isIssueWholeHU: false },
    });

    expect(result.qtyRejected).toEqual(0);
    expect(result.qtyRejectedReasonCode).toBeNull();
    expect(result.huWeightGrossBeforeIssue).toBeNull();
  });
});
