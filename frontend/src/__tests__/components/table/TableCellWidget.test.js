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
import TableCellWidget from '../../../components/table/TableCellWidget';

describe('TableCellLabel', () => {
  it('renders without errors with the given props', () => {

    const tableCellWidgetProps = {
      "widgetType": "Labels",
      "tableCellData": {
        "field": "Labels_548674",
        "value": {
          "values": [
            {
              "key": "K_Firma",
              "caption": "Firma"
            },
            {
              "key": "K_Gymnasium",
              "caption": "Gymnasium"
            }
          ]
        }
      },
      "rowId": "2156439"
    };

    const wrapperTableCellWidget = mount(
      <TableCellWidget {...tableCellWidgetProps} />
    );
    const html = wrapperTableCellWidget.html();

    expect(html).toContain(
      `table-cell-label-wrapper`
    );
    expect(html).toContain(`Firma`);
    expect(html).toContain(`<div data-toggle=\"tooltip\" data-placement=\"bottom\" title=\"Firma, Gymnasium\" class=\"table-cell-label-show-more\">...</div>`);
  });

});
