import React from 'react';
import { mount } from 'enzyme';
import { Provider } from 'react-redux';
import configureStore from 'redux-mock-store';
import { merge } from 'merge-anything';

import { initialState as appHandlerState } from '../../../reducers/appHandler';
import {
  initialState as windowHandlerState,
} from '../../../reducers/windowHandler';
import viewHandler from '../../../reducers/viewHandler';
import tablesHandler from '../../../reducers/tables';
import Table from '../../../components/table/Table';

/**
 * Load-time combobox stored-width clamp must survive the async column-metadata
 * path (BF-B4c).
 *
 * On a normal tab-open the table mounts BEFORE the view/tab column metadata is
 * reduced, so `Table.componentDidMount` runs `clampComboboxColumnWidths` with
 * `columns === []` -> empty widgetTypeByField -> a stored sub-210 combobox width
 * is returned UN-clamped and lands in `state.columnWidths`. Column metadata then
 * arrives moments later (columns []->populated) for the SAME window/view.
 *
 * Concrete failure pinned here: a returning user with a stored sub-210 combobox
 * width (localStorage columnWidths_<windowId>_<viewId>) never gets it clamped,
 * because the only post-mount re-clamp fired on a windowId/viewId change, not on
 * the columns []->populated transition. The stored 144px then wins in TableCell,
 * the ~210px floor is skipped, and the combobox dropdown overlaps the next
 * column (AC13 Partiecode overlap) again.
 */

const mockStore = configureStore([]);
const createStore = (state = {}) =>
  merge(
    {
      appHandler: {
        ...appHandlerState,
        me: { timeZone: 'America/Los_Angeles' },
      },
      windowHandler: { ...windowHandlerState },
      ...viewHandler,
      tables: { ...tablesHandler(undefined, {}) },
    },
    state
  );
const store = mockStore(createStore());

const WINDOW_ID = '540189';
const VIEW_ID = 'view-abc';
const COMBOBOX_FIELD = 'Partiecode';
const STORED_SUB_FLOOR_WIDTH = 144;

// Column metadata as it arrives AFTER mount (the combobox is a Lookup widget).
const populatedColumns = [
  { fields: [{ field: COMBOBOX_FIELD, supportZoomInto: false }], widgetType: 'Lookup' },
];

const baseProps = {
  windowId: WINDOW_ID,
  viewId: VIEW_ID,
  rows: [],
  columns: [],
  selected: [],
  collapsedParentRows: [],
  collapsedRows: [],
  keyProperty: 'id',
  mainTable: true,
  readonly: true,
  tabIndex: 0,
  entity: 'documentView',
  rowRefs: {},
  onRightClick: jest.fn(),
  handleSelect: jest.fn(),
  onSelect: jest.fn(),
  onGetAllLeaves: jest.fn(),
  onSelectAll: jest.fn(),
  onDeselectAll: jest.fn(),
  onDeselect: jest.fn(),
  onSortTable: jest.fn(),
  collapseTableRow: jest.fn(),
  deselectTableRows: jest.fn(),
  openModal: jest.fn(),
  updateTableSelection: jest.fn(),
};

function Host({ columns }) {
  return (
    <Provider store={store}>
      <Table {...baseProps} columns={columns} />
    </Provider>
  );
}

describe('Table — combobox load-time clamp survives async columns []->populated', () => {
  beforeEach(() => {
    localStorage.clear();
    localStorage.setItem(
      `columnWidths_${WINDOW_ID}_${VIEW_ID}`,
      JSON.stringify({ [COMBOBOX_FIELD]: STORED_SUB_FLOOR_WIDTH })
    );
  });

  afterEach(() => localStorage.clear());

  it('clamps a stored sub-210 combobox width once columns arrive for the same window/view', () => {
    const wrapper = mount(<Host columns={[]} />);
    const instance = wrapper.find('Table').instance();

    // Sanity: mounted with columns === [], the stored width lands un-clamped
    // (empty widgetTypeByField => the field is not recognized as a combobox).
    expect(instance.state.columnWidths[COMBOBOX_FIELD]).toBe(
      STORED_SUB_FLOOR_WIDTH
    );

    // Column metadata arrives (columns []->populated), same window/view.
    wrapper.setProps({ columns: populatedColumns });
    wrapper.update();

    // The stored sub-floor combobox width must now be clamped to the ~210 floor.
    expect(
      instance.state.columnWidths[COMBOBOX_FIELD]
    ).toBeGreaterThanOrEqual(210);

    wrapper.unmount();
  });
});
