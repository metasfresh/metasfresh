import {
  getReadAttributesFromActivity,
  getReadOnlyAttributesFromActivity,
} from '../../../../reducers/wfProcesses/picking/getReadAttributesFromActivity';

describe('getReadOnlyAttributesFromActivity', () => {
  it('returns the read-only attributes the backend sent, separate from the editable ones', () => {
    const activity = { dataStored: { readAttributes: ['BestBeforeDate'], readOnlyAttributes: ['LotNo'] } };

    expect(getReadAttributesFromActivity({ activity })).toEqual(['BestBeforeDate']);
    expect(getReadOnlyAttributesFromActivity({ activity })).toEqual(['LotNo']);
  });

  it('returns no read-only attributes when the backend sent none', () => {
    expect(getReadOnlyAttributesFromActivity({ activity: { dataStored: {} } })).toEqual([]);
  });
});
