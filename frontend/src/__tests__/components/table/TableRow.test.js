import React from 'react';
import { shallow, mount } from 'enzyme';
import renderer from 'react-test-renderer';

import fixtures from '../../../../test_setup/fixtures/table/table_item_props.json';

import { getTableId } from '../../../reducers/tables';
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
    const createRow = (updatePropertyValue) => {
      const props = createInitProps(fixtures.oldProps1, {
        updatePropertyValue,
      });
      return shallow(<TableRow {...props} />);
    };
    const keyEvent = (key, value) => ({
      key,
      target: { value },
      stopPropagation: jest.fn(),
      persist: jest.fn(),
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

    it('on Escape, restoring the value taken from the rendered cell', () => {
      const updatePropertyValue = jest.fn();
      const wrapper = createRow(updatePropertyValue);
      // e.g. a double click takes the value before editing from the rendered cell, which shows '12,50' in German
      wrapper.setState({
        edited: 'PriceEntered',
        valueBeforeEditing: '12,50',
        activeCell: { focus: jest.fn() },
      });

      wrapper.instance().handleKeyDown({
        event: keyEvent('Escape', '99,99'),
        property: 'PriceEntered',
      });

      expect(updatePropertyValue).toHaveBeenCalledWith(
        expect.objectContaining({ property: 'PriceEntered', value: '12.50' })
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
  });
});
