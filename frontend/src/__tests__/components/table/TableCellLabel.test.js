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
