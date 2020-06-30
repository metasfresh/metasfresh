import React from 'react';
import { shallow, mount } from 'enzyme';
import tableProps from '../../../../test_setup/fixtures/table/table.json';
import Table, { Table as TableDisconnected } from '../../../components/table/Table';
import { ShortcutProvider } from '../../../components/keyshortcuts/ShortcutProvider';
import { initialState as appHandlerState } from '../../../reducers/appHandler';
import { initialState as windowHandlerState } from '../../../reducers/windowHandler';
import { Provider } from 'react-redux';
import configureStore from 'redux-mock-store';
import merge from 'merge';

const mockStore = configureStore([]);

const convertObjToMap = (objItem) => {
  const mapTarget = new Map();
  Object.keys(objItem).forEach((k) => {
    mapTarget.set(k, objItem[k]);
  });
  return mapTarget;
};

const rowData = convertObjToMap(tableProps.rowData);

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
tableProps.rowData = rowData;
const tableWrapper = mount(
  <ShortcutProvider hotkeys={{}} keymap={{}}>
    <Provider store={store}>
      <Table {...tableProps} />
    </Provider>
  </ShortcutProvider>
);

describe('Table', () => {
  it('renders without errors with bare props', () => {
    shallow(<Table {...tableProps} />);
  });

  it('props passed are correctly set within the wrapper', () => {
    const wrapper = shallow(<Table {...tableProps} />);
    const wrapperProps = wrapper.prop('wrapperProps');

    expect(wrapperProps.entity).toEqual('documentView');
    expect(wrapperProps.tabId).toEqual('1');
    expect(wrapperProps.windowId).toEqual('143');
    expect(wrapperProps.emptyText).toEqual('There are no detail rows');
    expect(wrapperProps.emptyHint).toEqual(
      'You can create them in this window.'
    );
    expect(wrapperProps.readonly).toEqual(true);
    expect(wrapperProps.supportOpenRecord).toEqual(true);
    expect(wrapperProps.keyProperty).toEqual('id');
    expect(wrapperProps.mainTable).toEqual(true);
    expect(wrapperProps.tabIndex).toEqual(0);
    expect(wrapperProps.indentSupported).toEqual(false);
    expect(wrapperProps.defaultSelected).toEqual(['1000194']);
    expect(wrapperProps.openIncludedViewOnSelect).toEqual(null);
    expect(wrapperProps.blurOnIncludedView).toEqual(null);
    expect(wrapperProps.toggleState).toEqual('grid');
    expect(wrapperProps.spinnerVisible).toEqual(false);
    expect(wrapperProps.orderBy).toEqual([
      { fieldName: 'DateOrdered', ascending: false },
      { fieldName: 'C_Order_ID', ascending: false },
    ]);
    expect(wrapperProps.rowEdited).toEqual(false);
    expect(wrapperProps.size).toEqual(143);
    expect(wrapperProps.pageLength).toEqual(20);
    expect(wrapperProps.page).toEqual(1);
    expect(wrapperProps.inBackground).toEqual(false);
    expect(wrapperProps.disablePaginationShortcuts).toEqual(false);
    expect(wrapperProps.hasIncluded).toEqual(null);
    expect(wrapperProps.viewId).toEqual('143-B');
    expect(wrapperProps.windowType).toEqual('143');
    expect(wrapperProps.children).toEqual(false);
    expect(wrapperProps.allowShortcut).toEqual(true);
    expect(wrapperProps.allowOutsideClick).toEqual(true);
    expect(wrapperProps.modalVisible).toEqual(false);
    expect(wrapperProps.isGerman).toEqual(false);
    expect(wrapperProps.activeSort).toEqual(false);
    expect(wrapperProps.eventTypes).toEqual(['mousedown', 'touchstart']);
    expect(wrapperProps.outsideClickIgnoreClass).toEqual(
      'ignore-react-onclickoutside'
    );
    expect(wrapperProps.preventDefault).toEqual(false);
    expect(wrapperProps.stopPropagation).toEqual(false);
  });

  it('renders without errors with store data', () => {
    const html = tableWrapper.html();

    expect(html).toContain(
      'table table-bordered-vertically table-striped js-table table-read-only'
    );
  });

  it('Cell is selected and row focused', async () => {
    const test = rowData.get('1');
    const tableDiscWrapper = mount(
      <ShortcutProvider hotkeys={{}} keymap={{}}>
        <Provider store={store}>
          <TableDisconnected {...tableProps} />
        </Provider>
      </ShortcutProvider>
    );
    tableDiscWrapper.setState({ rows: test }, () => {
      tableDiscWrapper.update();
      // console.log(tableDiscWrapper.state());
      // console.log(tableDiscWrapper.html());
    });
  });

  it('Lookup widget is focused on selecting row', () => {});

  it('Lookup widget is blurred on patch and re-focused on key', () => {});

  it('Lookup widget is blurred on keys', () => {});

  it('Lookup widget is re-focused on select after traversing back', () => {});

  it('Number widget is focused on selecting row', () => {});

  it('Number widget is blurred on patch and re-focused on key', () => {});

  it('Number widget is blurred on keys', () => {});

  it('Number widget is re-focused on select after traversing back', () => {});

  it('Text widget is focused on selecting row', () => {});

  it('Text widget is blurred on patch and re-focused on key', () => {});

  it('Text widget is blurred on keys', () => {});

  it('Text widget is re-focused on select after traversing back', () => {});

  it('Textarea widget is focused on selecting row', () => {});

  it('Textarea widget is blurred on patch and re-focused on key', () => {});

  it('Textarea widget is blurred on keys', () => {});

  it('Textarea widget is re-focused on select after traversing back', () => {});
});
