import { attributesMapToArray } from '../../../reducers/wfProcesses/manufacturing_receipt';

describe('reducers: manufacturing receipt tests', () => {
  describe('attributesMapToArray', () => {
    it('converts a { code: value } map into a [{ code, value }] array', () => {
      expect(attributesMapToArray({ SizeCM: 'M', LotNumber: 'LOT-0001' })).toEqual([
        { code: 'SizeCM', value: 'M' },
        { code: 'LotNumber', value: 'LOT-0001' },
      ]);
    });

    it('drops empty/nullish values (optional attributes)', () => {
      expect(
        attributesMapToArray({
          SizeCM: 'M',
          Color: '',
          NetWeight: null,
          BestBeforeDate: undefined,
        })
      ).toEqual([{ code: 'SizeCM', value: 'M' }]);
    });

    it('returns an empty array for an empty/nullish map', () => {
      expect(attributesMapToArray({})).toEqual([]);
      expect(attributesMapToArray(null)).toEqual([]);
      expect(attributesMapToArray(undefined)).toEqual([]);
    });
  });
});
