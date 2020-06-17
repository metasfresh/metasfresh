import { isNumberField } from '../../utils/widgetHelper';

describe('Widget helpers', () => {
  describe('is NumberField function', () => {
    it('createAmmount works correctly', () => {
      const resultToCheckOne = isNumberField('Integer');
      expect(resultToCheckOne).toBe(true);

      const resultToCheckTwo = isNumberField('Amount');
      expect(resultToCheckTwo).toBe(true);

      const resultToCheckThree = isNumberField('Quantity');
      expect(resultToCheckThree).toBe(true);

      const resultToCheckFour = isNumberField('Date');
      expect(resultToCheckFour).toBe(false);

      const resultToCheckFive = isNumberField('Color');
      expect(resultToCheckFive).toBe(false);
    });
  });
});
