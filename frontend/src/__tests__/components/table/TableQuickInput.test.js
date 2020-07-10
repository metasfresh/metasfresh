import React from 'react';
import { mount } from 'enzyme';
// import { ShortcutProvider } from '../../../components/keyshortcuts/ShortcutProvider';
import { initialState as appHandlerState } from '../../../reducers/appHandler';
import { initialState as windowHandlerState } from '../../../reducers/windowHandler';
import { Provider } from 'react-redux';
import configureStore from 'redux-mock-store';
import merge from 'merge';
import tableQuickInputProps from '../../../../test_setup/fixtures/table/table_quick_input.json';
import TableQuickInput from '../../../components/table/TableQuickInput';
import { getSizeClass } from '../../../utils/tableHelpers'; // imported as it is passed as a prop..

const mockStore = configureStore([]);
tableQuickInputProps.getSizeClass = getSizeClass;
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

tableQuickInputProps.selected = [];

describe('TableQuickInput', () => {
  it('renders without errors with the given props', () => {
    const wrapperTableCMenu = mount(
      <Provider store={store}>
        <TableQuickInput {...tableQuickInputProps} />
      </Provider>
    );
    const html = wrapperTableCMenu.html();

    expect(html).toContain(
      `<form class="row quick-input-container"><div class="col-sm-12 col-md-3 col-lg-2 hint">(Press 'Enter' to add)</div><button type="submit" class="hidden-up"></button></form>`
    );
  });

});
