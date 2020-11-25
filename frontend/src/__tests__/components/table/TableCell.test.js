import React from 'react';
import { shallow } from 'enzyme';
import { omit } from 'lodash';

import tableCellProps from '../../../../test_setup/fixtures/table/table_cell.json';
import TableCell from '../../../components/table/TableCell';
import { getSizeClass, getTdValue, getDescription } from '../../../utils/tableHelpers'; // imported as it is passed as a prop..
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
