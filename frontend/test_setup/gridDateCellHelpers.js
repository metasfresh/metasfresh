import { getTableId } from '../src/reducers/tables';
import fixtures from './fixtures/table/table_item_props.json';

/**
 * Shared fixtures of the grid Date cell tests (TableRow / TableCell): a row whose
 * `PROPERTY` field has the widget type under test, and the DOM a browser has while a grid
 * date cell is being edited.
 */
export const PROPERTY = 'PriceEntered'; // fixture field; each test gives it the widget type under test
export const DATE_TEXT = '20.11.2026';

export function createRowProps(widgetType) {
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

// a focused <td> holding the grid editor input (value DATE_TEXT, caret at its end), as the
// browser has it right after the editor mounted
export function mountFocusedCellWithEditor() {
  const td = document.createElement('td');
  td.tabIndex = 0;
  const input = document.createElement('input');
  input.className = 'form-control js-input-field';
  input.value = DATE_TEXT;
  td.appendChild(input);
  document.body.appendChild(td);
  td.focus();
  input.setSelectionRange(input.value.length, input.value.length);
  return { td, input };
}
