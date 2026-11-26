import { computeStepScanPropsFromActivity } from '../../../../../../containers/activities/manufacturing/issue/step_scan/computeStepScanPropsFromActivity';

const call_computeStepScanPropsFromActivity = ({ line, step }) => {
  return computeStepScanPropsFromActivity({
    activity: {
      dataStored: {
        lines: {
          L1: {
            qtyIssued: 0,
            weightable: true,
            ...line,
            steps: {
              S1: {
                huQRCode: 'blabla',
                uom: 'kg',
                ...step,
              },
            },
          },
        },
        qtyRejectedReasons: {
          reasons: [
            { key: 'R1', caption: 'Reason 1' },
            { key: 'E', caption: 'empty (auto. inventory)' },
          ],
        },
        scaleDevice: { key: 'scale01', caption: 'scale 1' },
      },
    },
    lineId: 'L1',
    stepId: 'S1',
  });
};

describe('computeStepScanPropsFromActivity', () => {
  it('line qty > step qty', () => {
    const result = call_computeStepScanPropsFromActivity({
      line: { qtyToIssue: 11, qtyToIssueMax: 12 },
      step: { qtyToIssue: 10, qtyHUCapacity: 10 },
    });
    console.log('result', result);
    expect(result.qtyToIssueTarget).toEqual(10);
    expect(result.qtyToIssueMax).toEqual(12);
  });

  // Stk-stocked HU issued against a kg BOM line: the Qty ceiling must be the backend-provided
  // step.qtyToIssueMax (remaining demand converted to the stocking UOM, rounded up = 1 Stk), NOT the
  // raw kg line max (34.5) — otherwise a manual override could issue 34 Stk against a 34.5 kg demand.
  it('non-weightable Stk step caps at the converted step.qtyToIssueMax, not the kg line max', () => {
    const result = call_computeStepScanPropsFromActivity({
      line: { weightable: false, uom: 'kg', qtyToIssue: 34.5, qtyToIssueMax: 34.5, qtyIssued: 0 },
      step: { uom: 'Stk', qtyToIssue: 1, qtyToIssueMax: 1, qtyHUCapacity: 40 },
    });
    expect(result.qtyToIssueMax).toEqual(1); // 1 Stk — not 34.5
    expect(result.qtyToIssueTarget).toEqual(1); // capped in Stk; the kg remaining (34.5) must not leak in
  });

  it('non-weightable step without a backend max falls back to the line remaining (same-UOM)', () => {
    const result = call_computeStepScanPropsFromActivity({
      line: { weightable: false, uom: 'kg', qtyToIssue: 10, qtyToIssueMax: 12, qtyIssued: 3 },
      step: { uom: 'kg', qtyToIssue: 5, qtyHUCapacity: 100 }, // no qtyToIssueMax
    });
    expect(result.qtyToIssueMax).toEqual(12 - 3); // fallback: line max (incl. tolerance) minus issued
    expect(result.qtyToIssueTarget).toEqual(5); // min(stepQtyToIssue, qtyToIssueMax)
  });

  it('line qty < step qty', () => {
    const result = call_computeStepScanPropsFromActivity({
      line: { qtyToIssue: 10, qtyToIssueMax: 15, qtyIssued: 3 },
      step: { qtyToIssue: 100, qtyHUCapacity: 100 },
    });
    console.log('result', result);
    expect(result.qtyToIssueTarget).toEqual(10 - 3);
    expect(result.qtyToIssueMax).toEqual(15 - 3);
  });

  it('qtyToIssueTarget is capped at qtyHUCapacity when step has more planned qty than actual HU capacity', () => {
    const result = call_computeStepScanPropsFromActivity({
      line: { qtyToIssue: 20, qtyToIssueMax: 20 },
      step: { qtyToIssue: 20, qtyHUCapacity: 5 },
    });
    // Bug: Math.min(20, 20, 20) = 20 — qtyHUCapacity not in min, dialog suggests 20 when HU only has 5
    // Fix: Math.min(20, 20, 20, 5) = 5
    expect(result.qtyToIssueTarget).toEqual(5);
  });

  // The reason list is offered on EVERY step, whatever the step's target is next to the HU's own
  // content. Only the per-step eligibility of the "empty (auto. inventory)" reason still filters the
  // list (`step.allowEmptying`, applied by `getQtyRejectedReasonsForStep`).
  it('offers the reasons when the step takes only part of the HU', () => {
    const result = call_computeStepScanPropsFromActivity({
      // HU 0.502 kg against a remaining need of 0.5 kg: the target (0.5) is below the HU's content,
      // which used to suppress the whole reason list.
      line: { qtyToIssue: 0.5, qtyToIssueMax: 0.5 },
      step: { qtyToIssue: 0.5, qtyHUCapacity: 0.502, allowEmptying: true },
    });
    expect(result.qtyToIssueTarget).toEqual(0.5);
    expect(result.qtyRejectedReasons).toEqual([
      { key: 'R1', caption: 'Reason 1' },
      { key: 'E', caption: 'empty (auto. inventory)' },
    ]);
  });

  it('offers the reasons when the step takes the whole HU', () => {
    const result = call_computeStepScanPropsFromActivity({
      line: { qtyToIssue: 0.5, qtyToIssueMax: 0.5 },
      step: { qtyToIssue: 0.5, qtyHUCapacity: 0.5, allowEmptying: true },
    });
    expect(result.qtyRejectedReasons).toEqual([
      { key: 'R1', caption: 'Reason 1' },
      { key: 'E', caption: 'empty (auto. inventory)' },
    ]);
  });

  // ...and the step's own eligibility for "empty (auto. inventory)" is unchanged by dropping the gate:
  // a step whose source HU must refuse it (pallet LU step, multi-product HU) still never sees it.
  it('still filters out the empty reason for a step that does not allow emptying', () => {
    const result = call_computeStepScanPropsFromActivity({
      line: { qtyToIssue: 0.5, qtyToIssueMax: 0.5 },
      step: { qtyToIssue: 0.5, qtyHUCapacity: 0.502, allowEmptying: false },
    });
    expect(result.qtyRejectedReasons).toEqual([{ key: 'R1', caption: 'Reason 1' }]);
  });
});
