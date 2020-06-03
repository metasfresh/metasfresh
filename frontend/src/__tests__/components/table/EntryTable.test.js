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
const rData = new Map();
rData.set(0, entryTableProps.rowData[0])
entryTableProps.rowData = rData;
describe('EntryTable', () => {
  it('renders without errors with the given props', () => {
    const wrapperEntryTable = mount(
      <Provider store={store}>
        <EntryTable {...entryTableProps} />
      </Provider>
    );
    const html = wrapperEntryTable.html();

    expect(html).toContain(`<table class="table js-table layout-fix">`);
    expect(html).toContain(`Tab1-Section2-Line1-Field1`);
    expect(html).toContain(`form-field-100005 form-field-100005_Info`);
    expect(html).toContain(
      `<div class="input-editable input-dropdown-focused">`
    );
    expect(html).toContain(`Tab1-Section2-Line1-Field2`);
    expect(html).toContain(
      `form-group form-group-table form-field-100006 form-field-100006_Info`
    );
  });
});
