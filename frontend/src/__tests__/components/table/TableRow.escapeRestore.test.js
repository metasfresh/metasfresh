import React from 'react';
import { shallow } from 'enzyme';

import { getTableId } from '../../../reducers/tables';
import fixtures from '../../../../test_setup/fixtures/table/table_item_props.json';

import TableRow from '../../../components/table/TableRow';

/**
 * Escape after opening a grid cell editor must restore the STORED field value,
 * never the cell's displayed text.
 *
 * Concrete failure pinned here: in de_DE a number cell displays "3,00" while
 * its stored value is "3.00". Double-clicking the cell used to remember the
 * displayed text (`e.target.textContent`) as the value before editing; Escape
 * then wrote "3,00" into the `<input type="number">`, which the browser
 * rejects (value becomes ""), and the following blur PATCHed an empty price
 * ("Fill mandatory fields: Price").
 *
 * The double-click sequence is replayed exactly as the browser fires it: the
 * cell's onDoubleClick (TableCell -> TableRow.handleEditProperty) first, then
 * the row's onDoubleClick (TableRow.handleDoubleClick) as the event bubbles.
 */

const NUMBER_PROPERTY = 'PriceEntered'; // fixture: CostPrice, stored "3.00"
const STORED_VALUE = '3.00';
const DISPLAYED_TEXT_DE = '3,00'; // what the de_DE cell shows

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

function doubleClickCell(instance, property, displayedText) {
  const dblClickEvent = {
    target: { textContent: displayedText },
    persist: jest.fn(),
  };
  // TableCell.onDoubleClick -> TableRow.handleEditProperty (cell handler)
  instance.handleEditProperty({
    event: dblClickEvent,
    property,
    focus: true,
    readonly: false,
  });
  // the same dblclick bubbles to the <tr> -> TableRow.handleDoubleClick
  instance.handleDoubleClick(dblClickEvent);
}

function pressEscape(instance, property, editorValue) {
  const escapeEvent = {
    key: 'Escape',
    target: { value: editorValue },
    stopPropagation: jest.fn(),
    persist: jest.fn(),
  };
  instance.handleKeyDown({
    event: escapeEvent,
    property,
    readonly: false,
    isAttributeWidget: false,
  });
  return escapeEvent;
}

describe('TableRow — Escape restores the stored value, not the displayed text', () => {
  it('double-click a de_DE number cell, then Escape -> stored "3.00" is restored', () => {
    const updatePropertyValue = jest.fn();
    const wrapper = shallow(
      <TableRow {...createInitProps({ updatePropertyValue })} />
    );
    const instance = wrapper.instance();
    // Escape re-focuses the active cell element
    instance.setState({ activeCell: { focus: jest.fn() } });

    doubleClickCell(instance, NUMBER_PROPERTY, DISPLAYED_TEXT_DE);
    expect(instance.state.edited).toBe(NUMBER_PROPERTY);

    const escapeEvent = pressEscape(instance, NUMBER_PROPERTY, '7');

    const writes = updatePropertyValue.mock.calls
      .map((args) => args[0])
      .filter((p) => p.property === NUMBER_PROPERTY);
    expect(writes).toHaveLength(1);
    expect(writes[0].value).toBe(STORED_VALUE);
    // the editor input gets the stored (machine-format) value back
    expect(escapeEvent.target.value).toBe(STORED_VALUE);
  });

  it('Enter-activate (not yet edited) a de_DE number cell, then Escape -> stored "3.00" is restored', () => {
    const updatePropertyValue = jest.fn();
    const wrapper = shallow(
      <TableRow {...createInitProps({ updatePropertyValue })} />
    );
    const instance = wrapper.instance();
    instance.setState({ activeCell: { focus: jest.fn() } });

    // Enter on a focused, not-yet-edited cell: target is the <td>, so it has
    // displayed text but no input value
    instance.handleKeyDown({
      event: {
        key: 'Enter',
        target: { textContent: DISPLAYED_TEXT_DE },
        stopPropagation: jest.fn(),
        persist: jest.fn(),
      },
      property: NUMBER_PROPERTY,
      readonly: false,
      isAttributeWidget: false,
    });
    expect(instance.state.edited).toBe(NUMBER_PROPERTY);
    updatePropertyValue.mockClear();

    const escapeEvent = pressEscape(instance, NUMBER_PROPERTY, '7');

    const writes = updatePropertyValue.mock.calls
      .map((args) => args[0])
      .filter((p) => p.property === NUMBER_PROPERTY);
    expect(writes).toHaveLength(1);
    expect(writes[0].value).toBe(STORED_VALUE);
    expect(escapeEvent.target.value).toBe(STORED_VALUE);
  });

  it('Lookup cell: Enter on typed text, then Escape -> the typed text is never written over the stored object', () => {
    const LOOKUP_PROPERTY = 'M_Product_ID'; // fixture: { key, caption }
    const TYPED_TEXT = 'qzzx9nomatch';
    const updatePropertyValue = jest.fn();
    const wrapper = shallow(
      <TableRow {...createInitProps({ updatePropertyValue })} />
    );
    const instance = wrapper.instance();
    instance.setState({ activeCell: { focus: jest.fn() } });

    doubleClickCell(instance, LOOKUP_PROPERTY, '1000001_TestProduct1');
    // Enter while the editor holds the typed text (the Lookup itself restores
    // its previous value; the grid row must not remember the raw text)
    instance.handleKeyDown({
      event: {
        key: 'Enter',
        target: { value: TYPED_TEXT },
        stopPropagation: jest.fn(),
        persist: jest.fn(),
      },
      property: LOOKUP_PROPERTY,
      readonly: false,
      isAttributeWidget: false,
    });
    const escapeEvent = pressEscape(instance, LOOKUP_PROPERTY, TYPED_TEXT);

    const writes = updatePropertyValue.mock.calls
      .map((args) => args[0])
      .filter((p) => p.property === LOOKUP_PROPERTY);
    writes.forEach((payload) => {
      expect(typeof payload.value).not.toBe('string');
    });
    expect(escapeEvent.target.value).toBe(TYPED_TEXT);
  });
});
