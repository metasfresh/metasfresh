import {
  isNumberField,
  formatValueByWidgetType,
  validatePrecision,
} from '../../utils/widgetHelper';

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

  describe('formatValueByWidgetType function', () => {
    it('works correctly', () => {
      const resultToCheckOne = formatValueByWidgetType({
        widgetType: 'Quantity',
        value: '',
      });
      expect(resultToCheckOne).toBe(null);

      const resultToCheckTwo = formatValueByWidgetType({
        widgetType: 'Amount',
        value: null,
      });
      expect(resultToCheckTwo).toBe('0');
    });
  });

  describe('validatePrecision function', () => {
    it('works correctly', () => {
      const resultToCheckOne = validatePrecision({
        widgetValue: '223,544334',
        widgetType: 'Quantity',
        precision: 2,
      });
      expect(resultToCheckOne).toBe(true);


      const resultToCheckTwo = validatePrecision({
        widgetValue: '223,544334',
        widgetType: 'Quantity',
        precision: 0,
      });
      expect(resultToCheckTwo).toBe(true);


      const resultToCheckThree = validatePrecision({
        widgetValue: '223.544334',
        widgetType: 'Amount',
        precision: 0,
      });

      expect(resultToCheckThree).toBe(false);
    });

    it('test null and empty object as value', () => {
      const resultToCheckNull = validatePrecision({
        widgetValue: null,
        widgetType: 'Quantity',
        precision: 0,
      });
      expect(resultToCheckNull).toBe(false);


      const resultToCheckEmptyObj = validatePrecision({
        widgetValue: {},
        widgetType: 'Amount',
        precision: 0,
      });

      expect(resultToCheckEmptyObj).toBe(false);
    });
  });
});
