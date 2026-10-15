import React from 'react';
import { mount } from 'enzyme';
import { v4 as uuidv4 } from 'uuid';

import { RawList0 as RawListBare } from '../../../../components/widget/List/RawList';
import SelectionDropdown from '../../../../components/widget/SelectionDropdown';
import fixtures from '../../../../../test_setup/fixtures/raw_list.json';

const createDummyProps = function(props, data) {
  return {
    onFocus: jest.fn(),
    onBlur: jest.fn(),
    onSelect: jest.fn(),
    onOpenDropdown: jest.fn(),
    onCloseDropdown: jest.fn(),
    ...props,
    list: data,
    listHash: uuidv4(),
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

      const wrapper = mount(<RawListBare {...props} />);

      expect(wrapper.html()).toContain('focused');
      expect(wrapper.find('input').length).toBe(1);
      expect(wrapper.find('input').html()).toContain(
        'Testpreisliste Lieferanten'
      );
    });

    it('renders with selection dropdown toggled properly', () => {
      const props = createDummyProps(
        {
          ...fixtures.data1.widgetProps,
          isFocused: true,
          isToggled: true,
        },
        fixtures.data1.listData
      );

      RawListBare.prototype.dropdown = { offsetWidth: 100 };

      const wrapper = mount(<RawListBare {...props} />);

      expect(wrapper.html()).toContain('focused');
      expect(wrapper.find(SelectionDropdown).length).toBe(1);
    });
  });

  describe('functional tests', () => {
    it('list focuses and toggles on click', () => {
      const onOpenDropdownSpy = jest.fn();

      const props = createDummyProps(
        {
          ...fixtures.data1.widgetProps,
          isFocused: false,
          isToggled: false,
          onOpenDropdown: onOpenDropdownSpy,
        },
        fixtures.data1.listData
      );

      const wrapper = mount(<RawListBare {...props} />);
      expect(wrapper.find('.input-dropdown-container.focused').length).toBe(0);

      wrapper.find('.input-dropdown-container').simulate('click');

      expect(onOpenDropdownSpy).toBeCalled();

      wrapper.setProps({ isToggled: true, isFocused: true });
      wrapper.update();

      expect(wrapper.find('.input-dropdown-container.focused').length).toBe(1);

      wrapper.unmount();
    });

    it('list handles key events', () => {
      const onOpenDropdownSpy = jest.fn();
      const onSelectSpy = jest.fn();
      const props = createDummyProps(
        {
          ...fixtures.data1.widgetProps,
          isFocused: false,
          isToggled: false,
          onOpenDropdown: onOpenDropdownSpy,
          onSelect: onSelectSpy,
        },
        []
      );
      const eventProps = {
        preventDefault: jest.fn(),
        stopPropagation: jest.fn(),
      };

      const wrapper = mount(<RawListBare {...props} />);

      wrapper
        .find('.input-dropdown-container')
        .simulate('keyDown', { ...eventProps, key: 'Tab' });

      expect(onSelectSpy).toHaveBeenCalledTimes(0);

      wrapper
        .find('.input-dropdown-container')
        .simulate('keyDown', { ...eventProps, key: 'ArrowDown' });
      expect(onOpenDropdownSpy).toHaveBeenCalled();
    });

    it('list focuses and toggles on click', () => {
      const onOpenDropdownSpy = jest.fn();

      const props = createDummyProps(
        {
          ...fixtures.data1.widgetProps,
          isFocused: false,
          isToggled: false,
          onOpenDropdown: onOpenDropdownSpy,
        },
        fixtures.data1.listData
      );

      const wrapper = mount(<RawListBare {...props} />);
      expect(wrapper.find('.input-dropdown-container.focused').length).toBe(0);

      wrapper.find('.input-dropdown-container').simulate('click');

      expect(onOpenDropdownSpy).toBeCalled();

      wrapper.setProps({ isToggled: true, isFocused: true });
      wrapper.update();

      expect(wrapper.find('.input-dropdown-container.focused').length).toBe(1);

      wrapper.unmount();
    });

    it('list hides dropdown after selecting an option', () => {
      jest.useFakeTimers('legacy'); // used 'legacy' because the latest Jest 27 has breaking changes for fakeTimers. 
                                    // It seems Jest contributors didn't updated the documentation on time

      const onCloseDropdownSpy = jest.fn();
      const onSelectSpy = jest.fn();
      const props = createDummyProps(
        {
          ...fixtures.data1.widgetProps,
          isFocused: true,
          isToggled: true,
          onCloseDropdown: onCloseDropdownSpy,
          onSelect: onSelectSpy,
        },
        fixtures.data1.listData
      );

      const requestFocusSpy = jest.spyOn(RawListBare.prototype, 'requestFocus');
      RawListBare.prototype.dropdown = { offsetWidth: 100 };

      const wrapper = mount(<RawListBare {...props} />);
      wrapper.instance().handleSelect(fixtures.data1.listData[0]);

      jest.advanceTimersByTime(1);

      expect(onSelectSpy).toHaveBeenCalledWith(fixtures.data1.listData[0]);
      expect(onCloseDropdownSpy).toHaveBeenCalled();
      expect(setTimeout).toHaveBeenCalledTimes(1);
      expect(requestFocusSpy).toHaveBeenCalled();
    });

    it('list blurs and stays hidden after selecting an option', () => {
      jest.useFakeTimers();

      const onCloseDropdownSpy = jest.fn();
      const onSelectSpy = jest.fn();
      const props = createDummyProps(
        {
          ...fixtures.data1.widgetProps,
          isFocused: true,
          isToggled: false,
          mandatory: false,
          emptyText: 'Some fake text',
          onCloseDropdown: onCloseDropdownSpy,
          onSelect: onSelectSpy,
        },
        fixtures.data1.listData
      );

      const requestFocusSpy = jest.spyOn(
        RawListBare.prototype, 'requestFocus'
      );
      RawListBare.prototype.dropdown = { offsetWidth: 100 };

      const wrapper = mount(<RawListBare {...props} />);
      wrapper.instance().handleSelect(fixtures.data1.listData[0]);

      jest.advanceTimersByTime(1);
      wrapper.update();

      expect(requestFocusSpy).toHaveBeenCalled();

      requestFocusSpy.mockClear();

      wrapper.setProps({ isFocused: false });
      wrapper.update();

      expect(requestFocusSpy).not.toHaveBeenCalled();
    });

    describe('with elements attached to dummy element', function () {
      let wrapper;

      beforeAll(function () {
        document.body.innerHTML = '<div></div>';
      });

      beforeEach(function () {
        document.body.appendChild(document.createElement('div'));
      });

      afterEach(function () {
        if (wrapper.detach) {
          wrapper.detach();
        }
      });

      it('focused list blurs on tab', () => {
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

        wrapper = mount(<RawListBare {...props} />, {
          attachTo: document.body.firstChild,
        });

        expect(wrapper.find('.input-dropdown-container.focused').length).toBe(
          1
        );

        map.keydown({ ...eventProps, key: 'Tab' });
        wrapper.instance().forceUpdate();

        expect(onCloseDropdownSpy).not.toHaveBeenCalled();
        expect(onBlurSpy).toHaveBeenCalled();
      });
    });

    // A composed lookup (e.g. Business Partner -> Location -> Contact) can hand the focus to a
    // sub-list that is still disabled because the partner is empty. Its `isFocused` then stays
    // true (nothing clears it without a click-outside / Tab / select), so it must never pull the
    // caret onto itself on a later re-render while it is disabled.
    describe('a disabled list with isFocused', () => {
      let container;
      let otherInput;

      beforeEach(() => {
        container = document.createElement('div');
        document.body.appendChild(container);
        otherInput = document.createElement('input');
        document.body.appendChild(otherInput);
      });

      afterEach(() => {
        document.body.removeChild(container);
        document.body.removeChild(otherInput);
      });

      const mountAttached = (props) =>
        mount(
          <RawListBare
            {...createDummyProps(
              {
                ...fixtures.data1.widgetProps,
                isToggled: false,
                tabIndex: 0,
                ...props,
              },
              fixtures.data1.listData
            )}
          />,
          { attachTo: container }
        );

      it('does not take the focus when it is handed the focus while disabled', () => {
        const wrapper = mountAttached({ isFocused: false, readonly: true });
        otherInput.focus();

        wrapper.setProps({ isFocused: true });

        expect(document.activeElement).toBe(otherInput);
        wrapper.detach();
      });

      it('does not take the focus back from another field on a later re-render', () => {
        const wrapper = mountAttached({ isFocused: true, readonly: true });
        otherInput.focus();

        wrapper.setProps({ listHash: uuidv4() });

        expect(document.activeElement).toBe(otherInput);
        wrapper.detach();
      });

      it('does not take the focus when mounted focused while disabled', () => {
        otherInput.focus();

        const wrapper = mountAttached({ isFocused: true, readonly: true });

        expect(document.activeElement).toBe(otherInput);
        wrapper.detach();
      });

      // Intended: e.g. the contact sub-list gets the caret once a business partner is chosen and enables it.
      it('takes the focus again once it is enabled', () => {
        const wrapper = mountAttached({ isFocused: true, readonly: true });
        otherInput.focus();

        wrapper.setProps({ readonly: false });

        expect(document.activeElement).toBe(
          wrapper.find('.input-dropdown-container').getDOMNode()
        );
        wrapper.detach();
      });
    });
  });
});
