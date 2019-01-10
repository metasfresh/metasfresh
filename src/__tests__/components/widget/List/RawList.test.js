import React from 'react';
import { List } from 'immutable';
import { mount, shallow } from 'enzyme';

import RawList, { RawList as RawListBare } from '../../../../components/widget/List/RawList';
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

  describe('functional tests', () => {
    it.skip('list focuses and toggles on click', () => {
      const onOpenDropdownSpy = jest.fn();

      const props = createDummyProps(
        {
          ...fixtures.data1.widgetProps,
          isFocused: false,
          onOpenDropdown: onOpenDropdownSpy,
        },
        fixtures.data1.listData
      );

      const wrapper = mount(
        <RawListBare {...props} />
      );
      expect(wrapper.find('.input-dropdown-container.focused').length).toBe(0);

      wrapper.find('.input-dropdown-container').simulate('click');

      expect(onOpenDropdownSpy).toBeCalled();

      wrapper.setProps({ isToggled: true, isFocused: true });
      wrapper.update();

      expect(wrapper.find('.input-dropdown-container.focused').length).toBe(1);

      wrapper.unmount();
    });

    describe('with elements attached to dummy element', function() {
      let wrapper;

      beforeEach(function(){
        document.body.appendChild(document.createElement('div'))
      });

      afterEach(function(){
        if (wrapper.detach) {
          wrapper.detach();
        }
      });

      it('list blurs and stays out of focus after tabbing out', () => {
        const onCloseDropdownSpy = jest.fn();
        const onBlurSpy = jest.fn();
        const props = createDummyProps(
          {
            ...fixtures.data1.widgetProps,
            isFocused: true,
            isToggled: false,
            onCloseDropdown: onCloseDropdownSpy,
            onBlur: onBlurSpy,
          },
          fixtures.data1.listData
        );
        const map = {};
        window.addEventListener = jest.fn((event, cb) => {
          map[event] = cb;
        });
        const eventProps = {
          preventDefault: jest.fn(),
          stopPropagation: jest.fn(),
        };

        wrapper = mount(
          <RawList {...props} />,
          { attachTo: document.body.firstChild }
        );

        expect(wrapper.find('.input-dropdown-container.focused').length).toBe(1);

        map.keydown({ ...eventProps, key: 'Tab' });
        wrapper.instance().forceUpdate();
        wrapper.update();

        expect(onCloseDropdownSpy).not.toHaveBeenCalled();
        expect(onBlurSpy).toHaveBeenCalled();
      });
    });
  });
});
