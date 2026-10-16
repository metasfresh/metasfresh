import React from 'react';
import { shallow } from 'enzyme';

import { getTableId } from '../../../reducers/tables';
import fixtures from '../../../../test_setup/fixtures/table/table_item_props.json';

import TableRow from '../../../components/table/TableRow';

/**
 * A printable key (a letter, or Space) on an attribute cell (ProductAttributes, Address) neither
 * opens a text edit nor clears the cell's value; on a scalar cell a letter opens the edit.
 */

const ATTR_PROPERTY = 'M_AttributeSetInstance_ID'; // fixture: ProductAttributes, object-valued
const SCALAR_PROPERTY = 'QtyEntered'; // fixture: scalar Quantity cell

function createInitProps(customProps) {
  const propsSeed = fixtures.oldProps1;
  return {
    ...propsSeed,
    tableId: getTableId(propsSeed),
    onClick: jest.fn(),
    handleSelect: jest.fn(),
    onDoubleClick: jest.fn(),
    changeListenOnTrue: jest.fn(),
    changeListenOnFalse: jest.fn(),
    handleRowCollapse: jest.fn(),
    handleRightClick: jest.fn(),
    updatePropertyValue: jest.fn(),
    ...customProps,
  };
}

function printableKeyEvent(key) {
  return {
    key,
    keyCode: key === ' ' ? 32 : key.charCodeAt(0),
    ctrlKey: false,
    altKey: false,
    metaKey: false,
    target: { value: key, textContent: '' },
    persist: jest.fn(),
    stopPropagation: jest.fn(),
  };
}

describe('TableRow — Space/printable key must not raw-text-edit an attribute cell', () => {
  it('does NOT activate or clearValue an attribute (ProductAttributes/Address) cell on Space', () => {
    const wrapper = shallow(<TableRow {...createInitProps()} />);
    const instance = wrapper.instance();
    const clearValue = jest.fn();
    instance.selectedCell = { clearValue };

    instance.handleKeyDown({
      event: printableKeyEvent(' '),
      property: ATTR_PROPERTY,
      readonly: false,
      isAttributeWidget: true,
    });

    expect(instance.state.edited).not.toBe(ATTR_PROPERTY);
    expect(clearValue).not.toHaveBeenCalled();
  });

  it('does NOT activate or clearValue an attribute cell on a letter key', () => {
    const wrapper = shallow(<TableRow {...createInitProps()} />);
    const instance = wrapper.instance();
    const clearValue = jest.fn();
    instance.selectedCell = { clearValue };

    instance.handleKeyDown({
      event: printableKeyEvent('a'),
      property: ATTR_PROPERTY,
      readonly: false,
      isAttributeWidget: true,
    });

    expect(instance.state.edited).not.toBe(ATTR_PROPERTY);
    expect(clearValue).not.toHaveBeenCalled();
  });

  it('activates a scalar cell on a letter key', () => {
    const wrapper = shallow(<TableRow {...createInitProps()} />);
    const instance = wrapper.instance();

    instance.handleKeyDown({
      event: printableKeyEvent('a'),
      property: SCALAR_PROPERTY,
      readonly: false,
      isAttributeWidget: false,
    });

    expect(instance.state.edited).toBe(SCALAR_PROPERTY);
  });
});
