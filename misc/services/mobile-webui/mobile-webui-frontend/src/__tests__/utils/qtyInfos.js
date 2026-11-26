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
    it('a no-value qtyInfo is null, never the literal string "null"', () => {
      // a type="text" input renders the returned value verbatim, so "null" would show as text in the field
      expect(qtyInfos.toNumberOrString(qtyInfos.invalidOfNumber(undefined))).toBeNull();
      expect(qtyInfos.toNumberOrString({ qtyStr: null, qty: null })).toBeNull();
    });
    it('a zero qtyInfo still renders "0"', () => {
      expect(qtyInfos.toNumberOrString(qtyInfos.invalidOfNumber(0))).toEqual('0');
    });
  });
});
