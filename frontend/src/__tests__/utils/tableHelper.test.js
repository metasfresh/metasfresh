import {
  createAmount,
  createSpecialField,
  fieldValueToString,
} from '../../utils/tableHelpers';

describe('Table helpers', () => {
  describe('TableCell functions', () => {
    it('createAmmount works correctly', () => {
      const amountToCheckOne = createAmount('23.3339955', 2, true);
      expect(amountToCheckOne).toBe('23,334');

      const amountToCheckTwo = createAmount('23.3339955', 4, true);
      expect(amountToCheckTwo).toBe('23,3340');

      const amountToCheckThree = createAmount('23.3339955', 5, true);
      expect(amountToCheckThree).toBe('23,33400');

      const amountToCheckFour = createAmount('23,3339955', 2, true); // comma separated
      expect(amountToCheckFour).toBe('23,00');

      const amountToCheckFive = createAmount('23,3339955', 4, true); // comma separated
      expect(amountToCheckFive).toBe('23,0000');
    });

    it('createSpecialField works correctly', () => {
      const fieldToCheckOne = createSpecialField('Date', '21.04.2020');

      expect(fieldToCheckOne).toBe('21.04.2020'); // Input something like a Date you get the same value

      const fieldToCheckTwo = createSpecialField('Color', '#EEEEEE');
      expect(fieldToCheckTwo.props.style.backgroundColor).toBe('#EEEEEE'); // For the special `Color` type case you get a react el piece (used in table cell)

    });

    it('fieldValueToString works correctly when null value given', () => {
      let fieldValue = null;
      let fieldType = null;
      let precision = null;
      let isGerman = true;
      let activeLocale = { key: 'de_DE' };
      const fieldToCheck = fieldValueToString({
        fieldValue,
        fieldType,
        precision,
        isGerman,
        activeLocale,
      });

      expect(fieldToCheck).toBe(''); // when null value given for the fieldValue we expect to get nothing
    });

    it('fieldValueToString works correctly when array value given', () => {
      let fieldValue = ['1', '2', '3'];
      let fieldType = 'Text';
      let precision = null;
      let isGerman = true;
      let activeLocale = { key: 'de_DE' };
      const fieldToCheck = fieldValueToString({
        fieldValue,
        fieldType,
        precision,
        isGerman,
        activeLocale,
      });

      let test = fieldToCheck.includes('-');
      expect(test).toBe(true); // check formatting with a dash when array given
    });
  });
});
