import {
  isGRAIScanEnabled,
  isHUConsolidationJobLoaded,
  mergeJobIntoActivityDataStored,
  wasGRAIScanButtonShown,
} from '../../../apps/huConsolidation/reducers';

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

  describe('GRAI scan button latch', () => {
    it('is not latched while the backend never enabled the GRAI scan', () => {
      const dataStored = {};
      mergeJobIntoActivityDataStored({ draftActivityDataStored: dataStored, job: { graiScanEnabled: false } });
      expect(wasGRAIScanButtonShown({ activity: { dataStored } })).toBe(false);
      expect(dataStored.job).toEqual({ graiScanEnabled: false });
    });

    it('latches once the backend enabled the GRAI scan and stays latched after it is disabled', () => {
      const dataStored = {};
      mergeJobIntoActivityDataStored({ draftActivityDataStored: dataStored, job: { graiScanEnabled: true } });
      expect(wasGRAIScanButtonShown({ activity: { dataStored } })).toBe(true);
      mergeJobIntoActivityDataStored({ draftActivityDataStored: dataStored, job: { graiScanEnabled: false } });
      expect(wasGRAIScanButtonShown({ activity: { dataStored } })).toBe(true);
      expect(isGRAIScanEnabled({ activity: { dataStored } })).toBe(false);
    });

    it('is false without an activity', () => {
      expect(wasGRAIScanButtonShown({ activity: undefined })).toBe(false);
    });
  });
});
