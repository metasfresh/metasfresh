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
    // Updated expectations to include data-testid attributes
    expect(html).toContain(`<th class="td-sm" data-testid="column-R_Request_ID">Request</th>`);
    expect(html).toContain(`<th class="td-lg" data-testid="column-R_RequestType_ID">Request Type</th>`);
    expect(html).toContain(`<th class="td-md" data-testid="column-Created">Created</th>`);
    expect(html).toContain(`<th class="td-lg" data-testid="column-CreatedBy">Created By</th>`);
    expect(html).toContain(`<th class="td-lg" data-testid="column-Summary">Summary</th>`);
    expect(html).toContain(`<th class="td-md" data-testid="column-AD_Org_ID">Organisation</th>`);
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
