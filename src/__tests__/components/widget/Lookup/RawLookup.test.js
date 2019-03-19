import React from 'react';
import { mount, shallow, render } from 'enzyme';

import { RawLookup } from '../../../../components/widget/Lookup/RawLookup';
import fixtures from '../../../../../test_setup/fixtures/raw_lookup.json';

const createDummyProps = function(props) {
  return {
    dispatch: jest.fn(),
    onDropdownListToggle: jest.fn(),
    onChange: jest.fn(),
    placeholder: 'none',
    readonly: false,
    mandatory: true,
    lookupEmpty: true,
    localClearing: false,
    isOpen: false,
    initialFocus: true,
    autoFocus: true,
    defaultValue: '',
    disabled: false,
    ...props,
  };
};

describe('RawLookup component', () => {
  afterEach(() => {
    jest.clearAllMocks();
  });

  describe('generic tests using QuickAction\'s type widget:', () => {
    it('renders widget without errors', () => {
      const props = createDummyProps(
        {
          ...fixtures.data1,
        },
      );

      const spy = jest.spyOn(RawLookup.prototype, 'handleValueChanged');
      // const focusSpy = jest.spyOn(RawLookup.prototype, 'handleFocus');
      const wrapper = mount(<RawLookup {...props} />);
      const html = wrapper.html();

      expect(html).toContain('raw-lookup-wrapper');
      expect(html).toContain(fixtures.data1.idValue);
      expect(html).toContain('input');

      expect(spy).toHaveBeenCalled();
      // expect(focusSpy).toHaveBeenCalled();
    });

    it('doesn\'t focus input and sets default value accordingly', () => {
      const props = createDummyProps(
        {
          ...fixtures.data1,
          initialFocus: false,
          defaultValue: { ...fixtures.defaultValue1 },
        },
      );

      const wrapper = mount(<RawLookup {...props} />);
      const html = wrapper.html();

      expect(html).toContain('input');
      expect(wrapper.find('input').instance().value).toEqual(fixtures.defaultValue1.caption)
    });

    it('calls focus/blur handlers properly', () => {
      const dropdownListToggleSpy = jest.fn();
      const props = createDummyProps(
        {
          ...fixtures.data1,
          mandatory: false,
          onDropdownListToggle: dropdownListToggleSpy,
        },
      );

      const handleFocusSpy = jest.spyOn(RawLookup.prototype, 'handleFocus');
      const wrapper = mount(<RawLookup {...props} />);
      const instance = wrapper.instance();
      const html = wrapper.html();

      const handleBlurSpy = jest.spyOn(instance, 'handleBlur');

      wrapper.find('input').simulate('click');
      wrapper.update();
      expect(handleFocusSpy).toHaveBeenCalled();
      expect(dropdownListToggleSpy).toHaveBeenCalled();
      expect(wrapper.state().isFocused).toEqual(true);
      expect(dropdownListToggleSpy).toHaveBeenCalled();

      handleFocusSpy.mockClear();
      handleBlurSpy.mockClear();
      dropdownListToggleSpy.mockReset();

      wrapper.find('input').simulate('click');
      wrapper.update();
      expect(handleFocusSpy).toHaveBeenCalled();
      expect(dropdownListToggleSpy.mock.calls.length).toEqual(1);    
      expect(handleBlurSpy).toHaveBeenCalled();
      expect(wrapper.state().isFocused).toEqual(false);
    });

    it('calls handleValueChanged after defaultValue was updated', () => {
      const props = createDummyProps(
        {
          ...fixtures.data1,
        },
      );

      const wrapper = mount(<RawLookup {...props} />);
      const instance = wrapper.instance();
      const html = wrapper.html();

      const handleValueSpy = jest.spyOn(instance, 'handleValueChanged');

      wrapper.setProps({ defaultValue: { ...fixtures.defaultValue1 }} );
      expect(handleValueSpy).toHaveBeenCalled();
      expect(wrapper.state().list.length).toEqual(1);
    });

    it('none option added for non-mandatory field after defaultValue updated', () => {
      const props = createDummyProps(
        {
          ...fixtures.data1,
          mandatory: false,
        },
      );

      const wrapper = mount(<RawLookup {...props} />);
      const instance = wrapper.instance();
      const html = wrapper.html();

      const handleValueSpy = jest.spyOn(instance, 'handleValueChanged');

      wrapper.setProps({ defaultValue: { ...fixtures.defaultValue1 }} );
      expect(handleValueSpy).toHaveBeenCalled();
      expect(wrapper.state().list.length).toEqual(2);
    });
  });
});