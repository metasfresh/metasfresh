import React from 'react';
import { shallow } from 'enzyme';
import tableCellProps from '../../../../test_setup/fixtures/table/table_cell.json';
import TableCell from '../../../components/table/TableCell';
import { getSizeClass } from '../../../utils/tableHelpers'; // imported as it is passed as a prop..
// TODO: ^^ this should not be passed to the component as it makes the component not easy to test
// TODO:    components should receive bare props

// Mock getWidgetData
tableCellProps.getWidgetData = () => {
  return [{ field: 'DocumentNo', value: '0141', readonly: true }];
};

tableCellProps.getSizeClass = getSizeClass;

describe('TableCell', () => {
  it('renders without errors with the given props', () => {
    const wrapperTableCell = shallow(<TableCell {...tableCellProps} />);
    const html = wrapperTableCell.html();

    expect(html).toContain(`data-cy="cell-AD_Org_ID"`);
    expect(html).toContain('0141');
    expect(html).toContain(
      `class="text-left cell-disabled td-md Lookup pulse-off"`
    );
    expect(html).toContain(
      `<div class="cell-text-wrapper lookup-cell" title="0141">0141</div></div>`
    );
  });

  it('Changing the widget type should be present in the output', () => {
    tableCellProps.item.widgetType = 'LongText';
    const wrapperTableCell = shallow(<TableCell {...tableCellProps} />);
    const html = wrapperTableCell.html();

    expect(html).toContain(`LongText`);
  });
});
