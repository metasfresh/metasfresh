import React from 'react';
import { mount } from 'enzyme';
import { initialState as appHandlerState } from '../../../reducers/appHandler';
import { initialState as windowHandlerState } from '../../../reducers/windowHandler';
import { Provider } from 'react-redux';
import configureStore from 'redux-mock-store';
import merge from 'merge';
import tablePaginationProps from '../../../../test_setup/fixtures/table/table_pagination.json';
import hotkeys from '../../../../test_setup/fixtures/hotkeys.json';
import keymap from '../../../../test_setup/fixtures/keymap.json';
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

tablePaginationProps.page = 1;
describe('TablePagination', () => {
  it('renders without errors with the given props', () => {
    const wrapperTableCMenu = mount(
      <Provider store={store}>
        <ShortcutProvider hotkeys={hotkeys} keymap={keymap}>
          <TablePagination {...tablePaginationProps} />
        </ShortcutProvider>
      </Provider>
    );
    const html = wrapperTableCMenu.html();

    expect(html).toContain(`<div class="pagination-wrapper js-unselect">`);
    expect(html).toContain(`<div class="pagination-row">`);
    // First page should be selected
    expect(html).toContain(
      `<li class="page-item js-not-unselect active"><a class="page-link">1</a></li>`
    );
  });

  it('Page 2 should be selected', () => {
    tablePaginationProps.page = 2;
    const wrapperTableCMenu = mount(
      <Provider store={store}>
        <ShortcutProvider hotkeys={hotkeys} keymap={keymap}>
          <TablePagination {...tablePaginationProps} />
        </ShortcutProvider>
      </Provider>
    );
    const html = wrapperTableCMenu.html();

    // page one should not be selected
    expect(html).toContain(
      `<li class="page-item js-not-unselect"><a class="page-link">1</a></li>`
    );
    // 2nd page should be selected
    expect(html).toContain(
      `<li class="page-item js-not-unselect active"><a class="page-link">2</a></li>`
    );

    expect(html).toContain(`<a class="page-link"><span>»</span></a>`);
  });

  it('should contain page-link-compressed when compressed is true', () => {
    tablePaginationProps.compressed = true;
    const wrapperTableCMenu = mount(
      <Provider store={store}>
        <ShortcutProvider hotkeys={hotkeys} keymap={keymap}>
          <TablePagination {...tablePaginationProps} />
        </ShortcutProvider>
      </Provider>
    );
    const html = wrapperTableCMenu.html();

    expect(html).toContain(`page-link-compressed`);
  });

  it('should not contain page-link-compressed when compressed is false', () => {
    tablePaginationProps.compressed = false;
    const wrapperTableCMenu = mount(
      <Provider store={store}>
        <ShortcutProvider hotkeys={hotkeys} keymap={keymap}>
          <TablePagination {...tablePaginationProps} />
        </ShortcutProvider>
      </Provider>
    );
    const html = wrapperTableCMenu.html();

    expect(html).not.toContain(`page-link-compressed`);
  });
});
