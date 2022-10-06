import { computeStepScanPropsFromState } from '../../../../../../containers/activities/manufacturing/issue/step_scan/computeStepScanPropsFromState';

const call_computeStepScanPropsFromState = ({ line, step }) => {
  return computeStepScanPropsFromState({
    state: {
      wfProcesses: {
        WFP1: {
          activities: {
            A1: {
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
          },
        },
      },
    },
    wfProcessId: 'WFP1',
    activityId: 'A1',
    lineId: 'L1',
    stepId: 'S1',
  });
};

describe('computeStepScanPropsFromState', () => {
  it('line qty > step qty', () => {
    const result = call_computeStepScanPropsFromState({
      line: { qtyToIssue: 11, qtyToIssueMax: 12 },
      step: { qtyToIssue: 10, qtyHUCapacity: 10 },
    });
    console.log('result', result);
    expect(result.qtyToIssueTarget).toEqual(10);
    expect(result.qtyToIssueMax).toEqual(12);
    expect(result.isIssueWholeHU).toEqual(true);
  });

  it('line qty < step qty', () => {
    const result = call_computeStepScanPropsFromState({
      line: { qtyToIssue: 10, qtyToIssueMax: 15, qtyIssued: 3 },
      step: { qtyToIssue: 100, qtyHUCapacity: 100 },
    });
    console.log('result', result);
    expect(result.qtyToIssueTarget).toEqual(10 - 3);
    expect(result.qtyToIssueMax).toEqual(15 - 3);
    expect(result.isIssueWholeHU).toEqual(false);
  });
});
