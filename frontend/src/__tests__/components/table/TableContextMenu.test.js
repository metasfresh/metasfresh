/*
 * #%L
 * metasfresh
 * %%
 * Copyright (C) 2023 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import React from 'react';
import { mount } from 'enzyme';
// import { ShortcutProvider } from '../../../components/keyshortcuts/ShortcutProvider';
import { initialState as appHandlerState } from '../../../reducers/appHandler';
import {
  initialState as windowHandlerState
} from '../../../reducers/windowHandler';
import { Provider } from 'react-redux';
import configureStore from 'redux-mock-store';
import { merge } from 'merge-anything';
import tableCMenuProps
  from '../../../../test_setup/fixtures/table/table_context_menu.json';
import TableContextMenu from '../../../components/table/TableContextMenu';

const mockStore = configureStore([]);
const createStore = function(state = {}) {
  const res = merge(
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

tableCMenuProps.selected = [];
describe('TableContextMenu', () => {
  it('renders without errors with the given props', () => {
    const wrapperTableCMenu = mount(
      <Provider store={store}>
        <TableContextMenu {...tableCMenuProps} />
      </Provider>
    );
    const html = wrapperTableCMenu.html();

    expect(html).toContain(
      `context-menu context-menu-open panel-bordered panel-primary`
    );
    expect(html).toContain(`<hr class="context-menu-separator">`);
    expect(html).toContain(`<i class="meta-icon-settings"></i>`);
  });

  it('has the tooltip present when row is selected', () => {
    tableCMenuProps.selected = ['1000001'];
    tableCMenuProps.mainTable = false;
    const wrapperTableCMenu = mount(
      <Provider store={store}>
        <TableContextMenu {...tableCMenuProps} />
      </Provider>
    );
    const html = wrapperTableCMenu.html();

    expect(html).toContain(`<i class="meta-icon-edit"></i>`);
    expect(html).toContain(`<span class="tooltip-inline">Alt+E</span>`);
  });

  it('should have open selected keymap', () => {
    tableCMenuProps.selected = ['1000001'];
    tableCMenuProps.mainTable = true;
    const wrapperTableCMenu = mount(
      <Provider store={store}>
        <TableContextMenu {...tableCMenuProps} />
      </Provider>
    );
    const html = wrapperTableCMenu.html();

    expect(html).toContain(`<i class="meta-icon-file"></i>`);
    expect(html).toContain(`<span class="tooltip-inline">Alt+B</span>`);
  });

  it('should shouw delete shortcut when handleDelete function is passed', () => {
    tableCMenuProps.handleDelete = jest.fn();
    const wrapperTableCMenu = mount(
      <Provider store={store}>
        <TableContextMenu {...tableCMenuProps} />
      </Provider>
    );
    const html = wrapperTableCMenu.html();

    expect(html).toContain(`<i class="meta-icon-trash"></i>`);
    expect(html).toContain(`<span class="tooltip-inline">Alt+Y</span>`);
  });
});

// TODO: add more tests in here by mocking the SSE this is tricky
// TODO: this takes more of functional testing than unit testing 
