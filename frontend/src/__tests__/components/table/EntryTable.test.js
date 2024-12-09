import React from 'react';
<<<<<<< HEAD
import { shallow, mount } from 'enzyme';
=======
import { shallow } from 'enzyme';
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import { Provider } from 'react-redux';
import configureStore from 'redux-mock-store';
import { merge } from 'merge-anything';

import { initialState as appHandlerState } from '../../../reducers/appHandler';
import { initialState as windowHandlerState } from '../../../reducers/windowHandler';
import tablesHandler, { getTableId } from '../../../reducers/tables';

import entryTableProps from '../../../../test_setup/fixtures/table/entry_table_props.json';
import entryTableData from '../../../../test_setup/fixtures/table/entry_table_data.json';

import EntryTable from '../../../components/table/EntryTable';

const mockStore = configureStore([]);
const createStore = function(state = {}) {
<<<<<<< HEAD
  const res = merge(
=======
  return merge(
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
    {
      appHandler: {
        ...appHandlerState,
        me: { timeZone: 'America/Los_Angeles' },
      },
      windowHandler: { ...windowHandlerState },
      tables: { ...tablesHandler(undefined, {}) }
    },
    state
  );
<<<<<<< HEAD

  return res;
=======
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
};

const props = {
  ...entryTableProps,
  modalVisible: false,
  timeZone: 'Europe/Berlin',
  onBlurWidget: jest.fn(),
  addRefToWidgets: jest.fn(),
  addShortcut: jest.fn(),
  disableShortcut: jest.fn(),
};

describe('EntryTable', () => {
  it('renders without errors with the given props', () => {
    const { windowId, documentId, tabId } = props;
    const tableId = getTableId({ windowId, docId: documentId, tabId });
    const initialStateTables = {
      [`${tableId}`]: {
        windowId,
        docId: documentId,
        tabId,
        rows: entryTableData.result,
      },
    };
    const initialState = createStore({
      tables: {
        length: 1,
        ...initialStateTables,
      }
    });
    const store = mockStore(initialState);

    const wrapperEntryTable = shallow(
      <Provider store={store}>
        <EntryTable {...props} />
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
