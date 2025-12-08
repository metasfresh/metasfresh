import { qtyInfos } from '../../utils/qtyInfos';

describe('qtyInfos tests', () => {
  describe('toNumberOrString', () => {
    it('from null', () => {
      expect(qtyInfos.toNumberOrString(null)).toEqual(null);
    });
    it('from object: qtyStr missing', () => {
      expect(qtyInfos.toNumberOrString({ qty: 123.456 })).toEqual('123.456');
    });
    it('from object: number with trailing zeroes', () => {
      expect(qtyInfos.toNumberOrString({ qty: 1, qtyStr: '1.0000' })).toEqual('1.0000');
    });
    it('from string', () => {
      expect(qtyInfos.toNumberOrString('456.7890')).toEqual('456.7890');
    });
  });
});
