import Moment from 'moment';

import {
  isNumberField,
  formatValueByWidgetType,
  validatePrecision,
  getFormatForDateField,
  getFormattedDate,
  getClassNames,
  getWidgetField,
  shouldPatch,
} from '../../utils/widgetHelpers';
import {
  DATE_FORMAT,
  TIME_FORMAT,
  DATE_TIMEZONE_FORMAT,
} from '../../constants/Constants';

describe('Widget helpers', () => {
  describe('getClassNames', () => {
    class Widget {
      constructor(props) {
        this.props = props;

        this.getClassNames = getClassNames.bind(this);
      }
    }

    it(`returns default values`, () => {
      const widget = new Widget({ widgetData: [{}] });
      const classnames = widget.getClassNames();

      expect(classnames).toEqual('input-block input-secondary pulse-off');
    });

    it(`handles icon/forcedPrimary parameters`, () => {
      const widget = new Widget({ widgetData: [{}] });
      const classnames = widget.getClassNames({
        icon: true,
        forcedPrimary: true,
      });

      expect(classnames).toContain('input-icon-container');
      expect(classnames).toContain('input-primary');
    });

    it(`return proper values from props`, () => {
      const props = {
        widgetData: [
          {
            value: '',
            readonly: true,
            mandatory: true,
            validStatus: {
              valid: false,
            },
          },
        ],
        isFocused: true,
        gridAlign: 'foo',
        type: 'primary',
        updated: true,
        rowId: 1,
        isModal: false,
      };
      const widget = new Widget(props);
      const classnames = widget.getClassNames();
      const expected = [
        'input-block',
        'input-focused',
        'input-disabled',
        'input-mandatory',
        'text-xs-foo',
        'input-primary',
        'pulse-on',
        'input-table',
      ];

      expect(classnames.split(' ')).toEqual(expect.arrayContaining(expected));
    });
  });

  describe('getFormatForDateField', () => {
    it(`returns default format`, () => {
      const widgetType = 'Text';
      expect(getFormatForDateField(widgetType)).toEqual(DATE_TIMEZONE_FORMAT);
    });

    it(`returns default format`, () => {
      const widgetType = 'Date';
      expect(getFormatForDateField(widgetType)).toEqual(DATE_FORMAT);
    });

    it(`returns default format`, () => {
      const widgetType = 'Time';
      expect(getFormatForDateField(widgetType)).toEqual(TIME_FORMAT);
    });
  });

  describe('getFormattedDate', () => {
    const date = Moment('2020-04-07T00:00:00+02:00').format(
      `YYYY-MM-DDTHH:mm:ssZ`
    );
    it(`returns formatted date string if it's already a Moment object`, () => {
      const d = Moment(date);
      const f = DATE_FORMAT;

      expect(getFormattedDate(d, f)).toEqual('2020-04-07');
    });

    it(`returns a string with default formatting if it's already a Moment object`, () => {
      const d = Moment(date);

      expect(getFormattedDate(d)).toEqual(date);
    });

    it(`returns formatted string for date string`, () => {
      const f = DATE_FORMAT;

      expect(getFormattedDate(date, f)).toEqual('2020-04-07');
    });

    it(`returns a string with default formatting for date string`, () => {
      expect(getFormattedDate(date)).toEqual(date);
    });

    it(`returns 'null' for no value`, () => {
      expect(getFormattedDate()).toEqual(null);
    });
  });

  describe('isNumberField function', () => {
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

      const resultToCheckFour = validatePrecision({
        widgetValue: '223,544334',
        widgetType: 'Quantity',
        precision: 0,
        fieldName: 'qtyToDeliverCatchOverride',
      });
      expect(resultToCheckFour).toBe(true);
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

  describe('getWidgetField', () => {
    it(`return the widget name`, () => {
      const fields = [
        {
          field: 'Name',
          caption: 'Name',
          emptyText: 'none',
          clearValueText: 'none',
        },
      ];

      expect(getWidgetField({ fields })).toEqual('Name');
    });

    it(`return the widget name for filter widget`, () => {
      const fields = [
        {
          caption: 'Name',
          defaultValue: null,
          defaultValueTo: null,
          displayed: true,
          field: 'Name',
          mandatory: false,
          parameterName: 'Name',
          range: false,
          readonly: false,
          type: 'primary',
          value: '',
          valueTo: '',
          widgetType: 'Text',
        },
      ];

      expect(getWidgetField({ fields, filterWidget: true })).toEqual('Name');
    });
  });

  describe('shouldPatch', () => {
    const widgetData = (value, widgetType) => [
      { field: 'Description', value, widgetType },
    ];

    describe('an empty cell left without typing sends nothing', () => {
      it.each(['Text', 'LongText'])(
        '%s: stored null, editor left with "" -> no patch',
        (widgetType) => {
          expect(
            shouldPatch({
              property: 'Description',
              value: '',
              cachedValue: null,
              widgetData: widgetData(null, widgetType),
            })
          ).toBe(false);
        }
      );

      it('Text: stored null, store already holds "" -> no patch', () => {
        expect(
          shouldPatch({
            property: 'Description',
            value: '',
            cachedValue: null,
            widgetData: widgetData('', 'Text'),
          })
        ).toBe(false);
      });

      it('Text: stored "", editor left with "" -> no patch', () => {
        expect(
          shouldPatch({
            property: 'Description',
            value: '',
            cachedValue: '',
            widgetData: widgetData('', 'Text'),
          })
        ).toBe(false);
      });

      it('Text: never loaded (undefined), editor left with "" -> no patch', () => {
        expect(
          shouldPatch({
            property: 'Description',
            value: '',
            cachedValue: undefined,
            widgetData: widgetData(undefined, 'Text'),
          })
        ).toBe(false);
      });

      it('Number: stored null, editor left with "" -> no patch', () => {
        expect(
          shouldPatch({
            property: 'Qty',
            value: '',
            cachedValue: null,
            widgetData: [{ field: 'Qty', value: null, widgetType: 'Number' }],
          })
        ).toBe(false);
      });
    });

    describe('other widget types: real changes still patch, empty vs empty does not', () => {
      const patchOf = ({ field, widgetType, stored, value }) =>
        shouldPatch({
          property: field,
          value,
          cachedValue: stored,
          widgetData: [{ field, value: stored, widgetType }],
        });

      it.each([
        ['Quantity: 3 -> 5', 'QtyEntered', 'Quantity', '3', '5'],
        ['Quantity: 3 -> cleared', 'QtyEntered', 'Quantity', '3', ''],
        ['Number: 1.5 -> cleared', 'Qty', 'Number', '1.5', ''],
        [
          'Lookup: value -> cleared',
          'M_Product_ID',
          'Lookup',
          { key: '1', caption: 'A' },
          null,
        ],
        [
          'Lookup: empty -> value',
          'M_Product_ID',
          'Lookup',
          null,
          { key: '1', caption: 'A' },
        ],
        ['Checkbox: false -> true', 'IsActive', 'YesNo', false, true],
        ['Checkbox: false -> cleared', 'IsActive', 'YesNo', false, ''],
        ['Checkbox: true -> cleared', 'IsActive', 'YesNo', true, ''],
      ])('%s patches', (name, field, widgetType, stored, value) => {
        expect(patchOf({ field, widgetType, stored, value })).toBe(true);
      });

      it.each([
        ['Quantity: empty (null) left with ""', 'QtyEntered', 'Quantity'],
        ['Lookup: empty (null) left with ""', 'M_Product_ID', 'Lookup'],
        ['Checkbox: empty (null) cleared with ""', 'IsActive', 'YesNo'],
      ])('%s does not patch', (name, field, widgetType) => {
        expect(patchOf({ field, widgetType, stored: null, value: '' })).toBe(
          false
        );
      });
    });

    describe('real changes still patch', () => {
      it('Text: clearing a filled cell ("abc" -> "") patches', () => {
        expect(
          shouldPatch({
            property: 'Description',
            value: '',
            cachedValue: 'abc',
            widgetData: widgetData('abc', 'Text'),
          })
        ).toBe(true);
      });

      it('Text: typing into an empty cell patches', () => {
        expect(
          shouldPatch({
            property: 'Description',
            value: 'x',
            cachedValue: null,
            widgetData: widgetData(null, 'Text'),
          })
        ).toBe(true);
      });

      it('keeps the explicit null -> null clear case', () => {
        expect(
          shouldPatch({
            property: 'Description',
            value: null,
            cachedValue: null,
            widgetData: widgetData(null, 'Text'),
          })
        ).toBe(true);
      });

      it('Button with a null value: a click (value undefined) still patches', () => {
        expect(
          shouldPatch({
            property: 'Processing',
            value: undefined,
            cachedValue: null,
            widgetData: [
              { field: 'Processing', value: null, widgetType: 'Button' },
            ],
          })
        ).toBe(true);
      });
    });
  });
});
