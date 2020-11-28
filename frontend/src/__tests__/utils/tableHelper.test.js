import {
  createAmount,
  createSpecialField,
  fieldValueToString,
} from '../../utils/tableHelpers';
import LOCAL_LANG from '../../constants/Constants';

describe('Table helpers', () => {
  describe('TableCell functions', () => {
    afterEach(() => {
      localStorage.removeItem(LOCAL_LANG);
    });

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
      localStorage.setItem(LOCAL_LANG, 'de_DE');

      let fieldValue = null;
      let fieldType = null;
      let precision = null;
      let isGerman = true;

      const fieldToCheck = fieldValueToString({
        fieldValue,
        fieldType,
        precision,
        isGerman,
      });

      expect(fieldToCheck).toBe(''); // when null value given for the fieldValue we expect to get nothing
    });

    it('fieldValueToString works correctly when array value given', () => {
      localStorage.setItem(LOCAL_LANG, 'de_DE');

      let fieldValue = ['1', '2', '3'];
      let fieldType = 'Text';
      let precision = null;
      let isGerman = true;

      const fieldToCheck = fieldValueToString({
        fieldValue,
        fieldType,
        precision,
        isGerman,
      });

      let test = fieldToCheck.includes('-');
      expect(test).toBe(true); // check formatting with a dash when array given
    });

    it('fieldValueToString works correctly when boolean true value given', () => {
      localStorage.setItem(LOCAL_LANG, 'de_DE');

      let fieldValue = true;
      let fieldType = 'Text';
      let precision = null;
      let isGerman = true;

      const fieldToCheck = fieldValueToString({
        fieldValue,
        fieldType,
        precision,
        isGerman,
      });

      expect(fieldToCheck.props.className).toBe('meta-icon-checkbox-1');
    });

    it('fieldValueToString works correctly when boolean false value given', () => {
      localStorage.setItem(LOCAL_LANG, 'de_DE');

      let fieldValue = false;
      let fieldType = 'Text';
      let precision = null;
      let isGerman = true;

      const fieldToCheck = fieldValueToString({
        fieldValue,
        fieldType,
        precision,
        isGerman,
      });

      expect(fieldToCheck.props.className).toBe('meta-icon-checkbox');
    });

    it('fieldValueToString works correctly when Date type given', () => {
      localStorage.setItem(LOCAL_LANG, 'de_DE');

      let fieldValue = '2020-01-02';
      let fieldType = 'Date';
      let precision = null;
      let isGerman = true;

      let fieldToCheck = fieldValueToString({
        fieldValue,
        fieldType,
        precision,
        isGerman,
      });

      expect(fieldToCheck).toBe('02.01.2020');

      localStorage.setItem(LOCAL_LANG, 'en_EN');

      // we check also en locale
      fieldValue = '2020-01-02';
      fieldType = 'Date';
      precision = null;
      isGerman = false;

      fieldToCheck = fieldValueToString({
        fieldValue,
        fieldType,
        precision,
        isGerman,
      });

      expect(fieldToCheck).toBe('01/02/2020');
    });
  });

  it('fieldValueToString works correctly when Date type given', () => {
    localStorage.setItem(LOCAL_LANG, 'de_DE');

    let fieldValue = '2020-01-02';
    let fieldType = 'Date';
    let precision = null;
    let isGerman = true;

    let fieldToCheck = fieldValueToString({
      fieldValue,
      fieldType,
      precision,
      isGerman,
    });

    expect(fieldToCheck).toBe('02.01.2020');

    localStorage.setItem(LOCAL_LANG, 'en_EN');

    // we check also en locale
    fieldValue = '2020-01-02';
    fieldType = 'Date';
    precision = null;
    isGerman = false;
    fieldToCheck = fieldValueToString({
      fieldValue,
      fieldType,
      precision,
      isGerman,
    });

    expect(fieldToCheck).toBe('01/02/2020');
  });

  it('fieldValueToString works correctly when DateTime type given', () => {
    localStorage.setItem(LOCAL_LANG, 'de_DE');

    let fieldValue = '2020-01-02';
    let fieldType = 'DateTime';
    let precision = null;
    let isGerman = true;

    let fieldToCheck = fieldValueToString({
      fieldValue,
      fieldType,
      precision,
      isGerman,
    });

    expect(fieldToCheck).toBe('02.01.2020 00:00:00');

    localStorage.setItem(LOCAL_LANG, 'en_EN');
    // we check also en locale
    fieldValue = '2020-01-02';
    fieldType = 'DateTime';
    precision = null;
    isGerman = false;
    fieldToCheck = fieldValueToString({
      fieldValue,
      fieldType,
      precision,
      isGerman,
    });

    expect(fieldToCheck).toBe('01/02/2020 12:00:00 AM');
  });

  it('fieldValueToString works correctly when ZoneDateTime type given', () => {
    localStorage.setItem(LOCAL_LANG, 'de_DE');

    let fieldValue = '2020-01-02';
    let fieldType = 'ZoneDateTime';
    let precision = null;
    let isGerman = true;

    let fieldToCheck = fieldValueToString({
      fieldValue,
      fieldType,
      precision,
      isGerman,
    });

    expect(fieldToCheck).toBe('2020-01-02');

    localStorage.setItem(LOCAL_LANG, 'en_EN');

    // we check also en locale
    fieldValue = '2020-01-02';
    fieldType = 'ZoneDateTime';
    precision = null;
    isGerman = false;
    fieldToCheck = fieldValueToString({
      fieldValue,
      fieldType,
      precision,
      isGerman,
    });

    expect(fieldToCheck).toBe('2020-01-02');
  });

  it('fieldValueToString works correctly when Timestamp type given', () => {
    localStorage.setItem(LOCAL_LANG, 'de_DE');

    let fieldValue = '2020-01-02';
    let fieldType = 'Timestamp';
    let precision = null;
    let isGerman = true;
    let activeLocale = { key: 'de_DE' };
    let fieldToCheck = fieldValueToString({
      fieldValue,
      fieldType,
      precision,
      isGerman,
    });

    expect(fieldToCheck).toBe('02.01.2020 00:00:00');

    localStorage.setItem(LOCAL_LANG, 'en_EN');

    // we check also en locale
    fieldValue = '2020-01-02';
    fieldType = 'Timestamp';
    precision = null;
    isGerman = false;

    fieldToCheck = fieldValueToString({
      fieldValue,
      fieldType,
      precision,
      isGerman,
    });

    expect(fieldToCheck).toBe('01/02/2020 00:00:00');
  });

  it('fieldValueToString works correctly when Amount fieldType type given', () => {
    localStorage.setItem(LOCAL_LANG, 'de_DE');

    let fieldValue = '22,38342';
    let fieldType = 'Amount';
    let precision = null;
    let isGerman = true;
    let activeLocale = { key: 'de_DE' };
    let fieldToCheck = fieldValueToString({
      fieldValue,
      fieldType,
      precision,
      isGerman,
    });

    expect(fieldToCheck).toBe('22');

    precision = 2; // changing precision works
    fieldToCheck = fieldValueToString({
      fieldValue,
      fieldType,
      precision,
      isGerman,
    });

    expect(fieldToCheck).toBe('22,00');
  });

  it('fieldValueToString works correctly when special type Color fieldType type given', () => {
    localStorage.setItem(LOCAL_LANG, 'de_DE');

    let fieldValue = 'DDDDDD';
    let fieldType = 'Color';
    let precision = null;
    let isGerman = true;

    let fieldToCheck = fieldValueToString({
      fieldValue,
      fieldType,
      precision,
      isGerman,
    });
    expect(fieldToCheck.props.style.backgroundColor).toBe('DDDDDD');
  });
});
