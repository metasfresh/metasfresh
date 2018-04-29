import React from 'react';
import { List } from 'immutable';
import { mount } from 'enzyme';

import RawList from '../../../../components/widget/List/RawList';
import fixtures from '../../../../../test_setup/fixtures/raw_list.json';

const createDummyProps = function(props, data) {
  return {
    onFocus: jest.fn(),
    onBlur: jest.fn(),
    onSelect: jest.fn(),
    onOpenDropdown: jest.fn(),
    onCloseDropdown: jest.fn(),
    ...props,
    list: List(data),
  };
};

describe('RawList component', () => {
  describe('rendering tests:', () => {
    it('renders without errors', () => {
      const props = createDummyProps(
        {
          ...fixtures.data1.widgetProps,
          isFocused: true,
        },
        fixtures.data1.listData
      );

      const wrapper = mount(<RawList {...props} />);

      expect(wrapper.html()).toContain('focused');
      expect(wrapper.find('input').length).toBe(1);
      expect(wrapper.find('input').html()).toContain(
        'Testpreisliste Lieferanten'
      );
    });
  });
});
