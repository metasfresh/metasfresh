import React from 'react';
import { shallow } from 'enzyme';

import { getTableId } from '../../../reducers/tables';
import fixtures from '../../../../test_setup/fixtures/table/table_item_props.json';

import TableRow from '../../../components/table/TableRow';

/**
 * Grid date cell, row side: entering edit mode focuses the cell's `.js-input-field`
 * (the class DatePicker puts on a grid date input), a date cell keeps and selects its value
 * instead of clearing it, its widget patches its own value, and the pointer/keyboard origin of
 * the edit is recorded for the cell.
 */
const PROPERTY = 'PriceEntered'; // fixture field; each test gives it the widget type under test

function createInitProps(widgetType) {
  const propsSeed = fixtures.oldProps1;
  return {
    ...propsSeed,
    fieldsByName: {
      ...propsSeed.fieldsByName,
      [PROPERTY]: {
        ...propsSeed.fieldsByName[PROPERTY],
        field: PROPERTY,
        widgetType,
      },
    },
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
  };
}

// a focused <td> holding the grid editor input, as the browser has it while a cell is edited
function mountFocusedCellWithEditor() {
  const td = document.createElement('td');
  td.tabIndex = 0;
  const input = document.createElement('input');
  input.className = 'form-control js-input-field';
  input.value = '20.11.2026';
  td.appendChild(input);
  document.body.appendChild(td);
  td.focus();
  input.select = jest.fn();
  return { td, input };
}

const keyEvent = (target) => ({
  key: '1',
  target,
  stopPropagation: jest.fn(),
  persist: jest.fn(),
});

describe('TableRow — grid date cell edit entry', () => {
  let cleanup;
  afterEach(() => {
    cleanup && cleanup();
    cleanup = null;
  });

  it('entering edit mode focuses and selects the date input via .js-input-field', () => {
    const { td, input } = mountFocusedCellWithEditor();
    cleanup = () => td.remove();
    const instance = shallow(
      <TableRow {...createInitProps('Date')} />
    ).instance();
    const focusSpy = jest.spyOn(input, 'focus');

    instance._editProperty({
      event: { persist: jest.fn() },
      property: PROPERTY,
      focus: true,
      readonly: false,
      mark: true,
    });

    expect(instance.state.edited).toBe(PROPERTY);
    expect(input.select).toHaveBeenCalledTimes(1);
    expect(focusSpy).toHaveBeenCalled();
    expect(document.activeElement).toBe(input);
  });

  // runs the REAL handleEditProperty / _editProperty (only the DOM-lookup of the cell is skipped),
  // so what the edit does to the cell's widget is observable on `clearValue`
  const typeOnCell = (widgetType) => {
    const instance = shallow(
      <TableRow {...createInitProps(widgetType)} />
    ).instance();
    const clearValue = jest.fn();
    instance.selectedCell = { clearValue };
    jest.spyOn(instance, '_focusCell').mockImplementation((p, cb) => cb());
    const editSpy = jest.spyOn(instance, 'handleEditProperty');

    instance.handleKeyDown_RegularChar({
      event: keyEvent({}),
      property: PROPERTY,
      readonly: false,
      isAttributeWidget: false,
    });
    return { instance, clearValue, editSpy };
  };

  it('typing on a focused Date cell marks the value for replacement, never clears it', () => {
    const { instance, clearValue, editSpy } = typeOnCell('Date');

    expect(editSpy.mock.calls[0][0]).toMatchObject({
      property: PROPERTY,
      focus: true,
      select: false,
      mark: true,
    });
    expect(instance.state.edited).toBe(PROPERTY); // the real edit ran
    expect(clearValue).not.toHaveBeenCalled();
  });

  it.each(['CostPrice', 'DateTime', 'ZonedDateTime', 'Time'])(
    'typing on a %s cell still select-clears (unchanged: only Date is special)',
    (widgetType) => {
      const { instance, clearValue, editSpy } = typeOnCell(widgetType);

      expect(editSpy.mock.calls[0][0]).toMatchObject({
        select: true,
        mark: false,
      });
      expect(instance.state.edited).toBe(PROPERTY);
      expect(clearValue).toHaveBeenCalledTimes(1);
    }
  );

  it('a Date cell never has the input text written into row data; a number cell does', () => {
    const dateRow = shallow(
      <TableRow {...createInitProps('Date')} />
    ).instance();
    expect(dateRow.writeScalarFieldValue(PROPERTY, '20.11.2026')).toBe(
      false
    );
    expect(dateRow.props.updatePropertyValue).not.toHaveBeenCalled();

    const numberRow = shallow(
      <TableRow {...createInitProps('CostPrice')} />
    ).instance();
    expect(numberRow.writeScalarFieldValue(PROPERTY, '7')).toBe(true);
    expect(numberRow.props.updatePropertyValue).toHaveBeenCalledTimes(1);
  });

  it('records whether the edit was entered by pointer or keyboard', () => {
    const instance = shallow(
      <TableRow {...createInitProps('Date')} />
    ).instance();
    jest.spyOn(instance, '_focusCell').mockImplementation((p, cb) => cb());
    const edit = (extra) =>
      instance.handleEditProperty({
        event: { persist: jest.fn() },
        property: PROPERTY,
        focus: false,
        readonly: false,
        ...extra,
      });

    edit({ pointer: true });
    expect(instance.state.isPointerEdit).toBe(true);

    instance.handleEditProperty({ event: { persist: jest.fn() } }); // leave edit mode
    expect(instance.state.edited).toBeUndefined();

    edit({}); // entered again without a pointer flag (keyboard)
    expect(instance.state.edited).toBe(PROPERTY);
    expect(instance.state.isPointerEdit).toBe(false);
  });
});
