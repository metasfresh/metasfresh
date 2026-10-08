import React from 'react';
import { shallow } from 'enzyme';

import { getTableId } from '../../../reducers/tables';
import fixtures from '../../../../test_setup/fixtures/table/table_item_props.json';

import TableRow from '../../../components/table/TableRow';

/**
 * Real-life case: in the business partner window, Address tab, the user double-clicks the address
 * cell, changes the address in the address popup and clicks outside the popup. The popup saves the
 * new address and the row now holds it, while the cell stays in edit mode. The user then presses
 * Escape: the cell must keep showing the NEW (saved) address, not the address from before the edit.
 *
 * The same applies to an order line's attribute cell (ProductAttributes), which is edited through
 * the same popup.
 */

const OLD_ADDRESS = { key: '1000001', caption: 'Bremerhaven_Deutschland' };
const NEW_ADDRESS = { key: '1000002', caption: 'Cuxhaven_Deutschland' };
const OLD_ATTRIBUTES = { key: '0', caption: '---' };
const NEW_ATTRIBUTES = { key: '1000010', caption: 'Lot 4711' };

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
    onItemChange: jest.fn(),
    getSizeClass: jest.fn(),
    updatePropertyValue: jest.fn(),
    ...customProps,
  };
}

function withField(fieldsByName, property, widgetType, value) {
  return {
    ...fieldsByName,
    [property]: { ...fieldsByName[property], field: property, widgetType, value },
  };
}

describe('TableRow — Escape after an attribute popup saved a new value keeps the new value', () => {
  it.each([
    ['Address', 'C_Location_ID', OLD_ADDRESS, NEW_ADDRESS],
    [
      'ProductAttributes',
      'M_AttributeSetInstance_ID',
      OLD_ATTRIBUTES,
      NEW_ATTRIBUTES,
    ],
  ])(
    '%s cell: double-click, popup saves a new value, Escape -> the stale pre-edit value is not written back',
    (widgetType, property, oldValue, newValue) => {
      const updatePropertyValue = jest.fn();
      const fieldsBefore = withField(
        fixtures.oldProps1.fieldsByName,
        property,
        widgetType,
        oldValue
      );
      const wrapper = shallow(
        <TableRow
          {...createInitProps({
            updatePropertyValue,
            fieldsByName: fieldsBefore,
          })}
        />
      );
      const instance = wrapper.instance();
      instance.setState({ activeCell: { focus: jest.fn() } });

      // double-click opens the cell editor (TableCell.onDoubleClick -> handleEditProperty)
      instance.handleEditProperty({
        event: { target: { textContent: oldValue.caption }, persist: jest.fn() },
        property,
        focus: true,
        readonly: false,
      });
      expect(instance.state.edited).toBe(property);

      // the popup saved the new value: the row (redux) now holds it; the cell stays in edit mode
      wrapper.setProps({
        fieldsByName: withField(fieldsBefore, property, widgetType, newValue),
      });
      expect(instance.getFieldValue(property)).toEqual(newValue);

      instance.handleKeyDown({
        event: {
          key: 'Escape',
          target: {},
          stopPropagation: jest.fn(),
          persist: jest.fn(),
        },
        property,
        readonly: false,
        isAttributeWidget: true,
      });

      // the cell leaves edit mode ...
      expect(instance.state.edited).toBeFalsy();
      // ... and nothing overwrites the saved value in the row with the pre-edit one
      const writes = updatePropertyValue.mock.calls
        .map((args) => args[0])
        .filter((p) => p.property === property);
      expect(writes).toEqual([]);
    }
  );
});
