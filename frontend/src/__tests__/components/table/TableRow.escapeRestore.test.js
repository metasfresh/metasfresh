import React from 'react';
import { shallow } from 'enzyme';

import { getTableId } from '../../../reducers/tables';
import fixtures from '../../../../test_setup/fixtures/table/table_item_props.json';

import TableRow from '../../../components/table/TableRow';

/**
 * Escape after opening a grid cell's editor puts back the stored field value (e.g. "3.00"), not
 * the displayed text ("3,00" in de_DE, which a number input rejects).
 *
 * A double-click is replayed as the browser fires it: the cell's onDoubleClick first, then the
 * row's.
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
    // opening the editor writes nothing: the editor shows the stored value
    expect(updatePropertyValue).not.toHaveBeenCalled();

    const escapeEvent = pressEscape(instance, NUMBER_PROPERTY, '7');

    const writes = updatePropertyValue.mock.calls
      .map((args) => args[0])
      .filter((p) => p.property === NUMBER_PROPERTY);
    expect(writes).toHaveLength(1);
    expect(writes[0].value).toBe(STORED_VALUE);
    expect(escapeEvent.target.value).toBe(STORED_VALUE);
  });

  it('type-to-activate (a digit on a focused cell) a de_DE number cell, then Escape -> stored "3.00" is restored', () => {
    const updatePropertyValue = jest.fn();
    const wrapper = shallow(
      <TableRow {...createInitProps({ updatePropertyValue })} />
    );
    const instance = wrapper.instance();
    instance.setState({ activeCell: { focus: jest.fn() } });

    // a digit typed on a focused, not-yet-edited cell activates it
    // (handleKeyDown_RegularChar); the value to restore is captured on entry
    instance.handleKeyDown({
      event: {
        key: '7',
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

  it.each([
    ['text (LongText)', 'PriceLimitNote', 'Nicht erzwungen', 'typed text'],
    ['date', 'DateOrdered', '22.01.2020', '01.01.2030'],
  ])(
    '%s cell: double-click, then Escape -> the stored value is restored, not the display text',
    (_label, property, displayedText, typedValue) => {
      const updatePropertyValue = jest.fn();
      const wrapper = shallow(
        <TableRow {...createInitProps({ updatePropertyValue })} />
      );
      const instance = wrapper.instance();
      instance.setState({ activeCell: { focus: jest.fn() } });
      const storedValue = instance.getFieldValue(property);

      doubleClickCell(instance, property, displayedText);
      const escapeEvent = pressEscape(instance, property, typedValue);

      const writes = updatePropertyValue.mock.calls
        .map((args) => args[0])
        .filter((p) => p.property === property);
      expect(writes).toHaveLength(1);
      expect(writes[0].value).toBe(storedValue);
      expect(escapeEvent.target.value).toBe(storedValue);
    }
  );

  it('attribute (ProductAttributes) cell: Escape writes back the stored {key,caption}, never text', () => {
    const property = 'M_AttributeSetInstance_ID';
    const updatePropertyValue = jest.fn();
    const wrapper = shallow(
      <TableRow {...createInitProps({ updatePropertyValue })} />
    );
    const instance = wrapper.instance();
    instance.setState({ activeCell: { focus: jest.fn() } });
    const storedValue = instance.getFieldValue(property);

    doubleClickCell(instance, property, '---');
    pressEscape(instance, property, '');

    const writes = updatePropertyValue.mock.calls
      .map((args) => args[0])
      .filter((p) => p.property === property);
    expect(writes).toHaveLength(1);
    expect(writes[0].value).toEqual(storedValue);
  });
});
