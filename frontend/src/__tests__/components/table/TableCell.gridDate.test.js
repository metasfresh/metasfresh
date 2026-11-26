import React from 'react';
import { shallow } from 'enzyme';
import { omit } from 'lodash';
import MomentTZ from 'moment-timezone';

import tableCellProps from '../../../../test_setup/fixtures/table/table_cell.json';
import TableCell from '../../../components/table/TableCell';
import TableRow from '../../../components/table/TableRow';
import WidgetWrapper from '../../../containers/WidgetWrapper';
import {
  DATE_TEXT,
  PROPERTY,
  createRowProps,
  mountFocusedCellWithEditor,
} from '../../../../test_setup/gridDateCellHelpers';

/**
 * Grid date cell, cell side: only a Date cell hands isGridCell / isPointerEdit /
 * gridCellValue to its widget, and a pointer action (click / double click) enters its edit mode
 * flagged as pointer-originated.
 */
const cellProps = (overrides) => ({
  ...omit(tableCellProps, ['widgetData']),
  tdValue: '20.11.2026',
  isReadonly: false,
  isEditable: true,
  handleDoubleClick: jest.fn(),
  ...overrides,
});

const widgetProps = (props) =>
  shallow(<TableCell {...props} />)
    .find(WidgetWrapper)
    .props();

describe('TableCell — grid date wiring', () => {
  it('a Date cell passes isGridCell, isPointerEdit and the raw cell value to its widget', () => {
    const props = widgetProps(
      cellProps({
        isEdited: true,
        isDateCell: true,
        isPointerEdit: false,
        tableCellData: { value: '2026-11-20' },
      })
    );
    expect(props.isGridCell).toBe(true);
    expect(props.isPointerEdit).toBe(false);
    expect(props.gridCellValue).toBe('2026-11-20');
    expect(props.isOpenDatePicker).toBe(false); // keyboard entry: no auto-open
  });

  it('a Date cell entered by pointer asks its widget to open the calendar', () => {
    const props = widgetProps(
      cellProps({
        isEdited: true,
        isDateCell: true,
        isPointerEdit: true,
        tableCellData: { value: '2026-11-20' },
      })
    );
    expect(props.isPointerEdit).toBe(true);
    expect(props.isOpenDatePicker).toBe(true);
  });

  it('an unparseable moment cell value is handed over as its raw input text', () => {
    const invalid = MomentTZ('not a date', 'DD.MM.YYYY', true);
    expect(invalid.isValid()).toBe(false);
    const props = widgetProps(
      cellProps({
        isEdited: true,
        isDateCell: true,
        tableCellData: { value: invalid },
      })
    );
    expect(props.gridCellValue).toBe('not a date');
  });

  it('a non-Date cell gets none of the grid date props and never auto-opens', () => {
    const props = widgetProps(
      cellProps({
        isEdited: true,
        isDateCell: false,
        isPointerEdit: true, // even if a stale pointer flag were set
        tableCellData: { value: '2026-11-20' },
      })
    );
    expect(props.isGridCell).toBeUndefined();
    expect(props.isPointerEdit).toBeUndefined();
    expect(props.gridCellValue).toBeUndefined();
    expect(props.isOpenDatePicker).toBe(false);
  });

  it('a click on a static Date cell enters edit mode as a pointer edit', () => {
    const handleDoubleClick = jest.fn();
    const wrapper = shallow(
      <TableCell
        {...cellProps({ isDateCell: true, isEdited: false, handleDoubleClick })}
      />
    );
    wrapper.find('td').simulate('click', {});
    expect(handleDoubleClick).toHaveBeenCalledTimes(1);
    expect(handleDoubleClick.mock.calls[0][0]).toMatchObject({
      property: tableCellProps.property,
      focus: true,
      pointer: true,
    });
  });

  it('a modifier-click on a Date cell only extends the selection (no edit)', () => {
    const handleDoubleClick = jest.fn();
    const wrapper = shallow(
      <TableCell
        {...cellProps({ isDateCell: true, isEdited: false, handleDoubleClick })}
      />
    );
    wrapper.find('td').simulate('click', { shiftKey: true });
    expect(handleDoubleClick).not.toHaveBeenCalled();
  });

  it('a single click on a non-Date cell does not edit it', () => {
    const wrapper = shallow(
      <TableCell {...cellProps({ isDateCell: false, isEdited: false })} />
    );
    expect(wrapper.find('td').prop('onClick')).toBeUndefined();
  });

  it('a double click on any cell enters edit mode as a pointer edit', () => {
    const handleDoubleClick = jest.fn();
    const wrapper = shallow(
      <TableCell
        {...cellProps({ isDateCell: false, isEdited: false, handleDoubleClick })}
      />
    );
    wrapper.find('td').simulate('doubleClick', {});
    expect(handleDoubleClick.mock.calls[0][0]).toMatchObject({ pointer: true });
  });

  // a click into a static date cell must leave the date selected, like the keyboard
  // entry does, so that typing replaces it (`20.11.2026` + `03…` must not become `20.11.202603`)
  describe('pointer entry selects the date text, so typing replaces it', () => {
    // the real row, with only the DOM lookup of the cell (_focusCell) skipped
    const createRow = (widgetType) => {
      const row = shallow(<TableRow {...createRowProps(widgetType)} />).instance();
      jest.spyOn(row, '_focusCell').mockImplementation((p, cb) => cb());
      return row;
    };

    let td;
    afterEach(() => td && td.remove());

    const enterByPointer = ({ isDateCell, widgetType, simulate }) => {
      const row = createRow(widgetType);
      const mounted = mountFocusedCellWithEditor();
      td = mounted.td;
      const { input } = mounted;
      const cell = shallow(
        <TableCell
          {...cellProps({
            property: PROPERTY,
            isDateCell,
            isEdited: false,
            handleDoubleClick: row.handleEditProperty,
          })}
        />
      );
      [].concat(simulate).forEach((eventName) =>
        cell.find('td').simulate(eventName, { persist: jest.fn() })
      );
      return { row, input };
    };

    const expectDateFullySelected = ({ row, input }) => {
      expect(row.state.edited).toBe(PROPERTY);
      expect(row.state.isPointerEdit).toBe(true);
      expect(document.activeElement).toBe(input);
      expect(input.selectionStart).toBe(0);
      expect(input.selectionEnd).toBe(DATE_TEXT.length);
    };

    it('a click on a static Date cell focuses its input with the whole date selected', () => {
      expectDateFullySelected(
        enterByPointer({
          isDateCell: true,
          widgetType: 'Date',
          simulate: 'click',
        })
      );
    });

    // the real browser order: the first click already enters edit and selects; the dblclick
    // then finds the focus inside the input and leaves that selection untouched
    it('a click followed by a double click on a Date cell leaves the date fully selected', () => {
      expectDateFullySelected(
        enterByPointer({
          isDateCell: true,
          widgetType: 'Date',
          simulate: ['click', 'doubleClick'],
        })
      );
    });

    it('a double click reaching a not-yet-edited Date cell also selects the date', () => {
      expectDateFullySelected(
        enterByPointer({
          isDateCell: true,
          widgetType: 'Date',
          simulate: 'doubleClick',
        })
      );
    });

    it('a double click on a non-Date cell does not select its text (unchanged)', () => {
      const { row, input } = enterByPointer({
        isDateCell: false,
        widgetType: 'CostPrice',
        simulate: 'doubleClick',
      });

      expect(row.state.edited).toBe(PROPERTY);
      expect(document.activeElement).toBe(input);
      expect(input.selectionStart).toBe(input.value.length);
    });
  });
});
