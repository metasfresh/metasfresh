import React from 'react';
import { mount } from 'enzyme';

import { RawLookup } from '../../../../components/widget/Lookup/RawLookup';

const createDummyProps = (props = {}) => {
  return {
    "entity": "window",
    "windowType": "143",
    // "tabId": "187",
    // "dataId": "1001281",
    // "subentity": "quickInput",
    // "subentityId": "34",
    "idValue": "dummyIdValue",
    "item": {
      "field": "someField",
      // "source": "lookup",
      // "emptyText": "none",
      // "supportZoomInto": true
    },
    "mainProperty": {
      "field": "someField",
      // "source": "lookup",
      // "emptyText": "none",
      // "supportZoomInto": true
    },
    // placeholder: 'none',
    // readonly: false,
    // mandatory: true,
    // lookupEmpty: true,
    // localClearing: false,
    // isOpen: false,
    // initialFocus: true,
    // autoFocus: true,
    // defaultValue: '',
    // disabled: false,
    // "filter": {},
    // "forceHeight": 287,
    // "forcedWidth": 583,
    // "rank": "secondary",

    //
    // Callbacks and other functions:
    dispatch: jest.fn(),
    onDropdownListToggle: jest.fn(),
    onChange: jest.fn(),
    enableAutofocus: jest.fn(),
    setNextProperty: jest.fn(() => false),
    //
    // Overrides:
    ...props,
  };
};

describe('RawLookup component', () => {
  afterEach(() => {
    jest.clearAllMocks();
  });

  describe('generic tests using QuickAction\'s type widget:', () => {
    it('renders widget without errors', () => {
      const props = createDummyProps({ "idValue": "bla_bla_bla" });

      //const spy = jest.spyOn(RawLookup.prototype, 'handleValueChanged');
      // const focusSpy = jest.spyOn(RawLookup.prototype, 'handleFocus');
      const wrapper = mount(<RawLookup {...props} />);
      const html = wrapper.html();

      expect(html).toContain('raw-lookup-wrapper');
      expect(html).toContain('bla_bla_bla');
      expect(html).toContain('input');

      //expect(spy).toHaveBeenCalled();
      // expect(focusSpy).toHaveBeenCalled();
    });

    it('doesn\'t focus input and sets default value accordingly', () => {
      const props = createDummyProps(
        {
          initialFocus: false,
          defaultValue: {
            "caption": "Convenience Salat 250g_P002737",
            "key": "2005577"
          },
        },
      );

      const wrapper = mount(<RawLookup {...props} />);
      expect(wrapper.html()).toContain('input');
      expect(wrapper.find('input').instance().value).toEqual("Convenience Salat 250g_P002737")
    });

    it('calls focus/blur handlers properly', () => {
      const dropdownListToggleSpy = jest.fn();
      const props = createDummyProps(
        {
          mandatory: false,
          onDropdownListToggle: dropdownListToggleSpy,
        },
      );

      //const handleFocusSpy = jest.spyOn(RawLookup.prototype, 'handleFocus');
      const wrapper = mount(<RawLookup {...props} />);
      //const instance = wrapper.instance();
      //const html = wrapper.html();
      //const handleBlurSpy = jest.spyOn(instance, 'handleBlur');

      wrapper.find('input').simulate('click');
      wrapper.update();
      //expect(handleFocusSpy).toHaveBeenCalled();
      expect(dropdownListToggleSpy).toHaveBeenCalled();
      expect(wrapper.state().isFocused).toEqual(true);
      expect(dropdownListToggleSpy).toHaveBeenCalled();

      //handleFocusSpy.mockClear();
      //handleBlurSpy.mockClear();
      dropdownListToggleSpy.mockReset();

      wrapper.find('input').simulate('click');
      wrapper.update();
      //expect(handleFocusSpy).toHaveBeenCalled();
      expect(dropdownListToggleSpy.mock.calls.length).toEqual(1);
      //expect(handleBlurSpy).toHaveBeenCalled();
      expect(wrapper.state().isFocused).toEqual(false);
    });

    it('calls handleValueChanged after defaultValue was updated', () => {
      const props = createDummyProps({ mandatory: true });
      const wrapper = mount(<RawLookup {...props} />);
      const instance = wrapper.instance();
      //const html = wrapper.html();

      const handleValueChangedSpy = jest.spyOn(instance, 'handleValueChanged');

      wrapper.setProps({
        defaultValue: {
          "key": "2005577",
          "caption": "bla"
        }
      });
      expect(handleValueChangedSpy).toHaveBeenCalled();
      expect(wrapper.state().list).toEqual([{
        "key": "2005577",
        "caption": "bla"
      }]);
    });

    it('none option added for non-mandatory field after defaultValue updated', () => {
      const props = createDummyProps({ mandatory: false });

      const wrapper = mount(<RawLookup {...props} />);
      const instance = wrapper.instance();

      const handleValueSpy = jest.spyOn(instance, 'handleValueChanged');

      wrapper.setProps({
        defaultValue: {
          "caption": "Convenience Salat 250g_P002737",
          "key": "2005577"
        }
      });
      expect(handleValueSpy).toHaveBeenCalled();
      expect(wrapper.state().list.length).toEqual(2);
    });
  });

  describe('handleInputEmptyStatus guard (non-primary sub-fields receive no function)', () => {
    const item = { key: '1000123', caption: 'KT x 18 KG' };

    // Lookup.js hands non-primary sub-fields `false` (pre-fix) / `undefined` (post-fix);
    // primary sub-fields get the real callback. All three notification sites must cope.
    const propValues = [
      ['false', false],
      ['undefined', undefined],
      ['a function', 'FN'], // replaced by a fresh jest.fn() per test
    ];

    const mountWith = (handleInputEmptyStatus) => {
      const onChange = jest.fn(); // returns undefined → doThen runs the callback synchronously
      const props = createDummyProps({
        subentity: 'quickInput',
        onChange,
        handleInputEmptyStatus,
      });
      const wrapper = mount(<RawLookup {...props} />);
      return { wrapper, onChange, instance: wrapper.instance() };
    };

    describe.each(propValues)('handleInputEmptyStatus = %s', (_label, value) => {
      let fn;
      const resolve = () => (value === 'FN' ? (fn = jest.fn()) : value);

      it('handleAutoSelectAndAdvance (quick-input Enter path) does not throw and PATCHes', () => {
        const { instance, onChange } = mountWith(resolve());
        expect(() => instance.handleAutoSelectAndAdvance(item)).not.toThrow();
        expect(onChange).toHaveBeenCalledTimes(1);
        expect(onChange).toHaveBeenCalledWith('someField', item);
        expect(instance.inputSearch.value).toBe('KT x 18 KG');
        if (fn) expect(fn).toHaveBeenCalledWith(false);
      });

      it('handleSelect_RegularItem (mouse / regular form) does not throw and PATCHes', () => {
        const { instance, onChange } = mountWith(resolve());
        expect(() => instance.handleSelect_RegularItem(item)).not.toThrow();
        expect(onChange).toHaveBeenCalledTimes(1);
        expect(onChange).toHaveBeenCalledWith('someField', item);
        if (fn) expect(fn).toHaveBeenCalledWith(false);
      });

      it('componentDidUpdate (value arrives from the server) does not throw', () => {
        const { wrapper } = mountWith(resolve());
        expect(() => wrapper.setProps({ defaultValue: item })).not.toThrow();
        expect(wrapper.instance().inputSearch.value).toBe('KT x 18 KG');
        if (fn) expect(fn).toHaveBeenCalledWith(false);
      });
    });
  });
});
