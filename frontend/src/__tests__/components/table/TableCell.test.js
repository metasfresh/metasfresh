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
import { shallow } from 'enzyme';
import { omit } from 'lodash';

import tableCellProps
  from '../../../../test_setup/fixtures/table/table_cell.json';
import TableCell from '../../../components/table/TableCell';
import {
  getDescription,
  getSizeClass,
  getTdValue
} from '../../../utils/tableHelpers'; // imported as it is passed as a prop..
// TODO: ^^ this should not be passed to the component as it makes the component not easy to test
// TODO:    components should receive bare props

// Mock getWidgetData
const getCellProps = (overrideProps) => {
  const { widgetData } = tableCellProps;
  const tdValue = getPropsTdValue(tableCellProps);
  return {
    ...omit(tableCellProps, ['tdValue', 'widgetData']),
    tdValue,
    description: getDescription({ widgetData, tdValue }),
    getSizeClass,
    ...overrideProps,
  }
}

const getPropsTdValue = ({ widgetData, item, isEdited, isGerman }) => {
  return getTdValue({ widgetData, item, isEdited, isGerman });
};

describe('TableCell', () => {
  const props = getCellProps();

  it('renders without errors with the given props', () => {
    const wrapperTableCell = shallow(<TableCell {...props} />);
    const html = wrapperTableCell.html();
    const fieldValue = tableCellProps.widgetData[0].value.caption;

    expect(html).toContain(`data-cy="cell-AD_Org_ID"`);
    expect(html).toContain(fieldValue);
    expect(html).toContain(
      `class="table-cell text-left cell-disabled td-md Lookup pulse-off"`
    );
    expect(html).toContain(
      `title="${fieldValue}"`
    );
  });

  it('renders notification indicator', () => {
    const wrapperTableCell = shallow(<TableCell hasComments={true} {...props} />);
    const html = wrapperTableCell.html();

    expect(html).toContain('notification-number');
  });

  it('Changing the widget type should be present in the output', () => {
    tableCellProps.item.widgetType = 'LongText';
    const wrapperTableCell = shallow(<TableCell {...props} />);
    const html = wrapperTableCell.html();

    expect(html).toContain(`LongText`);
  });

  it('On escape input remains the same', done => {
    tableCellProps.item.widgetType = 'Text';
    const wrapperTableCell = shallow(<TableCell {...props} />);
    const fieldValue = tableCellProps.widgetData[0].value.caption;
    const input = wrapperTableCell.find(`div[title="${fieldValue}"]`);
    input.simulate('focus');
    input.simulate('change', { target: { value: '1234' } });
    input.simulate('keyDown', {
      which: 27,
      target: {
        blur() {
          input.simulate('blur');
        },
      },
    });
    const html = wrapperTableCell.html();
    expect(html).toContain(`>${fieldValue}<`);
    done();
  });
});
