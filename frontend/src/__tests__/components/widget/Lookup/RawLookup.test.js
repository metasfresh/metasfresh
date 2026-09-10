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

  // ------------------------------------------------------------------
  // TC8: keyboard-only completion when the lookup resolves to NO match.
  // Decision 2026-09-09: on a NON-MANDATORY sub-field, Enter is Tab-identical for
  // the no-match case — it commits the empty entry and advances focus, instead of
  // dead-ending. The mandatory product sub-field keeps the old beep-and-hold.
  //
  // The commit contract is asserted (onChange with null + focus advance), not the
  // beep: playBeep is module-local and its Web-Audio call is swallowed by a
  // try/catch under jsdom, so "did it beep" is not observable here.
  // ------------------------------------------------------------------
  describe('TC8: Enter with no match on a non-mandatory sub-field commits empty and advances', () => {
    const noneItem = { key: null, caption: 'none' };
    const realItem = { key: '1000123', caption: 'KT x 18 KG' };

    const mountQuickInput = (extraProps = {}) => {
      const onChange = jest.fn(); // undefined → doThen runs its callback synchronously
      const props = createDummyProps({
        subentity: 'quickInput',
        mandatory: false,
        onChange,
        handleInputEmptyStatus: jest.fn(),
        ...extraProps,
      });
      const wrapper = mount(<RawLookup {...props} />);
      const instance = wrapper.instance();
      const advanceSpy = jest
        .spyOn(instance, 'focusNextFieldInForm')
        .mockImplementation(() => {}); // no parent <form> in the test DOM
      return { wrapper, instance, onChange, advanceSpy };
    };

    it('resolveItems with no match commits null and advances', () => {
      const { instance, onChange, advanceSpy } = mountQuickInput();
      instance.inputSearch.value = 'leer';

      instance.resolveItems([]);

      expect(onChange).toHaveBeenCalledTimes(1);
      expect(onChange).toHaveBeenCalledWith('someField', null);
      expect(instance.inputSearch.value).toBe('');
      expect(advanceSpy).toHaveBeenCalled();
    });

    it('bare Enter on an empty input with a closed dropdown commits null and advances', () => {
      const { instance, onChange, advanceSpy } = mountQuickInput();
      instance.inputSearch.value = '';
      instance.setState({ list: [], loading: false });

      instance.resolveAndSelectOnEnter();

      expect(onChange).toHaveBeenCalledTimes(1);
      expect(onChange).toHaveBeenCalledWith('someField', null);
      expect(advanceSpy).toHaveBeenCalled();
    });

    it('Enter with only the synthetic empty row in the open dropdown commits null and advances', () => {
      const { instance, onChange, advanceSpy } = mountQuickInput();
      instance.inputSearch.value = '';
      instance.setState({ list: [noneItem], loading: false });

      instance.resolveAndSelectOnEnter();

      expect(onChange).toHaveBeenCalledTimes(1);
      expect(onChange).toHaveBeenCalledWith('someField', null);
      expect(advanceSpy).toHaveBeenCalled();
    });

    it('Enter on typed text that matches nothing commits null and advances (Tab-identical)', () => {
      const { instance, onChange, advanceSpy } = mountQuickInput();
      instance.inputSearch.value = 'xyzzy';
      instance.setState({ list: [noneItem], loading: false });

      instance.resolveAndSelectOnEnter();

      expect(onChange).toHaveBeenCalledTimes(1);
      expect(onChange).toHaveBeenCalledWith('someField', null);
      expect(instance.inputSearch.value).toBe('');
      expect(advanceSpy).toHaveBeenCalled();
    });

    it('MANDATORY sub-field (product) with no match does NOT commit and does NOT advance', () => {
      const { instance, onChange, advanceSpy } = mountQuickInput({ mandatory: true });
      instance.inputSearch.value = 'nonsense';

      instance.resolveItems([]);

      expect(onChange).not.toHaveBeenCalled();
      expect(advanceSpy).not.toHaveBeenCalled();
      expect(instance.inputSearch.value).toBe('nonsense');
    });

    it('an exact single match still selects that item (TC2 regression)', () => {
      const { instance, onChange, advanceSpy } = mountQuickInput();

      instance.resolveItems([realItem]);

      expect(onChange).toHaveBeenCalledTimes(1);
      expect(onChange).toHaveBeenCalledWith('someField', realItem);
      expect(instance.inputSearch.value).toBe('KT x 18 KG');
      expect(advanceSpy).toHaveBeenCalled();
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
