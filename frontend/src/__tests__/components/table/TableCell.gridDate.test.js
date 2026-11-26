import React from 'react';
import { shallow } from 'enzyme';
import { omit } from 'lodash';
import MomentTZ from 'moment-timezone';

import tableCellProps from '../../../../test_setup/fixtures/table/table_cell.json';
import TableCell from '../../../components/table/TableCell';
import TableRow from '../../../components/table/TableRow';
import { getTableId } from '../../../reducers/tables';
import tableRowFixtures from '../../../../test_setup/fixtures/table/table_item_props.json';
import WidgetWrapper from '../../../containers/WidgetWrapper';

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

  // me03#33085: a click into a static date cell must leave the date selected, like the keyboard
  // entry does, so that typing replaces it (`20.11.2026` + `03…` must not become `20.11.202603`)
  describe('pointer entry selects the date text, so typing replaces it', () => {
    const PROPERTY = 'PriceEntered'; // fixture field, given the widget type under test

    // the real row, with only the DOM lookup of the cell (_focusCell) skipped
    const createRow = (widgetType) => {
      const seed = tableRowFixtures.oldProps1;
      const row = shallow(
        <TableRow
          {...seed}
          fieldsByName={{
            ...seed.fieldsByName,
            [PROPERTY]: {
              ...seed.fieldsByName[PROPERTY],
              field: PROPERTY,
              widgetType,
            },
          }}
          tableId={getTableId(seed)}
          changeListenOnTrue={jest.fn()}
          changeListenOnFalse={jest.fn()}
          updatePropertyValue={jest.fn()}
        />
      ).instance();
      jest.spyOn(row, '_focusCell').mockImplementation((p, cb) => cb());
      return row;
    };

    // a focused <td> holding the grid editor input with the caret at the end of the date, as the
    // browser has it right after the editor mounted
    let td;
    const mountFocusedCellWithEditor = () => {
      td = document.createElement('td');
      td.tabIndex = 0;
      const input = document.createElement('input');
      input.className = 'form-control js-input-field';
      input.value = '20.11.2026';
      td.appendChild(input);
      document.body.appendChild(td);
      td.focus();
      input.setSelectionRange(input.value.length, input.value.length);
      return input;
    };
    afterEach(() => td && td.remove());

    const enterByPointer = ({ isDateCell, widgetType, simulate }) => {
      const row = createRow(widgetType);
      const input = mountFocusedCellWithEditor();
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
      cell.find('td').simulate(simulate, {});
      return { row, input };
    };

    it.each(['click', 'doubleClick'])(
      'a %s on a static Date cell focuses its input with the whole date selected',
      (simulate) => {
        const { row, input } = enterByPointer({
          isDateCell: true,
          widgetType: 'Date',
          simulate,
        });

        expect(row.state.edited).toBe(PROPERTY);
        expect(row.state.isPointerEdit).toBe(true);
        expect(document.activeElement).toBe(input);
        expect(input.selectionStart).toBe(0);
        expect(input.selectionEnd).toBe('20.11.2026'.length);
      }
    );

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
