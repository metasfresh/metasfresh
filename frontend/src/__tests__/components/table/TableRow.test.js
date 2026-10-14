import React from 'react';
import { shallow, mount } from 'enzyme';
import renderer from 'react-test-renderer';

import fixtures from '../../../../test_setup/fixtures/table/table_item_props.json';

import { getTableId } from '../../../reducers/tables';
import { createAmount } from '../../../utils/tableHelpers';
import TableRow from '../../../components/table/TableRow';

function createInitProps(propsSeed = fixtures.oldProps1, customProps) {
  return {
    ...propsSeed,
    tableId: getTableId(propsSeed),
    onClick: jest.fn(),
    handleSelect: jest.fn(),
    onDoubleClick: jest.fn(),
    changeListenOnTrue: jest.fn(),
    handleRowCollapse: jest.fn(),
    handleRightClick: jest.fn(),
    onItemChange: jest.fn(),
    changeListenOnFalse: jest.fn(),
    getSizeClass: jest.fn(),
    ...customProps,
  };
}

describe('Table row (TableRow)', () => {
  it('renders without errors', () => {
    const props = createInitProps();

    shallow(<TableRow {...props} />);
  });

  it('output matches snapshot', () => {
    const props = createInitProps();

    const component = renderer.create(<TableRow {...props} />);
    const tree = component.toJSON();
    expect(tree).toMatchSnapshot();
  });

  it('row re-renders after data changed', () => {
    const props = createInitProps();
    const updatedProps = createInitProps(fixtures.newProps1);
    const table = document.createElement('table');
    const tbody = document.createElement('tbody');
    table.appendChild(tbody);

    const wrapper = mount(<TableRow {...props} />, { attachTo: tbody });

    expect(wrapper.find('.row-1 [data-cy="cell-QtyEntered"]').text()).toEqual(
      '3'
    );

    wrapper.setProps(updatedProps);

    expect(wrapper.find('.row-1 [data-cy="cell-QtyEntered"]').text()).toEqual(
      '4'
    );
  });

  describe('leaving a cell writes a number typed with a decimal comma as a dot-decimal number', () => {
    // a decimal number cell (Amount, Quantity, Number, CostPrice) is a text input, so in German its editor holds
    // e.g. '12,50'; the row in the store must get 12.50 - the cell renders it through parseFloat, which reads '12,50'
    // as 12
    const createRow = (updatePropertyValue, customProps) => {
      const props = createInitProps(fixtures.oldProps1, {
        updatePropertyValue,
        ...customProps,
      });
      return shallow(<TableRow {...props} />);
    };
    const keyEvent = (key, value) => ({
      key,
      target: { value },
      stopPropagation: jest.fn(),
      persist: jest.fn(),
    });

    it('the fixture cells are decimal number, text and lookup cells', () => {
      const { fieldsByName } = fixtures.oldProps1;
      expect(fieldsByName.PriceEntered.widgetType).toBe('CostPrice');
      expect(fieldsByName.QtyEntered.widgetType).toBe('Quantity');
      expect(fieldsByName.Description.widgetType).toBe('LongText');
      expect(fieldsByName.M_Product_ID.widgetType).toBe('Lookup');
    });

    it('the written dot-decimal number is shown as 12,50 in German', () => {
      expect(createAmount('12.50', 2, true)).toBe('12,50');
    });

    it('on Tab', () => {
      const updatePropertyValue = jest.fn();
      const wrapper = createRow(updatePropertyValue);
      wrapper.setState({ edited: 'PriceEntered' });

      wrapper.instance().handleKeyDown({
        event: keyEvent('Tab', '12,50'),
        property: 'PriceEntered',
      });

      expect(updatePropertyValue).toHaveBeenCalledWith(
        expect.objectContaining({ property: 'PriceEntered', value: '12.50' })
      );
    });

    it('on Enter', () => {
      const updatePropertyValue = jest.fn();
      const wrapper = createRow(updatePropertyValue);
      wrapper.setState({ edited: 'QtyEntered' });

      wrapper.instance().handleKeyDown({
        event: keyEvent('Enter', '12,50'),
        property: 'QtyEntered',
        readonly: false,
      });

      expect(updatePropertyValue).toHaveBeenCalledWith(
        expect.objectContaining({ property: 'QtyEntered', value: '12.50' })
      );
    });

    it('on Escape, restoring the stored value - never the grouped text the cell shows', () => {
      const updatePropertyValue = jest.fn();
      const wrapper = createRow(updatePropertyValue, {
        fieldsByName: {
          ...fixtures.oldProps1.fieldsByName,
          PriceEntered: {
            ...fixtures.oldProps1.fieldsByName.PriceEntered,
            value: '1234',
          },
        },
      });
      wrapper.setState({ activeCell: { focus: jest.fn() } });
      const row = wrapper.instance();

      // a double click opens the editor; the cell shows 1234 with a thousands separator (format '0,0...'), which
      // can not be told apart from a decimal number
      row.handleDoubleClick({ target: { textContent: '1.234' } });
      row.handleEditProperty({ property: 'PriceEntered', readonly: false });
      const escapeEvent = keyEvent('Escape', '99,99');
      row.handleKeyDown({ event: escapeEvent, property: 'PriceEntered' });

      expect(updatePropertyValue).toHaveBeenCalledWith(
        expect.objectContaining({ property: 'PriceEntered', value: '1234' })
      );
      expect(escapeEvent.target.value).toBe('1234');
    });

    const fieldsWith = (fieldName, field) => ({
      fieldsByName: {
        ...fixtures.oldProps1.fieldsByName,
        [fieldName]: {
          ...fixtures.oldProps1.fieldsByName[fieldName],
          ...field,
        },
      },
    });
    const editAndEscape = (wrapper, property, typedValue) => {
      wrapper.setState({ activeCell: { focus: jest.fn() } });
      wrapper.instance().handleEditProperty({ property, readonly: false });
      const escapeEvent = keyEvent('Escape', typedValue);
      wrapper.instance().handleKeyDown({ event: escapeEvent, property });
      return escapeEvent;
    };

    it('on Escape, restoring an empty stored value as an empty text', () => {
      const updatePropertyValue = jest.fn();
      const wrapper = createRow(
        updatePropertyValue,
        fieldsWith('PriceEntered', { value: null })
      );

      const escapeEvent = editAndEscape(wrapper, 'PriceEntered', '99,99');

      expect(updatePropertyValue).toHaveBeenCalledWith(
        expect.objectContaining({ property: 'PriceEntered', value: '' })
      );
      expect(escapeEvent.target.value).toBe('');
    });

    it('on Escape after typing into a cell, restoring its stored value', () => {
      const updatePropertyValue = jest.fn();
      const wrapper = createRow(updatePropertyValue);
      wrapper.setState({ activeCell: { focus: jest.fn() } });

      // typing a digit on a selected cell opens its editor
      wrapper.instance().handleKeyDown({
        event: { key: '1', keyCode: 49, target: {}, persist: jest.fn() },
        property: 'PriceEntered',
        readonly: false,
      });
      wrapper.instance().handleKeyDown({
        event: keyEvent('Escape', '19,99'),
        property: 'PriceEntered',
      });

      expect(updatePropertyValue).toHaveBeenCalledWith(
        expect.objectContaining({ property: 'PriceEntered', value: '3.00' })
      );
    });

    it('on Escape after the editor was opened programmatically, restoring its stored value', () => {
      const updatePropertyValue = jest.fn();
      const wrapper = createRow(updatePropertyValue);
      wrapper.setState({ activeCell: { focus: jest.fn() } });

      wrapper.instance().initPropertyEditor({ fieldName: 'PriceEntered' });
      wrapper.instance().handleKeyDown({
        event: keyEvent('Escape', '19,99'),
        property: 'PriceEntered',
      });

      expect(updatePropertyValue).toHaveBeenCalledWith(
        expect.objectContaining({ property: 'PriceEntered', value: '3.00' })
      );
    });

    it('on Escape, writing nothing back into a lookup cell', () => {
      const updatePropertyValue = jest.fn();
      const wrapper = createRow(updatePropertyValue);

      const escapeEvent = editAndEscape(wrapper, 'M_Product_ID', 'Test');

      expect(updatePropertyValue).not.toHaveBeenCalled();
      expect(escapeEvent.target.value).toBe('Test');
    });

    it('on Escape, writing nothing back into a multi list cell', () => {
      const updatePropertyValue = jest.fn();
      const wrapper = createRow(
        updatePropertyValue,
        fieldsWith('Description', {
          widgetType: 'MultiListValue',
          value: [{ key: '1', caption: 'One' }],
        })
      );

      const escapeEvent = editAndEscape(wrapper, 'Description', 'One');

      expect(updatePropertyValue).not.toHaveBeenCalled();
      expect(escapeEvent.target.value).toBe('One');
    });

    it('but leaves the text of a field which is no decimal number as typed', () => {
      const updatePropertyValue = jest.fn();
      const wrapper = createRow(updatePropertyValue);
      wrapper.setState({ edited: 'Description' });

      wrapper.instance().handleKeyDown({
        event: keyEvent('Tab', '12,50'),
        property: 'Description',
      });

      expect(updatePropertyValue).toHaveBeenCalledWith(
        expect.objectContaining({ property: 'Description', value: '12,50' })
      );
    });

    it('and writes nothing for a lookup cell, whose value is a {key, caption} object', () => {
      const updatePropertyValue = jest.fn();
      const wrapper = createRow(updatePropertyValue);
      wrapper.setState({ edited: 'M_Product_ID' });

      wrapper.instance().handleKeyDown({
        event: keyEvent('Tab', 'Test'),
        property: 'M_Product_ID',
      });

      expect(updatePropertyValue).not.toHaveBeenCalled();
    });

    it('and writes nothing when Enter opens the editor of a cell', () => {
      const updatePropertyValue = jest.fn();
      const wrapper = createRow(updatePropertyValue);

      wrapper.instance().handleKeyDown({
        event: {
          ...keyEvent('Enter', undefined),
          target: { textContent: '3,00' },
        },
        property: 'PriceEntered',
        readonly: false,
      });

      expect(updatePropertyValue).not.toHaveBeenCalled();
    });
  });
});
