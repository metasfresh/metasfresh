import React from 'react';
import { shallow, mount } from 'enzyme';
// import { ShortcutProvider } from '../../../components/keyshortcuts/ShortcutProvider';
import { initialState as appHandlerState } from '../../../reducers/appHandler';
import { initialState as windowHandlerState } from '../../../reducers/windowHandler';
import { Provider } from 'react-redux';
import configureStore from 'redux-mock-store';
import merge from 'merge';
import entryTableProps from '../../../../test_setup/fixtures/table/entry_table.json';
import EntryTable from '../../../components/table/EntryTable';

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
entryTableProps.onBlurWidget = jest.fn();
entryTableProps.addRefToWidgets = jest.fn();
describe('EntryTable', () => {
  it('renders without errors with the given props', () => {
    const wrapperEntryTable = mount(
      <Provider store={store}>
        <EntryTable {...entryTableProps} />
      </Provider>
    );
    const html = wrapperEntryTable.html();

    expect(html).toContain(
      `<table class="table js-table layout-fix"><tbody><tr class="table-row"></tr></tbody></table>`
    );
  });
});
