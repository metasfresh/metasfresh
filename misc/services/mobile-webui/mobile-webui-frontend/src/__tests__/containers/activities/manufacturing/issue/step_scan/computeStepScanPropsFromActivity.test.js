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
            { key: 'R2', caption: 'Reason 2' },
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
    expect(result.isIssueWholeHU).toEqual(true);
  });

  it('line qty < step qty', () => {
    const result = call_computeStepScanPropsFromActivity({
      line: { qtyToIssue: 10, qtyToIssueMax: 15, qtyIssued: 3 },
      step: { qtyToIssue: 100, qtyHUCapacity: 100 },
    });
    console.log('result', result);
    expect(result.qtyToIssueTarget).toEqual(10 - 3);
    expect(result.qtyToIssueMax).toEqual(15 - 3);
    expect(result.isIssueWholeHU).toEqual(false);
  });
});
