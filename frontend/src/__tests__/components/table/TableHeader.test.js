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
import { mount, shallow } from 'enzyme';

import tableHeaderProps
  from '../../../../test_setup/fixtures/table/table_header.json';

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
