import React from 'react';
import { mount, shallow, render } from 'enzyme';

import RawWidget from '../../../components/widget/RawWidget';
import
  WidgetRenderer,
  { WidgetRenderer as UnwrappedWidgetRenderer }
from '../../../components/widget/WidgetRenderer';
import fixtures from '../../../../test_setup/fixtures/raw_widget.json';
import rawWidgetFixtures from '../../../../test_setup/fixtures/widget/raw_widget.json';
import { initNumeralLocales } from '../../../utils/locale';

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
    // A first field focuses when it mounts, but a new document in an already-mounted window
    // reconciles the widget instead of remounting it. These pin when that focus is repeated.
    const mountWithFocusMovedOn = (extra) => {
      const props = createDummyProps({
        ...fixtures.longText.layout1,
        widgetData: [{ ...fixtures.longText.data1 }],
        autoFocus: true,
        dataId: '1000001',
        ...extra,
      });

      const wrapper = mount(<RawWidget {...props} />);
      // The clerk has moved on to another field, so this widget no longer holds the focus.
      wrapper.setState({ isFocused: false });
      const focusSpy = jest.spyOn(wrapper.instance(), 'focus');

      return { wrapper, focusSpy };
    };

    it('focuses the first field again when the document changes', () => {
      const { wrapper, focusSpy } = mountWithFocusMovedOn();

      wrapper.setProps({ dataId: '1000002' });

      expect(focusSpy).toHaveBeenCalled();
    });

    it('does not focus the first field on a document change in a quick-input row', () => {
      // Same window and same document as the header, but the field that must get the focus on a
      // new document is the header's, not the quick-input row's.
      const { wrapper, focusSpy } = mountWithFocusMovedOn({ subentity: 'quickInput' });

      wrapper.setProps({ dataId: '1000002' });

      expect(focusSpy).not.toHaveBeenCalled();
    });

    it('does not take the caret from the field the user is typing in', () => {
      const props = createDummyProps({
        ...fixtures.longText.layout1,
        widgetData: [{ ...fixtures.longText.data1 }],
        autoFocus: true,
        dataId: '1000001',
      });
      const wrapper = mount(<RawWidget {...props} />);
      // Left as the mount-time focus leaves it: this widget holds the caret.
      wrapper.setState({ isFocused: true });
      const focusSpy = jest.spyOn(wrapper.instance(), 'focus');

      wrapper.setProps({ dataId: '1000002' });

      expect(focusSpy).not.toHaveBeenCalled();
    });

    it('leaves a lookup or a dropdown to repeat its own focus rule', () => {
      // Those two carry their own document-change rule with their own conditions; reaching them
      // through this widget's ref would bypass those conditions.
      const { wrapper, focusSpy } = mountWithFocusMovedOn({ widgetType: 'Lookup' });

      wrapper.setProps({ dataId: '1000002' });

      expect(focusSpy).not.toHaveBeenCalled();
    });

    it('does not focus the first field on a document change inside a modal', () => {
      // A process parameter panel and the barcode overlay pass a pinstance id as `dataId` with an
      // unconditional autoFocus; that is not a document change.
      const { wrapper, focusSpy } = mountWithFocusMovedOn({ isModal: true });

      wrapper.setProps({ dataId: '1000002' });

      expect(focusSpy).not.toHaveBeenCalled();
    });

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

  describe('decimal comma in a German user session:', () => {
    const amountLayout = {
      caption: 'Skonto',
      fields: [{ field: 'DiscountAmt', emptyText: 'none' }],
      emptyText: 'none',
      field: 'DiscountAmt',
      widgetType: 'Amount',
    };
    const amountData = {
      displayed: true,
      field: 'DiscountAmt',
      mandatory: false,
      readonly: false,
      validStatus: { valid: true, initialValue: true, fieldName: 'DiscountAmt' },
      value: '0',
      widgetType: 'Amount',
    };

    beforeEach(() => {
      initNumeralLocales('de', {
        numberDecimalSeparator: ',',
        numberGroupingSeparator: '.',
      });
    });

    afterEach(() => {
      jest.restoreAllMocks();
      initNumeralLocales('en', {
        numberDecimalSeparator: '.',
        numberGroupingSeparator: ',',
      });
    });

    const pressEnter = (input, value) =>
      input.simulate('keyDown', {
        key: 'Enter',
        target: { value },
        preventDefault: jest.fn(),
      });

    it('patches a pasted amount with blanks or no-break spaces as grouping', () => {
      const handlePatchSpy = jest.fn();
      const handleChangeSpy = jest.fn();
      const props = createDummyProps({
        ...amountLayout,
        widgetData: [{ ...amountData }],
        handlePatch: handlePatchSpy,
        handleChange: handleChangeSpy,
      });
      const wrapper = mount(<RawWidget {...props} />);

      wrapper.find('input').simulate('change', { target: { value: '1\u00A0234,56' } });
      expect(handleChangeSpy).toHaveBeenCalled();

      pressEnter(wrapper.find('input'), '1\u00A0234,56');
      expect(handlePatchSpy).toHaveBeenCalledWith('DiscountAmt', '1234.56', undefined, undefined);
    });


    it('shows a stored amount with the decimal comma of the session while editing', () => {
      const props = createDummyProps({
        ...amountLayout,
        widgetData: [{ ...amountData, value: '3.57' }],
      });
      const wrapper = mount(<RawWidget {...props} />);

      expect(wrapper.find('input').props().value).toEqual('3,57');
    });

    it('does not patch an untouched amount that is focused and left', () => {
      const handlePatchSpy = jest.fn();
      const props = createDummyProps({
        ...amountLayout,
        widgetData: [{ ...amountData, value: '1234.5' }],
        handlePatch: handlePatchSpy,
      });
      const wrapper = mount(<RawWidget {...props} />);
      const input = wrapper.find('input');

      input.simulate('focus');
      wrapper.find('input').simulate('blur', { target: { value: wrapper.find('input').props().value } });

      expect(handlePatchSpy).not.toHaveBeenCalled();
    });

    it('keeps the typed text while typing and reads the dot as the decimal separator (1.000 -> 1.000, no grouping)', () => {
      const handlePatchSpy = jest.fn();
      const props = createDummyProps({
        ...amountLayout,
        widgetData: [{ ...amountData }],
        handlePatch: handlePatchSpy,
      });
      const wrapper = mount(<RawWidget {...props} />);

      wrapper.find('input').simulate('change', { target: { value: '1.000' } });
      expect(wrapper.find('input').props().value).toEqual('1.000');

      pressEnter(wrapper.find('input'), '1.000');
      expect(handlePatchSpy).toHaveBeenCalledWith('DiscountAmt', '1.000', undefined, undefined);
    });

    it.each([
      ['3.57', '3.57'],
      ['1,234.56', '1234.56'],
    ])(
      'patches %s (a dotted decimal, now accepted) as %s: no refusal',
      (typed, patched) => {
        const addNotificationSpy = jest.fn();
        const handlePatchSpy = jest.fn();
        const props = createDummyProps({
          ...amountLayout,
          widgetData: [{ ...amountData, value: '2.5' }],
          handlePatch: handlePatchSpy,
          addNotification: addNotificationSpy,
        });
        const wrapper = mount(<RawWidget {...props} />);
        wrapper.find('input').simulate('focus');
        wrapper.find('input').simulate('change', { target: { value: typed } });

        pressEnter(wrapper.find('input'), typed);

        expect(handlePatchSpy).toHaveBeenCalledWith('DiscountAmt', patched, undefined, undefined);
        expect(addNotificationSpy).not.toHaveBeenCalled();
      }
    );

    it('does not patch an untouched amount that changed from outside while it had the focus', () => {
      const handlePatchSpy = jest.fn();
      const props = createDummyProps({
        ...amountLayout,
        widgetData: [{ ...amountData, value: '2.5' }],
        handlePatch: handlePatchSpy,
      });
      const wrapper = mount(<RawWidget {...props} />);
      wrapper.find('input').simulate('focus');

      wrapper.setProps({ widgetData: [{ ...amountData, value: '2.6' }] });
      wrapper.update();
      expect(wrapper.find('input').props().value).toEqual('2,6');

      wrapper
        .find('input')
        .simulate('blur', { target: { value: wrapper.find('input').props().value } });
      expect(handlePatchSpy).not.toHaveBeenCalled();
    });

    it.each(['Enter', 'Tab'])(
      'lets the table row see %s on a typed amount',
      (key) => {
        const typed = '3,57';
        const rowKeyDownSpy = jest.fn();
        const props = createDummyProps({
          ...amountLayout,
          widgetData: [{ ...amountData, value: '2.5' }],
          propagateEnterKeyEvent: true,
        });
        const wrapper = mount(
          <div onKeyDown={rowKeyDownSpy}>
            <RawWidget {...props} />
          </div>
        );
        wrapper.find('input').simulate('change', { target: { value: typed } });

        wrapper.find('input').simulate('focus');
        wrapper.find('input').simulate('keyDown', {
          key,
          target: { value: typed },
        });

        expect(rowKeyDownSpy).toHaveBeenCalledTimes(1);
      }
    );

    it('shows an empty amount as an empty text', () => {
      const props = createDummyProps({
        ...amountLayout,
        widgetData: [{ ...amountData, value: null }],
      });
      const wrapper = mount(<RawWidget {...props} />);

      expect(wrapper.find('input').props().value).toEqual('');
    });

    it('shows the stored amount once the patched value comes back (typed 1.000 is the decimal 1)', () => {
      const handlePatchSpy = jest.fn();
      const props = createDummyProps({
        ...amountLayout,
        widgetData: [{ ...amountData }],
        handlePatch: handlePatchSpy,
      });
      const wrapper = mount(<RawWidget {...props} />);
      wrapper.find('input').simulate('change', { target: { value: '1.000' } });
      pressEnter(wrapper.find('input'), '1.000');
      expect(handlePatchSpy).toHaveBeenCalledWith('DiscountAmt', '1.000', undefined, undefined);

      // the server stores the amount and returns its canonical value (1.000 is the number one)
      wrapper.setProps({ widgetData: [{ ...amountData, value: '1' }] });
      wrapper.update();

      expect(wrapper.find('input').props().value).toEqual('1');
    });

    const amountRangeFilterProps = (extra) =>
      createDummyProps({
        ...amountLayout,
        fields: [{ field: 'DiscountAmt', parameterName: 'DiscountAmt', emptyText: 'none' }],
        widgetData: [{ ...amountData, value: '2.5', valueTo: null }],
        range: true,
        filterWidget: true,
        ...extra,
      });

    it('hands both ends of a range filter to the parent the session way while one end is typed', () => {
      const handleChangeSpy = jest.fn();
      const wrapper = mount(<RawWidget {...amountRangeFilterProps({ handleChange: handleChangeSpy })} />);

      expect(wrapper.find('input').at(0).props().value).toEqual('2,5');
      wrapper.find('input').at(1).simulate('change', { target: { value: '3,5' } });

      expect(handleChangeSpy).toHaveBeenLastCalledWith('DiscountAmt', '2,5', undefined, '3,5');
    });

    it.each(['insertFromPaste', 'insertFromDrop', 'insertReplacementText'])(
      'shows the refusal for a whole text (inputType %s) with characters a number cannot hold',
      (inputType) => {
        const handleChangeSpy = jest.fn();
        const addNotificationSpy = jest.fn();
        const props = createDummyProps({
          ...amountLayout,
          widgetData: [{ ...amountData }],
          handleChange: handleChangeSpy,
          addNotification: addNotificationSpy,
        });
        const wrapper = mount(<RawWidget {...props} />);

        wrapper.find('input').simulate('change', {
          target: { value: '3,57 EUR' },
          nativeEvent: { inputType },
        });

        expect(handleChangeSpy).not.toHaveBeenCalled();
        expect(addNotificationSpy).toHaveBeenCalledTimes(1);
        expect(addNotificationSpy.mock.calls[0][1]).toContain('3,57 EUR');
      }
    );

    it('refuses a pasted digit string too long to be a number (it would overflow), leaving the field unchanged', () => {
      const handleChangeSpy = jest.fn();
      const addNotificationSpy = jest.fn();
      const props = createDummyProps({
        ...amountLayout,
        widgetData: [{ ...amountData, value: '2.5' }],
        handleChange: handleChangeSpy,
        addNotification: addNotificationSpy,
      });
      const wrapper = mount(<RawWidget {...props} />);

      wrapper.find('input').simulate('change', {
        target: { value: '1' + '0'.repeat(400) },
        nativeEvent: { inputType: 'insertFromPaste' },
      });

      expect(handleChangeSpy).not.toHaveBeenCalled();
      expect(addNotificationSpy).toHaveBeenCalledTimes(1);
      expect(wrapper.find('input').props().value).toEqual('2,5');
    });

    it('silently ignores a non-numeric single keystroke (not a pasted/dropped chunk), without an error', () => {
      const handleChangeSpy = jest.fn();
      const addNotificationSpy = jest.fn();
      const props = createDummyProps({
        ...amountLayout,
        widgetData: [{ ...amountData }],
        handleChange: handleChangeSpy,
        addNotification: addNotificationSpy,
      });
      const wrapper = mount(<RawWidget {...props} />);

      wrapper.find('input').simulate('change', {
        target: { value: '3,5a' },
        nativeEvent: { inputType: 'insertText' },
      });

      expect(handleChangeSpy).not.toHaveBeenCalled();
      expect(addNotificationSpy).not.toHaveBeenCalled();
    });

    it('does not patch an amount retyped to the stored value', () => {
      const handlePatchSpy = jest.fn();
      const props = createDummyProps({
        ...amountLayout,
        widgetData: [{ ...amountData, value: '2.5' }],
        handlePatch: handlePatchSpy,
      });
      const wrapper = mount(<RawWidget {...props} />);
      wrapper.find('input').simulate('change', { target: { value: '2,50' } });

      pressEnter(wrapper.find('input'), '2,50');

      expect(handlePatchSpy).not.toHaveBeenCalled();
    });

    it('patches an amount retyped after the server kept the stored value (e.g. it refused the first patch)', () => {
      const handlePatchSpy = jest.fn(() => Promise.resolve(null));
      const props = createDummyProps({
        ...amountLayout,
        widgetData: [{ ...amountData, value: '2.5' }],
        handlePatch: handlePatchSpy,
      });
      const wrapper = mount(<RawWidget {...props} />);

      wrapper.find('input').simulate('change', { target: { value: '3,5' } });
      pressEnter(wrapper.find('input'), '3,5');
      // the stored value stays 2.5; the user types the same number again
      wrapper.find('input').simulate('change', { target: { value: '3,50' } });
      pressEnter(wrapper.find('input'), '3,50');

      expect(handlePatchSpy).toHaveBeenCalledTimes(2);
      expect(handlePatchSpy).toHaveBeenLastCalledWith('DiscountAmt', '3.50', undefined, undefined);
    });

    it('keeps the typed amount when a view reload brings back the value it had before typing, and patches it', () => {
      const handlePatchSpy = jest.fn();
      const props = createDummyProps({
        ...amountLayout,
        widgetData: [{ ...amountData, value: '1' }],
        handlePatch: handlePatchSpy,
      });
      const wrapper = mount(<RawWidget {...props} />);
      wrapper.find('input').simulate('focus');
      wrapper.find('input').simulate('change', { target: { value: '3.57' } });
      // the parent keeps the typed text as the value
      wrapper.setProps({ widgetData: [{ ...amountData, value: '3.57' }] });
      wrapper.update();

      // the view is reloaded while the user types: the stored value before typing comes back
      wrapper.setProps({ widgetData: [{ ...amountData, value: '1' }] });
      wrapper.update();

      expect(wrapper.find('input').props().value).toEqual('3.57');
      pressEnter(wrapper.find('input'), wrapper.find('input').props().value);
      expect(handlePatchSpy).toHaveBeenCalledWith('DiscountAmt', '3.57', undefined, undefined);
    });

    it('keeps the amount typed into an empty field when a view reload brings back the empty value, and patches it', () => {
      const handlePatchSpy = jest.fn();
      const props = createDummyProps({
        ...amountLayout,
        widgetData: [{ ...amountData, value: null }],
        handlePatch: handlePatchSpy,
      });
      const wrapper = mount(<RawWidget {...props} />);
      wrapper.find('input').simulate('focus');
      wrapper.find('input').simulate('change', { target: { value: '3.57' } });
      wrapper.setProps({ widgetData: [{ ...amountData, value: '3.57' }] });
      wrapper.update();

      wrapper.setProps({ widgetData: [{ ...amountData, value: null }] });
      wrapper.update();

      expect(wrapper.find('input').props().value).toEqual('3.57');
      pressEnter(wrapper.find('input'), wrapper.find('input').props().value);
      expect(handlePatchSpy).toHaveBeenCalledWith('DiscountAmt', '3.57', undefined, undefined);
    });

    it('shows the old amount again when the server reverts it after the typed amount was patched', () => {
      const handlePatchSpy = jest.fn(() => Promise.resolve(null));
      const props = createDummyProps({
        ...amountLayout,
        widgetData: [{ ...amountData, value: '2.5' }],
        handlePatch: handlePatchSpy,
      });
      const wrapper = mount(<RawWidget {...props} />);
      wrapper.find('input').simulate('focus');
      wrapper.find('input').simulate('change', { target: { value: '3,5' } });
      wrapper.setProps({ widgetData: [{ ...amountData, value: '3,5' }] });
      wrapper.update();
      pressEnter(wrapper.find('input'), '3,5');
      expect(handlePatchSpy).toHaveBeenCalledWith('DiscountAmt', '3.5', undefined, undefined);

      // the server does not take over the patch and sends back the old amount
      wrapper.setProps({ widgetData: [{ ...amountData, value: '2.5' }] });
      wrapper.update();

      expect(wrapper.find('input').props().value).toEqual('2,5');
    });

    it('shows a different amount that comes from outside while the user types', () => {
      const props = createDummyProps({
        ...amountLayout,
        widgetData: [{ ...amountData, value: '1' }],
      });
      const wrapper = mount(<RawWidget {...props} />);
      wrapper.find('input').simulate('focus');
      wrapper.find('input').simulate('change', { target: { value: '3.57' } });
      wrapper.setProps({ widgetData: [{ ...amountData, value: '3.57' }] });
      wrapper.update();

      wrapper.setProps({ widgetData: [{ ...amountData, value: '2.5' }] });
      wrapper.update();

      expect(wrapper.find('input').props().value).toEqual('2,5');
    });

    it('applies an untouched number filter on Enter (an inline filter applies on patch)', () => {
      const handlePatchSpy = jest.fn();
      const props = createDummyProps({
        ...amountLayout,
        fields: [{ field: 'DiscountAmt', parameterName: 'DiscountAmt', emptyText: 'none' }],
        widgetData: [{ ...amountData, value: '2.5' }],
        filterWidget: true,
        handlePatch: handlePatchSpy,
      });
      const wrapper = mount(<RawWidget {...props} />);
      wrapper.find('input').simulate('focus');
      // the filter value changed meanwhile (e.g. set elsewhere): Enter applies what is shown, though not typed
      wrapper.setProps({ widgetData: [{ ...amountData, value: '2.6' }] });
      wrapper.update();

      pressEnter(wrapper.find('input'), wrapper.find('input').props().value);

      expect(handlePatchSpy).toHaveBeenCalledWith('DiscountAmt', '2.6', undefined, undefined);
    });

    it('patches both ends of an amount range filter (valueTo too)', () => {
      const handlePatchSpy = jest.fn();
      const props = createDummyProps({
        ...amountLayout,
        fields: [{ field: 'DiscountAmt', parameterName: 'DiscountAmt', emptyText: 'none' }],
        widgetData: [{ ...amountData, value: '1,5', valueTo: null }],
        range: true,
        filterWidget: true,
        handlePatch: handlePatchSpy,
      });
      const wrapper = mount(<RawWidget {...props} />);

      pressEnter(wrapper.find('input').at(1), '3,57');
      expect(handlePatchSpy).toHaveBeenCalledWith('DiscountAmt', '1.5', undefined, '3.57');
    });

    it('patches both ends of a price range filter (valueTo too)', () => {
      const handlePatchSpy = jest.fn();
      const props = createDummyProps({
        ...fixtures.costPrice.layout1,
        fields: [{ field: 'PriceList', parameterName: 'PriceList', emptyText: 'none' }],
        widgetData: [{ ...fixtures.costPrice.data1, value: '1,5', valueTo: null }],
        range: true,
        filterWidget: true,
        handlePatch: handlePatchSpy,
      });
      const wrapper = mount(<RawWidget {...props} />);
      wrapper.find('input').at(1).simulate('focus'); // a price input takes keys in edit mode only

      pressEnter(wrapper.find('input').at(1), '13,75');
      expect(handlePatchSpy).toHaveBeenCalledWith('PriceList', '1.5', undefined, '13.75');
    });

    it('patches 2,5 typed into a Number cell of a table row as 2.5', () => {
      const handlePatchSpy = jest.fn();
      const props = createDummyProps({
        ...fixtures.costPrice.layout1,
        widgetType: 'Number',
        widgetData: [{ ...fixtures.costPrice.data1, field: 'Discount', value: '0', widgetType: 'Number' }],
        fields: [{ field: 'Discount', emptyText: 'none' }],
        rowId: '1000001',
        handlePatch: handlePatchSpy,
      });
      const wrapper = mount(<RawWidget {...props} />);
      wrapper.find('input').simulate('focus');

      pressEnter(wrapper.find('input'), '2,5');
      expect(handlePatchSpy).toHaveBeenCalledWith('Discount', '2.5', undefined, undefined);
    });

    it('lets the user type a comma into an amount (no browser number input that drops it)', () => {
      const props = createDummyProps({
        ...amountLayout,
        widgetData: [{ ...amountData }],
      });

      const wrapper = mount(<RawWidget {...props} />);
      const input = wrapper.find('input');

      expect(input.props().type).toEqual('text');
      expect(input.props().inputMode).toEqual('decimal');
    });

    it('patches 3,57 typed into an amount as 3.57', () => {
      const handlePatchSpy = jest.fn();
      const props = createDummyProps({
        ...amountLayout,
        widgetData: [{ ...amountData }],
        handlePatch: handlePatchSpy,
      });

      const wrapper = mount(<RawWidget {...props} />);

      wrapper.find('input').simulate('keyDown', {
        key: 'Enter',
        target: { value: '3,57' },
        preventDefault: jest.fn(),
      });

      expect(handlePatchSpy).toHaveBeenCalledWith(
        'DiscountAmt',
        '3.57',
        undefined,
        undefined
      );
    });

    it('patches 13,75 typed into a price (CostPrice/Number widget) as 13.75', () => {
      const handlePatchSpy = jest.fn();
      const props = createDummyProps({
        ...fixtures.costPrice.layout1,
        widgetData: [{ ...fixtures.costPrice.data1 }],
        handlePatch: handlePatchSpy,
      });

      const wrapper = mount(<RawWidget {...props} />);
      wrapper.find('input').simulate('focus');
      const input = wrapper.find('input');

      expect(input.props().type).toEqual('text');

      input.simulate('keyDown', {
        key: 'Enter',
        target: { value: '13,75' },
        preventDefault: jest.fn(),
      });

      expect(handlePatchSpy).toHaveBeenCalledWith(
        'PriceList',
        '13.75',
        undefined,
        undefined
      );
    });

    it('ignores a letter typed into an amount', () => {
      const handleChangeSpy = jest.fn();
      const props = createDummyProps({
        ...amountLayout,
        widgetData: [{ ...amountData }],
        handleChange: handleChangeSpy,
      });

      const wrapper = mount(<RawWidget {...props} />);

      wrapper.find('input').simulate('change', { target: { value: '3,5a' } });
      expect(handleChangeSpy).not.toHaveBeenCalled();

      wrapper.find('input').simulate('change', { target: { value: '3,5' } });
      expect(handleChangeSpy).toHaveBeenCalledWith(
        'DiscountAmt',
        '3,5',
        undefined,
        undefined
      );
    });

    it('keeps the integer widget a browser number input', () => {
      const props = createDummyProps({
        ...fixtures.integer.layout1,
        widgetData: [{ ...fixtures.integer.data1 }],
      });

      const wrapper = mount(<RawWidget {...props} />);

      expect(wrapper.find('input').props().type).toEqual('number');
    });
  });

  describe('decimal input in an English user session:', () => {
    const amountLayout = {
      caption: 'Discount',
      fields: [{ field: 'DiscountAmt', emptyText: 'none' }],
      emptyText: 'none',
      field: 'DiscountAmt',
      widgetType: 'Amount',
    };
    const amountData = {
      displayed: true,
      field: 'DiscountAmt',
      mandatory: false,
      readonly: false,
      validStatus: { valid: true, initialValue: true, fieldName: 'DiscountAmt' },
      value: '0',
      widgetType: 'Amount',
    };

    beforeEach(() => {
      initNumeralLocales('en', {
        numberDecimalSeparator: '.',
        numberGroupingSeparator: ',',
      });
    });

    afterEach(() => {
      jest.restoreAllMocks();
    });

    it.each([
      ['an amount', '1'],
      ['an empty value', null],
    ])(
      'keeps the typed amount when a view reload brings back %s it had before typing, and patches it',
      (_, storedValue) => {
        const handlePatchSpy = jest.fn();
        const props = createDummyProps({
          ...amountLayout,
          widgetData: [{ ...amountData, value: storedValue }],
          handlePatch: handlePatchSpy,
        });
        const wrapper = mount(<RawWidget {...props} />);
        wrapper.find('input').simulate('focus');
        wrapper.find('input').simulate('change', { target: { value: '3.57' } });
        // the parent keeps the typed text as the value
        wrapper.setProps({ widgetData: [{ ...amountData, value: '3.57' }] });
        wrapper.update();

        // the view is reloaded while the user types: the stored value before typing comes back
        wrapper.setProps({ widgetData: [{ ...amountData, value: storedValue }] });
        wrapper.update();

        expect(wrapper.find('input').props().value).toEqual('3.57');
        wrapper.find('input').simulate('keyDown', {
          key: 'Enter',
          target: { value: wrapper.find('input').props().value },
          preventDefault: jest.fn(),
        });
        expect(handlePatchSpy).toHaveBeenCalledWith('DiscountAmt', '3.57', undefined, undefined);
      }
    );

    it('patches 1,5 (the comma is read as the decimal separator) as 1.5', () => {
      const addNotificationSpy = jest.fn();
      const handlePatchSpy = jest.fn();
      const props = createDummyProps({
        ...amountLayout,
        widgetData: [{ ...amountData }],
        handlePatch: handlePatchSpy,
        addNotification: addNotificationSpy,
      });
      const wrapper = mount(<RawWidget {...props} />);

      wrapper.find('input').simulate('keyDown', {
        key: 'Enter',
        target: { value: '1,5' },
        preventDefault: jest.fn(),
      });

      expect(handlePatchSpy).toHaveBeenCalledWith('DiscountAmt', '1.5', undefined, undefined);
      expect(addNotificationSpy).not.toHaveBeenCalled();
    });

    it.each([
      ['3.57', '3.57'],
      ['1,234.5', '1234.5'],
      ['1,000', '1.000'],
    ])('patches %s typed into an amount as %s', (typed, patched) => {
      const handlePatchSpy = jest.fn();
      const props = createDummyProps({
        ...amountLayout,
        widgetData: [{ ...amountData }],
        handlePatch: handlePatchSpy,
      });
      const wrapper = mount(<RawWidget {...props} />);

      wrapper.find('input').simulate('keyDown', {
        key: 'Enter',
        target: { value: typed },
        preventDefault: jest.fn(),
      });

      expect(handlePatchSpy).toHaveBeenCalledWith('DiscountAmt', patched, undefined, undefined);
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
