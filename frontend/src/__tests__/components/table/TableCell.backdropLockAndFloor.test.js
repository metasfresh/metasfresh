import React from 'react';
import { shallow } from 'enzyme';

import TableCell from '../../../components/table/TableCell';

/**
 * 1) A popup-editing cell (Address, ProductAttributes) and a List/Lookup cell do not call the
 *    grid's onClickOutside when their editor reports `false` (e.g. the Address popup closed), so
 *    the cell stays in edit mode.
 * 2) A Lookup/List column of size S (60px) gets an inline `min-width: 90px` on its <td> when no
 *    column width is stored; a stored width wins.
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
  it.each(['Address', 'ProductAttributes', 'List', 'Lookup'])(
    'keeps a %s cell in edit mode when its editor reports false',
    (widgetType) => {
      const props = cellProps({ widgetType });
      const wrapper = shallow(<TableCell {...props} />);

      wrapper.instance().handleBackdropLock(false);

      expect(props.onClickOutside).not.toHaveBeenCalled();
    }
  );

  it('closes a plain Text editor', () => {
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
