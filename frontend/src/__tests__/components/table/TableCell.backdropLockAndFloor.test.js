import React from 'react';
import { shallow } from 'enzyme';

import TableCell from '../../../components/table/TableCell';

/**
 * Two TableCell behaviours of the grid inline-edit fixes that are otherwise only reachable through
 * a live grid:
 *
 * 1) Address backdrop lock. An Address cell edits through the same <Attributes> overlay as
 *    ProductAttributes. When the overlay reports its backdrop state, the cell must NOT call the
 *    grid's onClickOutside - otherwise a click inside the overlay tears it down mid-edit.
 *
 * 2) Combobox 90px floor applied inline. A Lookup/List column whose size band is narrower than the
 *    90px combobox minimum (WidgetSize S = td-sm, 60px) gets an inline `min-width: 90px` on its
 *    <td> when no stored column width exists; a stored width still wins.
 */

const PROPERTY = 'SomeField';

function cellProps({ widgetType, size = 'M', ...overrides }) {
  return {
    item: {
      widgetType,
      size,
      gridAlign: 'left',
      fields: [{ field: PROPERTY }],
    },
    property: PROPERTY,
    tableId: 'anyid',
    isReadonly: false,
    isEdited: false,
    tabIndex: 0,
    docId: '1',
    tdValue: 'value',
    onClickOutside: jest.fn(),
    ...overrides,
  };
}

describe('TableCell — backdrop lock', () => {
  it.each(['Address', 'ProductAttributes', 'Attributes', 'List', 'Lookup'])(
    'does not tear down a %s editor when its overlay reports the backdrop state',
    (widgetType) => {
      const props = cellProps({ widgetType });
      const wrapper = shallow(<TableCell {...props} />);

      wrapper.instance().handleBackdropLock(false);

      expect(props.onClickOutside).not.toHaveBeenCalled();
    }
  );

  it('still closes a plain Text editor (regression control)', () => {
    const props = cellProps({ widgetType: 'Text' });
    const wrapper = shallow(<TableCell {...props} />);

    wrapper.instance().handleBackdropLock(false);

    expect(props.onClickOutside).toHaveBeenCalledTimes(1);
  });
});

describe('TableCell — combobox 90px floor on the <td>', () => {
  it.each(['Lookup', 'List'])('a %s column of size S gets min-width 90px', (widgetType) => {
    const wrapper = shallow(<TableCell {...cellProps({ widgetType, size: 'S' })} />);

    expect(wrapper.find('td').prop('style')).toEqual({ minWidth: '90px' });
  });

  it('a Text column of size S gets no inline floor', () => {
    const wrapper = shallow(<TableCell {...cellProps({ widgetType: 'Text', size: 'S' })} />);

    expect(wrapper.find('td').prop('style')).toBeUndefined();
  });

  it('a stored column width wins over the floor', () => {
    const wrapper = shallow(<TableCell {...cellProps({ widgetType: 'Lookup', size: 'S', columnWidth: 70 })} />);

    expect(wrapper.find('td').prop('style')).toEqual({
      width: '70px',
      minWidth: '70px',
      maxWidth: '70px',
    });
  });
});
