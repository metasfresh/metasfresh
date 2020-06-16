import React from 'react';
import { shallow, mount } from 'enzyme';
import { Provider } from 'react-redux';
import configureStore from 'redux-mock-store';
import merge from 'merge';

import { initialState as appHandlerState } from '../../../reducers/appHandler';
import { initialState as windowHandlerState } from '../../../reducers/windowHandler';
import viewHandler from '../../../reducers/viewHandler';
import
  tablesHandler,
  {
    initialTableState,
  }
from '../../../reducers/tables';
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
  ...createTableData(tableData)
}

describe('Table component', () => {
  it('renders without errors with bare props', () => {
    const tableWrapper = mount(
      <ShortcutProvider hotkeys={{}} keymap={{}}>
        <Provider store={store}>
          <Table {...tableProps} />
        </Provider>
      </ShortcutProvider>
    );
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
