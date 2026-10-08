import React from 'react';
import { shallow } from 'enzyme';
import { omit } from 'lodash';

import tableCellProps from '../../../../test_setup/fixtures/table/table_cell.json';
import tableRowFixtures from '../../../../test_setup/fixtures/table/table_item_props.json';
import { getTableId } from '../../../reducers/tables';
import TableCell from '../../../components/table/TableCell';
import TableRow from '../../../components/table/TableRow';

/**
 * While a grid cell's editor is open, the cell also renders an invisible copy of its displayed
 * value, so a column sized by that value (e.g. a long product name) keeps its width.
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

  it('keeps the value the cell showed when its editor opened, while the edited value changes', () => {
    // e.g. a Lookup cleared with its "x", then given another value: the column must not follow
    const wrapper = shallow(<TableCell {...cellProps({ isEdited: true })} />);

    wrapper.setProps({ tdValue: '' });
    expect(wrapper.find('.cell-width-keeper').html()).toContain(CAPTION);

    wrapper.setProps({ tdValue: 'another product' });
    expect(wrapper.find('.cell-width-keeper').html()).toContain(CAPTION);
    expect(wrapper.find('.cell-width-keeper').html()).not.toContain(
      'another product'
    );
  });

  it('takes the current value again when the editor is opened anew', () => {
    const wrapper = shallow(<TableCell {...cellProps({ isEdited: true })} />);

    wrapper.setProps({ isEdited: false, tdValue: 'another product' });
    wrapper.setProps({ isEdited: true });

    expect(wrapper.find('.cell-width-keeper').html()).toContain(
      'another product'
    );
  });

  it('renders no width keeper when the cell is not being edited', () => {
    const wrapper = shallow(<TableCell {...cellProps({ isEdited: false })} />);

    expect(wrapper.find('.cell-width-keeper')).toHaveLength(0);
    expect(wrapper.html()).toContain(CAPTION);
  });
});

describe('TableCell — the editor height follows an extended (multi-line) row', () => {
  // A row with a multi-line column grows while selected (TableRow.handleCellExtend): every cell's
  // static value gets extendLongText * 20 px of height. The editor must get the same height
  // (table.scss reads --cell-content-height), otherwise opening it would shrink the cell content.
  it('hands the extended static height to the editor of an edited cell', () => {
    const wrapper = shallow(
      <TableCell
        {...cellProps({ isEdited: true, cellExtended: true, extendLongText: 3 })}
      />
    );

    expect(wrapper.find('td').prop('style')).toEqual({
      '--cell-content-height': '60px',
    });
  });

  it('sets no content height when the row is not extended', () => {
    const wrapper = shallow(<TableCell {...cellProps({ isEdited: true })} />);

    expect(wrapper.find('td').prop('style')).toBeUndefined();
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
