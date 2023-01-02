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
import { mount } from 'enzyme';
import TableCellLabel from '../../../components/table/TableCellLabel';

describe('TableCellLabel', () => {
  it('renders without errors and does not show the dots for only one record', () => {

    const tableCellLabelProps = {
      "widgetType": "Labels",
      "tableCellData": {
        "field": "Labels_548674",
        "value": {
          "values": [
            {
              "key": "K_Bildungsinstitut",
              "caption": "Bildungsinstitut"
            }
          ]
        }
      },
      "rowId": "2156430"
    };

    const wrapperTableCellLabel = mount(
      <TableCellLabel {...tableCellLabelProps} />
    );
    const html = wrapperTableCellLabel.html();

    expect(html).toContain(
      `table-cell-label-wrapper`
    );
    expect(html).toContain(`Bildungsin`);
    expect(html).toContain(`...`);
  });

  it('renders without errors and shows the dots for more than one record', () => {

    const tableCellLabelProps = {
      "widgetType": "Labels",
      "tableCellData": {
        "field": "Labels_548674",
        "value": {
          "values": [
            {
              "key": "K_TestOne",
              "caption": "TestOne"
            },
            {
              "key": "K_TestTwo",
              "caption": "TestTwo"
            }
          ]
        }
      },
      "rowId": "2156431"
    };

    const wrapperTableCellLabel = mount(
      <TableCellLabel {...tableCellLabelProps} />
    );
    const html = wrapperTableCellLabel.html();

    expect(html).toContain(
      `table-cell-label-wrapper`
    );
    expect(html).toContain(`TestOne`);
    expect(html).toContain(`...`);
    expect(html).toContain(`TestOne, TestTwo`); // contains the comma separated tooltip content
  });

});
