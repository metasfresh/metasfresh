import { getCurrentPickingTargetInfoFromActivity } from '../../../../reducers/wfProcesses/picking/useCurrentPickTarget';

// A header-level (SALES_ORDER / DELIVERY_LOCATION) picking job: the pick target lives on the header,
// so isLineLevelPickTarget is false. When the lines carry DIFFERENT carriers the job header carrier
// is null (divergent), yet each line still carries its own resolved carrier product. In the line view
// (lineId in scope) the worker must still see that line's own carrier — so the selector must read the
// line's carrier fields even though the pick target is header-level.
describe('getCurrentPickingTargetInfoFromActivity', () => {
  const activity = {
    dataStored: {
      isLineLevelPickTarget: false,
      allowedPickToStructures: ['LU_TU', 'TU', 'LU_CU', 'CU'],
      luPickingTarget: null,
      tuPickingTarget: null,
      // job header carrier NOT populated (lines diverge)
      carrierProductCaption: null,
      carrierAdviseAvailable: true,
      carrierAdviseReadOnly: false,
      carrierAdviseDisabledReason: 'Carrier already advised',
      lines: {
        L1: {
          carrierProductCaption: 'GLS Germany ShipIT - Parcel',
          carrierAdviseAvailable: true,
          carrierAdviseReadOnly: false,
          carrierAdviseDisabledReason: 'Line carrier already advised',
        },
      },
    },
  };

  describe('header-level job, line view', () => {
    it("reads the line's own carrier caption even though the pick target is header-level", () => {
      const info = getCurrentPickingTargetInfoFromActivity({ activity, lineId: 'L1' });
      expect(info.lineCarrierProductCaption).toBe('GLS Germany ShipIT - Parcel');
      expect(info.lineCarrierAdviseAvailable).toBe(true);
    });

    it('does NOT take the LU/TU pick target from the line for a header-level job', () => {
      const info = getCurrentPickingTargetInfoFromActivity({ activity, lineId: 'L1' });
      expect(info.luPickingTarget).toBeNull();
      expect(info.tuPickingTarget).toBeNull();
    });

    it('exposes lineCarrierAdviseDisabledReason from the line', () => {
      const info = getCurrentPickingTargetInfoFromActivity({ activity, lineId: 'L1' });
      expect(info.lineCarrierAdviseDisabledReason).toBe('Line carrier already advised');
    });

    it('exposes jobCarrierAdviseDisabledReason from the job header', () => {
      const info = getCurrentPickingTargetInfoFromActivity({ activity, lineId: 'L1' });
      expect(info.jobCarrierAdviseDisabledReason).toBe('Carrier already advised');
    });
  });

  describe('header-level job, line view — line has no disabled reason (fallback to job)', () => {
    const activityLineNoReason = {
      dataStored: {
        ...activity.dataStored,
        carrierAdviseDisabledReason: 'Job-level reason',
        lines: {
          L1: {
            carrierProductCaption: 'GLS Germany ShipIT - Parcel',
            carrierAdviseAvailable: true,
            carrierAdviseReadOnly: false,
            carrierAdviseDisabledReason: null,
          },
        },
      },
    };

    it('returns null lineCarrierAdviseDisabledReason and the job reason when line has none', () => {
      const info = getCurrentPickingTargetInfoFromActivity({ activity: activityLineNoReason, lineId: 'L1' });
      expect(info.lineCarrierAdviseDisabledReason).toBeNull();
      expect(info.jobCarrierAdviseDisabledReason).toBe('Job-level reason');
    });
  });

  describe('header view (no line in scope)', () => {
    it('falls back to the job-level carrier caption', () => {
      const info = getCurrentPickingTargetInfoFromActivity({ activity, lineId: null });
      expect(info.lineCarrierProductCaption).toBeNull();
      expect(info.jobCarrierAdviseAvailable).toBe(true);
    });

    it('exposes jobCarrierAdviseDisabledReason and null lineCarrierAdviseDisabledReason', () => {
      const info = getCurrentPickingTargetInfoFromActivity({ activity, lineId: null });
      expect(info.lineCarrierAdviseDisabledReason).toBeNull();
      expect(info.jobCarrierAdviseDisabledReason).toBe('Carrier already advised');
    });
  });
});
