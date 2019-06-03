import React from "react";
import * as Immutable from "immutable";
import { mount, shallow, render } from "enzyme";
import uuid from "uuid/v4";

import Table, { Table as TableBare } from "../../../components/table/Table";
import { Tab } from "../../../components/tabs/Tab";
import tableFixtures from "../../../../test_setup/fixtures/table_full.json";
import tabFixtures from "../../../../test_setup/fixtures/tab_full.json";

const TAB_PROPS = tabFixtures.layout.tabs[0];
// const DATA = tabFixtures.data;

const createDummyTableProps = function(tableProps) {
  return {
    autofocus: false,
    rowEdited: false,
    dispatch: jest.fn(),
    onSelectionChanged: jest.fn(),
    onRowEdited: jest.fn(),
    defaultSelected: [],
    supportOpenRecord: false,
    ...tableProps,
    rowData: Immutable.Map(tableProps.rowData),
    page: 2,
    pageLength: 20
  };
};

const mockTabProps = {
  dispatch: jest.fn(),
  singleRowView: false,
  docId: null,
  orderBy: []
};

describe("Table in Tab component", () => {
  describe("rendering tests:", () => {
    it("renders without errors", () => {
      const dummyTableProps = createDummyTableProps(tableFixtures);

      const wrapper = shallow(
        <Tab {...TAB_PROPS} {...mockTabProps}>
          <TableBare {...dummyTableProps} />
        </Tab>
      );
      const html = wrapper.html();
    });

    it("cells widgets are rendered when necessary", () => {

    });
  });

  describe("Rows navigation tests:", () => {
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
