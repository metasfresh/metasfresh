import { toOrderBysCommaSeparatedString } from '../../utils/windowHelpers';

describe('windowHelpers', () => {
  describe('toOrderBysCommaSeparatedString', () => {
    it('null', () => {
      expect(toOrderBysCommaSeparatedString(null)).toEqual('');
    });

    it('array: field1 ascending', () => {
      expect(toOrderBysCommaSeparatedString([
        { fieldName: 'field1', ascending: true },
      ])).toEqual('+field1');
    });

    it('array: field1 ascending, field2 descending', () => {
      expect(toOrderBysCommaSeparatedString([
        { fieldName: 'field1', ascending: true },
        { fieldName: 'field2', ascending: false },
      ])).toEqual('+field1,-field2');
    });
  });
});
