import React from 'react';
import { List } from 'immutable';
import { mount, shallow } from 'enzyme';

import RawList, { RawList as RawListBare } from '../../../../components/widget/List/RawList';
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
    list: List(data),
  };
};

describe('RawList component', () => {
  describe.skip('rendering tests:', () => {
    it('renders without errors', () => {
      const props = createDummyProps(
        {
          ...fixtures.data1.widgetProps,
          isFocused: true,
        },
        fixtures.data1.listData
      );

      const wrapper = shallow(<RawListBare {...props} />);

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
    it.skip('list focuses and toggles on click', () => {
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

    it.skip('list handles key events', () => {
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

      const wrapper = shallow(<RawListBare {...props} />);

      wrapper.find('.input-dropdown-container').simulate(
        'keyDown',
        { ...eventProps, key: 'Tab' }
      );

      expect(onSelectSpy).toHaveBeenCalledWith(null);

      wrapper.find('.input-dropdown-container').simulate(
        'keyDown',
        { ...eventProps, key: 'ArrowDown' }
      );
      expect(onOpenDropdownSpy).toHaveBeenCalled();
    });

    it.skip('list focuses and toggles on click', () => {
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

  // handleSelect = selected => {
  //   const { onSelect, onCloseDropdown } = this.props;
  //   const { dropdownList } = this.state;
  //   const changedValues = {
  //     ...setSelectedValue(dropdownList, selected),
  //   };

  //   this.setState(changedValues, () => {
  //     if (selected.key === null) {
  //       onSelect(null);
  //     } else {
  //       onSelect(selected);
  //     }
  //     onCloseDropdown();

  //     setTimeout(() => {
  //       console.log('select')
  //       this.focusDropdown();
  //     }, 0);
  //   });
  // };

    it('list hides dropdown after selecting an option', () => {
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

      const eventProps = {
        preventDefault: jest.fn(),
        stopPropagation: jest.fn(),
      };

      const spyDropdown = jest.spyOn(RawListBare.prototype, 'focusDropdown');
      RawListBare.prototype.dropdown = { offsetWidth: 100 };

      const wrapper = mount(<RawListBare {...props} />);
      wrapper.instance().handleSelect(fixtures.data1.listData[0]);

      expect(onSelectSpy).toHaveBeenCalledWith(fixtures.data1.listData[0]);
      expect(onCloseDropdownSpy).toHaveBeenCalled();
      expect(onFocusSpy).toHaveBeenCalled();
    });

    // does the list stay hidden after list was updated when selection has been made
    // it('list blurs and stays out of focus after tabbing out', () => {
    // it('list hides dropdown after selecting an option', () => {
    //   const onCloseDropdownSpy = jest.fn();
    //   const onSelectSpy = jest.fn();
    //   // const onFocusSpy = jest.fn();
    //   const props = createDummyProps(
    //     {
    //       ...fixtures.data1.widgetProps,
    //       isFocused: true,
    //       isToggled: true,
    //       onCloseDropdown: onCloseDropdownSpy,
    //       onSelect: onSelectSpy,
    //       // onFocus: onFocusSpy,
    //       // readonly: false,
    //     },
    //     fixtures.data1.listData
    //   );
    //   // const map = {};
    //   // window.addEventListener = jest.fn((event, cb) => {
    //   //   console.log('event: ', event)
    //   //   map[event] = cb;
    //   // });
    //   const eventProps = {
    //     preventDefault: jest.fn(),
    //     stopPropagation: jest.fn(),
    //   };

    //   // console.log('RawList: ', RawList, RawList.prototype)
    //   // const spyTab = jest.spyOn(RawList.prototype, 'handleTab');

    //   const spyDropdown = jest.spyOn(RawListBare.prototype, 'focusDropdown');
    //   RawListBare.prototype.dropdown = { offsetWidth: 100 };

    //   const wrapper = mount(
    //     <RawListBare {...props} />
    //     // { attachTo: document.body.firstChild }
    //   );
    //   // const f = wrapper.find(RawListBare);

    //   // wrapper.find('.input-dropdown-container'.).prop('on')()
    //   wrapper.instance().handleSelect(fixtures.data1.listData[0]);
    //   // console.log('F: ', wrapper.instance())

    //   // const spyDropdown2 = jest.spyOn(wrapper.instance(), 'focusDropdown');

    //   // expect(wrapper.find('.input-dropdown-container.focused').length).toBe(0);

    //   // map.keydown({ ...eventProps, key: 'Tab' });
    //   // map.keydown({ ...eventProps, key: 'Tab' });
    //   // wrapper.instance().forceUpdate();
    //   // wrapper.update();

    //   expect(onSelectSpy).toHaveBeenCalledWith(fixtures.data1.listData[0]);

    //   // console.log('document: ', document.activeElement)
    //   // // expect(wrapper.find('.input-dropdown-container.focused').length).toBe(1);
    //   // // expect(spyDropdown2).toHaveBeenCalled();

    //   expect(onFocusSpy).toHaveBeenCalled();
    //   // expect(spyDropdown).toHaveBeenCalled();
    //   expect(onCloseDropdownSpy).toHaveBeenCalled();
    //   // expect(onBlurSpy).not.toHaveBeenCalled();
    // });

    describe('with elements attached to dummy element', function() {
      let wrapper;

      beforeAll(function() {
        document.body.innerHTML = '<div></div>';
      });

      beforeEach(function() {
        document.body.appendChild(document.createElement('div'))
      });

      afterEach(function() {
        if (wrapper.detach) {
          wrapper.detach();
        }
      });

      it.skip('focused list blurs on tab', () => {
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
          <RawListBare {...props} />,
          { attachTo: document.body.firstChild }
        );

        expect(wrapper.find('.input-dropdown-container.focused').length).toBe(1);

        map.keydown({ ...eventProps, key: 'Tab' });
        wrapper.instance().forceUpdate();

        expect(onCloseDropdownSpy).not.toHaveBeenCalled();
        expect(onBlurSpy).toHaveBeenCalled();
      });
    });
  });
});
