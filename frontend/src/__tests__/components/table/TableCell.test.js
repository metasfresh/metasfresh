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
      `class="table-cell text-left cell-disabled td-md Lookup pulse-off"`
    );
    expect(html).toContain(
      `title="0141">0141</div>`
    );
  });

  it('renders notification indicator', () => {
    const wrapperTableCell = shallow(<TableCell hasComments={true} {...tableCellProps} />);
    const html = wrapperTableCell.html();

    expect(html).toContain('notification-number');
  });

  it('Changing the widget type should be present in the output', () => {
    tableCellProps.item.widgetType = 'LongText';
    const wrapperTableCell = shallow(<TableCell {...tableCellProps} />);
    const html = wrapperTableCell.html();

    expect(html).toContain(`LongText`);
  });

  it('On escape input remains the same', done => {
    tableCellProps.item.widgetType = 'Text';
    const wrapperTableCell = shallow(<TableCell {...tableCellProps} />);
    const input = wrapperTableCell.find('div[title="0141"]');
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
    expect(html).toContain(`>0141<`);
    done();
  });
});
