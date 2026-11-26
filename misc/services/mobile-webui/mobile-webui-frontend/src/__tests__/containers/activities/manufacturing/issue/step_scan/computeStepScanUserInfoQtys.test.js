import { computeStepScanPropsFromActivity } from '../../../../../../containers/activities/manufacturing/issue/step_scan/computeStepScanPropsFromActivity';
import { computeStepScanUserInfoQtys } from '../../../../../../containers/activities/manufacturing/issue/step_scan/computeStepScanUserInfoQtys';

const call = ({ line, step }) => {
  const props = computeStepScanPropsFromActivity({
    activity: {
      dataStored: {
        lines: {
          L1: {
            qtyIssued: 0,
            weightable: false,
            uom: 'kg',
            ...line,
            steps: {
              S1: {
                huQRCode: 'blabla',
                uom: 'Stk',
                ...step,
              },
            },
          },
        },
        qtyRejectedReasons: { reasons: [] },
        scaleDevice: null,
      },
    },
    lineId: 'L1',
    stepId: 'S1',
  });

  return computeStepScanUserInfoQtys({
    lineUom: props.lineUom,
    lineQtyToIssue: props.lineQtyToIssue,
    lineQtyToIssueTolerance: props.lineQtyToIssueTolerance,
    lineQtyToIssueRemaining: props.lineQtyToIssueRemaining,
  });
};

describe('computeStepScanUserInfoQtys', () => {
  // The scanned HU is stocked in Stk; the BOM line demands kg. The two line-level
  // "Packmenge"/"Packmenge (total)" rows describe the BOM-line demand, so they must be
  // shown in the LINE uom (kg) — not the step/HU stocking uom (Stk).
  it('renders line-level qtys in the LINE uom, not the step (stocking) uom', () => {
    const [total, remaining] = call({
      line: { qtyToIssue: 34.5, qtyToIssueMax: 34.5, qtyIssued: 0, uom: 'kg' },
      step: { qtyToIssue: 1, qtyHUCapacity: 35, uom: 'Stk' },
    });

    expect(total.value).toEqual('34.5 kg');
    expect(remaining.value).toEqual('34.5 kg');
  });
});
