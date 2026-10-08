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
 * A stored combobox column width below 90px is raised to 90px also when the table mounts before
 * its column metadata arrives: the clamp runs again once the columns become available.
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
const COMBOBOX_FIELD = 'LotCode';
const STORED_SUB_FLOOR_WIDTH = 60;

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

  it('clamps a stored sub-90 combobox width once columns arrive for the same window/view', () => {
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

    // The stored sub-floor combobox width is clamped to the 90px floor.
    expect(
      instance.state.columnWidths[COMBOBOX_FIELD]
    ).toBe(90);

    wrapper.unmount();
  });

  it('clamps a stored sub-90 combobox width on mount when the columns are already known', () => {
    const wrapper = mount(<Host columns={populatedColumns} />);
    const instance = wrapper.find('Table').instance();

    expect(instance.state.columnWidths[COMBOBOX_FIELD]).toBe(90);

    wrapper.unmount();
  });
});
