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
    // ...while the rejected qty and its reason always reach the backend:
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

  it('sends the weight when the typed qty reaches the capacity, and the rejection alongside it', () => {
    // The weight is decided by the TYPED qty alone: typing up to the HU's capacity IS a weighing of
    // the whole container. The rejection is independent of it and travels regardless.
    const result = call_computeIssueRequest({
      qty: 0.5,
      qtyRejected: 0.1,
      reason: REASON_EMPTIED,
    });

    expect(result.huWeightGrossBeforeIssue).toEqual(0.5);
    expect(result.qtyRejected).toEqual(0.1);
    expect(result.qtyRejectedReasonCode).toEqual(REASON_EMPTIED);
  });

  it('sends qtyRejected and its reason on a step that takes only part of the HU', () => {
    // HU capacity 0.5, target below it: the reason list is offered on every step now, so what the
    // operator declared must reach the backend here too (it used to be silently dropped).
    const result = call_computeIssueRequest({
      qty: 0.2,
      qtyRejected: 0.3,
      reason: REASON_EMPTIED,
    });

    expect(result.qtyRejected).toEqual(0.3);
    expect(result.qtyRejectedReasonCode).toEqual(REASON_EMPTIED);
    expect(result.huWeightGrossBeforeIssue).toBeNull();
  });
});
