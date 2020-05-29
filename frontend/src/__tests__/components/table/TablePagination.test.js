import React from 'react';
import { mount } from 'enzyme';
import { initialState as appHandlerState } from '../../../reducers/appHandler';
import { initialState as windowHandlerState } from '../../../reducers/windowHandler';
import { Provider } from 'react-redux';
import configureStore from 'redux-mock-store';
import merge from 'merge';
import tablePaginationProps from '../../../../test_setup/fixtures/table/table_pagination.json';
import TablePagination from '../../../components/table/TablePagination';
import { ShortcutProvider } from '../../../components/keyshortcuts/ShortcutProvider';

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

tablePaginationProps.selected = [];
describe('TablePagination', () => {
  it('renders without errors with the given props', () => {
    const wrapperTableCMenu = mount(
      <Provider store={store}>
        <ShortcutProvider hotkeys={{}} keymap={{}}>
          <TablePagination {...tablePaginationProps} />
        </ShortcutProvider>
      </Provider>
    );
    const html = wrapperTableCMenu.html();

    expect(html).toContain(`<div class="pagination-wrapper js-unselect">`);
  });
});
