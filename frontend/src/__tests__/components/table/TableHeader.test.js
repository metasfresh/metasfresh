import React from 'react';
import { shallow, mount, render } from 'enzyme';
import configureStore from 'redux-mock-store';
import { Provider } from 'react-redux';
import { ShortcutProvider } from '../../../components/keyshortcuts/ShortcutProvider';
import tableHeaderProps from '../../../../test_setup/fixtures/table/table_header.json';
import { initialState as appHandlerState } from '../../../reducers/appHandler';
import { initialState as windowHandlerState } from '../../../reducers/windowHandler';
import TableHeader from '../../../components/table/TableHeader';
import hotkeys from '../../../../test_setup/fixtures/hotkeys.json';
import keymap from '../../../../test_setup/fixtures/keymap.json';
import merge from 'merge';
import thunk from 'redux-thunk';
const mockStore = configureStore([thunk]);

const props = {
  ...tableHeaderProps,
  selected: [],
};

const initialState = function(state = {}) {
  const res = merge.recursive(
    true,
    {
      appHandler: { ...appHandlerState },
      windowHandler: { ...windowHandlerState },
    },
    state
  );

  return res;
};
const store = mockStore(initialState);

describe('TableHeader', () => {
  it('renders without errors with the given props', () => {
    const wrapperTableCMenu = render(
      <Provider store={store}>
        <ShortcutProvider hotkeys={hotkeys} keymap={keymap}>
          <table>
            <thead>
              <TableHeader {...props} />
            </thead>
          </table>
        </ShortcutProvider>
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
    const localProps = {
      ...props,
      indentSupported: true,
    };

    const wrapperTableCMenu = render(
      <Provider store={store}>
        <ShortcutProvider hotkeys={hotkeys} keymap={keymap}>
          <table>
            <thead>
              <TableHeader {...localProps} />
            </thead>
          </table>
        </ShortcutProvider>
      </Provider>
    );
    const html = wrapperTableCMenu.html();

    expect(html).toContain(`<th class="indent"></th>`);
  });

  it('should call `onTableSort` when cell is clicked', () => {
    const onSortTableSpy = jest.fn();
    const localProps = {
      ...props,
      onSortTable: onSortTableSpy,
      setActiveSort: jest.fn(),
      deselect: jest.fn(),
    };

    const wrapper = mount(
      <Provider store={store}>
        <ShortcutProvider hotkeys={hotkeys} keymap={keymap}>
          <table>
            <thead>
              <TableHeader {...localProps} />
            </thead>
          </table>
        </ShortcutProvider>
      </Provider>
    );
    const html = wrapper.html();

    expect(html).toContain('sort-menu');
    expect(
      wrapper.find(
        `.th-caption[title="${tableHeaderProps.cols[0].description}"]`
      ).length
    ).toBe(1);
    wrapper
      .find(`.th-caption[title="${tableHeaderProps.cols[0].description}"]`)
      .simulate('click');
    expect(onSortTableSpy).toHaveBeenCalled();
  });
});
