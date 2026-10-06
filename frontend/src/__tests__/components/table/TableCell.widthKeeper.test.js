import React from 'react';
import { shallow } from 'enzyme';
import { omit } from 'lodash';

import tableCellProps from '../../../../test_setup/fixtures/table/table_cell.json';
import tableRowFixtures from '../../../../test_setup/fixtures/table/table_item_props.json';
import { getTableId } from '../../../reducers/tables';
import TableCell from '../../../components/table/TableCell';
import TableRow from '../../../components/table/TableRow';

/**
 * Opening a grid cell editor must not change the column width.
 *
 * Concrete failure pinned here: a column sized by its value (e.g. a long product name, 291px)
 * snapped to its band minimum (225px) the moment its cell was opened, because the static value
 * was removed and the editor (which takes no width of its own in a grid cell) left nothing to
 * hold the width. The cell therefore keeps an invisible copy of its static content next to the
 * editor - which needs the displayed value (tdValue) to still be computed while editing.
 */

const CAPTION = 'testfirma WebUI AG';

const cellProps = (overrides) => ({
  ...omit(tableCellProps, ['widgetData']),
  tdValue: CAPTION,
  isReadonly: false,
  ...overrides,
});

describe('TableCell — width keeper while editing', () => {
  it('renders an invisible copy of the static value next to the editor', () => {
    const wrapper = shallow(<TableCell {...cellProps({ isEdited: true })} />);

    const keeper = wrapper.find('.cell-width-keeper');
    expect(keeper).toHaveLength(1);
    expect(keeper.prop('aria-hidden')).toBe('true');
    expect(keeper.html()).toContain(CAPTION);
  });

  it('renders no width keeper when the cell is not being edited', () => {
    const wrapper = shallow(<TableCell {...cellProps({ isEdited: false })} />);

    expect(wrapper.find('.cell-width-keeper')).toHaveLength(0);
    expect(wrapper.html()).toContain(CAPTION);
  });
});

describe('TableRow — the edited cell still receives its displayed value', () => {
  it('passes tdValue to the cell that is being edited', () => {
    const propsSeed = tableRowFixtures.oldProps1;
    const wrapper = shallow(
      <TableRow
        {...propsSeed}
        tableId={getTableId(propsSeed)}
        onClick={jest.fn()}
        handleSelect={jest.fn()}
        onDoubleClick={jest.fn()}
        changeListenOnTrue={jest.fn()}
        changeListenOnFalse={jest.fn()}
        handleRowCollapse={jest.fn()}
        handleRightClick={jest.fn()}
        onItemChange={jest.fn()}
        getSizeClass={jest.fn()}
        updatePropertyValue={jest.fn()}
      />
    );
    wrapper.setState({ edited: 'M_Product_ID' });

    const editedCell = wrapper
      .find(TableCell)
      .filterWhere((cell) => cell.prop('property') === 'M_Product_ID');
    expect(editedCell.prop('isEdited')).toBe(true);
    expect(editedCell.prop('tdValue')).toBe('1000001_TestProduct1');
  });
});
