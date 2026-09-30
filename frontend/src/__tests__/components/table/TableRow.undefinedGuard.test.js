import React from 'react';
import { shallow } from 'enzyme';

import { getTableId } from '../../../reducers/tables';
import fixtures from '../../../../test_setup/fixtures/table/table_item_props.json';

import TableRow from '../../../components/table/TableRow';

/**
 * Type-guard: the grid row nav layer must not clobber an object-valued
 * Lookup/Search cell with the editor's raw text on Tab/Enter.
 *
 * A grid Lookup/Search cell holds an OBJECT value ({key, caption}). Today, on
 * Tab/Enter, TableRow.handleKeyDown_Tab and handleKeyDown_Enter write
 * `event.target.value` (the editor's RAW TEXT string) over that object via
 * `updatePropertyValue`, so after the user advances away and back the cell
 * renders the literal string it was clobbered with (e.g. "undefined").
 *
 * Concrete failure pinned here: an object-valued (Lookup/List) cell overwritten
 * by a raw string on Tab/Enter. Scalar (text/number) cells MUST still commit
 * their raw value. TableRow is the grid-only row component (used solely by
 * Table.js, renders a <tr>, dataSource="table"); single-row/detail forms use a
 * different component, so this guard is inherently grid-mode scoped.
 *
 * Shallow rendering exercises the real handleKeyDown -> handleKeyDown_Tab /
 * handleKeyDown_Enter code path (the write locus) without deep-mounting the
 * Redux-connected cell editor.
 */

const LOOKUP_PROPERTY = 'M_Product_ID'; // fixture: value = { key, caption } (object-valued)
const SCALAR_PROPERTY = 'QtyEntered'; // fixture: value = "3" (scalar)
const RAW_EDITOR_TEXT = '1000001_TestProduct1'; // raw string an active editor input holds
const SCALAR_EDITOR_TEXT = '53'; // scalar cell's committed value

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

function keyEvent(key, value) {
  return {
    key,
    target: { value, textContent: value },
    stopPropagation: jest.fn(),
    persist: jest.fn(),
  };
}

/** row-write calls made for a given property */
function callsForProperty(updatePropertyValue, property) {
  return updatePropertyValue.mock.calls
    .map((args) => args[0])
    .filter((payload) => payload && payload.property === property);
}

describe('TableRow — object-valued cell type-guard on Tab/Enter', () => {
  it('does NOT write a raw string over an object-valued Lookup cell on Tab or Enter', () => {
    const updatePropertyValue = jest.fn();
    const wrapper = shallow(
      <TableRow {...createInitProps({ updatePropertyValue })} />
    );
    const instance = wrapper.instance();

    instance.handleKeyDown({
      event: keyEvent('Tab', RAW_EDITOR_TEXT),
      property: LOOKUP_PROPERTY,
      readonly: false,
      isAttributeWidget: false,
    });
    instance.handleKeyDown({
      event: keyEvent('Enter', RAW_EDITOR_TEXT),
      property: LOOKUP_PROPERTY,
      readonly: false,
      isAttributeWidget: false,
    });

    const lookupWrites = callsForProperty(updatePropertyValue, LOOKUP_PROPERTY);
    // The nav layer must never persist the editor's raw text over the {key,caption} object.
    lookupWrites.forEach((payload) => {
      expect(typeof payload.value).not.toBe('string');
    });
  });

  it('still commits a scalar cell value on Tab (regression control)', () => {
    const updatePropertyValue = jest.fn();
    const wrapper = shallow(
      <TableRow {...createInitProps({ updatePropertyValue })} />
    );
    const instance = wrapper.instance();

    instance.handleKeyDown({
      event: keyEvent('Tab', SCALAR_EDITOR_TEXT),
      property: SCALAR_PROPERTY,
      readonly: false,
      isAttributeWidget: false,
    });

    const scalarWrites = callsForProperty(updatePropertyValue, SCALAR_PROPERTY);
    expect(scalarWrites).toHaveLength(1);
    expect(scalarWrites[0].value).toBe(SCALAR_EDITOR_TEXT);
  });
});
