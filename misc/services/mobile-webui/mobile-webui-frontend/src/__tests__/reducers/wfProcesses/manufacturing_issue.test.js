import * as CompleteStatus from '../../../constants/CompleteStatus';
import {
  computeLineQtyIssuedFromSteps,
  computeStepStatus,
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
});
