import { isGRAIScanEnabled, isHUConsolidationJobLoaded } from '../../../apps/huConsolidation/reducers';

describe('huConsolidation selectors', () => {
  describe('isHUConsolidationJobLoaded', () => {
    it('is false without an activity', () => {
      expect(isHUConsolidationJobLoaded({ activity: undefined })).toBe(false);
    });

    it('is false while the activity has no job', () => {
      expect(isHUConsolidationJobLoaded({ activity: { dataStored: {} } })).toBe(false);
    });

    it('is true once the job is loaded', () => {
      expect(isHUConsolidationJobLoaded({ activity: { dataStored: { job: { graiScanEnabled: false } } } })).toBe(true);
    });
  });

  describe('isGRAIScanEnabled', () => {
    it('is false while the job is not loaded', () => {
      expect(isGRAIScanEnabled({ activity: { dataStored: {} } })).toBe(false);
    });

    it('reads the flag the backend sent', () => {
      expect(isGRAIScanEnabled({ activity: { dataStored: { job: { graiScanEnabled: true } } } })).toBe(true);
      expect(isGRAIScanEnabled({ activity: { dataStored: { job: { graiScanEnabled: false } } } })).toBe(false);
    });
  });
});
