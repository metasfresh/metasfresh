import React from 'react';
import { shallow, mount } from 'enzyme';
import merge from 'merge';

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
});
