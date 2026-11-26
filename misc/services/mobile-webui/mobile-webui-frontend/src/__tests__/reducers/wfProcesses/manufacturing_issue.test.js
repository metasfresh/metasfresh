import * as CompleteStatus from '../../../constants/CompleteStatus';
import {
  computeLineQtyIssuedFromSteps,
  computeStepStatus,
  normalizeLines,
  updateActivityBottomUp,
} from '../../../reducers/wfProcesses/manufacturing_issue';

describe('reducers: manufacturing issue tests', () => {
  describe('computeStepStatus', () => {
    it('qtyIssued not set', () => {
      expect(computeStepStatus({ draftStep: {} })).toEqual(CompleteStatus.NOT_STARTED);
    });

    it('qtyIssued is ZERO', () => {
      expect(
        computeStepStatus({
          draftStep: { qtyIssued: 0, qtyRejectedReasonCode: null },
        })
      ).toEqual(CompleteStatus.NOT_STARTED);
    });

    it('qtyIssued is set', () => {
      expect(
        computeStepStatus({
          draftStep: { qtyIssued: 8, qtyRejectedReasonCode: null },
        })
      ).toEqual(CompleteStatus.COMPLETED);
    });

    it('qtyRejectedReasonCode is set', () => {
      expect(
        computeStepStatus({
          draftStep: { qtyIssued: 0, qtyRejectedReasonCode: 'SOME_CODE' },
        })
      ).toEqual(CompleteStatus.COMPLETED);
    });
  }); // computeStepStatus

  describe('updateActivityBottomUp', () => {
    it('single step, not issued at all', () => {
      const draftActivityDataStored = {
        lines: [
          {
            steps: {
              1: { qtyToIssue: 8, qtyIssued: 0, qtyRejectedReasonCode: null },
            },
          },
        ],
      };
      updateActivityBottomUp({ draftActivityDataStored });
      expect(draftActivityDataStored.completeStatus).toEqual(CompleteStatus.NOT_STARTED);
    });

    describe('step + alternative step', () => {
      it('nothing issued/reported', () => {
        const draftActivityDataStored = {
          lines: [
            {
              steps: {
                1: { qtyToIssue: 8, qtyIssued: 0, qtyRejectedReasonCode: null },
                2: { qtyToIssue: 0, qtyIssued: 0, qtyRejectedReasonCode: null }, // alternative step
              },
            },
          ],
        };
        updateActivityBottomUp({ draftActivityDataStored });
        expect(draftActivityDataStored.completeStatus).toEqual(CompleteStatus.NOT_STARTED);
      });

      it('issued on main step', () => {
        const draftActivityDataStored = {
          lines: [
            {
              qtyToIssue: 4, // line demand, met by the 4 issued across the steps
              steps: {
                1: { qtyToIssue: 8, qtyIssued: 4, qtyRejectedReasonCode: null },
                2: { qtyToIssue: 0, qtyIssued: 0, qtyRejectedReasonCode: null }, // alternative step
              },
            },
          ],
        };
        updateActivityBottomUp({ draftActivityDataStored });
        expect(draftActivityDataStored.completeStatus).toEqual(CompleteStatus.COMPLETED);
      });

      it('issued on alternative step', () => {
        const draftActivityDataStored = {
          lines: [
            {
              qtyToIssue: 4, // line demand, met by the 4 issued on the alternative step
              steps: {
                1: { qtyToIssue: 8, qtyIssued: 0, qtyRejectedReasonCode: null },
                2: { qtyToIssue: 0, qtyIssued: 4, qtyRejectedReasonCode: null }, // alternative step
              },
            },
          ],
        };
        updateActivityBottomUp({ draftActivityDataStored });
        expect(draftActivityDataStored.completeStatus).toEqual(CompleteStatus.COMPLETED);
      });

      it('issued on both main step and alternative step', () => {
        const draftActivityDataStored = {
          lines: [
            {
              qtyToIssue: 7, // line demand, met by the 3 + 4 issued across the steps
              steps: {
                1: { qtyToIssue: 8, qtyIssued: 3, qtyRejectedReasonCode: null },
                2: { qtyToIssue: 0, qtyIssued: 4, qtyRejectedReasonCode: null }, // alternative step
              },
            },
          ],
        };
        updateActivityBottomUp({ draftActivityDataStored });
        expect(draftActivityDataStored.completeStatus).toEqual(CompleteStatus.COMPLETED);
      });
    }); // step + alternative step;
  }); // computeActivityStatus

  describe('computeLineQtyIssuedFromSteps', () => {
    it('one step, qtyIssued missing', () => {
      expect(
        computeLineQtyIssuedFromSteps({
          draftLine: {
            steps: {
              1: {},
            },
          },
        })
      ).toEqual(0);
    });

    it('one step, qtyIssued=null', () => {
      expect(
        computeLineQtyIssuedFromSteps({
          draftLine: {
            steps: {
              1: { qtyIssued: null },
            },
          },
        })
      ).toEqual(0);
    });

    it('one step, qtyIssued=0', () => {
      expect(
        computeLineQtyIssuedFromSteps({
          draftLine: {
            steps: {
              1: { qtyIssued: null },
            },
          },
        })
      ).toEqual(0);
    });

    it('two steps, qtyIssued=null,10', () => {
      expect(
        computeLineQtyIssuedFromSteps({
          draftLine: {
            steps: {
              1: { qtyIssued: null },
              2: { qtyIssued: 10 },
            },
          },
        })
      ).toEqual(10);
    });

    it('three steps, qtyIssued=4,null,10', () => {
      expect(
        computeLineQtyIssuedFromSteps({
          draftLine: {
            steps: {
              1: { qtyIssued: 4 },
              2: { qtyIssued: null },
              3: { qtyIssued: 10 },
            },
          },
        })
      ).toEqual(14);
    });
  }); // computeLineQtyIssuedFromSteps

  // Regression: when the picked HU is stocked in a different uom than the BOM line demands
  // (e.g. 1 Stk = 35 kg, BOM line in kg), the backend aggregates line.qtyIssued in the LINE uom
  // (RawMaterialsIssueLine.computeQtyIssued converts each step's issued qty). The frontend must
  // NOT re-derive it by summing step qtys (which are in the step/stocking uom, un-convertible here).
  describe('line qtyIssued uom (Stk-stocked HU issued against a kg BOM line)', () => {
    it('normalizeLines carries the backend line.qtyIssued', () => {
      const [line] = normalizeLines([
        {
          uom: 'kg',
          qtyToIssue: 34.5,
          qtyIssued: 35, // backend value, in the LINE uom (kg)
          steps: [{ id: 'S1', qtyIssued: 1 /* Stk */ }],
        },
      ]);
      expect(line.qtyIssued).toEqual(35);
    });

    it('updateActivityBottomUp keeps the backend line.qtyIssued instead of summing step (Stk) qtys', () => {
      const draftActivityDataStored = {
        lines: [
          {
            uom: 'kg',
            qtyToIssue: 34.5,
            qtyIssued: 35, // backend value, in the LINE uom (kg)
            steps: {
              S1: { qtyToIssue: 1, qtyIssued: 1 /* Stk */, qtyRejectedReasonCode: null },
            },
          },
        ],
      };
      updateActivityBottomUp({ draftActivityDataStored });

      const line = draftActivityDataStored.lines[0];
      expect(line.qtyIssued).toEqual(35); // NOT 1
      expect(line.qtyToIssueRemaining).toEqual(0); // 35 kg covers the 34.5 kg demand
      expect(line.completeStatus).toEqual(CompleteStatus.COMPLETED);
    });
  });
});
