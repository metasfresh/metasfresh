import React from 'react';
import { shallow, mount } from 'enzyme';
import { Provider } from 'react-redux';
import configureStore from 'redux-mock-store';
import merge from 'merge';

import { initialState as appHandlerState } from '../../../reducers/appHandler';
import { initialState as windowHandlerState } from '../../../reducers/windowHandler';
import viewHandler from '../../../reducers/viewHandler';
import tablesHandler, { initialTableState } from '../../../reducers/tables';
import { createTableData } from '../../../actions/TableActions';
import propsData from '../../../../test_setup/fixtures/table/props.json';
import tableData from '../../../../test_setup/fixtures/table/data.json';

import { ShortcutProvider } from '../../../components/keyshortcuts/ShortcutProvider';
import Table from '../../../components/table/Table';

const mockStore = configureStore([]);

const createStore = function(state = {}) {
  const res = merge.recursive(
    true,
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

  return res;
};

const initialState = createStore({
  windowHandler: {
    allowShortcut: true,
    modal: {
      visible: false,
    },
  },
});
const store = mockStore(initialState);
const tableProps = {
  ...propsData.props1,
  ...initialTableState,
  ...createTableData(tableData),
  collapseTableRow: jest.fn(),
  deselectTableItems: jest.fn(),
  openModal: jest.fn(),
  updateTableSelection: jest.fn(),
  onSelect: jest.fn(),
  onGetAllLeaves: jest.fn(),
  onSelectAll: jest.fn(),
  onDeselectAll: jest.fn(),
  onDeselect: jest.fn(),
  onRightClick: jest.fn(),
  handleSelect: jest.fn(),
};

describe('Table component', () => {
  it('renders without errors with bare props', () => {
    shallow(<Table {...tableProps} />);
  });

  it('props passed are correctly set within the wrapper', () => {
    const wrapper = mount(<Table {...tableProps} />);
    const wrapperProps = wrapper.props();

    expect(wrapperProps.entity).toEqual(tableProps.entity);
    expect(wrapperProps.windowId).toEqual(tableProps.windowId);
    expect(wrapperProps.emptyText).toEqual(tableData.emptyResultText);
    expect(wrapperProps.emptyHint).toEqual(tableData.emptyResultHint);
    expect(wrapperProps.readonly).toEqual(true);
    expect(wrapperProps.mainTable).toEqual(true);
    expect(wrapperProps.tabIndex).toEqual(0);
    expect(wrapperProps.size).toEqual(143);
    expect(wrapperProps.pageLength).toEqual(20);
    expect(wrapperProps.page).toEqual(1);
    expect(wrapperProps.hasIncluded).toEqual(null);
    expect(wrapperProps.viewId).toEqual(tableProps.viewId);
  });

  it('renders empty table when there are no rows', () => {
    const localProps = {
      ...tableProps,
      rows: [],
    };
    const wrapper = shallow(<Table {...localProps} />);

    expect(wrapper.html()).toContain(tableData.emptyResultText);
  });

  it('renders without errors with store data', () => {
    const tableWrapper = mount(
      <ShortcutProvider hotkeys={{}} keymap={{}}>
        <Provider store={store}>
          <Table {...tableProps} />
        </Provider>
      </ShortcutProvider>
    );
    const html = tableWrapper.html();
    expect(html).toContain(
      'table table-bordered-vertically table-striped js-table table-read-only'
    );
    expect(html).toContain('testfirma WebUI AG');
  });

  it('No row is selected if selection is empty', async () => {
    const tableWrapper = mount(
      <ShortcutProvider hotkeys={{}} keymap={{}}>
        <Provider store={store}>
          <Table {...tableProps} selected={[]} />
        </Provider>
      </ShortcutProvider>
    );

    const html = tableWrapper.html();
    expect(html).not.toContain('row-selected');
  });

  it('Cell is selected and row focused', async () => {
    const tableWrapper = mount(
      <ShortcutProvider hotkeys={{}} keymap={{}}>
        <Provider store={store}>
          <Table {...tableProps} selected={['1000194']} />
        </Provider>
      </ShortcutProvider>
    );

    const html = tableWrapper.html();
    expect(html).toContain('row-selected');
    expect(html).toContain('row-0-143-B row-1000194 row-selected tr-even');
  });

  it.todo('Lookup widget is focused on selecting row');

  it.todo('Lookup widget is blurred on patch and re-focused on key');

  it.todo('Lookup widget is blurred on keys');

  it.todo('Lookup widget is re-focused on select after traversing back');

  it.todo('Number widget is focused on selecting row');

  it.todo('Number widget is blurred on patch and re-focused on key');

  it.todo('Number widget is blurred on keys');

  it.todo('Number widget is re-focused on select after traversing back');

  it.todo('Text widget is focused on selecting row');

  it.todo('Text widget is blurred on patch and re-focused on key');

  it.todo('Text widget is blurred on keys');

  it.todo('Text widget is re-focused on select after traversing back');

  it.todo('Textarea widget is focused on selecting row');

  it.todo('Textarea widget is blurred on patch and re-focused on key');

  it.todo('Textarea widget is blurred on keys');

  it.todo('Textarea widget is re-focused on select after traversing back');
});
