import React from 'react';
import { shallow, mount } from 'enzyme';
// import { ShortcutProvider } from '../../../components/keyshortcuts/ShortcutProvider';
import { initialState as appHandlerState } from '../../../reducers/appHandler';
import { initialState as windowHandlerState } from '../../../reducers/windowHandler';
import { Provider } from 'react-redux';
import configureStore from 'redux-mock-store';
import merge from 'merge';
import tableHeaderProps from '../../../../test_setup/fixtures/table/table_header.json';
import TableHeader from '../../../components/table/TableHeader';
import { getSizeClass } from '../../../utils/tableHelpers'; // imported as it is passed as a prop..

const mockStore = configureStore([]);
tableHeaderProps.getSizeClass = getSizeClass;
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

tableHeaderProps.selected = [];

describe('TableHeader', () => {
  it('renders without errors with the given props', () => {
    const wrapperTableCMenu = mount(
      <Provider store={store}>
        <TableHeader {...tableHeaderProps} />
      </Provider>
    );
    const html = wrapperTableCMenu.html();

    expect(html).not.toContain(`<th class="indent"></th>`);
    expect(html).toContain(`<th class="td-sm">Request</th>`);
    expect(html).toContain(`<th class="td-lg">Request Type</th>`);
    expect(html).toContain(`<th class="td-md">Created</th>`);
    expect(html).toContain(`<th class="td-lg">Created By</th>`);
    expect(html).toContain(`<th class="td-lg">Summary</th>`);
    expect(html).toContain(`<th class="td-md">Organisation</th>`);
  });

  it('should have indent present', () => {
    tableHeaderProps.indentSupported = true;
    const wrapperTableCMenu = mount(
      <Provider store={store}>
        <TableHeader {...tableHeaderProps} />
      </Provider>
    );
    const html = wrapperTableCMenu.html();

    expect(html).toContain(`<th class="indent"></th>`);
  });
});
