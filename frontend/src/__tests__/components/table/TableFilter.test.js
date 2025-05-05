import React from 'react';
import { mount } from 'enzyme';
import { initialState as appHandlerState } from '../../../reducers/appHandler';
import {
  initialState as windowHandlerState
} from '../../../reducers/windowHandler';
import { Provider } from 'react-redux';
import configureStore from 'redux-mock-store';
import { merge } from 'merge-anything';
import tableFilterProps
  from '../../../../test_setup/fixtures/table/table_context_menu.json';
import TableFilter from '../../../components/table/TableFilter';

const mockStore = configureStore([]);
const createStore = function(state = {}) {
  return merge(
    {
      appHandler: {
        ...appHandlerState,
        me: { timeZone: 'America/Los_Angeles' },
      },
      windowHandler: { ...windowHandlerState },
    },
    state
  );
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

tableFilterProps.selected = [];
describe('TableFilter', () => {
  it('renders without errors with the given props', () => {
    const wrapperTableCMenu = mount(
      <Provider store={store}>
        <TableFilter {...tableFilterProps} />
      </Provider>
    );
    const html = wrapperTableCMenu.html();

    expect(html).toContain(`<div class="table-filter-line">`);
    expect(html).toContain(`<div class="row filter-panel-buttons">`);
    expect(html).toContain(`<i class="meta-icon-fullscreen"></i>`);
  });
});

// TODO: add more tests for this
