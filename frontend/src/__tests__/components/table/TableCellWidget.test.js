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
