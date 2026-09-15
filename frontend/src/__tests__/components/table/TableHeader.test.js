import React from 'react';
import { shallow, mount } from 'enzyme';

import tableHeaderProps from '../../../../test_setup/fixtures/table/table_header.json';

import TableHeader from '../../../components/table/TableHeader';

const props = {
  ...tableHeaderProps,
  selected: [],
};

describe('TableHeader', () => {
  it('renders without errors with the given props', () => {
    const wrapperTableCMenu = shallow(
      <table>
        <thead>
          <TableHeader {...props} />
        </thead>
      </table>
    );
    const html = wrapperTableCMenu.html();

    expect(html).not.toContain(`<th class="indent"></th>`);
    // Check column headers with data-testid and resize handles
    expect(html).toContain(`data-testid="column-R_Request_ID">Request`);
    expect(html).toContain(`data-testid="column-R_RequestType_ID">Request Type`);
    expect(html).toContain(`data-testid="column-Created">Created`);
    expect(html).toContain(`data-testid="column-CreatedBy">Created By`);
    expect(html).toContain(`data-testid="column-Summary">Summary`);
    expect(html).toContain(`data-testid="column-AD_Org_ID">Organisation`);
    // Verify resize handles are present
    expect(html).toContain(`column-resize-handle`);
  });

  it('should have indent present', () => {
    const localProps = {
      ...props,
      indentSupported: true,
    };

    const wrapperTableCMenu = shallow(
      <table>
        <thead>
          <TableHeader {...localProps} />
        </thead>
      </table>
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
      <table>
        <thead>
          <TableHeader {...localProps} />
        </thead>
      </table>
    );
    const html = wrapper.html();

    expect(html).toContain('sort-menu');
    expect(wrapper.find(`.th-caption[title="${tableHeaderProps.cols[0].description}"]`).length).toBe(1);
    wrapper.find(`.th-caption[title="${tableHeaderProps.cols[0].description}"]`).simulate('click')
    expect(onSortTableSpy).toHaveBeenCalled();
  });
});
