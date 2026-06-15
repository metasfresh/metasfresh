import React from 'react';
import { shallow } from 'enzyme';

import CostPrice from '../../../components/widget/CostPrice';

// Minimal default props for the component — overridable per test.
const defaultProps = (overrides = {}) => ({
  value: '0',
  precision: 2,
  autoComplete: 'off',
  className: 'input-field js-input-field',
  disabled: false,
  placeholder: '',
  tabIndex: 0,
  title: '0',
  onChange: jest.fn(),
  onFocus: jest.fn(),
  onBlur: jest.fn(),
  onKeyDown: jest.fn(),
  ...overrides,
});

// Build a minimal SyntheticEvent-like object for handleBeforeInput tests.
// Only the props the handler actually reads need to be present.
const beforeInputEvent = (data) => ({
  data,
  preventDefault: jest.fn(),
});

// Build a minimal SyntheticEvent-like object for handleChange tests.
const changeEvent = (value) => ({
  target: { value },
});

describe('CostPrice component', () => {
  describe('render', () => {
    it('renders a single stable <input type="text"> with inputMode="decimal"', () => {
      const wrapper = shallow(<CostPrice {...defaultProps({ value: '19.00' })} />);
      const input = wrapper.find('input');
      expect(input.length).toBe(1);
      expect(input.props().type).toBe('text');
      expect(input.props().inputMode).toBe('decimal');
    });

    it('preserves trailing zeros from the value (the key UX difference vs type="number")', () => {
      // The server sends monetary values already formatted with their precision,
      // e.g. "19.00" not "19". With type="text" + fieldValueToString, those
      // trailing zeros render in the input box. With type="number" they would
      // be normalised away by the browser — that's the regression we explicitly
      // moved away from in me03#27080 follow-up #1.
      const wrapper = shallow(
        <CostPrice {...defaultProps({ value: '19.00', precision: 2 })} />
      );
      expect(wrapper.find('input').props().value).toBe('19.00');
    });
  });

  describe('handleBeforeInput — keystroke filter', () => {
    // Helper: invoke the handler on a freshly-shallowed instance and
    // report whether preventDefault was called (= keystroke rejected).
    const isRejected = (data) => {
      const wrapper = shallow(<CostPrice {...defaultProps()} />);
      const event = beforeInputEvent(data);
      wrapper.instance().handleBeforeInput(event);
      return event.preventDefault.mock.calls.length > 0;
    };

    describe('allows characters that may appear in a valid (or in-progress) number', () => {
      // Digits
      it.each(['0', '1', '5', '9'])('accepts digit "%s"', (d) => {
        expect(isRejected(d)).toBe(false);
      });

      // Decimal separators
      it('accepts decimal point "."', () => expect(isRejected('.')).toBe(false));
      it('accepts comma "," (DE/AT decimal separator)', () =>
        expect(isRejected(',')).toBe(false));

      // Sign
      it('accepts minus "-"', () => expect(isRejected('-')).toBe(false));

      // Swiss thousands separators
      it('accepts straight apostrophe "\'"', () => expect(isRejected("'")).toBe(false));
      it('accepts typographic apostrophe "’"', () =>
        expect(isRejected('’')).toBe(false));

      // Whitespace separators (Excel / Word paste)
      it('accepts regular space', () => expect(isRejected(' ')).toBe(false));
      it('accepts non-breaking space U+00A0', () =>
        expect(isRejected(' ')).toBe(false));
    });

    describe('temporary-but-valid-while-typing cases', () => {
      it("accepts leading minus before any digit (typing '-' first for a negative number)", () => {
        expect(isRejected('-')).toBe(false);
      });
      it("accepts a lone decimal point (typing '.5' starting with '.')", () => {
        expect(isRejected('.')).toBe(false);
      });
      it("accepts a trailing decimal point as part of in-progress paste like '1.'", () => {
        expect(isRejected('1.')).toBe(false);
      });
      it("accepts a paste of '1.' (digit + decimal point)", () => {
        expect(isRejected('1.')).toBe(false);
      });
      it("accepts a paste of '-12.3' (digit + sign + decimal)", () => {
        expect(isRejected('-12.3')).toBe(false);
      });
    });

    describe('rejects characters that are not valid in any monetary input', () => {
      it.each([
        'a',
        'Z',
        'e',
        '$',
        'EUR',
        'CHF',
        '@',
        ';',
        '/',
        '!',
        '?',
        '<',
        '>',
      ])('rejects "%s"', (data) => {
        expect(isRejected(data)).toBe(true);
      });

      it("rejects a mixed paste like 'CHF 1500.00' (letters present)", () => {
        expect(isRejected('CHF 1500.00')).toBe(true);
      });

      it("rejects a paste like '1500.00 EUR'", () => {
        expect(isRejected('1500.00 EUR')).toBe(true);
      });
    });

    describe('non-inserting events (backspace, delete, arrows, copy, etc.)', () => {
      it('passes through when e.data is null (e.g. backspace)', () => {
        const wrapper = shallow(<CostPrice {...defaultProps()} />);
        const event = beforeInputEvent(null);
        wrapper.instance().handleBeforeInput(event);
        expect(event.preventDefault).not.toHaveBeenCalled();
      });

      it('passes through when e.data is undefined', () => {
        const wrapper = shallow(<CostPrice {...defaultProps()} />);
        const event = { data: undefined, preventDefault: jest.fn() };
        wrapper.instance().handleBeforeInput(event);
        expect(event.preventDefault).not.toHaveBeenCalled();
      });
    });
  });

  describe('handleChange — Swiss-format strip', () => {
    // Helper: shallow-mount, invoke handleChange, return the value the
    // parent's onChange callback was invoked with.
    const valueFlowingUpstream = (typedOrPasted) => {
      const onChange = jest.fn();
      const wrapper = shallow(<CostPrice {...defaultProps({ onChange })} />);
      wrapper.instance().handleChange(changeEvent(typedOrPasted));
      // Parent received an event whose target.value is the cleaned string.
      expect(onChange).toHaveBeenCalledTimes(1);
      return onChange.mock.calls[0][0].target.value;
    };

    it('passes through values that already match server format', () => {
      expect(valueFlowingUpstream('1500.00')).toBe('1500.00');
    });

    it('strips a straight apostrophe (Swiss thousands)', () => {
      expect(valueFlowingUpstream("1'500.00")).toBe('1500.00');
    });

    it('strips a typographic apostrophe ’ (e.g. pasted from MS Word)', () => {
      expect(valueFlowingUpstream('1’500.00')).toBe('1500.00');
    });

    it('strips multiple apostrophes in one paste', () => {
      expect(valueFlowingUpstream("1'500'000.50")).toBe('1500000.50');
    });

    it('strips a regular space (e.g. Excel-style)', () => {
      expect(valueFlowingUpstream('120 000.50')).toBe('120000.50');
    });

    it('strips a non-breaking space U+00A0 (Word-style)', () => {
      expect(valueFlowingUpstream('120 000.50')).toBe('120000.50');
    });

    it('handles a mixed paste with all three separators', () => {
      // The user's clipboard happens to contain a curly mix from a finance
      // report — strip them all.
      expect(valueFlowingUpstream("1'200 000 000.50")).toBe('1200000000.50');
    });

    it("preserves a leading minus sign while stripping separators", () => {
      expect(valueFlowingUpstream("-1'500.00")).toBe('-1500.00');
    });

    it("preserves a decimal comma while stripping separators (DE/AT users)", () => {
      expect(valueFlowingUpstream("1'500,50")).toBe('1500,50');
    });

    it('calls the parent onChange with an event whose target.value is the cleaned string', () => {
      const onChange = jest.fn();
      const wrapper = shallow(<CostPrice {...defaultProps({ onChange })} />);
      const e = changeEvent("1'500.00");
      wrapper.instance().handleChange(e);
      expect(onChange).toHaveBeenCalledTimes(1);
      const forwardedEvent = onChange.mock.calls[0][0];
      expect(forwardedEvent.target.value).toBe('1500.00');
    });

    it('calls the parent onChange with the ORIGINAL event when nothing needs stripping (no clone)', () => {
      const onChange = jest.fn();
      const wrapper = shallow(<CostPrice {...defaultProps({ onChange })} />);
      const e = changeEvent('1500.00');
      wrapper.instance().handleChange(e);
      expect(onChange).toHaveBeenCalledTimes(1);
      // Same object reference — no spread-clone allocation when nothing to clean.
      expect(onChange.mock.calls[0][0]).toBe(e);
    });
  });

  describe('focus() — public method used by callers holding a ref', () => {
    it('invokes the underlying input.focus()', () => {
      const wrapper = shallow(<CostPrice {...defaultProps()} />);
      const instance = wrapper.instance();
      // Mock the inputRef's element with a spy-able focus method.
      instance.inputRef = { current: { focus: jest.fn() } };
      instance.focus();
      expect(instance.inputRef.current.focus).toHaveBeenCalledTimes(1);
    });

    it('invokes the onFocus prop after focusing', () => {
      const onFocus = jest.fn();
      const wrapper = shallow(<CostPrice {...defaultProps({ onFocus })} />);
      const instance = wrapper.instance();
      instance.inputRef = { current: { focus: jest.fn() } };
      instance.focus();
      expect(onFocus).toHaveBeenCalledTimes(1);
    });

    it('does not throw when inputRef.current is null (component unmounted mid-call)', () => {
      const wrapper = shallow(<CostPrice {...defaultProps()} />);
      const instance = wrapper.instance();
      instance.inputRef = { current: null };
      expect(() => instance.focus()).not.toThrow();
    });
  });
});
