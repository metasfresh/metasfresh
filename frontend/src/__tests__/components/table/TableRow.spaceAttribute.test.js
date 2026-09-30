import React from 'react';
import { shallow } from 'enzyme';

import { getTableId } from '../../../reducers/tables';
import fixtures from '../../../../test_setup/fixtures/table/table_item_props.json';

import TableRow from '../../../components/table/TableRow';

/**
 * Space (and any printable key) on an attribute-family grid cell must not enter
 * the raw-text edit path.
 *
 * BF-B2's type-to-activate gate keys off `event.key.length === 1`, which now
 * accepts Space. handleKeyDown_RegularChar routes a printable key into
 * handleEditProperty({ select: true }) -> _editProperty calls
 * `this.selectedCell.clearValue()`. For an object-valued attribute cell
 * (ProductAttributes / Address, whose value is {key,caption} and commits through
 * the <Attributes> overlay) that clears the widget's value on activation =
 * silent data loss — the same clobber class the Tab/Enter guards already prevent.
 *
 * Concrete failure pinned here: Space on a ProductAttributes cell that is the
 * active cell (selectedCell set) sets `edited` to the attribute property AND
 * calls clearValue() on it. The fix mirrors the Tab/Enter isAttributeWidget
 * guard: skip the raw-text edit path for attribute widgets. Scalar cells still
 * activate on a printable key (regression control below).
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

  it('still activates a scalar cell on a printable key (regression control)', () => {
    const wrapper = shallow(<TableRow {...createInitProps()} />);
    const instance = wrapper.instance();

    instance.handleKeyDown({
      event: printableKeyEvent(' '),
      property: SCALAR_PROPERTY,
      readonly: false,
      isAttributeWidget: false,
    });

    expect(instance.state.edited).toBe(SCALAR_PROPERTY);
  });
});
