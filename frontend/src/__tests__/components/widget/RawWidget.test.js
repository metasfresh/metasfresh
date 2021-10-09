import React from 'react';
import { mount, shallow, render } from 'enzyme';

import RawWidget from '../../../components/widget/RawWidget';
import
  WidgetRenderer,
  { WidgetRenderer as UnwrappedWidgetRenderer }
from '../../../components/widget/WidgetRenderer';
import fixtures from '../../../../test_setup/fixtures/raw_widget.json';
import rawWidgetFixtures from '../../../../test_setup/fixtures/widget/raw_widget.json';

const createDummyProps = function(props) {
  return {
    allowShortcut: jest.fn(),
    disableShortcut: jest.fn(),
    openModal: jest.fn(),
    patch: jest.fn(),
    updatePropertyValue: jest.fn(),
    onBlurWidget: jest.fn(),
    handleFocus: jest.fn(),
    handleBlur: jest.fn(),
    handlePatch: jest.fn(),
    handleChange: jest.fn(),
    handleProcess: jest.fn(),
    handleZoomInto: jest.fn(),

    modalVisible: false,
    timeZone: 'Europe/Berlin',
    entity: "window",
    dataId: "1001282",
    ...props,
  };
};

describe('RawWidget component', () => {
  describe('generic tests using LongText widget:', () => {
    it('renders widget without errors', () => {
      const props = createDummyProps(
        {
          ...fixtures.longText.layout1,
          widgetData: [{ ...fixtures.longText.data1 }],
        },
      );
 
      const wrapper = shallow(<RawWidget {...props} />);

      wrapper.update();

      const html = wrapper.html();

      expect(html).toContain('form-group');
      expect(html).toContain('row');
      expect(html).toContain('input-block');

      expect(wrapper.find(WidgetRenderer).length).toBe(1);
      expect(html).toContain('<textarea')
      expect(html).toContain(fixtures.longText.data1.value)
      expect(html).toContain(fixtures.longText.layout1.description)
    });

    it('renders nothing when widget is not displayed', () => {
      const props = createDummyProps(
        {
          ...fixtures.longText.layout1,
          widgetData: [{ ...fixtures.longText.data1, displayed: false }],
        },
      );

      const wrapper = shallow(<RawWidget {...props} />);
      const html = wrapper.html();

      expect(html).toEqual(null);
    });

    it('doesn\'t call `handlePatch` on `{enter}/{tab}` keydown if nothing changed', () => {
      const handlePatchSpy = jest.fn();
      const props = createDummyProps(
        {
          ...fixtures.longText.layout1,
          widgetData: [{ ...fixtures.longText.data1 }],
          handlePatch: handlePatchSpy,
        },
      );

      const wrapper = mount(<RawWidget {...props} />);
      const instance = wrapper.instance();
      const spy = jest.spyOn(instance, 'handleKeyDown');
      const textarea = wrapper.find('textarea');

      instance.forceUpdate();

      textarea.simulate(
        'keyDown',
        {
          key: 'Enter',
          target: { value: fixtures.longText.data1.value },
          preventDefault: jest.fn(),
        },
      );

      expect(spy).toHaveBeenCalled();
      expect(handlePatchSpy).not.toHaveBeenCalled();

      textarea.simulate(
        'keyDown',
        {
          key: 'Tab',
          target: { value: fixtures.longText.data1.value },
          preventDefault: jest.fn(),
        },
      );

      expect(spy).toHaveBeenCalled();
      expect(handlePatchSpy).not.toHaveBeenCalled();
    });

    it('calls `handlePatch` on `{enter}/{tab}` keydown', () => {
      const handlePatchSpy = jest.fn();
      const props = createDummyProps(
        {
          ...fixtures.longText.layout1,
          widgetData: [{ ...fixtures.longText.data1 }],
          handlePatch: handlePatchSpy,
        },
      );

      const wrapper = mount(<RawWidget {...props} />);
      const instance = wrapper.instance();
      const spy = jest.spyOn(instance, 'handleKeyDown');
      const textarea = wrapper.find('textarea');

      instance.forceUpdate();

      wrapper.find('textarea').simulate(
        'keyDown',
        {
          key: 'Enter',
          target: { value: '' },
          preventDefault: jest.fn(),
        },
      );

      expect(spy).toHaveBeenCalled();
      expect(handlePatchSpy).toHaveBeenCalled();

      wrapper.find('textarea').simulate(
        'keyDown',
        {
          key: 'Tab',
          target: { value: fixtures.longText.data1.value },
          preventDefault: jest.fn(),
        },
      );

      expect(spy).toHaveBeenCalled();
      expect(handlePatchSpy).toHaveBeenCalled();
    });

    it('doesn\'t call `handlePatch` on `{shift}{enter}` keydown', () => {
      const handlePatchSpy = jest.fn();
      const props = createDummyProps(
        {
          ...fixtures.longText.layout1,
          widgetData: [{ ...fixtures.longText.data1 }],
          handlePatch: handlePatchSpy,
        },
      );

      const wrapper = mount(<RawWidget {...props} />);
      const instance = wrapper.instance();
      const spy = jest.spyOn(instance, 'handleKeyDown');
      const textarea = wrapper.find('textarea');

      instance.forceUpdate();

      wrapper.find('textarea').simulate(
        'keyDown',
        {
          key: 'Enter',
          shiftKey: true,
          target: { value: fixtures.longText.data1.value },
          preventDefault: jest.fn(),
        },
      );

      expect(spy).toHaveBeenCalled();
      expect(handlePatchSpy).not.toHaveBeenCalled();
    });

    it('correct handlers/prop functions are called on focus/blur', () => {
      jest.useFakeTimers();

      const patchSpy = jest.fn();
      const blurSpy = jest.fn();
      const focusSpy = jest.fn();
      const clickOutsideSpy = jest.fn();
      const listenOnKeysTrueSpy = jest.fn();
      const listenOnKeysFalseSpy = jest.fn();
      const props = createDummyProps(
        {
          ...fixtures.longText.layout1,
          widgetData: [{ ...fixtures.longText.data1 }],
          handlePatch: patchSpy,
          handleBlur: blurSpy,
          handleFocus: focusSpy,
          listenOnKeysFalse: listenOnKeysFalseSpy,
          listenOnKeysTrue: listenOnKeysTrueSpy,
          enableOnClickOutside: clickOutsideSpy,
        },
      );

      const wrapper = mount(<RawWidget {...props} />);
      const instance = wrapper.instance();
      const handleFocusSpy = jest.spyOn(instance, 'handleFocus');
      const handleBlurSpy = jest.spyOn(instance, 'handleBlur');

      wrapper.instance().forceUpdate();
      wrapper.update();

      wrapper.find('textarea')
        .prop('onFocus')({ target: { value: '' } });
      jest.runAllTimers();

      expect(handleFocusSpy).toHaveBeenCalled();
      expect(focusSpy).toHaveBeenCalled();
      expect(listenOnKeysFalseSpy).toHaveBeenCalled();

      wrapper.find('textarea')
        .prop('onBlur')(
          { target: { value: fixtures.longText.data1.value } }
        );

      expect(patchSpy).not.toHaveBeenCalled();
      expect(blurSpy).toHaveBeenCalled();
      expect(handleBlurSpy).toHaveBeenCalled();
      expect(clickOutsideSpy).toHaveBeenCalled();
      expect(listenOnKeysTrueSpy).toHaveBeenCalled();
    });
  });

  describe('generic tests using Text widget:', () => {
    it('renders widget without errors', () => {
      const props = createDummyProps(
        {
          ...fixtures.text.layout1,
          widgetData: [{ ...fixtures.text.data1 }],
        },
      );
 
      const wrapper = shallow(<RawWidget {...props} />);
      const html = wrapper.html();

      expect(html).toContain('form-group');
      expect(html).toContain('row');
      expect(html).toContain('input-block');
      expect(html).toContain('<input');
      expect(html).toContain(fixtures.text.data1.value)
      expect(html).toContain(fixtures.text.layout1.description)
    });

    it('renders nothing when widget is not displayed', () => {
      const props = createDummyProps(
        {
          ...fixtures.text.layout1,
          widgetData: [{ ...fixtures.text.data1, displayed: false }],
        },
      );

      const wrapper = shallow(<RawWidget {...props} />);
      const html = wrapper.html();

      expect(html).toEqual(null);
    });

    it('doesn\'t call `handlePatch` on `{enter}/{tab}` keydown if nothing changed', () => {
      const handlePatchSpy = jest.fn();
      const props = createDummyProps(
        {
          ...fixtures.text.layout1,
          widgetData: [{ ...fixtures.text.data1 }],
          handlePatch: handlePatchSpy,
        },
      );

      const wrapper = mount(<RawWidget {...props} />);
      const instance = wrapper.instance();
      const spy = jest.spyOn(instance, 'handleKeyDown');

      instance.forceUpdate();

      wrapper.find('input').simulate(
        'keyDown',
        {
          key: 'Enter',
          target: { value: fixtures.text.data1.value },
          preventDefault: jest.fn(),
        },
      );

      expect(spy).toHaveBeenCalled();
      expect(handlePatchSpy).not.toHaveBeenCalled();

      wrapper.find('input').simulate(
        'keyDown',
        {
          key: 'Tab',
          target: { value: fixtures.text.data1.value },
          preventDefault: jest.fn(),
        },
      );

      expect(spy).toHaveBeenCalled();
      expect(handlePatchSpy).not.toHaveBeenCalled();
    });

    it('calls `handlePatch` on `{enter}/{tab}` keydown', () => {
      const handlePatchSpy = jest.fn();
      const props = createDummyProps(
        {
          ...fixtures.text.layout1,
          widgetData: [{ ...fixtures.text.data1 }],
          handlePatch: handlePatchSpy,
        },
      );
      const wrapper = mount(<RawWidget {...props} />);
      const instance = wrapper.instance();
      const spy = jest.spyOn(instance, 'handleKeyDown');

      instance.forceUpdate();

      wrapper.find('input').simulate(
        'keyDown',
        {
          key: 'Enter',
          target: { value: '' },
          preventDefault: jest.fn(),
        },
      );

      expect(spy).toHaveBeenCalled();
      expect(handlePatchSpy).toHaveBeenCalled();

      wrapper.find('input').simulate(
        'keyDown',
        {
          key: 'Tab',
          target: { value: fixtures.text.data1.value },
          preventDefault: jest.fn(),
        },
      );

      expect(spy).toHaveBeenCalled();
      expect(handlePatchSpy).toHaveBeenCalled();
    });

    it('correct handlers/prop functions are called on focus/blur', () => {
      jest.useFakeTimers();

      const patchSpy = jest.fn();
      const blurSpy = jest.fn();
      const focusSpy = jest.fn();
      const clickOutsideSpy = jest.fn();
      const listenOnKeysTrueSpy = jest.fn();
      const listenOnKeysFalseSpy = jest.fn();
      const props = createDummyProps(
        {
          ...fixtures.text.layout1,
          widgetData: [{ ...fixtures.text.data1 }],
          handlePatch: patchSpy,
          handleBlur: blurSpy,
          handleFocus: focusSpy,
          listenOnKeysFalse: listenOnKeysFalseSpy,
          listenOnKeysTrue: listenOnKeysTrueSpy,
          enableOnClickOutside: clickOutsideSpy,
        },
      );

      const wrapper = mount(<RawWidget {...props} />);
      const instance = wrapper.instance();
      const handleFocusSpy = jest.spyOn(instance, 'handleFocus');
      const handleBlurSpy = jest.spyOn(instance, 'handleBlur');

      wrapper.instance().forceUpdate();
      wrapper.update();

      wrapper.find('input')
        .prop('onFocus')({ target: { value: '' } });
      jest.runAllTimers();

      expect(handleFocusSpy).toHaveBeenCalled();
      expect(focusSpy).toHaveBeenCalled();
      expect(listenOnKeysFalseSpy).toHaveBeenCalled();

      wrapper.find('input')
        .prop('onBlur')(
          { target: { value: fixtures.text.data1.value } }
        );

      expect(patchSpy).not.toHaveBeenCalled();
      expect(blurSpy).toHaveBeenCalled();
      expect(handleBlurSpy).toHaveBeenCalled();
      expect(clickOutsideSpy).toHaveBeenCalled();
      expect(listenOnKeysTrueSpy).toHaveBeenCalled();
    });

    it('behaves correctly when selecting, removing and typing in new value', () => {
      const handlePatchSpy = jest.fn();
      const handleChangeSpy = jest.fn();
      const handleFocusSpy = jest.fn();
      const localFixtures = rawWidgetFixtures.text;
      const props = createDummyProps(
        {
          ...localFixtures.data1,
          ...localFixtures.props1,
          widgetData: [{ ...fixtures.text.data1 }],
          handlePatch: handlePatchSpy,
          handleChange: handleChangeSpy,
          handleFocus: handleFocusSpy,
        },
      );
      const widgetData = props.widgetData[0];

      jest.useFakeTimers();

      const wrapper = mount(<RawWidget {...props} />);
      const instance = wrapper.instance();
      const spyKey = jest.spyOn(instance, 'handleKeyDown');
      const spyTyped = jest.spyOn(instance, 'updateTypedCharacters');

      instance.forceUpdate();

      expect(wrapper.state().isFocused).toBeFalsy();
      expect(wrapper.state().cachedValue).toEqual(widgetData.value);

      wrapper.find('input').simulate('focus');
      wrapper.find('input').simulate('dblclick');

      jest.runOnlyPendingTimers();

      expect(wrapper.state().isFocused).toBeTruthy();
      expect(handleFocusSpy).toHaveBeenCalled();

      wrapper.find('input').simulate(
        'keyDown',
        {
          key: 'Backspace',
          target: { value: widgetData.value },
          preventDefault: jest.fn(),
        },
      );

      const changeEvent = {
        key: 'Backspace',
        target: { value: '' },
        preventDefault: jest.fn(),
      }
      wrapper.find('input').simulate(
        'change',
        changeEvent,
      );

      expect(spyTyped).toHaveBeenCalled();
      expect(handleChangeSpy).toHaveBeenCalled();

      wrapper.find('input').simulate(
        'keyDown',
        {
          key: 'a',
          target: { value: 'a' },
          preventDefault: jest.fn(),
        },
      );

      expect(spyTyped).toHaveBeenCalled();
      expect(handlePatchSpy).not.toHaveBeenCalled();
      expect(wrapper.state().cachedValue).toEqual(widgetData.value);
      expect(wrapper.state().charsTyped[localFixtures.props1.fieldName]).toEqual(1);

      wrapper.find('input').simulate(
        'blur',
        {
          key: 'a',
          target: { value: 'a' },
          preventDefault: jest.fn(),
        },
      );     

      expect(handlePatchSpy).toHaveBeenCalled();
    });   
  });

  describe('generic tests using Integer widget:', () => {
    it('renders widget without errors', () => {
      const props = createDummyProps(
        {
          ...fixtures.integer.layout1,
          widgetData: [{ ...fixtures.integer.data1 }],
        },
      );
 
      const wrapper = shallow(<RawWidget {...props} />);
      const html = wrapper.html();

      expect(html).toContain('form-group');
      expect(html).toContain('row');
      expect(html).toContain('input-block');
      expect(html).toContain('<input');
      expect(html).toContain(fixtures.integer.data1.value)
      expect(html).toContain(fixtures.integer.layout1.description)
    });

    it('renders nothing when widget is not displayed', () => {
      const props = createDummyProps(
        {
          ...fixtures.integer.layout1,
          widgetData: [{ ...fixtures.integer.data1, displayed: false }],
        },
      );

      const wrapper = shallow(<RawWidget {...props} />);
      const html = wrapper.html();

      expect(html).toEqual(null);
    });

    it('doesn\'t call `handlePatch` on `{enter}/{tab}` keydown if nothing changed', () => {
      const handlePatchSpy = jest.fn();
      const preventDefaultSpy = jest.fn();
      const props = createDummyProps(
        {
          ...fixtures.integer.layout1,
          widgetData: [{ ...fixtures.integer.data1 }],
          handlePatch: handlePatchSpy,
        },
      );

      const wrapper = mount(<RawWidget {...props} />);
      const instance = wrapper.instance();
      const spy = jest.spyOn(instance, 'handleKeyDown');

      instance.forceUpdate();

      wrapper.find('input').simulate(
        'keyDown',
        {
          key: 'Enter',
          target: { value: fixtures.integer.data1.value },
          preventDefault: preventDefaultSpy,
        },
      );

      expect(spy).toHaveBeenCalled();
      expect(handlePatchSpy).not.toHaveBeenCalled();
      expect(preventDefaultSpy).toHaveBeenCalled();
      preventDefaultSpy.mockClear();

      wrapper.find('input').simulate(
        'keyDown',
        {
          key: 'Tab',
          target: { value: fixtures.integer.data1.value },
          preventDefault: preventDefaultSpy,
        },
      );

      expect(spy).toHaveBeenCalled();
      expect(handlePatchSpy).not.toHaveBeenCalled();
      expect(preventDefaultSpy).not.toHaveBeenCalled();
    });

    it('calls `handlePatch` on `{enter}/{tab}` keydown', () => {
      const handlePatchSpy = jest.fn();
      const preventDefaultSpy = jest.fn();
      const props = createDummyProps(
        {
          ...fixtures.integer.layout1,
          widgetData: [{ ...fixtures.integer.data1 }],
          handlePatch: handlePatchSpy,
        },
      );

      const wrapper = mount(<RawWidget {...props} />);
      const instance = wrapper.instance();
      const spy = jest.spyOn(instance, 'handleKeyDown');

      instance.forceUpdate();

      wrapper.find('input').simulate(
        'keyDown',
        {
          key: 'Enter',
          target: { value: '' },
          preventDefault: preventDefaultSpy,
        },
      );

      expect(spy).toHaveBeenCalled();
      expect(handlePatchSpy).toHaveBeenCalled();
      expect(preventDefaultSpy).toHaveBeenCalled();
      preventDefaultSpy.mockClear();

      wrapper.find('input').simulate(
        'keyDown',
        {
          key: 'Tab',
          target: { value: fixtures.integer.data1.value },
          preventDefault: preventDefaultSpy,
        },
      );

      expect(spy).toHaveBeenCalled();
      expect(handlePatchSpy).toHaveBeenCalled();
      expect(preventDefaultSpy).not.toHaveBeenCalled();
    });
  });

  it('Shows a normal border when input does not exceeds the char limit', () => {
    const props = createDummyProps(
      {
        ...fixtures.text.layout1,
        widgetData: [{ ...fixtures.text.data1 }],
        maxLength: 40,
      },
    );

    const wrapper = mount(<RawWidget {...props} />);
    const instance = wrapper.instance();

    instance.forceUpdate();

    wrapper.find('input').simulate(
      'keyDown',
      {
        key: 'Alt',
        target: { value: 'This is a test' },
      },
    );

    const html = wrapper.html();
    expect(html).not.toContain('border-danger');
  });

  it('Shows a red border when input exceeds the char limit', () => {
    const props = createDummyProps(
      {
        ...fixtures.text.layout1,
        widgetData: [{ ...fixtures.text.data1 }],
        maxLength: 40,
      },
    );

    const wrapper = mount(<RawWidget {...props} />);
    const instance = wrapper.instance();

    instance.forceUpdate();

    wrapper.find('input').simulate(
      'keyDown',
      {
        key: 'Alt',
        target: { value: 'This is a test for checking the limit on inputs' },
      },
    );

    const html = wrapper.html();
    expect(html).toContain('border-danger');
  });
});
