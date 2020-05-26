import React from 'react';
import { shallow, render } from 'enzyme';
import renderer from 'react-test-renderer';
import tableProps from '../../../../test_setup/fixtures/table/table.json';
import Table from '../../../components/table/Table';
import { initialState as appHandlerState } from '../../../reducers/appHandler';
import { initialState as windowHandlerState } from '../../../reducers/windowHandler';
import { Provider } from 'react-redux';
import configureStore from 'redux-mock-store';
import merge from 'merge';
import DataLayoutWrapper from '../../../components/DataLayoutWrapper';

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

  // it('renders without errors with store data', () => {
  //   const initialState = createStore({
  //     windowHandler: {
  //       allowShortcut: true,
  //       modal: {
  //         visible: false,
  //       },
  //     },
  //   });
  //   const store = mockStore(initialState);
  //   tableProps.rowData = rowData;
  //   const wrapper = shallow(
  //     <Provider store={store}>
  //       <Table {...tableProps} />
  //     </Provider>
  //   );
  //   const html = wrapper.html();
  //   console.log(html);

  //   expect(html).toContain('something');

  // });


});
