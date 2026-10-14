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
      row.handleKeyDown({
        event: keyEvent('Escape', '99,99'),
        property: 'PriceEntered',
      });

      expect(updatePropertyValue).toHaveBeenCalledWith(
        expect.objectContaining({ property: 'PriceEntered', value: '1234' })
      );
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
