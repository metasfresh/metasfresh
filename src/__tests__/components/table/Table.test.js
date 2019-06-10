import React from "react";
import * as Immutable from "immutable";
import { mount, shallow, render } from "enzyme";
import uuid from "uuid/v4";
import { Provider } from 'react-redux';
import configureStore from 'redux-mock-store';

import { ShortcutProvider } from '../../../components/keyshortcuts/ShortcutProvider';
import Table, { Table as TableBare } from "../../../components/table/Table";
import tableFixtures from "../../../../test_setup/fixtures/table_full.json";
import tabFixtures from "../../../../test_setup/fixtures/tab_full.json";

const mockStore = configureStore([]);
const TAB_PROPS = tabFixtures.layout.tabs[0];

const createDummyTableProps = function(tableProps) {
  return {
    autofocus: false,
    rowEdited: false,
    dispatch: jest.fn(),
    onSelectionChanged: jest.fn(),
    onRowEdited: jest.fn(),
    defaultSelected: [],
    supportOpenRecord: false,
    page: 2,
    pageLength: 20,
    size: 2,
    tabIndex: 3,
    rowData: Immutable.Map(tableProps.rowData),
    ...tableProps,
  };
};

describe("Table in Tab component", () => {
  describe("rendering tests:", () => {
    it("renders without errors", () => {
      const initialState = { windowHandler: {
        allowShortcut: true,
        modal: {
          visible: false,
        },
      }};
      const store = mockStore(initialState)
      const dummyTableProps = createDummyTableProps(tableFixtures);

      const wrapper = render(
        <ShortcutProvider hotkeys={{}} keymap={{}} >
          <Provider store={store}>
            <Table {...dummyTableProps} />
          </Provider>
        </ShortcutProvider>
      );
      const html = wrapper.html();
    });

    it.skip("cells widgets are rendered when necessary", () => {

    });
  });

  describe.skip("Rows navigation tests:", () => {
    it("Cell is selected and row focused", () => {
    });

    it("Lookup widget is focused on selecting row", () => {
    });

    it("Lookup widget is blurred on patch and re-focused on key", () => {
    });

    it("Lookup widget is blurred on keys", () => {
    });

    it("Lookup widget is re-focused on select after traversing back", () => {
    });

    it("Number widget is focused on selecting row", () => {
    });

    it("Number widget is blurred on patch and re-focused on key", () => {
    });

    it("Number widget is blurred on keys", () => {
    });

    it("Number widget is re-focused on select after traversing back", () => {
    });

    it("Text widget is focused on selecting row", () => {
    });

    it("Text widget is blurred on patch and re-focused on key", () => {
    });

    it("Text widget is blurred on keys", () => {
    });

    it("Text widget is re-focused on select after traversing back", () => {
    });

    it("Textarea widget is focused on selecting row", () => {
    });

    it("Textarea widget is blurred on patch and re-focused on key", () => {
    });

    it("Textarea widget is blurred on keys", () => {
    });

    it("Textarea widget is re-focused on select after traversing back", () => {
    });
  });
});
